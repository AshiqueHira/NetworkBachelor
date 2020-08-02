package com.ashiquehira.networkbachelor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    SwipeRefreshLayout swipeLayout;

    CheckNetwork network = new CheckNetwork(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        network.myNetworkCheck();

        if (CheckNetwork.isNetworkConnected){
            Toast.makeText(getApplicationContext(), "Connection is ok", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
        }
        webViewMethod();

        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Insert your code here
                webView.reload();// refreshes the WebView

                if (!CheckNetwork.isNetworkConnected){
                    Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    public void webViewMethod(){

        webView.loadUrl("https://www.networkbachelor.com");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webView.loadUrl("https://www.networkbachelor.com");
                //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
