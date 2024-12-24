package com.reels.tv

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentActivity
import androidx.activity.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import androidx.media3.common.TrackSelectionParameters
import androidx.lifecycle.Observer
import androidx.lifecycle.observe

class ReelsPlayerActivity : FragmentActivity() {
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private val reelsViewModel: ReelsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        initializePlayer()
        setupRemoteControls()
        observeReels()
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .setMediaSourceFactory(DefaultMediaSourceFactory(this))
            .build()

        playerView = findViewById(R.id.player_view)
        playerView.player = player

        // Handle quality selection
        player.trackSelectionParameters = TrackSelectionParameters.Builder(this)
            .setMaxVideoSizeSd()
            .build()
    }

    private fun setupRemoteControls() {
        playerView.setOnKeyListener { _, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_CENTER,
                KeyEvent.KEYCODE_ENTER -> {
                    if (event.action == KeyEvent.ACTION_DOWN) {
                        if (player.isPlaying) player.pause() else player.play()
                        true
                    } else false
                }
                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    if (event.action == KeyEvent.ACTION_DOWN) {
                        reelsViewModel.loadNextReel()
                        true
                    } else false
                }
                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    if (event.action == KeyEvent.ACTION_DOWN) {
                        reelsViewModel.loadPreviousReel()
                        true
                    } else false
                }
                else -> false
            }
        }
    }

    private fun observeReels() {
        reelsViewModel.currentReel.observe(this, { reel: Reel ->
            prepareReel(reel)
        })
    }

    private fun prepareReel(reel: Reel) {
        val mediaItem = MediaItem.Builder()
            .setUri(reel.videoUrl)
            .setMediaId(reel.id)
            .build()

        player.setMediaItem(mediaItem)
        player.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}