package com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantCreateBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantGetActiveRedeemPointAddressesException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantGetActiveRedeemPointsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantGetExtendedPublicKeyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccountType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantAddHierarchyAccountException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantDeriveNewKeysException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;

import org.bitcoinj.crypto.DeterministicKey;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rodrigo on 9/20/15.
 */
public interface AssetVaultManager extends FermatManager, PlatformCryptoVault {

    /**
     * Will generate a CryptoAddress in the current network originated at the vault.
     * @return
     */
    CryptoAddress getNewAssetVaultCryptoAddress(BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException;


    /**
     * returns the sum of UTXO that was generated with the genesisTransaction
     * @param genesisTransaction
     * @return
     */
    long getAvailableBalanceForTransaction (String genesisTransaction);

    /**
     * Sends the bitcoins generated from the genesisTransactionId to the specified User Actor addres.
     * @param genesisTransactionHash
     * @param genesisBlockHash
     * @param addressTo
     * @return
     * @throws CantSendAssetBitcoinsToUserException
     */
    String sendAssetBitcoins(String genesisTransactionHash, String genesisBlockHash, CryptoAddress addressTo) throws CantSendAssetBitcoinsToUserException;

    /**
     * Gets the amount of unused keys that are available from the master account.

     * @return
     */
    int getAvailableKeyCount();


    /**
     * Derives the specified amount of keys in the master account. Only some plugins can execute this method.
     * @param plugin the plugin invoking this call. Might not have permissions to create new keys.
     * @param keysToDerive thre amount of keys to derive.
     * @throws CantDeriveNewKeysException
     */
    void deriveKeys(Plugins plugin, int keysToDerive) throws CantDeriveNewKeysException;

       /**
     * Gets the Extended Public Key from the specified account. Can't be from a master account.
     * @param redeemPointPublicKey a Redeem Point publicKey
     * @return the Extended Public Keythat will be used by the redeem Points.
     * @throws CantGetExtendedPublicKeyException
     */
    ExtendedPublicKey getRedeemPointExtendedPublicKey (String redeemPointPublicKey) throws CantGetExtendedPublicKeyException;

    /**
     * If the redeem point keys are initialized, will return all the generated addresses
     * @param redeemPointPublicKey
     * @return
     * @throws CantGetActiveRedeemPointAddressesException
     */
    List<CryptoAddress> getActiveRedeemPointAddresses (String redeemPointPublicKey) throws CantGetActiveRedeemPointAddressesException;

    /**
     * Returns the private Keys of all the active Redeem Points hierarchies in the asset vault
     * @return
     */
    List<String> getActiveRedeemPoints() throws CantGetActiveRedeemPointsException;

    /**
     * When we receive assets from a Redeemption processes, the Issuer that granted the extended public key to the redeem point
     * needs to inform us when an address is used, so we can generate more if needed.
     * @param cryptoAddress
     * @param redeemPointPublicKey
     */
    void notifyUsedRedeemPointAddress(CryptoAddress cryptoAddress, String redeemPointPublicKey);

    /**
     * Based on the passed transaction chain of Transactions hashes and Blocks hashes, determines the entire path
     * of the chain until the Genesis Transaction is reached.
     * The genesis Transaction will be the first transaction in the map.
     * @param transactionChain a Map with the form TransactionHash / BlockHash
     * @return the CryptoTransaction that represents the GenesisTransaction
     * @throws CantGetCryptoTransactionException
     */
    CryptoTransaction getGenesisTransaction(Map<String, String> transactionChain) throws CantGetCryptoTransactionException;

    /**
     * Will create a Bitcoin transaction and prepare it to be broadcasted later.
     * This transaction locks the bitcoins associated with the passed input (if valid).
     * @param inputTransaction the Transaction hash that will be used to get the funds from.
     * @param addressTo the destination of the bitcoins.
     * @return the Transaction Hash of the new transaction
     * @throws CantCreateBitcoinTransactionException
     */
    String createBitcoinTransaction (String inputTransaction, CryptoAddress addressTo) throws CantCreateBitcoinTransactionException;

}
