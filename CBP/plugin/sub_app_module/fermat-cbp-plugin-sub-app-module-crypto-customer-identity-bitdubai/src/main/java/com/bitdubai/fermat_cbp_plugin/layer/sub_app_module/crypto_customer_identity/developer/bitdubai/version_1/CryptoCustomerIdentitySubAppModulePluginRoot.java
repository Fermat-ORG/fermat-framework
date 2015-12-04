package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_identity.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantCreateCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotCreateCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotPublishCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotUnPublishCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_identity.developer.bitdubai.version_1.structure.CryptoCustomerIdentityInformationImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia on 16.09.15.
 */
public class CryptoCustomerIdentitySubAppModulePluginRoot extends AbstractPlugin implements
        CryptoCustomerIdentityModuleManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CRYPTO_CUSTOMER)
    private CryptoCustomerIdentityManager identityManager;

    public CryptoCustomerIdentitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public CryptoCustomerIdentityInformation createCryptoCustomerIdentity(String cryptoCustomerName, byte[] profileImage) throws CouldNotCreateCryptoCustomerException {
        try {
            CryptoCustomerIdentity identity = this.identityManager.createCryptoCustomerIdentity(cryptoCustomerName, profileImage);
            return converIdentityToInformation(identity);
        } catch (CantCreateCryptoCustomerIdentityException e) {
            throw new CouldNotCreateCryptoCustomerException(CantCreateCryptoBrokerException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public void publishCryptoCustomerIdentity(String cryptoCustomerPublicKey) throws CouldNotPublishCryptoCustomerException {
        
    }

    @Override
    public void unPublishCryptoCustomerIdentity(String cryptoCustomerPublicKey) throws CouldNotUnPublishCryptoCustomerException {

    }

    @Override
    public List<CryptoCustomerIdentityInformation> getAllCryptoCustomersIdentities(int max, int offset) throws CantGetCryptoCustomerListException {
        try {
            List<CryptoCustomerIdentityInformation> cryptoCustomers = new ArrayList<>();
            for(CryptoCustomerIdentity identity : this.identityManager.getAllCryptoCustomerFromCurrentDeviceUser()){
                cryptoCustomers.add(converIdentityToInformation(identity));
            }
            return cryptoCustomers;
        } catch (CantGetCryptoCustomerIdentityException e) {
            throw new CantGetCryptoCustomerListException(CantGetCryptoCustomerListException.DEFAULT_MESSAGE, e, "","");
        }
    }

    private CryptoCustomerIdentityInformation converIdentityToInformation(final CryptoCustomerIdentity identity){
        return new CryptoCustomerIdentityInformationImpl(identity.getAlias(), identity.getPublicKey(), identity.getProfileImage(), identity.isPublished());
    }
}