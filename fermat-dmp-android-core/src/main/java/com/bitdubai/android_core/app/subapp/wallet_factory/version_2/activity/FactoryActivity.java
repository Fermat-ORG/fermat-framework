package com.bitdubai.android_core.app.subapp.wallet_factory.version_2.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.smartwallet.R;
import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;
import com.bitdubai.android_core.app.common.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.android_core.app.subapp.wallet_factory.version_2.fragment.BalanceFragment;
import com.bitdubai.android_core.app.subapp.wallet_factory.version_2.fragment.DiscountsFragment;
import com.bitdubai.android_core.app.subapp.wallet_factory.version_2.fragment.HomeFragment;
import com.bitdubai.android_core.app.subapp.wallet_factory.version_2.fragment.ReceiveFragment;
import com.bitdubai.android_core.app.subapp.wallet_factory.version_2.fragment.RefillFragment;
import com.bitdubai.android_core.app.subapp.wallet_factory.version_2.fragment.ResourcesFragment;
import com.bitdubai.android_core.app.subapp.wallet_factory.version_2.fragment.SendFragment;
import com.bitdubai.android_core.app.subapp.wallet_factory.version_2.fragment.ShopFragment;

import com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyLayoutInflaterFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.view.View;
import android.view.View.OnClickListener;
public class FactoryActivity extends FragmentActivity
{
    private final int EDITED_TICKET = 1;
    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private Drawable oldBackground = null;
    private int currentColor = 0xFFff9900;
    private String walletStyle = "";
    private CharSequence mTitle = "Wallet Factory";
    private Menu menu;
    private ImageView imageBackGround;
    private  LinearLayout actiobarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MyApplication.setActivityId("FactoryActivity");
        setContentView(R.layout.wallet_factory_activity);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);

        Intent i=getIntent();

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);


        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        ((MyApplication) this.getApplication()).setDefaultTypeface(tf);
        tabs.setTypeface(tf, 1);
        //changeColor(currentColor);

        tabs.setDividerColor(0xFFFFFFFF);
        tabs.setIndicatorColor(0xFFFFFFFF);
        tabs.setIndicatorHeight(9);
        tabs.setBackgroundColor(0xFFff9900);
        tabs.setTextColor(0xFFFFFFFF);

        String color = "#ff9900";
        MyApplication.setActionBar(getActionBar());
        MyApplication.setDefaultTypeface(MyApplication.getDefaultTypeface());
        ((MyApplication) this.getApplication()).changeColor(Color.parseColor(color), getResources());

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.wallet_factory_editbackground_actionbar,
                null);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
       // actionBar.setTitle(mTitle);
       // actionBar.setIcon(R.drawable.ic_action_factory);

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);
        abTitle.setTypeface(MyApplication.getDefaultTypeface());

        actiobarLayout = (LinearLayout) actionBarLayout.findViewById(R.id.layout1);
        imageBackGround= (ImageView) actionBarLayout.findViewById(R.id.edit);
        imageBackGround.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                 clickImage();
            }
        });


    }



    private void clickImage(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.usd_1);

        File mFile1 = Environment.getExternalStorageDirectory();

        String fileName ="banner_kid_yellow_blue.pmg";
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.banner_kid_yellow_blue);
        File mFile2 = new File(mFile1,fileName);
        try {
            FileOutputStream outStream;

            outStream = new FileOutputStream(mFile2);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

            outStream.flush();

            outStream.close();

        } catch (FileNotFoundException e2) {
            //
            e2.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        String imagePath = mFile1.getAbsolutePath().toString()+"/"+fileName;
        File temp=new File(imagePath);

        if(temp.exists()){
            //  "Double Click open external Editor";
            //  String imagePath = "android.resource://" + getResources().getResourcePackageName(R.drawable.usd_1) + "/drawable-xxhdpi/usd_1.jpg";
            // String imagePath ="file://" +  getResources().getResourcePackageName(R.drawable.usd_1) + "/structured_res/drawable-xxhdpi/usd_1.jpg";
            Intent editIntent = new Intent(Intent.ACTION_EDIT);
            //getResources().getIdentifier("ic_launcher", "drawable", getPackageName());
            editIntent.setDataAndType(Uri.parse("file://" + imagePath), "image/*");
            editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            editIntent.putExtra("finishActivityOnSaveCompleted", true);
            //startActivity(Intent.createChooser(editIntent, null));
          startActivityForResult(editIntent, EDITED_TICKET);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        switch(requestCode) {
            case EDITED_TICKET:
                if(imageReturnedIntent != null){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        Drawable d = new BitmapDrawable(getResources(),selectedImage);

                        actiobarLayout.setBackground(d);
                    } catch (Exception e) {
                        String strError = e.getMessage();
                    }

                }
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wallet_shop_activity_account_detail_menu, menu);

        LayoutInflater inflaterClone = getLayoutInflater().cloneInContext(getLayoutInflater().getContext());
        LayoutInflater.Factory lif = new MyLayoutInflaterFactory();
        inflaterClone.setFactory(lif);

    return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
/*
            case R.id.action_contact:
                TabbedDialogFragment dialog = new TabbedDialogFragment();
                dialog.show(getSupportFragmentManager(), "QuickContactFragment");
                return true;
*/
            case R.id.action_requests_sent:
                Intent intent;
              //  intent = new Intent(this, RequestsSentActivity.class);
               // startActivity(intent);

                Platform platform = MyApplication.getPlatform();
                CorePlatformContext platformContext = platform.getCorePlatformContext();

                AppRuntimeManager appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
                appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);

                appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX);

                intent = new Intent(this, com.bitdubai.android_core.app.FragmentActivity.class);

                startActivity(intent);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    private void changeColor(int newColor) {

        tabs.setIndicatorColor(newColor);

        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

            if (oldBackground == null) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ld.setCallback(drawableCallback);
                } else {
                    getActionBar().setBackgroundDrawable(ld);
                }

            } else {

                TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

                // workaround for broken ActionBarContainer drawable handling on
                // pre-API 17 builds
                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    td.setCallback(drawableCallback);
                } else {
                    getActionBar().setBackgroundDrawable(td);
                }

                td.startTransition(200);

            }

            oldBackground = ld;


            getActionBar().setDisplayShowTitleEnabled(false);
            getActionBar().setDisplayShowTitleEnabled(true);

        }

        currentColor = newColor;

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //   currentColor = savedInstanceState.getInt("currentColor");
        //  changeColor(currentColor);
    }
    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };



    public class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;
        private String[] titles_1 = { };
        private String[] titles_2 = {  "Home", "Balance", "Send", "Receive","Shops","Refill","Discounts","Resources"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (walletStyle.equals("Kids") )
            {titles = titles_1;}
            else
            {titles = titles_2;}

            return titles[position];
        }

        @Override
        public int getCount() {
           titles = titles_2;
            return titles.length;
        }
        @Override
        public Fragment getItem(int position) {
           titles = titles_2;
            Fragment currentFragment;
            switch (position) {
                case 0:
                    currentFragment = HomeFragment.newInstance(position);
                    break;
                case 1:
                    currentFragment = BalanceFragment.newInstance(position);
                    break;
                case 2:
                    currentFragment = SendFragment.newInstance(position);
                    break;
                case 3:
                    currentFragment = ReceiveFragment.newInstance(position);
                    break;
                case 4:
                    currentFragment = ShopFragment.newInstance(position);
                    break;
                case 5:
                    currentFragment = RefillFragment.newInstance(position);
                    break;
                case 7:
                    currentFragment = ResourcesFragment.newInstance(position);
                    break;
                default:
                    currentFragment = DiscountsFragment.newInstance(position);
                    break;
            }
            return currentFragment;
        }
    }
}