package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.news;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.R;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.ParseQueries;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.News;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.Newspaper;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.news.adapter.NewsListAdapter;

public class NewsListFragment extends Fragment implements ParseQueries.NewsListListener {
    private OnFragmentInteractionListener mListener;

    public static final String NEWSPAPER = "newspaper";

    public static final String TAG = "newslistfragment";

    private Newspaper mNewspaper;

    private RecyclerView mNewsList;
    private NewsListAdapter mAdapter;
    private List<News> newsArticleList;
    private ParseQueries parseQueries;

    // TODO: Rename and change types and number of parameters
    public static NewsListFragment newInstance(String param1, String param2) {
        NewsListFragment fragment = new NewsListFragment();
        return fragment;
    }

    public String getNewspaperUid(){
        return mNewspaper.getId();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mNewsList = (RecyclerView)view.findViewById(R.id.news_list);
        Bundle bundle=getArguments();
        mNewspaper = bundle.getParcelable(NEWSPAPER);

        toolbar.setTitle(mNewspaper.getName());

        setUpNewList();
        return view;
    }

    private void setUpNewList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mNewsList.setLayoutManager(linearLayoutManager);
        newsArticleList = new ArrayList<News>();
        mAdapter = new NewsListAdapter(newsArticleList,getActivity());
        mNewsList.setAdapter(mAdapter);

        parseQueries = new ParseQueries();
        parseQueries.getListOfNewsArticles(mNewspaper.getId(), this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void listOfNewsArticles(List<News> newsList) {
        newsArticleList.clear();
        newsArticleList.addAll(newsList);
        mNewsList.getAdapter().notifyDataSetChanged();
    }

    public void stopSectionFetchingTask(){
        if(parseQueries != null){
            parseQueries.cancelSectionFetchingTask();
        }
    }
}
