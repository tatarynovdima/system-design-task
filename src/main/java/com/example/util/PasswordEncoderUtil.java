package com.example.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordEncoderUtil {
    private static final Integer HASH_ITERATIONS = 2;
    private static final Integer HASH_MEMORY = 15 * 1024;
    private static final Integer HASH_PARALLELISM = 1;
    private static final Integer SALT_LENGTH = 32;
    private static final Integer HASH_LENGTH = 32;
    public static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, SALT_LENGTH, HASH_LENGTH);

    public static String securePassword(String password) {
        return argon2.hash(HASH_ITERATIONS, HASH_MEMORY, HASH_PARALLELISM, password.toCharArray());
    }

    public static boolean validatePassword(String hash, String userEnterPassword) {
        return argon2.verify(hash, userEnterPassword.toCharArray());
    }
}
