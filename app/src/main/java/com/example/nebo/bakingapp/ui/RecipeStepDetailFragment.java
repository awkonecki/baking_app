package com.example.nebo.bakingapp.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.RecipeDetailsActivity;
import com.example.nebo.bakingapp.data.RecipeStep;
import com.example.nebo.bakingapp.databinding.FragmentRecipeStepDetailBinding;
import com.example.nebo.bakingapp.databinding.IncludeRecipeStepVideoBinding;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeStepDetailFragment extends Fragment implements ExoPlayer.EventListener {
    private FragmentRecipeStepDetailBinding mBinding = null;
    private RecipeStep mRecipeStep = null;
    private ExoPlayer mPlayer = null;
    private MediaSessionCompat mMediaSession = null;
    private PlaybackStateCompat.Builder mStateBuilder = null;

    public RecipeStepDetailFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_step_detail,
                container,
                false);

        Bundle fragmentArgs = getArguments();

        if (fragmentArgs != null && fragmentArgs.containsKey(getString(R.string.key_recipe_step))) {
            mRecipeStep = fragmentArgs.getParcelable(getString(R.string.key_recipe_step));
        }

        if (mRecipeStep != null) {
            mBinding.description.tvRecipeDescription.setText(mRecipeStep.getDescription());
            // mBinding.tvRecipeDescriptionTemp.setText(mRecipeStep.getDescription());

            if (mRecipeStep.getVideoURL() != null && !mRecipeStep.getVideoURL().isEmpty()) {
                if (mPlayer != null) {
                    releaseMediaSession();
                    releasePlayer();
                }
                initializeMediaSession();
                initializePlayer();
            }
            else {
                mBinding.video.getRoot().setVisibility(View.GONE);
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
    public void onDestroy() {
        super.onDestroy();
        releaseMediaSession();
        releasePlayer();
    }

    private void initializePlayer() {
        if (mRecipeStep != null &&
                mRecipeStep.getVideoURL() != null &&
                !mRecipeStep.getVideoURL().isEmpty()) {

            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),
                    new DefaultTrackSelector());
            mBinding.video.pvRecipeStepVideo.setPlayer(mPlayer);
            mBinding.video.pvRecipeStepVideo.setControllerShowTimeoutMs(0);
            mBinding.video.pvRecipeStepVideo.setControllerHideOnTouch(false);

            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(
                    new DefaultDataSourceFactory(getContext(),
                            Util.getUserAgent(getContext(),
                                    "com.example.nebo.bakingapp"))).
                    createMediaSource(Uri.parse(mRecipeStep.getVideoURL()).buildUpon().build());

            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

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
                    mPlayer.getCurrentPosition(),
                    1f);
        }
        else if (playbackState == ExoPlayer.STATE_READY) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mPlayer.getCurrentPosition(),
                    1f);
        }

        mMediaSession.setPlaybackState(mStateBuilder.build());
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
            mPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mPlayer.setPlayWhenReady(false);
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
}
