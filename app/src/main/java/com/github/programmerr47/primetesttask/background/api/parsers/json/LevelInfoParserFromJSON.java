package com.github.programmerr47.primetesttask.background.api.parsers.json;

import com.github.programmerr47.primetesttask.background.api.objects.LevelInfo;
import com.github.programmerr47.primetesttask.background.api.parsers.ParserFrom;

import org.json.JSONObject;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class LevelInfoParserFromJSON implements ParserFrom<LevelInfo, JSONObject> {

    private static final String LEVEL_TAG = "level";
    private static final String POINTS_TO_GAIN_TAG = "points_to_gain";
    private static final String POINTS_PER_CLICK_TAG = "points_per_click";

    @Override
    public LevelInfo parse(JSONObject object) {
        return new LevelInfo.Builder()
                .setLevel(object.optInt(LEVEL_TAG))
                .setPointsToGain(object.optInt(POINTS_TO_GAIN_TAG))
                .setPointsPerClick(object.optInt(POINTS_PER_CLICK_TAG))
                .build();
    }
}
