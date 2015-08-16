package com.github.programmerr47.primetesttask.background.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.github.programmerr47.primetesttask.background.api.facades.LevelFacade;
import com.github.programmerr47.primetesttask.background.api.objects.LevelInfo;
import com.github.programmerr47.primetesttask.background.db.LevelInfoDao;
import com.github.programmerr47.primetesttask.util.AndroidUtils;

import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class RestService extends IntentService {

    public static final String METHOD_NAME = "RestService.METHOD_NAME";

    public static final String GET_LEVELS_METHOD = "RestService.GET_LEVELS_METHOD";

    public static final String LEVELS_RESULT_BROADCAST = "RestService.LEVELS_RESULT_BROADCAST";

    public RestService() {
        this("RestService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RestService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String methodName = intent.getStringExtra(METHOD_NAME);

        switch (methodName) {
            case GET_LEVELS_METHOD:
                executeGetLevelsMethod();
                return;
            default:
        }
    }

    private void executeGetLevelsMethod() {
        if (AndroidUtils.isNetworkConnected()) {
            List<LevelInfo> levelInfos = LevelFacade.getLevels();
            LevelInfoDao.saveLevels(levelInfos);
        }

        Intent intent = new Intent(LEVELS_RESULT_BROADCAST);
        //TODO add status result (successful or not)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
