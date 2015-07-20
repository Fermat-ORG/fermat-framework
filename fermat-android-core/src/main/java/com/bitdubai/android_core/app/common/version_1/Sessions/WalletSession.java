package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Wallets;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class WalletSession implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession{

    Wallets walletType;
    Map<String,Object> data;


    public WalletSession(Wallets wallets){
        walletType=wallets;
        data= new HashMap<String,Object>();
    }

    @Override
    public Wallets getWalletSessionType() {
        return walletType;
    }

    @Override
    public void setData(String key, Object object) {
        data.put(key,object);
    }

    @Override
    public Object getData(String key) {
        return data.get(key);
    }
}
