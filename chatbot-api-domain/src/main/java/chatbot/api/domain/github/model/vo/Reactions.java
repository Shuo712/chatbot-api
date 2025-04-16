package chatbot.api.domain.github.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reactions {
    private String url;
    private int totalCount;

    @JsonProperty("+1")
    private int plusOne;

    @JsonProperty("-1")
    private int minusOne;

    private int laugh;
    private int hooray;
    private int confused;
    private int heart;
    private int rocket;
    private int eyes;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPlusOne() {
        return plusOne;
    }

    public void setPlusOne(int plusOne) {
        this.plusOne = plusOne;
    }

    public int getMinusOne() {
        return minusOne;
    }

    public void setMinusOne(int minusOne) {
        this.minusOne = minusOne;
    }

    public int getLaugh() {
        return laugh;
    }

    public void setLaugh(int laugh) {
        this.laugh = laugh;
    }

    public int getHooray() {
        return hooray;
    }

    public void setHooray(int hooray) {
        this.hooray = hooray;
    }

    public int getConfused() {
        return confused;
    }

    public void setConfused(int confused) {
        this.confused = confused;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getRocket() {
        return rocket;
    }

    public void setRocket(int rocket) {
        this.rocket = rocket;
    }

    public int getEyes() {
        return eyes;
    }

    public void setEyes(int eyes) {
        this.eyes = eyes;
    }
}