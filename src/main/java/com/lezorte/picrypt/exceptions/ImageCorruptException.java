package com.lezorte.picrypt.exceptions;

import java.io.IOException;

/**
 * Created by lezorte on 12/31/16.
 */
public class ImageCorruptException extends IOException{

    public ImageCorruptException(String s) {
        super(s);
    }
}
