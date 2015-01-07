package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.fragment.NavigationDrawerFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ContactsFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.DiscountsFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.HomeFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.SendFragment;
//import com.bitdubai.smartwallet.ui.os.android.app.subapp.walletframework.wallets.kids.BalanceFragment;


import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.BalanceFragment;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ReceiveFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.RefillFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.StoresFragment;


//public class FrameworkActivity extends FragmentActivity {
public class FrameworkActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;


    //***
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Menu menu;


    public void onNavigationDrawerEditProfileIconClicked(View v) {

        Intent intent;
        intent = new Intent(this, EditPersonalProfileActivity.class);
        startActivity(intent);

        return;

    }


    public void onSendToContactIconClicked(View v) {

        int tagId = (int)v.getTag();
        MyApplication.setTagId(tagId);
        Intent intent;
        intent = new Intent(this, SendToContactActivity.class);
        startActivity(intent);

        return;

    }

    public void onReceiveFromContactIconClicked(View v) {

        Intent intent;
        intent = new Intent(this, ReceiveFromContactActivity.class);
        startActivity(intent);

        return;

    }


    public void onChatOverTrxIconClicked(View v) {

        Intent intent;
        intent = new Intent(this, ChatOverTrxActivity.class);
        startActivity(intent);

        return;

    }

    public void onSendIconClicked(View v) {

        String tagId = v.getTag().toString();
        MyApplication.setChildId(tagId);

        Intent intent;
        intent = new Intent(this, SendToContactActivity.class);
        startActivity(intent);

        return;

    }

    public void onSendAllIconClicked(View v) {

        int tagId = (int) v.getTag();
        MyApplication.setTagId(tagId);
        Intent intent;
        intent = new Intent(this, SentHistoryActivity.class);
        startActivity(intent);
        return;

    }

    public void onReceiveIconClicked(View v) {

        String tagId = v.getTag().toString();
        MyApplication.setChildId(tagId);
        Intent intent;
        intent = new Intent(this, ReceiveFromContactActivity.class);
        startActivity(intent);
        return;

    }

    public void onReceiveAllIconClicked(View v) {

        int tagId = (int) v.getTag();
        MyApplication.setTagId(tagId);
        Intent intent;
        intent = new Intent(this, ReceiveAllHistoryActivity.class);
        startActivity(intent);
        return;

    }
    public void onAvailableBalanceIconClicked(View v) {

        Intent intent;
        intent = new Intent(this, AvailableBalanceActivity.class);
        startActivity(intent);

        return;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_framework_activity_framework);
        // I get the action bar title id and put it on a text view in order to later change its color
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);


        Intent i=getIntent();

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        switch ( ((MyApplication) this.getApplication()).getWalletId() )
        {
            case 1:
                mTitle = "Girl's savings";

                break;

            case 2:
                mTitle = "Boy's savings";

                break;

            case 3:
                mTitle = "Ladies wallet";

                break;

            case 4:
                mTitle = "Young";

                break;

            case 5:
                mTitle = "Professional";

                break;

            case 6:
                mTitle = "Gucci";

                break;

            case 7:
                mTitle = "Carrefour";

                break;

            case 8:
                mTitle = "Banco Itau";

                break;

            case 9:
                mTitle = "Banco Popular";

                break;

            case 10:
                mTitle = "Boca Juniors";

                break;

            case 11:
                mTitle = "Barcelona";

                break;
        }

        if (mTitle.equals("Boy's savings") ){

        }
        else {

            mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getFragmentManager().findFragmentById(R.id.navigation_drawer);


            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));

        }


        ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),tabs, getActionBar(), getResources(),abTitle, mTitle.toString());
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //getWindow().getDecorView().setBackgroundResource(R.drawable.wallet_wallpaper_yellow);

        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        ((MyApplication) this.getApplication()).setDefaultTypeface(tf);
        tabs.setTypeface(tf,1 );
        //changeColor(currentColor);

        tabs.setBackgroundResource(R.drawable.background_tiled_diagonal_light);
        tabs.setDividerColor(0xFFBBBBBB);


    }

    //***
    @Override
    public void onNavigationDrawerItemSelected(int position) {



    }

    //***
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

    //***

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    //     this.menu = menu;
    //     getMenuInflater().inflate(R.menu.actionbar_menu, menu);
    //menu.getItem(1).getActionView().set
    //     return true;
    //}

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wallet_framework_activity_framework_menu, menu);

        LayoutInflater inflaterClone = getLayoutInflater().cloneInContext(getLayoutInflater().getContext());
        LayoutInflater.Factory lif = new MyLayoutInflaterFactory();
        inflaterClone.setFactory(lif);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
/*
            case R.id.action_contact:
                TabbedDialogFragment dialog = new TabbedDialogFragment();
                dialog.show(getSupportFragmentManager(), "QuickContactFragment");
                return true;
*/
            case R.id.action_requests_sent:

                intent = new Intent(this, RequestsSentActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_requests_received:

                intent = new Intent(this, RequestsReceivedActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }



    public void onColorClicked(View v) {

        int color = Color.parseColor(v.getTag().toString());
        ((MyApplication) this.getApplication()).changeColor(color,getResources());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        ((MyApplication) this.getApplication()).changeColor(currentColor,getResources());
    }



    public class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;
        private String[] titles_1 = { "Balance", "Contacts"};
        private String[] titles_2 = { "Home", "Balance", "Send", "Receive","Stores","Refill","Discounts"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (mTitle.equals("Boy's savings") )
            {titles = titles_1;}
            else
            {titles = titles_2;}

            return titles[position];
        }

        @Override
        public int getCount() {

            if (mTitle.equals("Boy's savings") )
            {titles = titles_1;}
            else
            {titles = titles_2;}

            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment currentFragment = HomeFragment.newInstance(position);

            if (mTitle.equals("Boy's savings") )
            {titles = titles_1;}
            else
            {titles = titles_2;}


            if (mTitle == "Boy's savings")

                switch (position) {

                    case 0:
                        currentFragment =  com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment.BalanceFragment.newInstance(position);
                        break;
                    case 1:
                        currentFragment =  com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment.ContactsFragment.newInstance(position);
                        break;
                }


            else

            switch (position) {
                case 0:
                    currentFragment =   HomeFragment.newInstance(position);
                    break;
                case 1:
                    {currentFragment =  BalanceFragment.newInstance(position);}
                    break;
                case 2:
                    currentFragment =  SendFragment.newInstance(position);
                    break;
                case 3:
                    currentFragment =  ReceiveFragment.newInstance(position);
                    break;
                case 4:
                    currentFragment =  StoresFragment.newInstance(position);
                    break;
                case 5:
                    currentFragment =  RefillFragment.newInstance(position);
                    break;
                default:
                    currentFragment =   DiscountsFragment.newInstance(position);
                    break;
            }
            return currentFragment;
        }

    }


}