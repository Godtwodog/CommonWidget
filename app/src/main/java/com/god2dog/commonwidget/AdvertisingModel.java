package com.god2dog.commonwidget;

/**
 * @author god2dog
 * 版本：1.0
 * 创建日期：2020/4/23
 * 描述：CommonWidget
 */
public class AdvertisingModel {
    private String title;
    private boolean isPlay;
    private String url;
    private int imageRes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
