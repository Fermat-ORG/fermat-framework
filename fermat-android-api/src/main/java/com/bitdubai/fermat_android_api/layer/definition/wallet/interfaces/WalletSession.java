package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface WalletSession {

    public InstalledWallet getWalletSessionType();
    public void setData (String key,Object object);
    public Object getData (String key);
    public CryptoWalletManager getCryptoWalletManager();
    public ErrorManager getErrorManager();
    public String getBalanceTypeSelected();
    public void setBalanceTypeSelected(BalanceType balanceTypeSelected);

}
