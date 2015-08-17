package com.github.programmerr47.primetesttask.background.tasks;

import android.content.Intent;

import com.github.programmerr47.primetesttask.PApplication;
import com.github.programmerr47.primetesttask.background.api.objects.LevelInfo;
import com.github.programmerr47.primetesttask.background.db.LevelInfoDao;
import com.github.programmerr47.primetesttask.background.services.RestService;
import com.github.programmerr47.primetesttask.representation.adapters.items.LevelInfoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class GetLevelItemsFromDbAsync extends AsyncTaskWithListener<Void, Void, List<LevelInfoItem>> {

    @Override
    protected List<LevelInfoItem> doInBackground(Void... params) {
        List<LevelInfo> result = LevelInfoDao.getAllLevelsInfos();
        List<LevelInfoItem> items = new ArrayList<>();

        for (LevelInfo levelInfo : result) {
            items.add(LevelInfoItem.createInstance(levelInfo));
        }

        return items;
    }
}
