class QualitySelectionDialog : DialogFragment() {
    private val qualities = arrayOf("Auto", "480p", "720p", "1080p")
    private lateinit var player: ExoPlayer

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Select Quality")
            .setItems(qualities) { _, which ->
                val parameters = when (which) {
                    0 -> TrackSelectionParameters.Builder(requireContext())
                        .clearVideoSizeConstraints()
                        .build()
                    1 -> TrackSelectionParameters.Builder(requireContext())
                        .setMaxVideoSize(854, 480)
                        .build()
                    2 -> TrackSelectionParameters.Builder(requireContext())
                        .setMaxVideoSize(1280, 720)
                        .build()
                    3 -> TrackSelectionParameters.Builder(requireContext())
                        .setMaxVideoSize(1920, 1080)
                        .build()
                    else -> return@setItems
                }

                player.trackSelectionParameters = parameters
            }
            .create()
    }
}