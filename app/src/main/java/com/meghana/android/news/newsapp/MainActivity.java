package com.meghana.android.news.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the list view id and set click listener on the item selected
        ListView list = findViewById(R.id.news_category_list);
        list.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String queryParam = (String)adapterView.getItemAtPosition(i);
        Log.d(TAG, "Item selected : " + queryParam);

        // Create a new activity which will display the queried item news
        Intent intent = new Intent(this, NewsListActivity.class);
        intent.putExtra("query", queryParam);
        startActivity(intent);
    }
}
