package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.utils.Debug;
import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.util.Random;

/**
 * Created by lezorte on 12/3/16.
 */
public class Hider {


    public static void hide(String filePath, String imagePath, String savePath, String password) {
        File file = new File(filePath);
        File imageFile = new File(imagePath);
        if(!imageFile.exists()){
            System.err.println("Image file does not exist");
        }
        try(FileInputStream inputStream = new FileInputStream(file)) {
            int next;
            while((next = inputStream.read()) != -1) {

            }
        } catch (FileNotFoundException e) {
            System.err.println("File to hide does not exist");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        String s = "sup";
        try {
            long time = System.currentTimeMillis();
//            ByteArrayInputStream stream = new ByteArrayInputStream(s.getBytes("UTF-8"));
            RandomInputStream stream = new RandomInputStream(1_000_000_000L);
//            Encrypter encrypter = new Encrypter("pasdf2uin#$adsof8329834*)&)SDFF)J38r3**HH@ss", stream);
//            ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            int next;
            while((next = stream.read())!=-1) {
//                oStream.write(next);
            }
//            System.out.println(oStream.toByteArray().length);
//            System.out.println("Done");
            System.out.println(System.currentTimeMillis() - time);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class RandomInputStream extends InputStream {
        private long length;
        private long index;
        Random random;

        public RandomInputStream(long length) {
            this.length = length;
            random = new Random();
            index = 0;
        }

        @Override
        public int read() throws IOException {
            index++;
            if(index-1<length) {
                return random.nextInt(255);
            }
            return -1;
        }
    }
}
