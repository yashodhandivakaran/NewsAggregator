package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.news.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.R;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.News;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String NEWS = "news";

    private WebView mWebView;
    private View loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        News news = getIntent().getParcelableExtra(NEWS);

        loader = findViewById(R.id.loading_webpage);
        mWebView = (WebView)findViewById(R.id.news_webview);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setInitialScale(1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loader.setVisibility(View.GONE);
            }
        });
        mWebView.loadUrl(news.getLink());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
