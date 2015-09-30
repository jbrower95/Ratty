package com.jbrower.ratty.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Some useful network functions.
 */
public final class NetworkUtils {
    public static String consumeInputStream(InputStream inputStream){
        try {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;
        } catch (Exception e){
            return null;
        }
    }
}
