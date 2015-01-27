package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ChatOverReceiveTrxFragment;

/**
 * Created by Natalia on 08/01/2015.
 */
public class ChatOverReceiveTrxActivity extends Activity {

    private PagerSlidingTabStrip tabs;
    private CharSequence mTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_framework_activity_chat_over_received_trx);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ChatOverReceiveTrxFragment())
                    .commit();
        }
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);
        ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),tabs, getActionBar(), getResources(),abTitle, mTitle.toString());


        String[]  whens = new String[]{"","4 hours ago", "5 hours ago", "yesterday 11:00 PM", "24 Mar 14","3 Feb 14","1 year ago","1 year ago","2 year ago"};
        String[] notes = new String[]{"","New telephone", "Old desk", "Car oil", "Sandwich","Headphones","Computer monitor","Pen","Apartment in Dubai"};

        String[] contacts = new String[]{"","Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };
        String[] amounts = new String[]{"","$200.00", "$3,000.00", "$400.00", "$3.00","$45.00","$600.00","50.00","$80,000.00"};

        String[][]  transactions = new String[][]{
                {},
                {"New telephone","Hot dog","Telephone credit","Coffee"},
                {"Old desk","Flat rent","New glasses","House in Europe","Coffee","Gum"},
                {"Car oil","Headphones","Apartment"},
                {"Sandwich","New kitchen","Camera repair"},
                {"Headphones"},
                {"Computer monitor","New car"},
                {"Pen"},
                {"Apartment in Dubai"}
        };
        String[][] transactions_amounts = new String[][]{

                {},
                {"$200.00", "$3.00", "$460.00", "$2.00", "$1.5"},
                {"$3,000.00", "$34,200.00", "$4,500.00", "$4,000,000", "$2,00.00", "$0.50"},
                {"$400,00", "$43.00", "$350,000.00"},
                {"$3.00", "$55,000.00", "$7,500.00"},
                {"$45.00"},
                {"$600.00","$5050.00"},
                {"$50.00"},
                {"$80,000.00"}

        };

        String[][]  transactions_whens = new String[][]{

                {},
                {"4 hours ago","8 hours ago","yesterday 10:33 PM","yesterday 9:33 PM"},
                {"5 hours ago","yesterday","20 Sep 14","16 Sep 14","13 Sep 14","12 Sep 14"},
                {"yesterday 11:00 PM","23 May 14", "12 May 14"},
                {"24 Mar 14","15 Apr 14","2 years ago"},
                {"3 Feb 14"},
                {"1 year ago","1 year ago"},
                {"1 year ago"},
                {"2 years ago"}


        };

        String[] tagId = MyApplication.getChildId().split("\\|");


        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.wallet_framework_activity_chat_over_received_trx_action_bar,
                null);

        // Set up your ActionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);


        TextView tv;

        tv = (TextView) actionBarLayout.findViewById(R.id.contact_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(contacts[Integer.parseInt(tagId[0])].toString());


        ImageView profile_picture = (ImageView) actionBarLayout.findViewById(R.id.profile_picture);
        switch (Integer.parseInt(tagId[0]))
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

        tv = (TextView) actionBarLayout.findViewById(R.id.notes);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(transactions[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);

        tv = (TextView) actionBarLayout.findViewById(R.id.amount);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(transactions_amounts[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);;

        tv = (TextView) actionBarLayout.findViewById(R.id.when);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(transactions_whens[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);

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



}
