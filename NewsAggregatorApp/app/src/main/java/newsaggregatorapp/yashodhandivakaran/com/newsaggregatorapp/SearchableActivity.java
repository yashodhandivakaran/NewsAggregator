package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SearchableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
            String newspaperUid = "";
            if(bundle != null) {
                newspaperUid = bundle.getString("newspaper_uid");
            }

            showResults(query,newspaperUid);
        }
    }

    private void showResults(String query,String newspaperUid) {
        //TODO: populate the result
    }
}
