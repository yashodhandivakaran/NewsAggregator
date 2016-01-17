package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.R;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.News;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.news.detail.NewsDetailActivity;

/**
 * Created by Yashodhan on 16/01/16.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private List<News> newsList;
    private Context context;

    public NewsListAdapter(List<News> newsList,Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Todo:open new activity which loads the article

            News news = newsList.get(getAdapterPosition());

            Intent newsDetailsIntent = new Intent(context,NewsDetailActivity.class);
            newsDetailsIntent.putExtra(NewsDetailActivity.NEWS, news);

            context.startActivity(newsDetailsIntent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.title = (TextView)v.findViewById(R.id.news_title);
        vh.description = (TextView)v.findViewById(R.id.news_desc);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
