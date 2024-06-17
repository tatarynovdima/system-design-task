package com.example.util;

import com.example.entity.type.Role;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

public class TokenUtils {

    public static String generateToken(String userName, Set<Role> roles, Long duration, String issuer) throws Exception {
        String privateKeyLocation = "/privatekey.pem";
        PrivateKey privateKey = readPrivateKey(privateKeyLocation);

        JwtClaimsBuilder claimsBuilder = Jwt.claims();

        long currentTimeInSecs = currentTimeInSecs();

        Set<String> groups = new HashSet<>();
        for (Role role : roles) {
            groups.add(role.toString());
        }

        claimsBuilder.issuer(issuer);
        claimsBuilder.subject(userName);
        claimsBuilder.issuedAt(currentTimeInSecs);
        claimsBuilder.expiresAt(currentTimeInSecs + duration);
        claimsBuilder.groups(groups);

        return claimsBuilder.jws().signatureKeyId(privateKeyLocation).sign(privateKey);
    }

    private static PrivateKey readPrivateKey(String pemResName) throws Exception {
        try (InputStream contentIs = TokenUtils.class.getResourceAsStream(pemResName)) {
            if (contentIs == null) {
                throw new IllegalArgumentException("Private key file not found: " + pemResName);
            }
            byte[] tmp = new byte[4096];
            int length = contentIs.read(tmp);
            if (length <= 0) {
                throw new IllegalArgumentException("Empty private key file: " + pemResName);
            }
            return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
        }
    }

    private static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)----", "")
                .replaceAll("\r\n", "")
                .replaceAll("\n", "");
        return pem.trim();
    }

    private static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }
}
