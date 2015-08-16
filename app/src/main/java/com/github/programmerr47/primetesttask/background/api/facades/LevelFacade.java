package com.github.programmerr47.primetesttask.background.api.facades;

import com.github.programmerr47.primetesttask.background.api.methods.ApiGetMethod;
import com.github.programmerr47.primetesttask.background.api.methods.ApiMethod;
import com.github.programmerr47.primetesttask.background.api.objects.GitHubFile;
import com.github.programmerr47.primetesttask.background.api.objects.LevelInfo;
import com.github.programmerr47.primetesttask.background.api.parsers.JSONArrayParserFromStream;
import com.github.programmerr47.primetesttask.background.api.parsers.json.GitHubFileParserFromJSON;
import com.github.programmerr47.primetesttask.background.api.parsers.json.LevelInfoParserFromJSON;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class LevelFacade {

    private static final String API_URL = "https://api.github.com";
    private static final String GET_CONTENTS_METHOD = "/repos/programmerr47/prime-test-task/contents";
    private static final String REMOTE_FILE_NAME = "table-content.json";

    public static List<LevelInfo> getLevels() {
        GitHubFile levelsFile = getRootGitFileFromName(REMOTE_FILE_NAME);

        ApiMethod<List<LevelInfo>> method = new ApiGetMethod<List<LevelInfo>>()
                .setUrl(levelsFile.getDownloadUrl())
                .setMethodResultParser(new JSONArrayParserFromStream<>(
                        new LevelInfoParserFromJSON()
                ));

        List<LevelInfo> levels = method.execute();
        //todo
        return levels;
    }

    private static GitHubFile getRootGitFileFromName(String fileName) {
        ApiMethod<List<GitHubFile>> method = new ApiGetMethod<List<GitHubFile>>()
                .setUrl(API_URL + GET_CONTENTS_METHOD)
                .setMethodResultParser(new JSONArrayParserFromStream<>(
                        new GitHubFileParserFromJSON()
                ));

        List<GitHubFile> files = method.execute();
        GitHubFile requiredFile = getFromIterable(files, fileName);

        if (requiredFile == null) {
            throw new IllegalStateException("Required file with name: " + fileName + " must be found. Please check repo name");
        }

        return requiredFile;
    }

    private static GitHubFile getFromIterable(Iterable<GitHubFile> files, String requestName) {
        for (GitHubFile file : files) {
            if (requestName != null && requestName.equals(file.getName())) {
                return file;
            }
        }

        return null;
    }
}
