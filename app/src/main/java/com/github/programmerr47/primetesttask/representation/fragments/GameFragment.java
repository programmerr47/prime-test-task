package com.github.programmerr47.primetesttask.representation.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.programmerr47.primetesttask.R;

/**
 * @author Michael Spitsin
 * @since 2015-08-16
 */
public class GameFragment extends Fragment {

    private Button infoButton;
    private Button scoreButton;
    private TextView scoreView;
    private TextView levelView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
    }
}
