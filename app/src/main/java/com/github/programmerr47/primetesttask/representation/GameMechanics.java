package com.github.programmerr47.primetesttask.representation;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.programmerr47.primetesttask.PApplication;
import com.github.programmerr47.primetesttask.background.api.objects.LevelInfo;
import com.github.programmerr47.primetesttask.background.tasks.AsyncTaskWithListener;
import com.github.programmerr47.primetesttask.background.tasks.GetLevelsFromDbAsyncTask;

import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class GameMechanics implements AsyncTaskWithListener.OnTaskFinishedListener {

    private static final String PREFS_KEY = "com.github.programmerr47.primetesttask.GAME";
    private static final String CURRENT_LEVEL_KEY = "CURRENT_LEVEL_KEY";
    private static final String CURRENT_SCORE_KEY = "CURRENT_SCORE_KEY";
    private static final String CURRENT_POINTS_PER_CLICK_KEY = "CURRENT_POINTS_PER_CLICK_KEY";

    private Context context;

    private GameMechanicsListener listener;

    private int currentLevel;
    private int currentScore;
    private int currentPointsPerClick;
    private int currentLevelPosition;

    private List<LevelInfo> levels;

    public GameMechanics() {
        context = PApplication.getAppContext();
    }

    public void onCreate() {
        restore();
    }

    public void onDestroy() {
        save();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getCurrentPointsPerClick() {
        return currentPointsPerClick;
    }

    public GameMechanicsListener getGameMechanicsListener() {
        return listener;
    }

    public void setGameMechanicsListener(GameMechanicsListener listener) {
        this.listener = listener;
    }

    public void click() {
        currentScore += currentPointsPerClick;
        checkAndUpdateLevel();
    }

    public void save() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(CURRENT_LEVEL_KEY, currentLevel);
        editor.putInt(CURRENT_SCORE_KEY, currentScore);
        editor.putInt(CURRENT_POINTS_PER_CLICK_KEY, currentPointsPerClick);
        editor.apply();
    }

    public void restore() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        currentLevel = prefs.getInt(CURRENT_LEVEL_KEY, 1);
        currentScore = prefs.getInt(CURRENT_SCORE_KEY, 0);
        currentPointsPerClick = prefs.getInt(CURRENT_POINTS_PER_CLICK_KEY, 1);
    }

    private void checkAndUpdateLevel() {
        boolean updated = false;
        if (currentLevelPosition != -1) {
            for (int i = currentLevelPosition; i < levels.size() - 1; i++) {
                LevelInfo nextLevel = levels.get(i);

                if (currentScore > nextLevel.getPointsToGain()) {
                    currentLevel = nextLevel.getLevel();
                    currentPointsPerClick = nextLevel.getPointsPerClick();
                    updated = true;
                }
            }
        }

        if (listener != null) {
            if (updated) {
                listener.onLevelInfoUpdate();
            }

            if (levels.size() != 0 && currentLevel == levels.get(levels.size() - 1).getLevel()) {
                listener.onGameFinished();
            }
        }
    }

    private static int computeCurrentLevelPosition(List<LevelInfo> levels, int currentLevel) {
        for(int i = 0; i < levels.size(); i++) {
            LevelInfo level = levels.get(i);
            if (level.getLevel() == currentLevel) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void onTaskFinished(String taskName, Object extraObject) {
        if (taskName.equals(GetLevelsFromDbAsyncTask.class.getName())) {
            levels = (List<LevelInfo>) extraObject;
            currentLevelPosition = computeCurrentLevelPosition(levels, currentLevel);

            checkAndUpdateLevel();
        }
    }

    public interface GameMechanicsListener {
        void onGameFinished();
        void onLevelInfoUpdate();
    }
}
