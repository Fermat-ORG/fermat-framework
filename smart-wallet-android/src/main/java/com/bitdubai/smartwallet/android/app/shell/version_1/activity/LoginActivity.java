package com.bitdubai.smartwallet.android.app.shell.version_1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.bitdubai.smartwallet.R;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);



        /**
         * Get access to the com.bitdubai.platform.
         */
        //MyApplication MyApp = (MyApplication) getApplication();
       // Platform com.bitdubai.platform = MyApp.getPlatform();

        /**
         * Now I have access to the OS.
         */
        //Os os =  ((OsLayer) MyApp.getPlatform().getOsLayer()).getOs();

        /**
         * And now to the bitcoin network..
         */
       // CryptoNetwork bitcoinNetwork =  ((CryptoNetworkLayer) com.bitdubai.platform.getCryptoNetworkLayer()).getCryptoNetwork(CryptoNetworks.BITCOIN);


/**

         LocalDevice localDevice = new LocalDevice();

        /**
         * Initially, the APP will run with one user created when the APP run for the
         * first time and the user was asked which was his intended use of the APP was.
         * During this situation the system will bypass this first activity and navigate
         * directly into the {@link DesktopActivity}
         */
   /**     if (localDevice.getLocalPersonalUsers().size() == 1 ) {
            if (localDevice.getLocalPersonalUsers().get(0).getLoginType() == LocalUser.LoginType.NONE)  {
*/
                Intent intent;
                intent = new Intent(this, DesktopActivity.class);
                startActivity(intent);
  /*          }
        }*/


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
