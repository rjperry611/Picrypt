package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.exceptions.VersionDoesNotExistException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by lezorte on 1/8/17.
 */
public class DecrypterInputStreamTest {

    @Test
    public void testEncryptionDecryption() {
        try {
            int totalBytes = 1000;
            int[] original = new int[totalBytes];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            EncrypterOutputStream encrypterOutputStream = EncrypterOutputStream.getInstance("password", byteArrayOutputStream);
            Random random = new Random();
            for(int i=0;i<totalBytes;i++) {
                original[i] = random.nextInt(256);
                encrypterOutputStream.write(original[i]);
            }
            encrypterOutputStream.close();
            byte[] encrypted = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(encrypted);
            DecrypterInputStream decrypterInputStream = DecrypterInputStream.getInstance("password", byteArrayInputStream);
            for(int i=0;i<totalBytes;i++) {
                assertEquals(original[i],decrypterInputStream.read());
            }


        } catch(IOException e) {
            e.printStackTrace();
            fail("Unknown IOException");
        } catch (VersionDoesNotExistException e) {
            e.printStackTrace();
            fail("Decryption unsuccessful");
        }
    }

    @Test
    public void testDecryptionWithWrongPassword() {
        try {
            int totalBytes = 1000;
            int[] original = new int[totalBytes];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            EncrypterOutputStream encrypterOutputStream = EncrypterOutputStream.getInstance("password", byteArrayOutputStream);
            Random random = new Random();
            for(int i=0;i<totalBytes;i++) {
                original[i] = random.nextInt(256);
                encrypterOutputStream.write(original[i]);
            }
            encrypterOutputStream.close();
            byte[] encrypted = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(encrypted);
            DecrypterInputStream decrypterInputStream = DecrypterInputStream.getInstance("pa1ssword", byteArrayInputStream);
            for(int i=0;i<totalBytes;i++) {
                return;
            }

            fail("Decryption with wrong password should not have been successful");

        } catch(IOException e) {
        } catch (VersionDoesNotExistException e) {
            e.printStackTrace();
            fail("Decryption unsuccessful");
        }
    }
}
