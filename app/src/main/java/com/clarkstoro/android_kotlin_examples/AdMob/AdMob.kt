package com.clarkstoro.android_kotlin_examples.AdMob

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.clarkstoro.android_kotlin_examples.R
import com.google.android.gms.ads.*

class AdMob : Fragment() {

    lateinit var mAdViewRectangle : AdView
    lateinit var mAdViewSmartBanner : AdView
    private lateinit var mInterstitialAd: InterstitialAd

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v : View = inflater.inflate(R.layout.fragment_ad_mob, container, false)

        MobileAds.initialize(context, "ca-app-pub-8270965522829708~7333725969") //put your ADmob ID

        //Banners
        mAdViewRectangle = v?.findViewById(R.id.adViewRectangle)!!
        mAdViewSmartBanner = v?.findViewById(R.id.adViewSmart)!!
        val adRequest = AdRequest.Builder().build()
        mAdViewRectangle.loadAd(adRequest)
        mAdViewSmartBanner.loadAd(adRequest)


        //Interstitial
        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712" //put your Interstitial ID the testing ID provided from Google: ca-app-pub-3940256099942544/1033173712
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        var btnInterstitialAd = v.findViewById(R.id.btnInterstitialAd) as Button
        btnInterstitialAd.setOnClickListener {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
        }

        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }
            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                super.onAdClosed()
                mInterstitialAd.loadAd(AdRequest.Builder().build()) //when the ad is closed reload it again or it won't be showed after the first time
            }
        }

        return v
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
}
