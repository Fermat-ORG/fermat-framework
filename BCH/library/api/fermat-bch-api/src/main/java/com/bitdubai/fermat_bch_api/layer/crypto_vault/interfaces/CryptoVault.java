package com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 11/3/15.
 */
public interface CryptoVault {

    /**
     * Gets the list of Platforms this Crypto Vault will be taking requests from.
     * @return List of enabled platforms
     */
    List<Platforms> getServingPlatforms();

    /**
     * Gets the list of Actors this Crypto Vault will be taking requests from.
     * @return the list of enabled Actors.
     */
    List<Actors> getServingActors();

    /**
     * Sends bitcoins immediately to the specified destination address
     * @param fermatTxId The internal transactionId as part of the Crypto Transmission protocol
     * @param addressTo The destination Crypto Address
     * @param amount The amount of bitcoins that will be sent, expressed in satoshis.
     * @param op_Return optional parameter to include an OP_Return value in a new transaction output.
     * @return The Transaction hash created for this operation.
     * @throws InsufficientCryptoFundsException if we don't have enough funds to full fil this request.
     * @throws InvalidSendToAddressException if the specified CryptoAddress is not valid.
     * @throws CouldNotSendMoneyException Any uncaught exception.
     */
    String sendBitcoins(UUID fermatTxId, CryptoAddress addressTo, long amount, @Nullable String op_Return)
            throws InsufficientCryptoFundsException,
            InvalidSendToAddressException,
            CouldNotSendMoneyException;

}
