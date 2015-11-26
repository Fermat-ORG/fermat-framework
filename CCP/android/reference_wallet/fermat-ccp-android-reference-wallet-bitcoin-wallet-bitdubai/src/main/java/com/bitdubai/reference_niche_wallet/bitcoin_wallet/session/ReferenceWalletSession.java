package com.bitdubai.reference_niche_wallet.bitcoin_wallet.session;


import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */

public class ReferenceWalletSession extends AbstractFermatSession<InstalledWallet,CryptoWalletManager,WalletResourcesProviderManager> implements WalletSession {




    private String accountName;

    /**
     * Navigation Estructure
     */

    /**
     * Intra User Module
     */
    private IntraUserModuleManager intraUserModuleManager;


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

    public static String typeBalanceSelected =BalanceType.AVAILABLE.getCode();

    public static int typeAmountSelected= ShowMoneyType.BITCOIN.getCode();

    private CryptoWalletWalletContact lastContactSelected;

    private PaymentRequest paymentRequest;



    public ReferenceWalletSession(InstalledWallet installedWallet, CryptoWalletManager cryptoWalletManager,WalletSettings walletSettings,WalletResourcesProviderManager walletResourcesProviderManager, ErrorManager errorManager, IntraUserModuleManager intraUserModuleManager){//,EventManager eventManager){
        super(installedWallet.getWalletPublicKey(),installedWallet,errorManager,cryptoWalletManager,walletResourcesProviderManager);
        this.walletSettings=walletSettings;
        this.intraUserModuleManager = intraUserModuleManager;

    }


    public String getAccountName(){
        return accountName;
    }

    public void setAccountName(String accountName){
        this.accountName=accountName;
    }
    public void setLastContactSelected(CryptoWalletWalletContact walletContact){
        this.lastContactSelected=walletContact;
    }

    public void setLastRequestSelected(PaymentRequest paymentRequest){
        this.paymentRequest=paymentRequest;
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

        return getFermatApp() == that.getFermatApp();

    }

    @Override
    public int hashCode() {
        return getFermatApp().hashCode();
    }

    public void setBalanceTypeSelected(BalanceType balaceType) {
        typeBalanceSelected=balaceType.getCode();
    }

    /**
     *
     */
    @Override
    public WalletSettings getWalletSettings() {
        return this.walletSettings;
    }


    public CryptoWalletWalletContact getLastContactSelected() {
        return lastContactSelected;
    }

    public PaymentRequest getLastRequestSelected() {
        return this.paymentRequest;
    }

    public IntraUserModuleManager getIntraUserModuleManager() {
        return intraUserModuleManager;
    }


}
