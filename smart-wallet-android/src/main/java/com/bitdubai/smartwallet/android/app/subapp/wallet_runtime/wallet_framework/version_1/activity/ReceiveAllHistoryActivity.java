package com.bitdubai.smartwallet.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.smartwallet.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ReceiveAllFragment;

/**
 * Created by Natalia on 24/12/2014.
 */
public class ReceiveAllHistoryActivity extends Activity {

    private PagerSlidingTabStrip tabs;
    private CharSequence mTitle = "Receive History";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_framework_activity_send_all);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ReceiveAllFragment())
                    .commit();
        }
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);
        ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),tabs, getActionBar(), getResources(),abTitle, mTitle.toString());


        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.wallet_framework_activity_received_history_action_bar,
                null);

        // Set up your ActionBar
        String[] contacts = new String[]{"","Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);
        int tagId = MyApplication.getTagId();
        TextView tv;

        tv = (TextView) actionBarLayout.findViewById(R.id.contact_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(contacts[tagId].toString());

        tv = (TextView) actionBarLayout.findViewById(R.id.activity_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());

        ImageView profile_picture = (ImageView) actionBarLayout.findViewById(R.id.profile_picture);
        switch (tagId)
        {
            case 1:
                profile_picture.setImageResource(R.drawable.lucia_profile_picture);
                break;
            case 2:
                profile_picture.setImageResource(R.drawable.juan_profile_picture);
                break;
            case 3:
                profile_picture.setImageResource(R.drawable.karina_profile_picture);
                break;
            case 4:
                profile_picture.setImageResource(R.drawable.simon_profile_picture);
                break;
            case 5:
                profile_picture.setImageResource(R.drawable.celine_profile_picture);
                break;
            case 6:
                profile_picture.setImageResource(R.drawable.taylor_profile_picture);
                break;
            case 7:
                profile_picture.setImageResource(R.drawable.stephani_profile_picture);
                break;
            case 8:
                profile_picture.setImageResource(R.drawable.kimberly_profile_picture);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wallet_framework_activity_sent_all_menu, menu);
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

    public void onReceiveIconClicked(View v) {

        String tagId = v.getTag().toString();
        MyApplication.setChildId(tagId);
        Intent intent;
        intent = new Intent(this, ReceiveFromContactActivity.class);
        startActivity(intent);
        return;

    }

    public void onChatOverTrxIconClicked(View v) {
        String tagId = v.getTag().toString();
        MyApplication.setChildId(tagId);
        Intent intent;
        intent = new Intent(this, ChatOverReceiveTrxActivity.class);
        startActivity(intent);

        return;

    }
}
