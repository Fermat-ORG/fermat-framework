package com.bitdubai.reference_niche_wallet.loss_protected_wallet.session;


import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;

import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */

public class LossProtectedWalletSession extends AbstractFermatSession<InstalledWallet,LossProtectedWalletManager,WalletResourcesProviderManager>  {




    private String accountName;
    /**
     * Navigation Estructure
     */

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

    private LossProtectedWalletContact lastContactSelected;

    private LossProtectedWalletTransaction lossProtectedWalletTransaction;

    private LossProtectedPaymentRequest paymentRequest;
    private String communityConnection;

    private double actualExchangeRate;

    private UUID transactionDetailId;
    public LossProtectedWalletSession() {
    }


    public LossProtectedWalletSession(String publicKey, InstalledWallet fermatApp, ErrorManager errorManager, LossProtectedWalletManager moduleManager, WalletResourcesProviderManager resourceProviderManager) {
        super(publicKey, fermatApp, errorManager, moduleManager, resourceProviderManager);
    }


    public void setTransactionDetailId(UUID transactionDetailId){
        this.transactionDetailId = transactionDetailId;
    }
    public  UUID getTransactionDetailId(){return transactionDetailId;}

    public void setActualExchangeRate(double rate){this.actualExchangeRate = rate; }
    public double getActualExchangeRate(){return actualExchangeRate;}

    public String getAccountName(){
        return accountName;
    }

    public void setAccountName(String accountName){
        this.accountName=accountName;
    }
    public void setLastContactSelected(LossProtectedWalletContact walletContact){
        this.lastContactSelected=walletContact;
    }

    public void setLastRequestSelected(LossProtectedPaymentRequest paymentRequest){
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

        LossProtectedWalletSession that = (LossProtectedWalletSession) o;

        return getFermatApp() == that.getFermatApp();

    }

    @Override
    public int hashCode() {
        return getFermatApp().hashCode();
    }

    public void setBalanceTypeSelected(BalanceType balaceType) {
        typeBalanceSelected=balaceType.getCode();
    }

    public LossProtectedWalletContact getLastContactSelected() {
        return lastContactSelected;
    }

    public LossProtectedPaymentRequest getLastRequestSelected() {
        return this.paymentRequest;
    }

    public LossProtectedWalletIntraUserIdentity getIntraUserModuleManager() throws CantListCryptoWalletIntraUserIdentityException, CantGetCryptoLossProtectedWalletException {
        List<LossProtectedWalletIntraUserIdentity> lst = null;
        try {
            lst = getModuleManager().getCryptoWallet().getAllIntraWalletUsersFromCurrentDeviceUser();
        } catch (CantListLossProtectedWalletIntraUserIdentityException e) {
            e.printStackTrace();
        } catch (CantGetCryptoLossProtectedWalletException e) {
            e.printStackTrace();
        }
        return (lst.isEmpty()) ? null : lst.get(0);
    }

    public String getCommunityConnection() {
        //return searchConnectionPublicKey(SubApps.CCP_INTRA_USER_COMMUNITY.getCode());
        return "public_key_intra_user_commmunity";
    }

    private String searchConnectionPublicKey(String code){
        for(FermatApp fermatApp : getPosibleConnections()){
            if(fermatApp.getAppName().equals(code)){
                return fermatApp.getAppPublicKey();
            }
        }
        return null;
    }
}
