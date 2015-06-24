package com.bitdubai.android_core.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.PagerAdapter;
import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;
import com.bitdubai.android_core.app.common.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.android_core.app.common.version_1.fragment.NavigationDrawerFragment;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.AndroidOsDataBaseSystem;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.AndroidOsFileSystem;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.AndroidOsLocationSystem;
import com.bitdubai.fermat_api.CantReportCriticalStartingProblem;
import com.bitdubai.fermat_api.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Activity;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.App;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.smartwallet.R;

//import android.support.v7.widget.SearchView;


/**
 * Created by toshiba on 16/02/2015.
 */

/**
 * This Activity is used as a loader, to show something while the Fermat Platform is being initialized.
 *
 * -- Luis.
 */

public class StartActivity extends FragmentActivity{


    public static final String START_ACTIVITY_INIT = "Init";

    private NavigationDrawerFragment NavigationDrawerFragment;

    private PagerAdapter PagerAdapter;
    public CharSequence Title; // NATALIA TODO:porque esto es publico? LUIS lo usa la funcion Restore Action bar
    private Menu menu;
    private PagerSlidingTabStrip tabStrip;
    private App app;
    private SubApp subApp;
    private Activity activity;
    private AppRuntimeManager appRuntimeMiddleware;
    private WalletRuntimeManager walletRuntimeMiddleware;
    private ErrorManager errorManager;

    private  AndroidOsFileSystem fileSystemOs;
    private CorePlatformContext platformContext;
    private AndroidOsDataBaseSystem databaseSystemOs;
    private AndroidOsLocationSystem locationSystemOs;

    private Bundle savedInstanceState;
    private Platform platform;


    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            try {
                setContentView(R.layout.splash_screen);
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), "Can't set content view as runtime_app_activity_runtime: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
               // System.err.println("Can't set content view as runtime_app_activity_runtime: " + e.getMessage());
            }

            this.savedInstanceState = savedInstanceState;
            new GetTask(this).execute();



            //NavigateActivity();
        }
        catch (Exception e) {

            //TODO : MATIAS estas seguro que el error Manager existe en este punto?? LUIS

            this.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);


            Toast.makeText(getApplicationContext(), "Error Load RuntimeApp - " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


    }

    public void handleTouch(View view){
        fermatInit();
    }

    private boolean fermatInit(){
        Intent intent = new Intent(this,RuntimeAppActivity.class);
        intent.putExtra(START_ACTIVITY_INIT,"init");
        startActivity(intent);
        return true;
    }

    class GetTask extends AsyncTask<Object, Void, Boolean> {
        Context context;

        GetTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(context);
            mDialog.setMessage("Please wait...");
            mDialog.show();
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            //init runtime app

            Context context = getApplicationContext();


            platform = MyApplication.getFermatPlatform();


            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);


            //set Os Addons in platform
            fileSystemOs = new AndroidOsFileSystem();
            fileSystemOs.setContext(context);
            platform.setFileSystemOs(fileSystemOs);

            databaseSystemOs = new AndroidOsDataBaseSystem();
            databaseSystemOs.setContext(context);
            platform.setDataBaseSystemOs(databaseSystemOs);

            locationSystemOs = new AndroidOsLocationSystem();
            locationSystemOs.setContext(context);
            platform.setLocationSystemOs(locationSystemOs);

            Bundle bundle = getIntent().getExtras();

            try
            {
                if(bundle != null){
                    if(bundle.getString("executeStart").toString() == "0")
                        platform.start();
                }else
                {
                    platform.start();
                }
            }
            catch (CantStartPlatformException | CantReportCriticalStartingProblem e) {
                System.err.println("CantStartPlatformException: " + e.getMessage());

                Toast.makeText(getApplicationContext(), "Catastrophic Failure Starting Fermat Platform" + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }


            //get platform object
            platformContext = platform.getCorePlatformContext();

            //get instances of Runtime Middleware object
            appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
            walletRuntimeMiddleware =  (WalletRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);

            //save object on global class
            MyApplication.setAppRuntime(appRuntimeMiddleware);
            MyApplication.setWalletRuntime(walletRuntimeMiddleware);

            errorManager = (ErrorManager) platformContext.getAddon(Addons.ERROR_MANAGER);
            MyApplication.setErrorManager(errorManager);

            /** Download wallet images **/

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            mDialog.dismiss();
            fermatInit();
        }
    }
}
