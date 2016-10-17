package com.example.ukyan.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);

        // 接続先のURLを指定してHTTP GET実行
        URL url = null;
        String card_id = "ghthetfe";
        try {
            url = new URL("http://test-ukyankyan.c9users.io/api/users/"+ card_id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        new HttpGetTask().execute(url);
    }

    // AsyncTaskのサブクラスとして、バックグラウンドでHTTP GETしてTextViewに表示するタスクを定義
    class HttpGetTask extends AsyncTask<URL, Void, String> {
        // HttpURLConnectionを使ったデータ取得 (バックグラウンド)
        @Override
        protected String doInBackground(URL... url) {
            String result = "";
            String token = "9566714722251566337d4a4701de6153";
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url[0].openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Authorization","Token token="+"9566714722251566337d4a4701de6153");
                if(urlConnection.getInputStream() != null)
                    result = IOUtils.toString(urlConnection.getInputStream());
                else
                    Log.e("error","result is null");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result;
        }

        // データ取得結果のTextViewへの表示 (UIスレッド)
        @Override
        protected void onPostExecute(String response) {
            if(response != null)
                MainActivity.this.textView.setText(response);
        }
    }
}
