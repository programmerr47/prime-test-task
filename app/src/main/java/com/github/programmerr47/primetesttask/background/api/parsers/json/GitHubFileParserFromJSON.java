package com.github.programmerr47.primetesttask.background.api.parsers.json;

import com.github.programmerr47.primetesttask.background.api.objects.GitHubFile;
import com.github.programmerr47.primetesttask.background.api.parsers.ParserFrom;

import org.json.JSONObject;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class GitHubFileParserFromJSON implements ParserFrom<GitHubFile, JSONObject> {

    private static final String NAME_TAG = "name";
    private static final String DOWNLOAD_URL_TAG = "download_url";

    @Override
    public GitHubFile parse(JSONObject object) {
        return new GitHubFile.Builder()
                .setName(object.optString(NAME_TAG))
                .setDownloadUrl(object.optString(DOWNLOAD_URL_TAG))
                .build();
    }
}
