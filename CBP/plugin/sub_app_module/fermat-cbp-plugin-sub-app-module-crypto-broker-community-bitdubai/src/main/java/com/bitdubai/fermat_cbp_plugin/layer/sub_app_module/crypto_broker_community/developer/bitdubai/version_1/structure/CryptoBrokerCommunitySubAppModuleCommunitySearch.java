package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.classes.CryptoBrokerCommunitySubAppModuleInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerSearchResult;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySearch;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure.CryptoBrokerCommunitySubAppModuleCommunitySearch</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/12/2015.
 */
public class CryptoBrokerCommunitySubAppModuleCommunitySearch implements CryptoBrokerCommunitySearch {

    private final CryptoBrokerManager cryptoBrokerActorNetworkServiceManager;

    public CryptoBrokerCommunitySubAppModuleCommunitySearch(final CryptoBrokerManager cryptoBrokerActorNetworkServiceManager) {

        this.cryptoBrokerActorNetworkServiceManager = cryptoBrokerActorNetworkServiceManager;
    }

    @Override
    public void addAlias(String alias) {

    }

    @Override
    public List<CryptoBrokerCommunityInformation> getResult(Integer max, Integer offset) throws CantGetCryptoBrokerSearchResult {

        try {

            CryptoBrokerSearch cryptoBrokerSearch = cryptoBrokerActorNetworkServiceManager.getSearch();

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = cryptoBrokerSearch.getResult(max, offset);

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for (CryptoBrokerExposingData cbed : cryptoBrokerExposingDataList)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(cbed));

            return cryptoBrokerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoBrokerSearchResult(exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoBrokerCommunityInformation> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer max, Integer offset) throws CantGetCryptoBrokerSearchResult {
        try {

            CryptoBrokerSearch cryptoBrokerSearch = cryptoBrokerActorNetworkServiceManager.getSearch();

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = cryptoBrokerSearch.getResult(publicKey, deviceLocation, distance, alias, max, offset);

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for (CryptoBrokerExposingData cbed : cryptoBrokerExposingDataList)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(cbed));

            return cryptoBrokerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoBrokerSearchResult(exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoBrokerCommunityInformation> getResultLocation(DeviceLocation deviceLocation, Integer max, Integer offset) throws CantGetCryptoBrokerSearchResult {

        try {

            CryptoBrokerSearch cryptoBrokerSearch = cryptoBrokerActorNetworkServiceManager.getSearch();

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = cryptoBrokerSearch.getResultLocation(deviceLocation, max, offset);

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for (CryptoBrokerExposingData cbed : cryptoBrokerExposingDataList)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(cbed));

            return cryptoBrokerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoBrokerSearchResult(exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoBrokerCommunityInformation> getResultDistance(double distance, Integer max, Integer offset) throws CantGetCryptoBrokerSearchResult {

        try {

            CryptoBrokerSearch cryptoBrokerSearch = cryptoBrokerActorNetworkServiceManager.getSearch();

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = cryptoBrokerSearch.getResultDistance(distance, max, offset);

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for (CryptoBrokerExposingData cbed : cryptoBrokerExposingDataList)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(cbed));

            return cryptoBrokerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoBrokerSearchResult(exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoBrokerCommunityInformation> getResultAlias(String alias, Integer max, Integer offset) throws CantGetCryptoBrokerSearchResult {

        try {

            CryptoBrokerSearch cryptoBrokerSearch = cryptoBrokerActorNetworkServiceManager.getSearch();

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = cryptoBrokerSearch.getResultAlias(alias, max, offset);

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for (CryptoBrokerExposingData cbed : cryptoBrokerExposingDataList)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(cbed));

            return cryptoBrokerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoBrokerSearchResult(exception, "", "Unhandled Error.");
        }
    }

    @Override
    public CryptoBrokerExposingData getResult(String publicKey) throws CantGetCryptoBrokerSearchResult {
        CryptoBrokerSearch cryptoBrokerSearch = cryptoBrokerActorNetworkServiceManager.getSearch();

        CryptoBrokerExposingData cryptoBrokerExposingData = null;

        try {
            cryptoBrokerExposingData = cryptoBrokerSearch.getResult(publicKey);
        } catch (CantListCryptoBrokersException e) {
            throw new CantGetCryptoBrokerSearchResult(e, "", "Unhandled Error.");
        }
        return cryptoBrokerExposingData;
    }
}
