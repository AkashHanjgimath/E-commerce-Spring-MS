package com.ecom.auth_service;

import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

public class JwtSecurityGenerator {

    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Base64-encoded key: " + base64Key);
    }
}

