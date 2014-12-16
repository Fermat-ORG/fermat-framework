package com.bitdubai.smartwallet.walletframework;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bitdubai.smartwallet.wallets.teens.AccountDetailAccountFragment;
import com.bitdubai.smartwallet.wallets.teens.AccountDetailAllFragment;
import com.bitdubai.smartwallet.wallets.teens.AccountDetailCreditsFragment;
import com.bitdubai.smartwallet.wallets.teens.AccountDetailDebitsFragment;
import com.bitdubai.smartwallet.wallets.teens.AccountDetailFiltersFragment;
import com.bitdubai.smartwallet.wallets.teens.DiscountsFragment;
import com.bitdubai.smartwallet.wallets.teens.HomeFragment;
import com.bitdubai.smartwallet.wallets.teens.SendFragment;
import com.bitdubai.smartwallet.wallets.teens.BalanceFragment;
import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.wallets.teens.ReceiveFragment;
import com.bitdubai.smartwallet.wallets.teens.RefillFragment;
import com.bitdubai.smartwallet.wallets.teens.StoresFragment;
public class AccountDetailActivity extends FragmentActivity
        {

    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;

    private String walletStyle = "";

        /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Menu menu;





    public void onSendToContactIconClicked(View v) {

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



        // Change the title of the action bar and the typeface
        SpannableString s = new SpannableString("Unidentified wallet");

        // I get the action bar title id and put it on a text view in order to later change its color
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);

        Intent i=getIntent();
        // String walletId =i.getStringExtra( "Wallet Id");

        //  ((MyApplication) this.getApplication()).setWalletId(Integer.parseInt(walletId));

        String color = "";

        Drawable bg = getResources().getDrawable(R.drawable.transparent);
        bg.setVisible(false,false);

        Drawable wallpaper = getResources().getDrawable(R.drawable.transparent);


        switch (((MyApplication) this.getApplication()).getWalletId() )
        {
            case 1:
                mTitle = "Girl's savings";
                s = new SpannableString(mTitle);
                color = "#FFC2F1";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.icono_piggy_pink));
                bg = getResources().getDrawable(R.drawable.wallet_wallpaper_pink);
                bg.setVisible(true,false);
                wallpaper = getResources().getDrawable(R.drawable.wallet_wallpaper_pink);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Kids";
                break;

            case 2:
                mTitle = "Boy's savings";
                s = new SpannableString(mTitle);
                color = "#84DCF5";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.icono_piggy_yellow));
                bg = getResources().getDrawable(R.drawable.banner_kid_yellow_blue);
                bg.setVisible(true,false);
                wallpaper = getResources().getDrawable(R.drawable.wallet_wallpaper_yellow);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Kids";
                break;

            case 3:
                mTitle = "Ladies";
                s = new SpannableString(mTitle);
                color = "#F0E173";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.wallet_1));
                wallpaper = getResources().getDrawable(R.drawable.background_tabs_diagonal_rotated);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Young";
                break;

            case 4:
                mTitle = "Young";
                s = new SpannableString(mTitle);
                color = "#1EE635";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.wallet_3));
                wallpaper = getResources().getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Club";
                break;

            case 5:
                mTitle = "Professional";
                s = new SpannableString(mTitle);
                color = "#F0C64A";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.wallet_3));
                wallpaper = getResources().getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Club";
                break;

            case 6:
                mTitle = "Gucci";
                s = new SpannableString(mTitle);
                color = "#9B80FF";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.wallet_3));
                wallpaper = getResources().getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.BLACK);
                walletStyle = "Club";
                break;

            case 7:
                mTitle = "Carrefour";
                s = new SpannableString(mTitle);
                color = "#E8E8E8";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.icono_retailer_1));
                wallpaper = getResources().getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.BLUE);
                walletStyle = "Club";
                break;

            case 8:
                mTitle = "Banco Itau";
                s = new SpannableString(mTitle);
                color = "#AB0A80";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.icono_banco_1));
                wallpaper = getResources().getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.YELLOW);
                walletStyle = "Club";
                break;

            case 9:
                mTitle = "Banco Popular";
                s = new SpannableString(mTitle);
                color = "#FF0004";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.icono_banco_2));
                wallpaper = getResources().getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.WHITE);
                walletStyle = "Club";
                break;

            case 10:
                mTitle = "Boca Juniors";
                s = new SpannableString(mTitle);
                color = "#3864F5";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.icono_club_1));
                wallpaper = getResources().getDrawable(R.drawable.background_tiled_diagonal_light);
                abTitle.setTextColor(Color.YELLOW);
                bg = getResources().getDrawable(R.drawable.banner_club_1);
                bg.setVisible(true,false);
                walletStyle = "Club";
                break;

            case 11:
                mTitle = "Barcelona";
                s = new SpannableString(mTitle);
                color = "#DE186B";
                getActionBar().setIcon(getResources().getDrawable(R.drawable.icono_club_2));

                abTitle.setTextColor(Color.WHITE);
                wallpaper = getResources().getDrawable(R.drawable.wallet_wallpaper_club_2);
                bg = getResources().getDrawable(R.drawable.banner_club_2);
                bg.setVisible(true,false);
                walletStyle = "Club";
                break;
        }


        s.setSpan(new MyTypefaceSpan(this, "CaviarDreams.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);



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
        tabs.setTypeface(tf,1 );
        //changeColor(currentColor);

        tabs.setBackgroundResource(R.drawable.background_tiled_diagonal_light);
        tabs.setDividerColor(0xFFBBBBBB);


        changeColor(Color.parseColor(color));

        if (bg.isVisible() == true) {getActionBar().setBackgroundDrawable(bg);}



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




    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wallet_framework_activity_account_detail_menu, menu);

        LayoutInflater inflaterClone = getLayoutInflater().cloneInContext(getLayoutInflater().getContext());
        LayoutInflater.Factory lif = new MyLayoutInflaterFactory();
        inflaterClone.setFactory(lif);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_contact:
                TabbedDialogFragment dialog = new TabbedDialogFragment();
                dialog.show(getSupportFragmentManager(), "QuickContactFragment");
                return true;

            case R.id.action_requests_sent:
                Intent intent;
                intent = new Intent(this, RequestsSentActivity.class);
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

    public void onColorClicked(View v) {

        int color = Color.parseColor(v.getTag().toString());
        changeColor(color);

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
        changeColor(currentColor);
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
        private String[] titles_2 = { "Account", "Debits", "Credits", "All", "Filters"};

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

            if (walletStyle.equals("Kids") )
            {titles = titles_1;}
            else
            {titles = titles_2;}

            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {

            if (walletStyle.equals("Kids") )
            {titles = titles_1;}
            else
            {titles = titles_2;}

            Fragment currentFragment;
            switch (position) {
                case 0:
                    currentFragment =   AccountDetailAccountFragment.newInstance(position);
                    break;
                case 1:
                    currentFragment =   AccountDetailDebitsFragment.newInstance(position);
                    break;
                case 2:
                    currentFragment =  AccountDetailCreditsFragment.newInstance(position);
                    break;
                case 3:
                    currentFragment =  AccountDetailAllFragment.newInstance(position);
                    break;
                case 4:
                    currentFragment =  AccountDetailFiltersFragment.newInstance(position);
                    break;

                default:
                    currentFragment =   AccountDetailFiltersFragment.newInstance(position);
                    break;
            }
            return currentFragment;
        }

    }



    //***

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.util_fragment_navegation_drawler, container, false);
            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((AccountDetailActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }




}