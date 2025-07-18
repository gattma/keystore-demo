package com.example.rest.model;

import java.util.Date;

public record KeystoreMetaInfos(String subject, String issuer, String serialNumber, Date validFrom, Date validUntil) {
}
