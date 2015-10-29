package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.session;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;

import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.enums.ShowMoneyType;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */

public class ReferenceWalletSession implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession{


    /**
     * Wallet type
     */
    InstalledWallet wallet;
    /**
     * Active objects in wallet session
     */
    Map<String,Object> data;

    private String accountName;

    /**
     * Navigation Estructure
     */


    /**
     * Basic wallet plugin
     */
    private CryptoWalletManager cryptoWalletManager;

    /**
     * Error manager addon
     */
    private ErrorManager errorManager;

    /**
     * Wallet Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;

    /**
     *  Wallet Settings
     */
    private WalletSettings walletSettings;

    /**
     * Event manager.
     */
    // Ver si esto va acá
    //private EventManager eventManager;

    /*
    * Options selected
    *
    */

    private String typeBalanceSelected =BalanceType.AVAILABLE.getCode();

    private int typeAmountSelected= ShowMoneyType.BITCOIN.getCode();

    //private WalletContact lastContactSelected;



    public ReferenceWalletSession(InstalledWallet installedWallet, CryptoWalletManager cryptoWalletManager,WalletSettings walletSettings,WalletResourcesProviderManager walletResourcesProviderManager, ErrorManager errorManager){//,EventManager eventManager){
        this.wallet=installedWallet;
        data= new HashMap<String,Object>();
        this.cryptoWalletManager=cryptoWalletManager;
        this.walletResourcesProviderManager=walletResourcesProviderManager;
        this.walletSettings=walletSettings;
        this.errorManager=errorManager;
    }


    public String getAccountName(){
        return accountName;
    }

    public void setAccountName(String accountName){
        this.accountName=accountName;
    }
//    public void setLastContactSelected(WalletContact walletContact){
//        this.lastContactSelected=walletContact;
//    }

    @Override
    public InstalledWallet getWalletSessionType() {
        return wallet;
    }

    @Override
    public void setData(String key, Object object) {
        data.put(key,object);
    }

    @Override
    public Object getData(String key) {
        return data.get(key);
    }

    public CryptoWalletManager getCryptoWalletManager() {
        return cryptoWalletManager;
    }
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    public String getBalanceTypeSelected() {
        return typeBalanceSelected;
    }

    public int getTypeAmount(){
        return typeAmountSelected;
    }

    public void setTypeAmount(ShowMoneyType showMoneyType){
        typeAmountSelected=showMoneyType.getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReferenceWalletSession that = (ReferenceWalletSession) o;

        return wallet == that.wallet;

    }

    @Override
    public int hashCode() {
        return wallet.hashCode();
    }

    public void setBalanceTypeSelected(BalanceType balaceType) {
        typeBalanceSelected=balaceType.getCode();
    }

    @Override
    public WalletResourcesProviderManager getWalletResourcesProviderManager() {
        return walletResourcesProviderManager;
    }

    /**
     *
     */
    @Override
    public WalletSettings getWalletSettings() {
        return this.walletSettings;
    }


    //public WalletContact getLastContactSelected() {
    //    return lastContactSelected;
    //}


}
