package com.reels.tv

import android.os.Bundle
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.ObjectAdapter
import androidx.fragment.app.viewModels

class SearchFragment : SearchSupportFragment(), SearchSupportFragment.SearchResultProvider {

    private val searchViewModel: SearchViewModel by viewModels()
    private val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSearchResultProvider(this)
        setupObservers()
    }

    private fun setupObservers() {
        searchViewModel.searchResults.observe(viewLifecycleOwner) { results ->
            rowsAdapter.clear()

            val listRowAdapter = ArrayObjectAdapter(ReelPresenter())
            results.forEach { reel ->
                listRowAdapter.add(reel)
            }

            val header = HeaderItem(0L, "Search Results")
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
    }

    override fun getResultsAdapter(): ObjectAdapter = rowsAdapter

    override fun onQueryTextChange(newQuery: String): Boolean {
        searchViewModel.search(newQuery)
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchViewModel.search(query)
        return true
    }
}