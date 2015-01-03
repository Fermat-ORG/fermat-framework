package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ReceiveFromContactFragment;


public class ReceiveFromContactActivity extends Activity {

    private PagerSlidingTabStrip tabs;
    private CharSequence mTitle = "Receive From Contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_framework_activity_receive_from_contact);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ReceiveFromContactFragment())
                    .commit();
        }
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);
        ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),tabs, getActionBar(), getResources(),abTitle, mTitle.toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wallet_framework_activity_send_to_contact_menu, menu);
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
