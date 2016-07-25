package com.bitdubai.android_core.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.constants.ApplicationConstants;

/**
 * Created by Matias Furszyfer
 */
public class LoadingScreenActivity extends AppCompatActivity {


    ImageView left_wallet;
    ImageView right_wallet;
    ImageView center_wallet;

    //Introduce an delay
    private final int WAIT_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        System.out.println("LoadingScreenActivity  screen started");
        setContentView(R.layout.loading_screen);


        left_wallet = (ImageView) findViewById(R.id.left_wallet);
        left_wallet.setVisibility(View.GONE);
        right_wallet = (ImageView) findViewById(R.id.right_wallet);
        right_wallet.setVisibility(View.GONE);
        center_wallet = (ImageView) findViewById(R.id.center_wallet);
        center_wallet.setVisibility(View.GONE);


        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);


        slide_up.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                slide_down.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.slide_up);
                        slide_down.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                                        R.anim.slide_up);
                                slide_down.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        right_wallet.setVisibility(View.GONE);
                                        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                                                R.anim.slide_up);
                                        slide_down.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                center_wallet.setVisibility(View.GONE);
                                                Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                                                        R.anim.slide_up);
                                                slide_down.setAnimationListener(new Animation.AnimationListener() {
                                                    @Override
                                                    public void onAnimationStart(Animation animation) {

                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animation animation) {
                                                        left_wallet.setVisibility(View.GONE);
                                                        final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                                                                R.anim.slide_up);
                                                        slide_down.setAnimationListener(new Animation.AnimationListener() {
                                                            @Override
                                                            public void onAnimationStart(Animation animation) {

                                                            }

                                                            @Override
                                                            public void onAnimationEnd(Animation animation) {
                                                                right_wallet.setVisibility(View.VISIBLE);
                                                                right_wallet.startAnimation(slide_up);
                                                            }

                                                            @Override
                                                            public void onAnimationRepeat(Animation animation) {

                                                            }
                                                        });
                                                        left_wallet.startAnimation(slide_down);

                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animation animation) {

                                                    }
                                                });
                                                left_wallet.startAnimation(slide_down);
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                        center_wallet.startAnimation(slide_down);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                right_wallet.startAnimation(slide_down);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        left_wallet.startAnimation(slide_down);
                        left_wallet.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                center_wallet.startAnimation(slide_down);
                center_wallet.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        right_wallet.startAnimation(slide_up);
        right_wallet.setVisibility(View.VISIBLE);


        new Handler().postDelayed(new Runnable() {
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
                Intent mainIntent = new Intent(LoadingScreenActivity.this, AppActivity.class);
                String publicKey = getIntent().getStringExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY);
                mainIntent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, publicKey);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                LoadingScreenActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                LoadingScreenActivity.this.finish();
            }
        }, WAIT_TIME);
    }


}


