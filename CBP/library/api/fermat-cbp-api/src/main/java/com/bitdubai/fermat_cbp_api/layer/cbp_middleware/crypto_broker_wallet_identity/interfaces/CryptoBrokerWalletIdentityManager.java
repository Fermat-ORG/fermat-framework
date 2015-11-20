package com.bitdubai.fermat_cbp_api.layer.cbp_middleware.crypto_broker_wallet_identity.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.crypto_broker_wallet_identity.exceptions.CantAssociationWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.crypto_broker_wallet_identity.exceptions.CantCreateCryptoBrokerWalletIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.crypto_broker_wallet_identity.exceptions.CantDeleteAssociationWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.crypto_broker_wallet_identity.exceptions.CantDeleteCryptoBrokerWalletIdentityException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 17/9/15.
 */
public interface CryptoBrokerWalletIdentityManager {

    List<CryptoBrokerWalletIdentity> getAllCryptoBrokerWalletIdentity();

    List<CryptoBrokerWalletIdentity> getAllCryptoBrokerWalletIdentityAssociatedWithAWallet(UUID Wallet);

    CryptoBrokerWalletIdentity getCryptoBrokerWalletIdentityByIdentity(CryptoBrokerIdentity identity);

    CryptoBrokerWalletIdentity getCryptoBrokerWalletIdentityByWallet(UUID Wallet);

    void newAssociationWallet(UUID Wallet, CryptoBrokerWalletIdentity walletIdentity) throws CantAssociationWalletException;

    void deleteAssociationWallet(UUID Wallet, CryptoBrokerWalletIdentity walletIdentity) throws CantDeleteAssociationWalletException;

    CryptoBrokerWalletIdentity createCryptoBrokerWalletIdentity(CryptoBrokerIdentity identity, UUID Wallet) throws CantCreateCryptoBrokerWalletIdentityException;

    void deleteCryptoBrokerWalletIdentity(CryptoCustomerIdentity identity) throws CantDeleteCryptoBrokerWalletIdentityException;

}
