package com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;

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

}
