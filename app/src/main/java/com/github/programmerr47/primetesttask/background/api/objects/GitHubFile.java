package com.github.programmerr47.primetesttask.background.api.objects;

/**
 * @author Michael Spitsin
 * @since 2015-08-16
 */
public class GitHubFile {

    private final String name;
    private final String downloadUrl;

    private GitHubFile(Builder builder) {
        this.name = builder.name;
        this.downloadUrl = builder.downloadUrl;
    }

    public String getName() {
        return name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public static class Builder {
        private String name;
        private String downloadUrl;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDownloadUrl(String name) {
            this.name = name;
            return this;
        }

        public GitHubFile build() {
            return new GitHubFile(this);
        }
    }
}
