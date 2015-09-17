package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.interfaces.WalletNavigationStructure</code>
 * indicates the functionality of a WalletNavigationStructure
 * <p/>
 *
 * Created by natalia on 07/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletNavigationStructure {

    //Modified by Manuel Perez on 12/08/2015
    //This interface is based on com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWallet

    public Activity getActivity();

    public Activity getLastActivity();

    public String getPublicKey();

    public Activity getStartActivity();

    public void setActivity(Activity activity, Activities type);

    public void setLastActivity(Activity activity, Activities type);

    public void setPublicKey(String publicKey);

    public void setStartActivity(Activity activity, Activities type);

}
