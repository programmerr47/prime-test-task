package com.github.programmerr47.primetesttask.representation.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class LevelInfoHolder extends RecyclerView.ViewHolder {

    private final TextView levelView;
    private final TextView stgView;
    private final TextView spcView;

    public LevelInfoHolder(View itemView, ResourceParams params) {
        super(itemView);

        levelView = (TextView) itemView.findViewById(params.levelId);
        stgView = (TextView) itemView.findViewById(params.stgId);
        spcView = (TextView) itemView.findViewById(params.spcId);
    }

    public TextView getLevelView() {
        return levelView;
    }

    public TextView getStgView() {
        return stgView;
    }

    public TextView getSpcView() {
        return spcView;
    }

    public static final class ResourceParams {
        public int levelId;
        public int stgId;
        public int spcId;
    }
}
