package com.bitdubai.smartwallet.ui.os.android.app.shell.version_1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.platform.Platform;
import com.bitdubai.smartwallet.platform.layer._2_os.Os;
import com.bitdubai.smartwallet.platform.layer._2_os.OsLayer;
import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CryptoNetwork;
import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CryptoNetworkLayer;
import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CryptoNetworks;
import com.bitdubai.smartwallet.platform.layer._9_middleware.shell.developer.bitdubai.version_1.engine.LocalDevice;
import com.bitdubai.smartwallet.platform.layer._8_network_service.user.developer.bitdubai.version_1.service.LocalUser;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);



        /**
         * Get access to the platform.
         */
        //MyApplication MyApp = (MyApplication) getApplication();
       // Platform platform = MyApp.getPlatform();

        /**
         * Now I have access to the OS.
         */
        //Os os =  ((OsLayer) MyApp.getPlatform().getOsLayer()).getOs();

        /**
         * And now to the bitcoin network..
         */
       // CryptoNetwork bitcoinNetwork =  ((CryptoNetworkLayer) platform.getCryptoNetworkLayer()).getCryptoNetwork(CryptoNetworks.BITCOIN);




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
