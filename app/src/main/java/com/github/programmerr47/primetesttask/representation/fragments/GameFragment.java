package com.github.programmerr47.primetesttask.representation.fragments;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.programmerr47.primetesttask.PApplication;
import com.github.programmerr47.primetesttask.R;
import com.github.programmerr47.primetesttask.background.services.RestService;
import com.github.programmerr47.primetesttask.background.tasks.GetLevelsFromDbAsyncTask;
import com.github.programmerr47.primetesttask.representation.AnimationListener;
import com.github.programmerr47.primetesttask.representation.GameMechanics;
import com.github.programmerr47.primetesttask.representation.activities.LevelListActivity;
import com.github.programmerr47.primetesttask.representation.views.CustomFontTextView;
import com.github.programmerr47.primetesttask.util.AndroidUtils;
import com.github.programmerr47.primetesttask.util.AnimationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Michael Spitsin
 * @since 2015-08-16
 */
public class GameFragment extends Fragment implements
        GameMechanics.GameMechanicsListener,
        View.OnClickListener,
        AnimationListener {

    private FrameLayout gameScene;
    private Button infoButton;
    private Button scoreButton;
    private TextView scoreView;
    private TextView levelView;

    private GameMechanics mechanics;

    private int screenWidthPixels;
    private boolean won;

    private List<TextView> labels = new ArrayList<>();

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
        gameScene = (FrameLayout) view.findViewById(R.id.game_scene);
        infoButton = (Button) view.findViewById(R.id.info_button);
        scoreButton = (Button) view.findViewById(R.id.score_button);
        scoreView = (TextView) view.findViewById(R.id.score);
        levelView = (TextView) view.findViewById(R.id.level);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mechanics.onCreate();

        screenWidthPixels = AndroidUtils.getScreenWidth(getActivity());

        scoreButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);
        updateViews();

        if (won) {
            showWinDialog();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(RestService.LEVELS_RESULT_BROADCAST);
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mechanics.onDestroy();
    }

    @Override
    public void onGameFinished() {
        won = true;
        if (getActivity() != null) {
            showWinDialog();
        }
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
                mechanics.save();
                createNewLabel();
                break;
            case R.id.info_button:
                Intent intent = new Intent(getActivity(), LevelListActivity.class);
                getActivity().startActivity(intent);
                break;
            default:
        }
    }

    private void showWinDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.win_title)
                .setMessage(R.string.win_message)
                .setPositiveButton(R.string.again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mechanics.refresh();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void updateViews() {
        scoreButton.setText("+" + mechanics.getCurrentPointsPerClick());
        scoreView.setText("" + mechanics.getCurrentScore());
        levelView.setText("" + mechanics.getCurrentLevel());
    }

    private void createNewLabel() {
        String text = "+" + mechanics.getCurrentPointsPerClick();

        Random random = new Random();
        int offset = getPixelsOffsetForText(text);
        int randomX = random.nextInt((screenWidthPixels - offset) + 1);
        int randomAnimationDuration = random.nextInt(1200 - 300 + 1) + 300;

        CustomFontTextView view = new CustomFontTextView(getActivity());
        view.setFontName("Roboto-Bold.ttf");
        view.setText(text);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        view.setLayoutParams(params);
        view.setTextColor(PApplication.getAppContext().getResources().getColor(R.color.accent));
        view.setTextSize(AndroidUtils.dpToPx(22));
        view.setX(randomX);

        labels.add(view);
        gameScene.addView(view);
        AnimationUtils.hideView(view, this, randomAnimationDuration);
    }

    //TODO temp solution. need to think about more elegant way
    private static int getPixelsOffsetForText(String text) {
        return (int)(text.length() * AndroidUtils.dpToPx(22));
    }

    @Override
    public void onAnimationEnd(View animatedView, Animator animator) {
        gameScene.removeView(animatedView);
    }
}
