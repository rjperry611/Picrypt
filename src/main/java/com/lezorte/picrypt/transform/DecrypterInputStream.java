package com.lezorte.picrypt.transform;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * This class is in charge of encrypting any data coming from the provided inputStream
 * using the password. It will add any nessecary metadata it needs to
 *
 */
public class DecrypterInputStream extends FilterInputStream {

    public static DecrypterInputStream getInstance(String password, InputStream inputStream) {
        try {
            // Build ciphers
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            // Generate IV and salt
            byte[] iv = new byte[16];
            byte[] salt = new byte[64];
            inputStream.read(iv);
            inputStream.read(salt);

            // Generate key
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 100000, 128);
            SecretKey key = keyFactory.generateSecret(pbeKeySpec);
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

            // Init cipher
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));

            // Init cipher stream
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            return new DecrypterInputStream(cipherInputStream);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DecrypterInputStream(CipherInputStream inputStream) {
        super(inputStream);
    }

}
