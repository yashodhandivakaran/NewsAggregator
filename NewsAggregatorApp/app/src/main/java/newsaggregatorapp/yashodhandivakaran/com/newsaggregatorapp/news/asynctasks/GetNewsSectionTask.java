package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.news.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.News;

/**
 * Created by webyog on 19/01/16.
 */
public class GetNewsSectionTask extends AsyncTask<List<News>,Integer,List<News>> {

    private List<News> newsList;

    public interface GetNewsSectionListener{
        public void passNewsWithSection(List<News> newsList);
    }

    public GetNewsSectionListener mListener;

    public GetNewsSectionTask(GetNewsSectionListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected List<News> doInBackground(List<News>... params) {
        newsList = params[0];

        int count = 0;
        for (News news: newsList){
            try {
                if(isCancelled()){
                    break;
                }
                Log.e("JSOUP","connect");
                Document document = Jsoup.connect(news.getLink()).get();
                Elements section = document.select("meta[property=article:section]");
                news.setNewsCategory(section.attr("content"));
                Log.e("JSOUP", "section fetched");
                count++;
                publishProgress(count);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return newsList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        if(values[0]%2 == 0 ){
            mListener.passNewsWithSection(newsList);
        }

    }

    @Override
    protected void onPostExecute(List<News> newsList) {
        super.onPostExecute(newsList);
        mListener.passNewsWithSection(newsList);
    }
}
