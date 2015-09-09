package com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.interfaces.WalletNavigationStructure;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.database.WalletNavigationStructureMiddlewareDatabaseConstants;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.structure.WalletNavigationStructureMiddleware</code>
 * is the implementation of WalletNavigationStructure.
 * <p/>
 * <p/>
 * Created by Natalia on 07/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */


public class WalletNavigationStructureMiddleware implements WalletNavigationStructure {

    //Modified by Manuel Perez on 12/08/2015
    private String publicKey;
    private Activity activity;
    private Activity startActivity;
    private Activity lastActivity;

    public WalletNavigationStructureMiddleware(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public Activity getActivity() {
        return this.activity;
    }

    @Override
    public Activity getLastActivity() {
        return this.lastActivity;
    }

    @Override
    public Activity getStartActivity() {
        return this.startActivity;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public void setActivity(Activity activity, Activities type) {
        this.activity = activity;
        this.activity.setType(type);
    }

    @Override
    public void setLastActivity(Activity activity, Activities type) {
        this.lastActivity = activity;
        this.lastActivity.setType(type);
    }

    @Override
    public void setStartActivity(Activity activity, Activities type) {
        this.startActivity = activity;
        this.startActivity.setType(type);
    }

    @Override
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
