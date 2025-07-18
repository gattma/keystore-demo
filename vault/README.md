# CREATE NEW CLUSTER
kind create cluster --name liwest-playground

# INSTALL EXTERNAL SECRETS OPERATOR (https://external-secrets.io/latest/introduction/getting-started/)
helm repo add external-secrets https://charts.external-secrets.io
helm install external-secrets \
external-secrets/external-secrets \
-n external-secrets \
--create-namespace

# INSTALL AND SETUP HASHICORP VAULT (https://developer.hashicorp.com/vault/tutorials/kubernetes-introduction/kubernetes-minikube-raft)
## Initial Installation
helm repo add hashicorp https://helm.releases.hashicorp.com
helm repo update
helm install vault hashicorp/vault --values helm-vault-raft-values.yml -n hc-vault --create-namespace
kubectl exec vault-0 -- vault operator init \
-key-shares=1 \
-key-threshold=1 \
-format=json > cluster-keys.json
VAULT_UNSEAL_KEY=$(jq -r ".unseal_keys_b64[]" cluster-keys.json)
kubectl exec vault-0 -- vault operator unseal $VAULT_UNSEAL_KEY
kubectl exec -ti vault-1 -- vault operator raft join http://vault-0.vault-internal:8200
kubectl exec -ti vault-2 -- vault operator raft join http://vault-0.vault-internal:8200
kubectl exec -ti vault-1 -- vault operator unseal $VAULT_UNSEAL_KEY

## Create a secret in Vault
jq -r ".root_token" cluster-keys.json
kubectl exec --stdin=true --tty=true vault-0 -- /bin/sh
vault login
vault secrets enable -path=secret kv-v2
vault kv put secret/test/cas/config username="static-user" password="static-password"
vault kv get secret/test/cas/config
exit

## Configure Kubernetes authentication
kubectl exec --stdin=true --tty=true vault-0 -- /bin/sh
vault auth enable kubernetes
vault write auth/kubernetes/config \
kubernetes_host="https://$KUBERNETES_PORT_443_TCP_ADDR:443"
vault policy write webapp - <<EOF
path "secret/data/test/cas/config" {
capabilities = ["read"]
}
EOF
vault write auth/kubernetes/role/webapp \
bound_service_account_names=vault \
bound_service_account_namespaces=default \
policies=webapp \
ttl=24h
exit


# CONFIGURE ESO FOR VAULT (https://external-secrets.io/latest/provider/hashicorp-vault/)%