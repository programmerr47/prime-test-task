package com.github.programmerr47.primetesttask.background.tasks;

import android.content.Intent;

import com.github.programmerr47.primetesttask.PApplication;
import com.github.programmerr47.primetesttask.background.api.objects.LevelInfo;
import com.github.programmerr47.primetesttask.background.db.LevelInfoDao;
import com.github.programmerr47.primetesttask.background.services.RestService;

import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2015-08-08
 */
public class GetLevelsFromDbAsyncTask extends AsyncTaskWithListener<Boolean, Void, List<LevelInfo>> {

    @Override
    protected List<LevelInfo> doInBackground(Boolean... params) {
        boolean withRemote = false;

        if (params.length > 0 && params[0] != null) {
            withRemote = params[0];
        }

        List<LevelInfo> result = LevelInfoDao.getAllLevelsInfos();

        if (withRemote) {
            Intent intent = new Intent(PApplication.getAppContext(), RestService.class);
            intent.putExtra(RestService.METHOD_NAME, RestService.GET_LEVELS_METHOD);
            PApplication.getAppContext().startService(intent);
        }

        return result;
    }
}
