class SearchFragment : androidx.leanback.app.SearchFragment(),
    androidx.leanback.app.SearchFragment.SearchResultProvider {

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
            results.forEach { listRowAdapter.add(it) }

            val header = HeaderItem("Search Results")
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
