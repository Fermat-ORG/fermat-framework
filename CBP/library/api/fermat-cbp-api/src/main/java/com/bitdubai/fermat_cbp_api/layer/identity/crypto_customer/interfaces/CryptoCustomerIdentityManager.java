package com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantCreateCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;

import java.util.List;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface CryptoCustomerIdentityManager extends FermatManager {

    List<CryptoCustomerIdentity> getAllCryptoCustomerFromCurrentDeviceUser() throws CantGetCryptoCustomerIdentityException;

    CryptoCustomerIdentity createCryptoCustomerIdentity(final String alias, final byte[] profileImage) throws CantCreateCryptoCustomerIdentityException;

}
