package com.example.nebo.bakingapp.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.data.RecipeStep;
import com.example.nebo.bakingapp.databinding.FragmentRecipeStepDetailBinding;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeStepDetailFragment extends Fragment /* implements ExoPlayer.EventListener */ {
    private FragmentRecipeStepDetailBinding mBinding = null;
    private RecipeStep mRecipeStep = null;
    private ExoPlayer mVideoPlayer = null;
    private ExoPlayer mImagePlayer = null;
    private MediaSessionCompat mMediaSession = null;
    private PlaybackStateCompat.Builder mStateBuilder = null;
    private long mPosition = 0;

    public RecipeStepDetailFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && mPosition == 0) {
            if (savedInstanceState.containsKey(getString(R.string.key_video_position))) {
                mPosition = savedInstanceState.getLong(getString(R.string.key_video_position));
            }
            else {
                mPosition = 0;
            }
        }

        Log.d("RecipeStepDetailFrag","onActivityCreated Position is " + mPosition);
        mVideoPlayer.seekTo(mPosition);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_step_detail,
                container,
                false);
        Bundle fragmentArgs = getArguments();

        if (fragmentArgs != null) {
            if (fragmentArgs.containsKey(getString(R.string.key_recipe_step))) {
                mRecipeStep = fragmentArgs.getParcelable(getString(R.string.key_recipe_step));
            }
        }

        if (mRecipeStep != null) {
            mBinding.description.tvRecipeDescription.setText(mRecipeStep.getDescription());

            if (mRecipeStep.getVideoURL() != null && !mRecipeStep.getVideoURL().isEmpty()) {
                if (mVideoPlayer != null) {
                    releasePlayer();
                }
                mVideoPlayer = initializePlayer(mRecipeStep.getVideoURL(),
                        mBinding.video.pvRecipeStepVideo);
            }
            else {
                mBinding.llVideo.setVisibility(View.GONE);
            }

            if (mRecipeStep.getThumbnailURL() != null && !mRecipeStep.getThumbnailURL().isEmpty()) {
                if (mImagePlayer != null) {
                    releaseImagePlayer();
                }

                mImagePlayer = initializePlayer(mRecipeStep.getThumbnailURL(),
                        mBinding.thumbnail.pvRecipeStepThumbnail);
            }
            else {
                mBinding.llThumbnail.setVisibility(View.GONE);
            }
        }

        if (mBinding != null) {
            return mBinding.getRoot();
        }
        else {
            return null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("RecipeStepDetailFrag", "Current Position is " + mPosition);
        outState.putLong(getString(R.string.key_video_position), mPosition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseImagePlayer();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseImagePlayer();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoPlayer != null) {
            mVideoPlayer.setPlayWhenReady(false);
            mPosition = mVideoPlayer.getCurrentPosition();
        }

        releaseImagePlayer();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mVideoPlayer == null && mRecipeStep != null && mRecipeStep.getVideoURL() != null &&
                !mRecipeStep.getVideoURL().isEmpty()) {
            // initializeMediaSession();
            mVideoPlayer = initializePlayer(mRecipeStep.getVideoURL(),
                    mBinding.video.pvRecipeStepVideo);
            mVideoPlayer.seekTo(mPosition);
        }

        if (mImagePlayer == null && mRecipeStep != null && mRecipeStep.getThumbnailURL() != null &&
                !mRecipeStep.getThumbnailURL().isEmpty()) {
            mImagePlayer = initializePlayer(mRecipeStep.getThumbnailURL(),
                    mBinding.thumbnail.pvRecipeStepThumbnail);
        }
    }

    private ExoPlayer initializePlayer(String urlString, PlayerView playerView) {
        ExoPlayer player = null;

        if (urlString != null && !urlString.isEmpty()) {
            player = ExoPlayerFactory.newSimpleInstance(getContext(),
                    new DefaultTrackSelector());
            playerView.setPlayer(player);
            // player.addListener(this);
            playerView.setControllerShowTimeoutMs(0);
            playerView.setControllerHideOnTouch(false);

            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(
                    new DefaultDataSourceFactory(getContext(),
                            Util.getUserAgent(getContext(),
                                    "com.example.nebo.bakingapp"))).
                    createMediaSource(Uri.parse(urlString).buildUpon().build());

            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }

        return player;
    }

    private void releaseImagePlayer() {
        if (mImagePlayer != null) {
            mImagePlayer.setPlayWhenReady(false);
            mImagePlayer.stop();
            mImagePlayer.release();
            mImagePlayer = null;
        }
    }

    private void releasePlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.setPlayWhenReady(false);
            mVideoPlayer.stop();
            mVideoPlayer.release();
            mVideoPlayer = null;
        }
    }

    /*
    private void releaseMediaSession() {
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
            mMediaSession.release();
            mMediaSession = null;
        }
    }

    private void initializeMediaSession() {
        // 1. Create a media session compact object
        mMediaSession = new MediaSessionCompat(getContext(), "TAG");
        // 2. Set the flags
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        // 3. Setp an optional media button receiver component
        mMediaSession.setMediaButtonReceiver(null);
        // 4. Set the available actions and initial state.
        mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT);
        mMediaSession.setPlaybackState(mStateBuilder.build());
        // 5. Set the callbacks
        mMediaSession.setCallback(new MediaSessionCallback());
        // 6. Start the session
        mMediaSession.setActive(true);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {}

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

    @Override
    public void onLoadingChanged(boolean isLoading) {}

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_READY && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mVideoPlayer.getCurrentPosition(),
                    1f);
        }
        else if (playbackState == ExoPlayer.STATE_READY) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mVideoPlayer.getCurrentPosition(),
                    1f);
        }

        // Upon reloading from an onResumed state the mMediaSession is null.
        if (mMediaSession != null) {
            mMediaSession.setPlaybackState(mStateBuilder.build());
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {}

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {}

    @Override
    public void onPlayerError(ExoPlaybackException error) {}

    @Override
    public void onPositionDiscontinuity(int reason) {}

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}

    @Override
    public void onSeekProcessed() {}

    private class MediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mVideoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mVideoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            // Go to previous fragment will need a callback for this
        }

        @Override
        public void onSkipToNext() {
            // Go to next fragment will need a callback for this.
        }
    }
    */
}
