package com.example.newstest;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<NewsData> mDataset;
    private static View.OnClickListener onClickListener;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder {//안드가 제공하는 클래스
        // each data item is just a string in this case
        public TextView TextView_title;
        public TextView TextView_description;
        public SimpleDraweeView ImageView_title;
        public View rootView;

        public MyViewHolder(View v) {//v가 부모
            super(v);
            TextView_title=v.findViewById(R.id.TextView_title);
            TextView_description=v.findViewById(R.id.TextView_description);
            ImageView_title = (SimpleDraweeView) v.findViewById(R.id.ImageView_title);
            rootView =v;

            v.setClickable(true);//누를수 있다 없다
            v.setEnabled(true);//활성화 상태
            v.setOnClickListener(onClickListener);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<NewsData> myDataset,Context context,View.OnClickListener onClick) {//데이터 받아오기
        //{"1","2"}
        mDataset = myDataset;
        onClickListener=onClick;
        Fresco.initialize(context);//이미지를 가져오기 위함(fresco)
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {//전체뉴스
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        NewsData news=mDataset.get(position);

        holder.TextView_title.setText(news.getTitle());//holder - MyViewHolder

        String description=news.getDescription();

        if(description!=null && description.length()>0){
            holder.TextView_description.setText(description);
        }
        else {
            holder.TextView_description.setText("-");
        }

        Uri uri = Uri.parse(news.getUrlToImage());

        holder.ImageView_title.setImageURI(uri);

        //tag-label
        holder.rootView.setTag(position);//해당 뉴스 정보를 가지고 오기 위한 tag를 붙임
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset==null ? 0: mDataset.size();
    }//반복할 횟수

    public NewsData getNews(int position){
        return mDataset!=null ? mDataset.get(position):null;
    }

}

