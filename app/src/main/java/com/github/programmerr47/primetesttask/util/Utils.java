package com.github.programmerr47.primetesttask.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * @author Michael Spitsin
 * @since 2015-08-08
 */
public class Utils {

    /**
     * Converts given input stream (<strong>without</strong> closing it) to {@link String}.
     *
     * @param is given input stream
     * @return converted stream to string
     */
    public static String covertInputStreamToString(InputStream is) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        String result = "";

        try {
            while((line = bufferedReader.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //ignored
        }

        return result;
    }
}
