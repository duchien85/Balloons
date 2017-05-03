package com.kara4k.balloons;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication implements AdMobHandler{

	public static final int SHOW_ADS = 1;
	public static final int HIDE_ADS = 0;

	protected AdView adView;
	private Handler handler;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler() {

//			@Override
//			public void handleMessage(Message msg) {
//				switch (msg.what) {
//					case SHOW_ADS:
//						adView.setVisibility(View.VISIBLE);
//						break;
//					case HIDE_ADS:
//						adView.setVisibility(View.GONE);
//						break;
//				}
//			}
		};

		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new Balloons(this), config);
		layout.addView(gameView);

//		adView = new AdView(this);
//		adView.setAdListener(new AdListener() {
//			@Override
//			public void onAdLoaded() {
//				int visibility = adView.getVisibility();
//				adView.setVisibility(AdView.GONE);
//				adView.setVisibility(visibility);
////				System.out.println("Ad Loaded");
//			}
//		});
//		adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
//		adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
//
//		AdRequest.Builder builder = new AdRequest.Builder();
//		builder.addTestDevice("5A3EB4CDB39C1E3AF339B3C6F9931603");
//		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT
//		);
//		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//		adParams.setMargins(0,45, 0, 0);
//
//		layout.addView(adView, adParams);
//		adView.loadAd(builder.build());
		setContentView(layout);
//        showAds(false);
	}

	@Override
	public void showAds(boolean show) {
//		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
}
