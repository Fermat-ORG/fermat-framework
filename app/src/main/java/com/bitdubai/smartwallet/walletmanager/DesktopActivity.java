package com.bitdubai.smartwallet.walletmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bitdubai.smartwallet.walletframework.FrameworkActivity;
import com.bitdubai.smartwallet.R;





public class DesktopActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_manager_activity_desktop);
        getActionBar().hide();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wallet_manager_desktop_activity_menu, menu);
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


    public void onWalletClicked(View v) {

        String tag = v.getTag().toString();
        Intent intent;

        intent = new Intent(this, FrameworkActivity.class);

        intent.putExtra("Wallet Id", tag );
        startActivity(intent);

    }

}
