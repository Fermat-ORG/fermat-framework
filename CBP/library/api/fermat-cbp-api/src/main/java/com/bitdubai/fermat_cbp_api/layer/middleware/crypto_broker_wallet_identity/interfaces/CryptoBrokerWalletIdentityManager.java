package com.bitdubai.fermat_cbp_api.layer.middleware.crypto_broker_wallet_identity.interfaces;

import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;

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

    void newAssociationWallet(UUID Wallet, CryptoBrokerWalletIdentity walletIdentity) throws com.bitdubai.fermat_cbp_api.layer.middleware.crypto_broker_wallet_identity.exceptions.CantAssociationWalletException;

    void deleteAssociationWallet(UUID Wallet, CryptoBrokerWalletIdentity walletIdentity) throws com.bitdubai.fermat_cbp_api.layer.middleware.crypto_broker_wallet_identity.exceptions.CantDeleteAssociationWalletException;

    CryptoBrokerWalletIdentity createCryptoBrokerWalletIdentity(CryptoBrokerIdentity identity, UUID Wallet) throws com.bitdubai.fermat_cbp_api.layer.middleware.crypto_broker_wallet_identity.exceptions.CantCreateCryptoBrokerWalletIdentityException;

    void deleteCryptoBrokerWalletIdentity(CryptoCustomerIdentity identity) throws com.bitdubai.fermat_cbp_api.layer.middleware.crypto_broker_wallet_identity.exceptions.CantDeleteCryptoBrokerWalletIdentityException;

}
