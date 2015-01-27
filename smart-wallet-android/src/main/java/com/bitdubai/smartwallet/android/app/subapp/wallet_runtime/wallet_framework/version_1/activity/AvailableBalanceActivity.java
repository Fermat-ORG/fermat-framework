package com.bitdubai.smartwallet.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.smartwallet.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.AvailableBalanceFragment;

public class AvailableBalanceActivity extends Activity {

    private PagerSlidingTabStrip tabs;
    private CharSequence mTitle = "Available balance";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_framework_activity_available_balance);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new AvailableBalanceFragment())
                    .commit();

        }
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);
        ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),tabs, getActionBar(), getResources(),abTitle, mTitle.toString());



        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.wallets_teens_fragment_account_detail_action_bar,
                null);


        // Set up your ActionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        TextView tv;

        tv = (TextView) actionBarLayout.findViewById(R.id.account_type);
        tv.setTypeface(MyApplication.getDefaultTypeface());


        tv = (TextView) actionBarLayout.findViewById(R.id.balance);
        tv.setTypeface(MyApplication.getDefaultTypeface());

        tv = (TextView) actionBarLayout.findViewById(R.id.balance_available);
        tv.setTypeface(MyApplication.getDefaultTypeface());

        tv = (TextView) actionBarLayout.findViewById(R.id.account_alias);
        tv.setTypeface(MyApplication.getDefaultTypeface());




    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wallet_framework_activity_available_balance_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
