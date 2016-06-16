package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerSearchResult;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */
public class CryptoCustomerCommunitySubAppModuleCommunitySearch implements CryptoCustomerCommunitySearch {

    private final CryptoCustomerManager cryptoCustomerActorNetworkServiceManager;

    public CryptoCustomerCommunitySubAppModuleCommunitySearch(final CryptoCustomerManager cryptoCustomerActorNetworkServiceManager) {

        this.cryptoCustomerActorNetworkServiceManager = cryptoCustomerActorNetworkServiceManager;
    }

    @Override
    public void addAlias(String alias) {

    }

    @Override
    public List<CryptoCustomerCommunityInformation> getResult() throws CantGetCryptoCustomerSearchResult {

        try {

            CryptoCustomerSearch cryptoCustomerSearch = cryptoCustomerActorNetworkServiceManager.getSearch();

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = cryptoCustomerSearch.getResult();

            final List<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();

            for(CryptoCustomerExposingData cced : cryptoCustomerExposingDataList)
                cryptoCustomerCommunityInformationList.add(new CryptoCustomerCommunitySubAppModuleInformation(cced));

            return cryptoCustomerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoCustomerSearchResult("", exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getResultLocation(DeviceLocation deviceLocation) throws CantGetCryptoCustomerSearchResult {

        try {

            CryptoCustomerSearch cryptoCustomerSearch = cryptoCustomerActorNetworkServiceManager.getSearch();

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = cryptoCustomerSearch.getResultLocation(deviceLocation);

            final List<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();

            for(CryptoCustomerExposingData cced : cryptoCustomerExposingDataList)
                cryptoCustomerCommunityInformationList.add(new CryptoCustomerCommunitySubAppModuleInformation(cced));

            return cryptoCustomerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoCustomerSearchResult("", exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getResultDistance(double distance) throws CantGetCryptoCustomerSearchResult {

        try {

            CryptoCustomerSearch cryptoCustomerSearch = cryptoCustomerActorNetworkServiceManager.getSearch();

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = cryptoCustomerSearch.getResultDistance(distance);

            final List<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();

            for(CryptoCustomerExposingData cced : cryptoCustomerExposingDataList)
                cryptoCustomerCommunityInformationList.add(new CryptoCustomerCommunitySubAppModuleInformation(cced));

            return cryptoCustomerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoCustomerSearchResult("", exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getResultAlias(String alias) throws CantGetCryptoCustomerSearchResult {

        try {

            CryptoCustomerSearch cryptoCustomerSearch = cryptoCustomerActorNetworkServiceManager.getSearch();

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = cryptoCustomerSearch.getResultAlias(alias);

            final List<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();

            for(CryptoCustomerExposingData cced : cryptoCustomerExposingDataList)
                cryptoCustomerCommunityInformationList.add(new CryptoCustomerCommunitySubAppModuleInformation(cced));

            return cryptoCustomerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoCustomerSearchResult("", exception, "", "Unhandled Error.");
        }
    }
}
