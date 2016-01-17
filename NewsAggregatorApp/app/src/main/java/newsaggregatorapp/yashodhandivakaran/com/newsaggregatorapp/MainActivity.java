package newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.data.entities.Newspaper;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.news.NewsListFragment;
import newsaggregatorapp.yashodhandivakaran.com.newsaggregatorapp.newspaper.NewspapersFragment;

public class MainActivity extends AppCompatActivity implements NewspapersFragment.OnNewspaperFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewspapersFragment newspapersFragment = new NewspapersFragment();
        newspapersFragment.setupInteractionListener(this);
        getSupportFragmentManager().beginTransaction().
                add(R.id.newspapers_content, newspapersFragment, NewspapersFragment.TAG)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


    return true;
    }

    @Override
    public void onFragmentInteractionOpenArticle(Newspaper newspaper) {
        //TODO: open NewsListFragment and pass newspaper as argument
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        NewsListFragment newsListFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NewsListFragment.NEWSPAPER,newspaper);
        newsListFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.newspapers_content, newsListFragment,NewsListFragment.TAG);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_search:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putString("newspaper_uid",((NewsListFragment) getSupportFragmentManager()
                .findFragmentByTag(NewsListFragment.TAG)).getNewspaperUid());
        startSearch(null, false, appData, false);
        return true;
    }
}
