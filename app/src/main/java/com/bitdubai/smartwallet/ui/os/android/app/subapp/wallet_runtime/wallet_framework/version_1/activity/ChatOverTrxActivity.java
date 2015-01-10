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
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ChatOverTransactionFragment;

public class ChatOverTrxActivity extends Activity {

    private PagerSlidingTabStrip tabs;
    private CharSequence mTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_framework_activity_chat_over_received_trx);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ChatOverTransactionFragment())
                    .commit();
        }
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);
        ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),tabs, getActionBar(), getResources(),abTitle, mTitle.toString());

        /* Custom Action Bar with Icon and Text */
      /* Custom Action Bar with Icon and Text */
        String[] contacts = new String[]{ "", "Guillermo Villanueva", "Luis Fernando Molina", "Pedro Perrotta", "Mariana Duyos"};
        String[] amounts = new String[]{ "", "$1,400.00", "$325.00", "$0.50", "$25.00"};
        String[] whens = new String[]{ "", "2 hours ago", "3 min ago", "today 9:24 AM", "yesterday"};
        String[] notes = new String[]{"", "Flat rent",  "Electricity bill", "Test address", "More pictures"};

        String[][] transactions = new String[][]{

                {},
                {"Flat rent","Flat rent","Flat rent","interest paid :(","Flat rent","Car repair","Invoice #2,356 that should have been paid on August"},
                {"Electricity bill","New chair","New desk"},
                {"Test address"},
                {"More pictures"}
        };

        String[][] transactions_amounts = new String[][]{

                {},
                {"$1,400.00","$1,200.00","$1,400.00","$40.00","$1,900.00","$10,550.00","$1.00"},
                {"$325.00","$55.00","$420.00"},
                {"$0.50"},
                {"$25.00"}
        };

        String[][] transactions_whens = new String[][]{

                {},
                {"3 min ago","15 min ago","yesterday"},
                {"2 hours ago","1 month ago","2 months ago","3 months ago","3 months ago","5 months ago","7 months ago"},
                {"today 9:24 AM"},
                {"yesterday"}
        };
        String[] tagId = MyApplication.getChildId().split("\\|");


        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.wallet_framework_activity_chat_over_trx_action_bar,
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
                profile_picture.setImageResource(R.drawable.guillermo_profile_picture);

                break;
            case 2:
                profile_picture.setImageResource(R.drawable.luis_profile_picture);

                break;
            case 3:
                profile_picture.setImageResource(R.drawable.pedro_profile_picture);
                break;
            case 4:
                profile_picture.setImageResource(R.drawable.mariana_profile_picture);

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
