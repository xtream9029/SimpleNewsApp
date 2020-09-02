package com.example.newstest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class NewsDetailActivity extends Activity {//NewsActivity에서 넘겨준 intent를 받고 해당
                                                  // intent에 있는 정보를 활용하여 news 정보를 출력하기 위한 클래스

    private NewsData news;
    private TextView TextView_title, TextView_description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        setComp();
        getNewsDetail();
        setNews();
    }

    //기본 컴포넌트 셋팅
    public void setComp() {//xml에 있는 컴포넌트 들과 맵핑
        TextView_title = findViewById(R.id.TextView_title);
        TextView_description = findViewById(R.id.TextView_description);
    }

    //이전 액티비티에서 받아오는 인텐트
    public void getNewsDetail() {
        Intent intent = getIntent();
        if(intent != null) {
            Bundle bld = intent.getExtras();

            Object obj = bld.get("news");//인텐트에서 받아오는 값 (세부 뉴스 정보)
            if(obj != null && obj instanceof NewsData) {
                //인덴트에서 받아온 값을 NewsData로 형변환 해주는 부분
                this.news = (NewsData) obj;//클래스 형 변환(클래스: 구조체 + 함수들을 정의 해놓은 사용자 정의 type)
            }
        }
    }

    //이전 액티비티에서 받아오는 인텐트에서 정보를 확인하여 뉴스표시
    public void setNews() {
        if(this.news != null) {
            String title = this.news.getTitle();
            if(title != null) {
                TextView_title.setText(title);//실제 xml에서 타이틀을 출력
            }
            String description = this.news.getDescription();
            if(description != null) {
                //전체 본문은 url 값의 실제 뉴스 사이트에 있으며, 해당 전체 본문을 불러오기 위해서는 스크래핑 (크롤링) 기술로 읽어와야 합니다.
                TextView_description.setText(description);//실제 xml에 뉴스 상세 설명을 출력
            }
        }
    }
}