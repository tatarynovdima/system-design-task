package com.example.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import jakarta.inject.Singleton;

@Singleton
public class PasswordEncoderUtil {

    private static final int HASH_ITERATIONS = 2;
    private static final int HASH_MEMORY = 15 * 1024; // 15 MB memory cost
    private static final int HASH_PARALLELISM = 1;   // 1 parallelism (threads)
    private static final int SALT_LENGTH = 32;       // 32 bytes salt length
    private static final int HASH_LENGTH = 32;       // 32 bytes hash length

    private static final Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id, SALT_LENGTH, HASH_LENGTH);

    /**
     * Securely hashes a password using Argon2.
     *
     * @param password the password to hash
     * @return the hashed password as a String
     */
    public static String securePassword(String password) {
        return argon2.hash(HASH_ITERATIONS, HASH_MEMORY, HASH_PARALLELISM, password.toCharArray());
    }

    /**
     * Validates a password against a hashed password using Argon2.
     *
     * @param hash             the previously hashed password
     * @param userEnterPassword the password entered by the user
     * @return true if the passwords match, false otherwise
     */
    public static boolean validatePassword(String hash, String userEnterPassword) {
        return argon2.verify(hash, userEnterPassword.toCharArray());
    }
}
