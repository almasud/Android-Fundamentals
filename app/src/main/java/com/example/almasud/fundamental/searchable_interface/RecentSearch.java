package com.example.almasud.fundamental.searchable_interface;

import android.content.SearchRecentSuggestionsProvider;

public class RecentSearch extends SearchRecentSuggestionsProvider {
    public static String AUTHORITY = "com.example.almasud.fundamental.searchable_interface.RecentSearch";
    public static int MODE = DATABASE_MODE_QUERIES;

    public RecentSearch() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
