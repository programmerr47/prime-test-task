package com.github.programmerr47.primetesttask.representation.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.programmerr47.primetesttask.PApplication;
import com.github.programmerr47.primetesttask.R;
import com.github.programmerr47.primetesttask.background.api.objects.LevelInfo;
import com.github.programmerr47.primetesttask.background.services.RestService;
import com.github.programmerr47.primetesttask.background.tasks.AsyncTaskWithListener;
import com.github.programmerr47.primetesttask.background.tasks.GetLevelItemsFromDbAsync;
import com.github.programmerr47.primetesttask.background.tasks.GetLevelsFromDbAsyncTask;
import com.github.programmerr47.primetesttask.representation.adapters.LevelAdapter;
import com.github.programmerr47.primetesttask.representation.adapters.items.LevelInfoItem;
import com.github.programmerr47.primetesttask.representation.callback.RecyclerViewFirstItemFinder;
import com.github.programmerr47.primetesttask.representation.callback.impl.ToolbarHideScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class LevelListFragment extends Fragment implements AsyncTaskWithListener.OnTaskFinishedListener {

    private Toolbar toolbar;
    private RecyclerView levelListView;
    private List<LevelInfoItem> levels;
    private LevelAdapter levelAdapter;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RestService.LEVELS_RESULT_BROADCAST)) {
                GetLevelItemsFromDbAsync getLevelItemsFromDbAsync = new GetLevelItemsFromDbAsync();
                getLevelItemsFromDbAsync.setOnTaskFinishedListener(LevelListFragment.this);
                getLevelItemsFromDbAsync.execute();
            }
        }
    };

    private ToolbarHideScrollListener scrollListener = new ToolbarHideScrollListener(new RecyclerViewFirstItemFinder() {
        @Override
        public int findFirstVisibleItemPosition(RecyclerView recyclerView) {
            return ((LinearLayoutManager) levelListView.getLayoutManager()).findFirstVisibleItemPosition();
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        levelAdapter = new LevelAdapter(new ArrayList<LevelInfoItem>());
        GetLevelItemsFromDbAsync getLevelItemsFromDbAsync = new GetLevelItemsFromDbAsync();
        getLevelItemsFromDbAsync.setOnTaskFinishedListener(this);
        getLevelItemsFromDbAsync.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_level_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        levelListView = (RecyclerView) view.findViewById(R.id.levels);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar.setTitle(PApplication.getAppContext().getString(R.string.level_list));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scrollListener.addView(toolbar);

        levelListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        levelListView.setAdapter(levelAdapter);
        levelListView.addOnScrollListener(scrollListener);
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
    public void onTaskFinished(String taskName, Object extraObject) {
        if (taskName.equals(GetLevelItemsFromDbAsync.class.getName())) {
            levels = (List<LevelInfoItem>) extraObject;
            levelAdapter.updateLevels(levels);
        }
    }
}
