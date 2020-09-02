package com.example.newstest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//데이터를 서버로 몽창 받아와서 어댑터로 정보를 넘겨줌

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;//뷰 홀더 객체를 관리
    private RecyclerView.LayoutManager layoutManager;
   // private List<NewsData> myDataset={"1","2"};//몽땅 받아와서 어댑터에 줌
    RequestQueue queue ;//Request queue 초기화

    @Override
    protected void onCreate(Bundle savedInstanceState) {//메인이 되는 함수
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);//recyclerView 리스트 화면을 띄워놓음
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);//레이아웃 맵핑

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        queue=Volley.newRequestQueue(this);
        getNews();

        //1.화면이 로딩되면 뉴스정보를 받아온다.
        //2.정보->어댑터 넘겨준다.
        //3.어댑터 ->셋팅
    }//onCreate

    public void getNews(){
        // Instantiate the RequestQueue.
        String url ="http://newsapi.org/v2/top-headlines?country=kr&apiKey=5317d1bb702942f6a2ffc96dbcc44dd4";//volley한테 이 주소로 접속해,news api 홈페이지 주소

        // Request a string response from the provided URL.
        
        //volley를 이용한 네트워크 전송
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        //Log.d("NEWS",response);
                        try {
                            //웹 서버로부터 데이터 읽어오기,json형태로 뉴스를 읽어옴
                            JSONObject jsonObj=new JSONObject(response);
                            JSONArray arrayArticles= jsonObj.getJSONArray("articles");//아티클만 있으면 되기 때문에 얘만 읽어들임
                            List<NewsData> news=new ArrayList<>();//왜 리스트를 썻는지 생각할것-->내가 원하는 정보들만 따오기 위해서

                            for(int i=0,j=arrayArticles.length();i<j;i++){//json형태로 받아온 뉴스 데이터를 내가 생성한 NewsData 객체형태의 리스트로 받아오기 위한 작업
                               JSONObject obj= arrayArticles.getJSONObject(i);

                               Log.d("NEWS",obj.toString());//확인상 찍어보기,없어도 됨
                                //기사 하나하나의 정보를 newData에 입력하고 그 newData를 NewsData형 리스트에 삽입
                               NewsData newsData=new NewsData();
                               newsData.setTitle(obj.getString("title"));
                               newsData.setUrlToImage(obj.getString("urlToImage"));
                               newsData.setDescription(obj.getString("description"));
                               news.add(newsData);
                            }

                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(news, NewsActivity.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {//view -> v
                                    Object obj=v.getTag();
                                    if(obj!=null){
                                        int position=(int)obj;//형 변환
                                        ((MyAdapter)mAdapter).getNews(position);//포지션에 해당하는 뉴스를 가져옴,클래스 변환(친자 확인)
                                        Intent intent=new Intent(NewsActivity.this,NewsDetailActivity.class);//값을 넘기는 용도
                                        //1.본문내용만 넘기기(description만)
                                        intent.putExtra("news",((MyAdapter)mAdapter).getNews(position));
                                        //2.전체내용을 다 옮기기
                                        startActivity(intent);
                                    }
                                }
                            });//데이터 넘겨주기
                            recyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {//오류가 발생했을때는 일로
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);//위에서 진행한 요청을 큐에 넣어놓음
    }//getNews


}