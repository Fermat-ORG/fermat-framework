package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantLoginCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantStartRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerConnectionRejectionFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerDisconnectingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerSearch;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by natalia on 16.09.15.
 * Updated by Nelson Ramirez on 17.12.2015.
 */
public class CommunitySubAppModuleCryptoBrokenPluginRoot extends AbstractPlugin implements CryptoBrokerCommunityModuleManager {

    /**
     * DealsWithErrors interface member variables
     */
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;


    public CommunitySubAppModuleCryptoBrokenPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<CryptoBrokerInformation> getSuggestionsToContact(int max, int offset) throws CantGetCryptoBrokerListException {
        return null;
    }

    @Override
    public CryptoBrokerSearch searchCryptoBroker() {
        return null;
    }

    @Override
    public void askCryptoBrokerForAcceptance(String cryptoBrokerToAddName, String cryptoBrokerToAddPublicKey, byte[] profileImage) throws CantStartRequestException {

    }

    @Override
    public void acceptCryptoBroker(String cryptoBrokerToAddName, String cryptoBrokerToAddPublicKey, byte[] profileImage) throws CantAcceptRequestException {

    }

    @Override
    public void denyConnection(String cryptoBrokerToRejectPublicKey) throws CryptoBrokerConnectionRejectionFailedException {

    }

    @Override
    public void disconnectCryptoBroker(String cryptoBrokerToDisconnectPublicKey) throws CryptoBrokerDisconnectingFailedException {

    }

    @Override
    public void cancelCryptoBroker(String cryptoBrokerToCancelPublicKey) throws CryptoBrokerCancellingFailedException {

    }

    @Override
    public List<CryptoBrokerInformation> getAllCryptoBrokers(int max, int offset) throws CantGetCryptoBrokerListException {
        return null;
    }

    @Override
    public List<CryptoBrokerInformation> getCryptoBrokersWaitingYourAcceptance(int max, int offset) throws CantGetCryptoBrokerListException {
        return null;
    }

    @Override
    public List<CryptoBrokerInformation> getCryptoBrokersWaitingTheirAcceptance(int max, int offset) throws CantGetCryptoBrokerListException {
        return null;
    }

    @Override
    public void login(String customerPublicKey) throws CantLoginCustomerException {

    }
}