package com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_community.interfaces;



import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerSearchResult;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_community.interfaces.CryptoCustomerSearch</code>
 * provides the methods to search for a particular crypto customer
 */
public interface CryptoCustomerSearch {

    public void setNameToSearch(String nameToSearch);

    List<CryptoCustomerInformation> getResult() throws CantGetCryptoCustomerSearchResult;
}
