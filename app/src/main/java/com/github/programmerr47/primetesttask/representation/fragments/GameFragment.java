package com.github.programmerr47.primetesttask.representation.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.programmerr47.primetesttask.R;
import com.github.programmerr47.primetesttask.background.services.RestService;
import com.github.programmerr47.primetesttask.background.tasks.GetLevelsFromDbAsyncTask;
import com.github.programmerr47.primetesttask.representation.GameMechanics;
import com.github.programmerr47.primetesttask.util.AndroidUtils;

/**
 * @author Michael Spitsin
 * @since 2015-08-16
 */
public class GameFragment extends Fragment implements GameMechanics.GameMechanicsListener, View.OnClickListener {

    private Button infoButton;
    private Button scoreButton;
    private TextView scoreView;
    private TextView levelView;

    private GameMechanics mechanics;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RestService.LEVELS_RESULT_BROADCAST)) {
                GetLevelsFromDbAsyncTask getLevelsFromDbAsyncTask = new GetLevelsFromDbAsyncTask();
                getLevelsFromDbAsyncTask.setOnTaskFinishedListener(mechanics);
                getLevelsFromDbAsyncTask.execute(false);
            }
        }
    };

    public GameFragment() {
        mechanics = new GameMechanics();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mechanics.onCreate();
        mechanics.setGameMechanicsListener(this);

        GetLevelsFromDbAsyncTask getLevelsFromDbAsyncTask = new GetLevelsFromDbAsyncTask();
        getLevelsFromDbAsyncTask.setOnTaskFinishedListener(mechanics);
        getLevelsFromDbAsyncTask.execute(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        infoButton = (Button) view.findViewById(R.id.info_button);
        scoreButton = (Button) view.findViewById(R.id.score_button);
        scoreView = (TextView) view.findViewById(R.id.score);
        levelView = (TextView) view.findViewById(R.id.level);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        scoreButton.setOnClickListener(this);

        updateViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(RestService.LEVELS_RESULT_BROADCAST);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mechanics.onDestroy();
    }

    @Override
    public void onGameFinished() {

    }

    @Override
    public void onLevelInfoUpdate() {
        updateViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.score_button:
                mechanics.click();
                break;
            case R.id.info_button:
                break;
            default:
        }
    }

    private void updateViews() {
        scoreButton.setText("+" + mechanics.getCurrentPointsPerClick());
        scoreView.setText("" + mechanics.getCurrentScore());
        levelView.setText("" + mechanics.getCurrentLevel());
    }
}
