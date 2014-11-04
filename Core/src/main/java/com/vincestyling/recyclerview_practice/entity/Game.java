package com.vincestyling.recyclerview_practice.entity;

import java.util.List;

public class Game {
    private int id;
    private String name;
    private String logoUrl;
    private int readCount;
    private int videoCount;
    private int recentAddedCount;
    private List<Video> videoList;
    private boolean isFavorite;

    public Game() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getRecentAddedCount() {
        return recentAddedCount;
    }

    public void setRecentAddedCount(int recentAddedCount) {
        this.recentAddedCount = recentAddedCount;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
