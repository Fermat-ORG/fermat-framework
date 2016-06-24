package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerSearchResult;

import java.io.Serializable;
import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySearch</code>
 * provides the methods to search for a particular crypto broker
 */
public interface CryptoBrokerCommunitySearch extends Serializable {

    void addAlias(String alias);

    List<CryptoBrokerCommunityInformation> getResult() throws CantGetCryptoBrokerSearchResult;

    List<CryptoBrokerCommunityInformation> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer offSet, Integer max) throws CantGetCryptoBrokerSearchResult;

    List<CryptoBrokerCommunityInformation> getResultLocation(DeviceLocation deviceLocation) throws CantGetCryptoBrokerSearchResult;

    List<CryptoBrokerCommunityInformation> getResultDistance(double distance) throws CantGetCryptoBrokerSearchResult;

    List<CryptoBrokerCommunityInformation> getResultAlias(String alias) throws CantGetCryptoBrokerSearchResult;

}
