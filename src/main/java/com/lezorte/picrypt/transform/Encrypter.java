package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.utils.Constants;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * This class is in charge of encrypting any data coming from the provided inputStream
 * using the password. It will add any nessecary metadata it needs to
 *
 */
public class Encrypter extends OutputStream {

    private CipherOutputStream cipherOutputStream;

    public Encrypter(String password, OutputStream outputStream) {
        try {
            // Build ciphers
            Cipher cipher = Cipher.getInstance(Constants.ENCRYPTION_ALGORITHM);

            // Generate IV and salt
            SecureRandom random = new SecureRandom();
            byte[] iv = new byte[Constants.IV_LENGTH];
            byte[] salt = new byte[Constants.SALT_LENGTH];
            random.nextBytes(salt);
            random.nextBytes(iv);

            // Generate key
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Constants.KEY_GENERATION_ALGORITHM);
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 100000, 128);
            SecretKey key = keyFactory.generateSecret(pbeKeySpec);
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), Constants.ENCRYPTION_ALGORITHM.split("/")[0]);

            // Init cipher
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));

            // Add metadata to output
            outputStream.write(iv);
            outputStream.write(salt);

            // Init cipher stream
            this.cipherOutputStream = new CipherOutputStream(outputStream, cipher);

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
    }


    @Override
    public void write(int next) throws IOException {
        this.cipherOutputStream.write(next);
    }
}
