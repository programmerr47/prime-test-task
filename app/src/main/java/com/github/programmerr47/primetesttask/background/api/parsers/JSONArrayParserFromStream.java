package com.github.programmerr47.primetesttask.background.api.parsers;

import android.support.annotation.NonNull;

import com.github.programmerr47.primetesttask.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2015-08-08
 */
public class JSONArrayParserFromStream<ParseResult> implements ParserFrom<List<ParseResult>, InputStream> {

    private final ParserFrom<ParseResult, JSONObject> parserFromJSON;

    public JSONArrayParserFromStream(@NonNull ParserFrom<ParseResult, JSONObject> parserFromJSON) {
        this.parserFromJSON = parserFromJSON;
    }

    @Override
    public List<ParseResult> parse(InputStream object) {
        String resultString = Utils.covertInputStreamToString(object);
        JSONArray array = new JSONArray();
        try {
            array = new JSONArray(resultString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parseList(array);
    }

    private List<ParseResult> parseList(JSONArray array) {
        List<ParseResult> resultList = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject resultJSONObject = array.optJSONObject(i);
            ParseResult resultObject = parserFromJSON.parse(resultJSONObject != null ? resultJSONObject : new JSONObject());
            resultList.add(resultObject);
        }

        return resultList;
    }
}