package com.bitdubai.android_core.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bitdubai.android_core.app.common.version_1.ApplicationConstants;
import com.bitdubai.fermat.R;

public class LoadingScreenActivity extends AppCompatActivity {

//Introduce an delay
    private final int WAIT_TIME = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	System.out.println("LoadingScreenActivity  screen started");
	setContentView(R.layout.loading_screen);
	findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);

	new Handler().postDelayed(new Runnable(){
	@Override
	    public void run() {
              //Simulating a long running task
//		try {
//			TimeUnit.MILLISECONDS.sleep(200);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println("Going to Profile Data");
	  /* Create an Intent that will start the ProfileData-Activity. */
		Intent mainIntent = new Intent(LoadingScreenActivity.this,AppActivity.class);
		String publicKey = getIntent().getStringExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY);
		mainIntent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY,publicKey);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	    LoadingScreenActivity.this.startActivity(mainIntent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	    LoadingScreenActivity.this.finish();
	}
	}, WAIT_TIME);
      }
}