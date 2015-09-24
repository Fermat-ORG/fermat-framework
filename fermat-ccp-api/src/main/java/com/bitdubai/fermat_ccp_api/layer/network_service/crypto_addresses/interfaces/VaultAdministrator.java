package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantIdentifyVaultException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface VaultAdministrator {

    CryptoVaultManager getVaultByCryptoCurrency(CryptoCurrency cryptoCurrency) throws CantIdentifyVaultException;

}
