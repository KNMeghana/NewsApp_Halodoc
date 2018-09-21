package com.meghana.android.news.newsapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.meghana.android.news.adapter.NewsAdapter;
import com.meghana.android.news.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class NewsListActivity extends AppCompatActivity {

    private static final String TAG = "NewsListActivity";
    private NewsDownloadAsyncTask downloadAsyncTask;

    private static final String BASEURL = "https://hn.algolia.com/api/v1/search?query=";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);

        String categorySelected = getIntent().getStringExtra("query");

        listView = findViewById(R.id.news_info_list);

        downloadAsyncTask = new NewsDownloadAsyncTask(listView, this);
        downloadAsyncTask.execute(categorySelected);
    }


    static class NewsDownloadAsyncTask extends AsyncTask<String, ArrayList, ArrayList>{
        private WeakReference<ListView> listViewWeakReference;
        private WeakReference<Activity> activityWeakReference;

        NewsDownloadAsyncTask(ListView view, Activity activity){
            listViewWeakReference = new WeakReference<ListView>(view);
            activityWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList doInBackground(String... urls) {
            Log.d(TAG, "The news to be fetched is : " + urls[0]);
            ArrayList<News> newsList = null;
            if(!isCancelled()) {
                try {
                    String urlToConnect = BASEURL + "<" + urls[0] + ">";
                    URL url = new URL(urlToConnect);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    Log.d(TAG, "The url to connect to is: " + urlToConnect);
                    Log.d(TAG, "The response is: " + con.getResponseCode());

                    String output = "";
                    InputStream inputStream = con.getInputStream();
                    if (inputStream == null)
                        return null;
                    int readCount = 0;
                    Reader reader = null;
                    reader = new InputStreamReader(inputStream, "UTF-8");
                    char[] buffer = new char[1024];
                    StringWriter writer = new StringWriter();
                    while (-1 != (readCount = reader.read(buffer))) {
                        writer.write(buffer, 0, readCount);
                    }
                    output = writer.toString();

                    JSONObject newsJson = new JSONObject(output);
                    JSONArray hitsArray = newsJson.getJSONArray("hits");
                    newsList = new ArrayList<>();
                    for (int i = 0; i < hitsArray.length(); i++) {
                        JSONObject newsObject = hitsArray.getJSONObject(i);
                        News news = new News();
                        String title = newsObject.getString("title");
                        news.setTitle(title != null ? title : "No title");
                        String urlOfNews = newsObject.getString("url");
                        news.setUrl(urlOfNews);
                        String author = newsObject.getString("author");
                        news.setAuthor(author != null ? author : "No author");
                        newsList.add(news);
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//
            return newsList;
        }

        @Override
        protected void onPostExecute(ArrayList s) {
            super.onPostExecute(s);
            ListView view = listViewWeakReference.get();
            if(view != null){
                view.setAdapter(new NewsAdapter(s, activityWeakReference.get()));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downloadAsyncTask.cancel(true);
    }
}
