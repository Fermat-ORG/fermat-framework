package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface WalletSession {

    public Wallets getWalletSessionType();
    public void setData (String key,Object object);
    public Object getData (String key);

}
