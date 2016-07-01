package com.bitdubai.reference_niche_wallet.fermat_wallet.session;


import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.CantGetFermatWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.CantListFermatWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.PaymentRequest;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.enums.ShowMoneyType;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */

public class FermatWalletSessionReferenceApp extends AbstractReferenceAppFermatSession<InstalledWallet,FermatWallet,WalletResourcesProviderManager> implements FermatSession {




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

    private FermatWalletWalletContact lastContactSelected;

    private PaymentRequest paymentRequest;
    private String communityConnection;


    public FermatWalletSessionReferenceApp() {
    }




    public FermatWalletSessionReferenceApp(String publicKey, InstalledWallet fermatApp, ErrorManager errorManager, FermatWallet moduleManager, WalletResourcesProviderManager resourceProviderManager) {
        super(publicKey, fermatApp, errorManager, moduleManager, resourceProviderManager);
    }


    public String getAccountName(){
        return accountName;
    }

    public void setAccountName(String accountName){
        this.accountName=accountName;
    }
    public void setLastContactSelected(FermatWalletWalletContact walletContact){
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

        FermatWalletSessionReferenceApp that = (FermatWalletSessionReferenceApp) o;

        return getFermatApp() == that.getFermatApp();

    }

    @Override
    public int hashCode() {
        return getFermatApp().hashCode();
    }

    public void setBalanceTypeSelected(BalanceType balaceType) {
        typeBalanceSelected=balaceType.getCode();
    }

    public FermatWalletWalletContact getLastContactSelected() {
        return lastContactSelected;
    }

    public PaymentRequest getLastRequestSelected() {
        return this.paymentRequest;
    }

    public ActiveActorIdentityInformation getIntraUserModuleManager() throws CantListFermatWalletIntraUserIdentityException, CantGetFermatWalletException {
        ActiveActorIdentityInformation cryptoWalletIntraUserIdentity = null;
        try {
            cryptoWalletIntraUserIdentity = getModuleManager().getSelectedActorIdentity();
        } catch (CantGetSelectedActorIdentityException e) {
            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }
        return cryptoWalletIntraUserIdentity;
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


    public List<FermatApp> getPosibleConnections() {
        return null;
    }
}
