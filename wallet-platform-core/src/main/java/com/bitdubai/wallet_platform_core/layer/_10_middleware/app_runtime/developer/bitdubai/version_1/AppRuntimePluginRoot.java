package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1;

/**
 * Created by ciencias on 2/14/15.
 */

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.*;
import com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure.*;

/**
 * The App Runtime is the module in charge of the UI navigation structure. A user is always at a certain point in this 
 * structure.
 */


/**
 * A Navigation stack is maintained by this plugin to allow the user to go back all the stack down to the root if necessary.
 */



public class AppRuntimePluginRoot {





    
    
    
    
    
    
    /**
     * The first time this plugins runs, it will setup the initial structure for the App, subApp and so on through the local
     * interfaces of the classes involved, 
     */
     private void firstRunCheck() {

         /**
          * First I check weather this a structure already created, if not I create the "Factory" structure.
          */
         
         
     }


    /**
     * Here is where I actually generate the factory structure of the APP. This method is also useful to reset to the 
     * factory structure.
     */
    private void factoryReset() {

        RuntimeApp runtimeApp;
        RuntimeSubApp subApp;
        RuntimeActivity runtimeActivity;
        RuntimeFragment runtimeFragment;
        RuntimeWallet runtimeWallet;

        runtimeApp = new RuntimeApp();

        runtimeApp.setType(Apps.CRYPTO_WALLET_PLATFORM);
       
        
        subApp = new RuntimeSubApp();
        subApp.setType(SubApps.CWP_SHELL);
        runtimeApp.addSubApp(subApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_SHELL_LOGIN);
        subApp.addActivity(runtimeActivity);
        
        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SHELL_LOGIN);
        runtimeActivity.addFragment(runtimeFragment);
        
        
        
        subApp = new RuntimeSubApp();
        subApp.setType(SubApps.CWP_WALLET_FACTORY);
        runtimeApp.addSubApp(subApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_MAIN);
        subApp.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_FACTORY_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        
        

        subApp = new RuntimeSubApp();
        subApp.setType(SubApps.CWP_WALLET_MANAGER);
        runtimeApp.addSubApp(subApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
        subApp.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_MANAGER_MAIN);
        runtimeActivity.addFragment(runtimeFragment);

        
        
        subApp = new RuntimeSubApp();
        subApp.setType(SubApps.CWP_WALLET_RUNTIME);
        runtimeApp.addSubApp(subApp);

        runtimeWallet = new RuntimeWallet();
        runtimeWallet.setType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL);
        subApp.addWallet(runtimeWallet);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_MAIN);
        runtimeWallet.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_PROFILE);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_DESKTOP);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_CONTACTS);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_COMMUNITY);
        runtimeActivity.addFragment(runtimeFragment);
        
        
        
        subApp = new RuntimeSubApp();
        subApp.setType(SubApps.CWP_WALLET_STORE);
        runtimeApp.addSubApp(subApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN);
        subApp.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        
        
        
    }
    
}
