package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.RemoteViews;

import java.util.List;

import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.R;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.ParseQueries;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.News;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.news.detail.NewsDetailActivity;

/**
 * Created by Yashodhan on 26/01/16.
 */
public class NewsWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        context.startService(new Intent(context, NewsUpdateService.class));
    }

    public static class NewsUpdateService extends Service implements ParseQueries.NewsListListener{

        private  Context context;
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onStart(Intent intent, int startId) {
            super.onStart(intent, startId);
            buildUpdate(this);

        }

        public void buildUpdate(Context context){
            this.context = context;


            ParseQueries parseQueries = new ParseQueries();
            parseQueries.getTopTwoNewsArticles(this);

        }

        @Override
        public void listOfNewsArticles(List<News> newsList) {

            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.news_widget);

            News news1 = newsList.get(0);
            News news2 = newsList.get(1);

            views.setTextViewText(R.id.news_title, news1.getTitle().trim());
            String des = news1.getDescription().split("<")[0];
            if(des != null && !des.isEmpty()){
                views.setTextViewText(R.id.news_desc, Html.fromHtml(des));
            }

            views.setTextViewText(R.id.news_title2, news2.getTitle().trim());
            des = news1.getDescription().split("<")[0];
            if(des != null && !des.isEmpty()){
                views.setTextViewText(R.id.news_desc2, news2.getDescription());
            }

            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra(NewsDetailActivity.NEWS, news1);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent intent2 = new Intent(context, NewsDetailActivity.class);
            intent2.putExtra(NewsDetailActivity.NEWS,news2);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            views.setOnClickPendingIntent(R.id.headline_1, pendingIntent);
            views.setOnClickPendingIntent(R.id.headline_2, pendingIntent2);


            ComponentName thisWidget = new ComponentName(this, NewsWidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, views);
        }
    }
}
