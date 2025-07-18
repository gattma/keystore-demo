package com.example.rest.model;

import java.util.Date;

public record KeystoreInfo(String alias, KeystoreMetaInfos metaInfos, Date creationDate) {
}
