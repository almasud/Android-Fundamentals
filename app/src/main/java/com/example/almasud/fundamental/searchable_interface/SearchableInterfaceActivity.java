package com.example.almasud.fundamental.searchable_interface;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

public class SearchableInterfaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable_interface);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this, RecentSearch.AUTHORITY, RecentSearch.MODE);
            searchRecentSuggestions.saveRecentQuery(query, null);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        return true;
    }
}
