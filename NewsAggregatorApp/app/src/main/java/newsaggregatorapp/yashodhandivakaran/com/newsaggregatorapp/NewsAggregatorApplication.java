package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by webyog on 16/01/16.
 */
public class NewsAggregatorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
    }
}
