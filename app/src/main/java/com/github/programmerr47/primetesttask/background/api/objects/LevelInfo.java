package com.github.programmerr47.primetesttask.background.api.objects;

/**
 * @author Michael Spitsin
 * @since 2015-08-16
 */
public class LevelInfo {

    private final int level;
    private final int pointsToGain;
    private final int pointsPerClick;

    private LevelInfo(Builder builder) {
        this.level = builder.level;
        this.pointsToGain = builder.pointsToGain;
        this.pointsPerClick = builder.pointsPerClick;
    }

    public int getLevel() {
        return level;
    }

    public int getPointsToGain() {
        return pointsToGain;
    }

    public int getPointsPerClick() {
        return pointsPerClick;
    }

    public static class Builder {
        private int level;
        private int pointsToGain;
        private int pointsPerClick;

        public Builder setLevel(int level) {
            this.level = level;
            return this;
        }

        public Builder setPointsToGain(int pointsToGain) {
            this.pointsToGain = pointsToGain;
            return this;
        }

        public Builder setPointsPerClick(int pointsPerClick) {
            this.pointsPerClick = pointsPerClick;
            return this;
        }

        public LevelInfo build() {
            return new LevelInfo(this);
        }
    }
}
