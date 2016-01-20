package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
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
    public List<CryptoBrokerCommunityInformation> getResult() throws CantGetCryptoBrokerSearchResult {

        try {

            CryptoBrokerSearch cryptoBrokerSearch = cryptoBrokerActorNetworkServiceManager.getSearch();

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = cryptoBrokerSearch.getResult();

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for(CryptoBrokerExposingData cbed : cryptoBrokerExposingDataList)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(cbed));

            return cryptoBrokerCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetCryptoBrokerSearchResult(exception, "", "Unhandled Error.");
        }
    }
}
