package com.github.programmerr47.primetesttask.representation.adapters.items;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.programmerr47.primetesttask.R;
import com.github.programmerr47.primetesttask.background.api.objects.LevelInfo;
import com.github.programmerr47.primetesttask.representation.adapters.holders.LevelInfoHolder;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class LevelInfoItem {

    private LevelInfo levelInfo;

    public static LevelInfoItem createInstance(@NonNull LevelInfo levelInfo) {
        LevelInfoItem result = new LevelInfoItem();
        result.levelInfo = levelInfo;
        return result;
    }

    public void bindView(LevelInfoHolder holder, @SuppressWarnings("unused") int position) {
        holder.getLevelView().setText("" + levelInfo.getLevel());
        holder.getStgView().setText("" + levelInfo.getPointsToGain());
        holder.getSpcView().setText("" + levelInfo.getPointsPerClick());
    }

    public static LevelInfoHolder produce(ViewGroup parentView) {
        LayoutInflater layoutInflater = LayoutInflater.from(parentView.getContext());
        View view = layoutInflater.inflate(R.layout.level_list_item, parentView, false);

        if (view == null) {
            throw new IllegalStateException("View not created");
        }

//        ViewGroup.LayoutParams params = view.getLayoutParams();
//        params.width = dimension;
//        params.height = dimension;

        LevelInfoHolder.ResourceParams params = new LevelInfoHolder.ResourceParams();
        params.levelId = R.id.level_info;
        params.stgId = R.id.stg_info;
        params.spcId = R.id.spc_info;

        return new LevelInfoHolder(view, params);
    }


}
