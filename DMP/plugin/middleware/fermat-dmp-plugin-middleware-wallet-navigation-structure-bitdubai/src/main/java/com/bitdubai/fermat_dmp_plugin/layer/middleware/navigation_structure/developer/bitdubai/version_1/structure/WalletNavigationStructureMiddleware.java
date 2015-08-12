package com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.interfaces.WalletNavigationStructure;



/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.structure.WalletNavigationStructureMiddleware</code>
 * is the implementation of WalletNavigationStructure.
 * <p/>
 *
 * Created by Natalia on 07/08/15.
 * @version 1.0
 * @since Java JDK 1.7
 */


public class WalletNavigationStructureMiddleware implements WalletNavigationStructure {

    //Modified by Manuel Perez on 12/08/2015
    @Override
    public String getPublicKey() {
        return null;
    }

    @Override
    public Activity getActivity(Activities activities) {
        return null;
    }

    @Override
    public Activity getStartActivity() {
        return null;
    }

    @Override
    public void setStartActivity(Activities activity) {

    }

    @Override
    public Activity getLastActivity() {
        return null;
    }

    @Override
    public void setPublicKey(String publicKey) {

    }
}
