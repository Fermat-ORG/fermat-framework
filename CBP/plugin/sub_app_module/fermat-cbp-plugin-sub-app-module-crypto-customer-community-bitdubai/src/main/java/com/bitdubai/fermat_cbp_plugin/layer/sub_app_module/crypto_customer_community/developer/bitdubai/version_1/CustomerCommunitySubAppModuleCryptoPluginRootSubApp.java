package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;

import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantLoginCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantStartRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerConnectionRejectionFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerDisconnectingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySearch;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.settings.CryptoCustomerCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.List;

/**
 * Created by natalia on 16.09.15.
 */

/**

 */

public class CustomerCommunitySubAppModuleCryptoPluginRootSubApp extends AbstractPlugin implements CryptoCustomerCommunitySubAppModuleManager {

    /**
     * DealsWithErrors interface member variables
     */
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;


    public CustomerCommunitySubAppModuleCryptoPluginRootSubApp() {
        super(new PluginVersionReference(new Version()));
    }


    @Override
    public List<CryptoCustomerCommunityInformation> getSuggestionsToContact(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public CryptoCustomerCommunitySearch searchNewCryptoCustomer(CryptoCustomerCommunitySelectableIdentity cryptoCustomerCommunitySelectableIdentity) {
        return null;
    }

    @Override
    public void askCryptoCustomerForAcceptance(String cryptoCustomerToAddName, String cryptoCustomerToAddPublicKey, byte[] profileImage) throws CantStartRequestException {

    }

    @Override
    public void acceptCryptoCustomer(String cryptoCustomerToAddName, String cryptoCustomerToAddPublicKey, byte[] profileImage) throws CantAcceptRequestException {

    }

    @Override
    public void denyConnection(String cryptoCustomerToRejectPublicKey) throws CryptoCustomerConnectionRejectionFailedException {

    }

    @Override
    public void disconnectCryptoCustomer(String cryptoCustomerToDisconnectPublicKey) throws CryptoCustomerDisconnectingFailedException {

    }

    @Override
    public void cancelCryptoBroker(String cryptoCustomerToCancelPublicKey) throws CryptoCustomerCancellingFailedException {

    }

    @Override
    public List<CryptoCustomerCommunityInformation> getAllCryptoCustomers(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getCryptoCustomersWaitingYourAcceptance(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getCryptoCustomersWaitingTheirAcceptance(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public void login(String customerPublicKey) throws CantLoginCustomerException {

    }

    @Override
    public SettingsManager<CryptoCustomerCommunitySettings> getSettingsManager() {
        return null;
    }

    @Override
    public CryptoCustomerCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}