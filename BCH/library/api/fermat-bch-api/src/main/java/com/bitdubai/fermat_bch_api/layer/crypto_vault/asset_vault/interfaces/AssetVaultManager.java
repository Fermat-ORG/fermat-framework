package com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantGetExtendedPublicKeyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantAddHierarchyAccountException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantDeriveNewKeysException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;

import org.bitcoinj.crypto.DeterministicKey;

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
     * @param genesisTransactionId
     * @param addressTo
     * @param amount
     * @return the generated Transaction Hash
     * @throws CantSendAssetBitcoinsToUserException
     */
    String sendAssetBitcoins(String genesisTransactionId, CryptoAddress addressTo, long amount) throws CantSendAssetBitcoinsToUserException;

    /**
     * Gets the amount of unused keys that are available from the passed account.
     * @param  account the hierarchy account to get the keys from
     * @return
     */
    int getAvailableKeyCount(HierarchyAccount account);


    /**
     * Derives the specified amount of keys in the selected account. Only some plugins can execute this method.
     * @param pluginId the pluginId invoking this call. Might not have permissions to create new keys.
     * @param account the account to derive keys from.
     * @param keysToDerive thre amount of keys to derive.
     * @throws CantDeriveNewKeysException
     */
    void deriveKeys(UUID pluginId, HierarchyAccount account, int keysToDerive) throws CantDeriveNewKeysException;

    /**
     * Creates a new hierarchy Account in the vault.
     * This will create the sets of keys and start monitoring the default network with these keys.
     * @param hierarchyAccount
     * @throws CantAddHierarchyAccountException
     */
    void addHierarchyAccount(HierarchyAccount hierarchyAccount) throws CantAddHierarchyAccountException;

    /**
     * Gets the Extended Public Key from the specified account. Can't be from a master account.
     * @param hierarchyAccount a Redeem Point account.
     * @return the DeterministicKey that will be used by the redeem Points.
     * @throws CantGetExtendedPublicKeyException
     */
    DeterministicKey getExtendedPublicKey (HierarchyAccount hierarchyAccount) throws CantGetExtendedPublicKeyException;

}
