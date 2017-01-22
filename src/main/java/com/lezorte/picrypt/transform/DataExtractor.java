package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.exceptions.ImageCorruptException;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by lezorte on 12/3/16.
 */
public class DataExtractor {
    public static void seek(String imagePath, String saveFolderPath, String password) throws IOException {
        try (InputStream imageDataExtractorInputStream = new ImageDataExtractorInputStream(imagePath);
             InputStream decrypterInputStream = DecrypterInputStream.getInstance(password, imageDataExtractorInputStream)) {

            ByteArrayOutputStream pathBuilder = new ByteArrayOutputStream();
            int next;
            while((next=decrypterInputStream.read())!=0) {
                if(next==-1) {
                    throw new ImageCorruptException("Could not read save path from image. Did you enter the wrong password?");
                }
                pathBuilder.write(next);
            }
            String path = pathBuilder.toString("UTF-8");
            if(saveFolderPath.charAt(saveFolderPath.length()-1) != '/') {
                saveFolderPath += "/";
            }
            path = saveFolderPath + path;

            try (OutputStream fileOutputStream = new FileOutputStream(path)) {
                IOUtils.copy(decrypterInputStream, fileOutputStream);
            }

        }
    }
}
