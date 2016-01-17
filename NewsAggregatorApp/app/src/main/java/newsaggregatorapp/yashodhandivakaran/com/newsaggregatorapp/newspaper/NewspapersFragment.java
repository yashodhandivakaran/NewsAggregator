package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.newspaper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.R;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.ParseQueries;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.Newspaper;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.newspaper.adapter.NewspaperListAdapter;

public class NewspapersFragment extends Fragment implements ParseQueries.NewspaperListener,
        NewspaperListAdapter.NewspaperClickEventListener {


    public static String TAG = "NewspaperFragment";
    private RecyclerView mNewsPaperGrid;
    private OnNewspaperFragmentInteractionListener mListener;
    private List<Newspaper> mNewspaperList;

    public NewspapersFragment() {
    }

    public void setupInteractionListener(OnNewspaperFragmentInteractionListener listener){
        this.mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_newspapers, container, false);
        mNewsPaperGrid = (RecyclerView)view.findViewById(R.id.news_provider);

        setUpGrid();
        return view;
    }

    private void setUpGrid(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        mNewsPaperGrid.setLayoutManager(gridLayoutManager);

        mNewspaperList = new ArrayList<>();
        ParseQueries.getNewsPapers(this);
        NewspaperListAdapter adapter = new NewspaperListAdapter(mNewspaperList,this);
        mNewsPaperGrid.setAdapter(adapter);

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

    public interface OnNewspaperFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteractionOpenArticle(Newspaper newspaper);
    }

    @Override
    public void listOfNewspapers(List<Newspaper> newspaperList) {
        mNewspaperList.clear();
        mNewspaperList.addAll(newspaperList);
        mNewsPaperGrid.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void openNewArticles(Newspaper newspaper) {
        mListener.onFragmentInteractionOpenArticle(newspaper);
    }
}
