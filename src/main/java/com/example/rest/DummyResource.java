package com.example.rest;


import com.example.rest.model.KeystoreInfo;
import com.example.rest.model.KeystoreInfos;
import com.example.rest.model.KeystoreMetaInfos;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

@Path("/keystore")
public class DummyResource {

    @Inject
    @ConfigProperty(name = "keystore.path")
    String KEYSTORE_PATH;

    @Inject
    @ConfigProperty(name = "keystore.password")
    String KEYSTORE_PW;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public KeystoreInfos getDummyData() {
        var keystoreinfos = parseKeystore(KEYSTORE_PATH, KEYSTORE_PW);
        if(keystoreinfos == null) {
            throw new WebApplicationException("Error parsing keystore", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return keystoreinfos;
    }

    private KeystoreInfos parseKeystore(String keystorePath, String keystorePW) {
        var keystoreinfos = new ArrayList<KeystoreInfo>();
        char[] keystorePassword = keystorePW.toCharArray();


        try (InputStream is = new FileInputStream(keystorePath)) {
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(is, keystorePassword);

            // Iterate through all aliases
            Enumeration<String> aliases = keystore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                var metainfos = parseMetainfos(keystore.getCertificateChain(alias));
                var keystoreinfo = new KeystoreInfo(alias, metainfos, keystore.getCreationDate(alias));
                keystoreinfos.add(keystoreinfo);
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            System.err.println("Error accessing keystore: " + e.getMessage());
            return null;
        }

        return keystoreinfos.isEmpty() ? null : new KeystoreInfos(keystoreinfos);
    }

    private KeystoreMetaInfos parseMetainfos(Certificate[] chainCerts) {
        if (chainCerts != null) {
            X509Certificate cert = (X509Certificate) chainCerts[0];
            return new KeystoreMetaInfos(cert.getSubjectX500Principal().getName(), cert.getIssuerX500Principal().getName(), cert.getSerialNumber().toString(), cert.getNotBefore(), cert.getNotAfter());
        }
        return null;
    }
}