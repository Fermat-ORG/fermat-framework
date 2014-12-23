package com.bitdubai.smartwallet.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.core.device.LocalDevice;
import com.bitdubai.smartwallet.core.system.user.LocalUser;
import com.bitdubai.smartwallet.walletmanager.DesktopActivity;
import com.bitdubai.smartwallet.walletstore.StoreFrontActivity;

public class ActivityMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);

        LocalDevice localDevice = new LocalDevice();

        /**
         * Initially, the APP will run with one user created when the APP run for the
         * first time and the user was asked which was his intended use of the APP was.
         * During this situation the system will bypass this first activity and navigate
         * directly into the {@link DesktopActivity}
         */
        if (localDevice.getLocalPersonalUsers().size() == 1 ) {
            if (localDevice.getLocalPersonalUsers().get(0).getLoginType() == LocalUser.LoginType.NONE)  {

                Intent intent;
                intent = new Intent(this, DesktopActivity.class);
                startActivity(intent);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_activity_main_menu, menu);
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
