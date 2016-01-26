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
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.news.asynctasks.GetNewsSectionTask;

/**
 * Created by webyog on 16/01/16.
 */
public class ParseQueries implements GetNewsSectionTask.GetNewsSectionListener {

    private NewsListListener mNewsListListener;
    private GetNewsSectionTask sectionTask;

    public interface NewspaperListener{
        public void listOfNewspapers(List<newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.Newspaper> newspaperList);
    }

    public interface NewsListListener{
        public void listOfNewsArticles(List<News> newsList);
    }

    public void getNewsPapers(final NewspaperListener listener){
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
                        newspaper.setColor(obj.getString(NewspaperConstant.COL_COLOR));

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

    public void getListOfNewsArticles(String newspaperUid,final NewsListListener listener){

        mNewsListListener = listener;

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

                    sectionTask = new GetNewsSectionTask(ParseQueries.this);
                    sectionTask.execute(newsArticleList);

                } else {
                    Log.d("Exception", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void passNewsWithSection(List<News> newsList) {
        mNewsListListener.listOfNewsArticles(newsList);
    }

    public void cancelSectionFetchingTask(){
        if(sectionTask!=null){
            sectionTask.cancel(true);
        }
    }

    public void getTopTwoNewsArticles(NewsListListener newsListListener){

        mNewsListListener = newsListListener;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("News");
        query.setLimit(2);
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

                    sectionTask = new GetNewsSectionTask(ParseQueries.this);
                    sectionTask.execute(newsArticleList);

                } else {
                    Log.d("Exception", "Error: " + e.getMessage());
                }
            }
        });
    }
}
