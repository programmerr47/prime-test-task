package com.github.programmerr47.primetesttask.background.api.methods;

import android.support.annotation.NonNull;

import com.github.programmerr47.primetesttask.background.api.parsers.ParserFrom;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Michael Spitsin
 * @since 2015-08-08
 */
public class ApiGetMethod<Response> implements ApiMethod<Response> {

    private String url;
    private ParserFrom<Response, InputStream> resultParser;

    @Override
    public Response execute() {
        if (url != null) {
            try {
                URL url = new URL(this.url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
                if (connection.getResponseCode() == 200) {
                    InputStream responseStream = connection.getInputStream();

                    if (responseStream != null) {
                        Response response = resultParser.parse(responseStream);
                        responseStream.close();
                        return response;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public ApiGetMethod<Response> setUrl(@NonNull String url) {
        this.url = url;
        return this;
    }

    public ApiGetMethod<Response> setMethodResultParser(@NonNull ParserFrom<Response, InputStream> parser) {
        resultParser = parser;
        return this;
    }
}
