package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.Constants.NewsConstant;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.Constants.NewspaperConstant;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.News;

/**
 * Created by webyog on 16/01/16.
 */
public class ParseQueries {

    public interface NewspaperListener{
        public void listOfNewspapers(List<newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.Newspaper> newspaperList);
    }

    public interface NewsListListener{
        public void listOfNewsArticles(List<News> newsList);
    }

    public static void getNewsPapers(final NewspaperListener listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Newspaper");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> newspapers, ParseException e) {
                if (e == null) {

                    List<newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.Newspaper> newspaperList = new ArrayList<newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.Newspaper>();
                    for(ParseObject obj : newspapers){

                        newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.Newspaper newspaper = new newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.Newspaper();
                        newspaper.setName(obj.getString(NewspaperConstant.COL_NAME));
                        newspaper.setRssUrl(obj.getString(NewspaperConstant.COL_RSSURL));
                        newspaper.setId(obj.getString(NewspaperConstant.COL_NEWSPAPER_UID));

                        newspaperList.add(newspaper);
                    }

                    if(listener != null) {
                        listener.listOfNewspapers(newspaperList);
                    }


                } else {
                    Log.d("Exception", "Error: " + e.getMessage());
                }
            }
        });
    }

    public static void getListOfNewsArticles(String newspaperUid,final NewsListListener listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("News");
        query.whereEqualTo(NewsConstant.COL_NEWSPAPER_UID,newspaperUid);
        query.orderByDescending("updatedAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> news, ParseException e) {
                if (e == null) {

                    List<News> newsArticleList = new ArrayList<News>();
                    for (ParseObject obj : news) {

                        News newsArticle = new News();
                        newsArticle.setObjectId(obj.getObjectId());
                        newsArticle.setTitle(obj.getString(NewsConstant.COL_TITLE));
                        newsArticle.setLink(obj.getString(NewsConstant.COL_LINK));
                        newsArticle.setDescription(obj.getString(NewsConstant.COL_DESCRIPTION));
                        newsArticle.setNewspaperUid(NewsConstant.COL_NEWSPAPER_UID);


                        newsArticleList.add(newsArticle);
                    }

                    if (listener != null) {
                        listener.listOfNewsArticles(newsArticleList);
                    }


                } else {
                    Log.d("Exception", "Error: " + e.getMessage());
                }
            }
        });
    }
}
