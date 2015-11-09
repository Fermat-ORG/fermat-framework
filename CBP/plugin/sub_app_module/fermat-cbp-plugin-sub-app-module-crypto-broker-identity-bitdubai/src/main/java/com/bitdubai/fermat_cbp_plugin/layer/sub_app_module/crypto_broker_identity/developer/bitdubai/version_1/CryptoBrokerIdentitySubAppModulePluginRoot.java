package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.exceptions.CantCreateCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.exceptions.CantGetCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.DealsWithCryptoBrokerIdentities;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotUnPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1.structure.CryptoBrokerIdentityInformationImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel 16/10/2015
 */
public class CryptoBrokerIdentitySubAppModulePluginRoot extends AbstractPlugin implements
        CryptoBrokerIdentityModuleManager {

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CRYPTO_BROKER)
    private CryptoBrokerIdentityManager identityManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    public CryptoBrokerIdentitySubAppModulePluginRoot() {
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

    public List<CryptoBrokerIdentity> getAllCryptoBrokersFromCurrentDeviceUser() throws CantGetCryptoBrokerIdentityException {
        return this.identityManager.getAllCryptoBrokersFromCurrentDeviceUser();
    }

    @Override
    public CryptoBrokerIdentityInformation createCryptoBrokerIdentity(String cryptoBrokerName, byte[] profileImage) throws CouldNotCreateCryptoBrokerException {
        try {
            CryptoBrokerIdentity identity = this.identityManager.createCryptoBrokerIdentity(cryptoBrokerName,profileImage);
            return converIdentityToInformation(identity);
        } catch (CantCreateCryptoBrokerIdentityException e) {
            throw new CouldNotCreateCryptoBrokerException(CouldNotCreateCryptoBrokerException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public void publishCryptoBrokerIdentity(String cryptoBrokerPublicKey) throws CouldNotPublishCryptoBrokerException {

    }

    @Override
    public void unPublishCryptoBrokerIdentity(String cryptoBrokerPublicKey) throws CouldNotUnPublishCryptoBrokerException {

    }

    @Override
    public List<CryptoBrokerIdentityInformation> getAllCryptoBrokersIdentities(int max, int offset) throws CantGetCryptoBrokerListException {
        try {
            List<CryptoBrokerIdentityInformation> cryptoBrokers = new ArrayList<>();
            for(CryptoBrokerIdentity identity : this.identityManager.getAllCryptoBrokersFromCurrentDeviceUser()){
                cryptoBrokers.add(converIdentityToInformation(identity));
            }
            return cryptoBrokers;
        } catch (CantGetCryptoBrokerIdentityException e) {
            throw new CantGetCryptoBrokerListException(CantGetCryptoBrokerListException.DEFAULT_MESSAGE, e, "","");
        }
    }

    private CryptoBrokerIdentityInformation converIdentityToInformation(final CryptoBrokerIdentity identity){
        return new CryptoBrokerIdentityInformationImpl(identity.getAlias(), identity.getPublicKey(), identity.getProfileImage(), identity.isPublished());
    }
}