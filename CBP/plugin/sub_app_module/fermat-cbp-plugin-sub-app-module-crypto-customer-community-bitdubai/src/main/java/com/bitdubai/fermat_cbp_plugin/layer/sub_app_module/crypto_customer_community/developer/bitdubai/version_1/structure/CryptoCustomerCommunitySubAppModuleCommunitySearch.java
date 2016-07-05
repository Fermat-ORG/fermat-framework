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
    public List<CryptoCustomerCommunityInformation> getResult(Integer max, Integer offSet) throws CantGetCryptoCustomerSearchResult {

        try {

            CryptoCustomerSearch cryptoCustomerSearch = cryptoCustomerActorNetworkServiceManager.getSearch();

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = cryptoCustomerSearch.getResult(max, offSet);

            final List<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();

            for (CryptoCustomerExposingData customerExposingData : cryptoCustomerExposingDataList)
                cryptoCustomerCommunityInformationList.add(new CryptoCustomerCommunitySubAppModuleInformation(customerExposingData));

            return cryptoCustomerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoCustomerSearchResult("", exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer max, Integer offSet) throws CantGetCryptoCustomerSearchResult {

        try {

            CryptoCustomerSearch cryptoCustomerSearch = cryptoCustomerActorNetworkServiceManager.getSearch();

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = cryptoCustomerSearch.getResult(publicKey, deviceLocation, distance, alias, max, offSet);

            final List<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();

            for (CryptoCustomerExposingData cced : cryptoCustomerExposingDataList)
                cryptoCustomerCommunityInformationList.add(new CryptoCustomerCommunitySubAppModuleInformation(cced));

            return cryptoCustomerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoCustomerSearchResult("", exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getResultLocation(DeviceLocation deviceLocation, Integer max, Integer offSet) throws CantGetCryptoCustomerSearchResult {

        try {

            CryptoCustomerSearch cryptoCustomerSearch = cryptoCustomerActorNetworkServiceManager.getSearch();

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = cryptoCustomerSearch.getResultLocation(deviceLocation, max, offSet);

            final List<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();

            for (CryptoCustomerExposingData cced : cryptoCustomerExposingDataList)
                cryptoCustomerCommunityInformationList.add(new CryptoCustomerCommunitySubAppModuleInformation(cced));

            return cryptoCustomerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoCustomerSearchResult("", exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getResultDistance(double distance, Integer max, Integer offSet) throws CantGetCryptoCustomerSearchResult {

        try {

            CryptoCustomerSearch cryptoCustomerSearch = cryptoCustomerActorNetworkServiceManager.getSearch();

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = cryptoCustomerSearch.getResultDistance(distance, max, offSet);

            final List<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();

            for (CryptoCustomerExposingData cced : cryptoCustomerExposingDataList)
                cryptoCustomerCommunityInformationList.add(new CryptoCustomerCommunitySubAppModuleInformation(cced));

            return cryptoCustomerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoCustomerSearchResult("", exception, "", "Unhandled Error.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getResultAlias(String alias, Integer max, Integer offSet) throws CantGetCryptoCustomerSearchResult {

        try {

            CryptoCustomerSearch cryptoCustomerSearch = cryptoCustomerActorNetworkServiceManager.getSearch();

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = cryptoCustomerSearch.getResultAlias(alias, max, offSet);

            final List<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();

            for (CryptoCustomerExposingData cryptoCustomerExposingData : cryptoCustomerExposingDataList)
                cryptoCustomerCommunityInformationList.add(new CryptoCustomerCommunitySubAppModuleInformation(cryptoCustomerExposingData));

            return cryptoCustomerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoCustomerSearchResult("", exception, "", "Unhandled Error.");
        }
    }

    @Override
    public CryptoCustomerExposingData getResult(String publicKey) throws CantGetCryptoCustomerSearchResult {
        CryptoCustomerSearch cryptoCustomerSearchSearch = cryptoCustomerActorNetworkServiceManager.getSearch();

        CryptoCustomerExposingData cryptoCustomerExposingData = null;

        try {
            cryptoCustomerExposingData = cryptoCustomerSearchSearch.getResult(publicKey);
        } catch (Exception e) {
            throw new CantGetCryptoCustomerSearchResult("Unhandled Error.", e, "", "");
        }
        return cryptoCustomerExposingData;
    }
}
