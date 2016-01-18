package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.newspaper.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.R;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.Newspaper;

/**
 * Created by Yashodhan on 16/01/16.
 */
public class NewspaperListAdapter extends RecyclerView.Adapter<NewspaperListAdapter.ViewHolder> {

    public interface NewspaperClickEventListener{
        public void openNewArticles(Newspaper newspaper);
    }

    private List<Newspaper> newspapers;
    private NewspaperClickEventListener mListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView name;
        public View container;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Newspaper newspaper = newspapers.get(getAdapterPosition());
            mListener.openNewArticles(newspaper);
        }
    }

    public NewspaperListAdapter(List<Newspaper> newspapers,NewspaperClickEventListener listener) {
        this.newspapers = newspapers;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newspaper_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.name = (TextView)v.findViewById(R.id.name);
        vh.container = v.findViewById(R.id.container);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Newspaper newspaper = newspapers.get(position);
        holder.name.setText(newspaper.getName());
        holder.container.setBackgroundColor(Color.parseColor(newspaper.getColor()));
    }

    @Override
    public int getItemCount() {
        return newspapers.size();
    }
}
