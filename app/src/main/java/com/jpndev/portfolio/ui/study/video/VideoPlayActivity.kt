package com.jpndev.portfolio.ui.study.video

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.jpndev.portfolio.R
import com.jpndev.portfolio.databinding.ActivityLifeCycleBinding
import com.jpndev.portfolio.databinding.ActivityVideoPlayBinding
import com.jpndev.portfolio.ui.study.actvity.LifeCycleViewModel
import com.jpndev.portfolio.ui.study.actvity.LifeCycleViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class VideoPlayActivity : AppCompatActivity() {


    @Inject
    lateinit var  factory: VideoPlayViewModelFactory
    lateinit var viewModel: VideoPlayViewModel
  //  private lateinit var binding: ActivityVideoPlayBinding

    // creating a variable for exoplayer
    var exoPlayer: SimpleExoPlayer? = null

    // url of video which we are loading.
    var videoURL =
        "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4"

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityVideoPlayBinding.inflate(layoutInflater)
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // binding = ActivityVideoPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this,factory).get(VideoPlayViewModel::class.java)
        binding.viewmodel=viewModel
        binding.lifecycleOwner = this


     /*   binding.lookmeVideoview .init(this);
        // binding.lookmeVideoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videoplayback));

// to use video from a url
         binding.lookmeVideoview.setVideoPath(videoURL);

         binding.lookmeVideoview.start();
         binding.lookmeVideoview.setLookMe()*/;

        try {

            // bandwisthmeter is used for
            // getting default bandwidth
            val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()

            // track selector is used to navigate between
            // video using a default seekbar.
            val trackSelector: TrackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))

            // we are adding our track selector to exoplayer.
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

            // we are parsing a video url
            // and parsing its video uri.
            val videouri: Uri = Uri.parse(videoURL)

            // we are creating a variable for datasource factory
            // and setting its user agent as 'exoplayer_view'
            val dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")

            // we are creating a variable for extractor factory
            // and setting it to default extractor factory.
            val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()

            // we are creating a media source with above variables
            // and passing our event handler as null,
            val mediaSource: MediaSource =
                ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null)

            // inside our exoplayer view
            // we are setting our player
            binding.exoPlayerVIew.setPlayer(exoPlayer)

            // we are preparing our exoplayer
            // with media source.
            exoPlayer!!.prepare(mediaSource)

            // we are setting our exoplayer
            // when it is ready.
            exoPlayer!!.playWhenReady = true
        } catch (e: Exception) {
            // below line is used for 
            // handling our errors.
            Log.e("TAG", "Error : $e")
        }
    }
}