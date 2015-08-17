package com.github.programmerr47.primetesttask.representation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.github.programmerr47.primetesttask.representation.adapters.holders.LevelInfoHolder;
import com.github.programmerr47.primetesttask.representation.adapters.items.LevelInfoItem;

import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class LevelAdapter extends RecyclerView.Adapter<LevelInfoHolder> {

    private List<LevelInfoItem> levels;

    public LevelAdapter(List<LevelInfoItem> levels) {
        this.levels = levels;
    }

    @Override
    public LevelInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return LevelInfoItem.produce(parent);
    }

    @Override
    public void onBindViewHolder(LevelInfoHolder holder, final int position) {
        levels.get(position).bindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public void updateLevels(List<LevelInfoItem> newLevels) {
        if (levels.size() == 0 && newLevels.size() != 0) {
            levels = newLevels;
            notifyItemRangeInserted(0, newLevels.size());
        } else if (levels.size() != 0) {
            levels = newLevels;
            notifyDataSetChanged();
        }
    }
}
