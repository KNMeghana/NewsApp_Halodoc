package com.meghana.android.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meghana.android.news.model.News;
import com.meghana.android.news.newsapp.NewsDetailsActivity;
import com.meghana.android.news.newsapp.R;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {
    private ArrayList<News> list;
    private Context context;

    public NewsAdapter(ArrayList<News> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.item = convertView.findViewById(R.id.item);
            holder.title = convertView.findViewById(R.id.title);
            holder.author = convertView.findViewById(R.id.author);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        News news = (News)getItem(i);

        holder.title.setText(news.getTitle());
        holder.author.setText(news.getAuthor());

        final String newsUrl = news.getUrl();
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("url", newsUrl);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder{
        public RelativeLayout item;
        public TextView title;
        public TextView author;
    }
}
