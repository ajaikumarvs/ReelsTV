class ReelsViewModel : ViewModel() {
    private val reelsRepository: ReelsRepository by inject()

    private val _currentReel = MutableLiveData<Reel>()
    val currentReel: LiveData<Reel> = _currentReel

    private var currentReels = listOf<Reel>()
    private var currentIndex = 0

    init {
        loadReels()
    }

    private fun loadReels(hashtag: String? = null) {
        viewModelScope.launch {
            try {
                currentReels = reelsRepository.getReels(hashtag)
                if (currentReels.isNotEmpty()) {
                    _currentReel.value = currentReels[0]
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun loadNextReel() {
        if (currentIndex < currentReels.size - 1) {
            currentIndex++
            _currentReel.value = currentReels[currentIndex]
        }
    }

    fun loadPreviousReel() {
        if (currentIndex > 0) {
            currentIndex--
            _currentReel.value = currentReels[currentIndex]
        }
    }

    fun searchReels(query: String) {
        viewModelScope.launch {
            loadReels(query)
        }
    }
}