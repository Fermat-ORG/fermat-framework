package com.bitdubai.reference_niche_wallet.bitcoin_wallet.session;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class WalletSession implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession{


    /**
     * Wallet type
     */
    Wallets walletType;
    /**
     * Active objects in wallet session
     */
    Map<String,Object> data;
    /**
     * Basic wallet
     */
    private CryptoWalletManager cryptoWalletManager;

    /**
     * Error manager
     */
    private ErrorManager errorManager;

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




    public WalletSession(Wallets wallets,CryptoWalletManager cryptoWalletManager,ErrorManager errorManager){//,EventManager eventManager){
        walletType=wallets;
        data= new HashMap<String,Object>();
        this.cryptoWalletManager=cryptoWalletManager;
        //this.eventManager=eventManager;
        this.errorManager=errorManager;
    }

    public WalletSession(Wallets walletType) {
        this.walletType = walletType;
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

    public CryptoWalletManager getCryptoWalletManager() {
        return cryptoWalletManager;
    }
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    @Override
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

        WalletSession that = (WalletSession) o;

        return walletType == that.walletType;

    }

    @Override
    public int hashCode() {
        return walletType.hashCode();
    }

    public void setBalanceTypeSelected(BalanceType balaceType) {
        typeBalanceSelected=balaceType.getCode();
    }
}
