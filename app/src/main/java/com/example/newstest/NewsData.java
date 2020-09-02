package com.example.newstest;

import java.io.Serializable;

public class NewsData implements Serializable {
    //직접접근해서 임의로 값을 바꾸지 못하게 하기위해 private로 선언
    private String title;
    private String urlToImage;
    private String description;//뉴스 본문 내용

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
