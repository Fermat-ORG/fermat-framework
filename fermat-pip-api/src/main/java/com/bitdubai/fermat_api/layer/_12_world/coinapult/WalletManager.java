/*
 * @#ICoinapultWalletManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer._12_world.coinapult;

import com.bitdubai.fermat_api.layer._12_world.coinapult.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer._12_world.coinapult.exceptions.CantValidateAddressException;
import com.bitdubai.fermat_api.layer._12_world.coinapult.wallet.CryptoWallet;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer._11_world.coinapult.interface</code>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 02/05/15.
 *
 * @version 1.0
 */
public interface WalletManager {

    /**
     * Create a new coinapult wallet
     *
     * @param agree If you are agree whit the terms of service of coinapult
     * @return UUID The New Id for the wallet
     */
    public UUID createNewCoinapultWallet(boolean agree) throws CantCreateWalletException;

    /**
     * Method responsible for obtain the coinapult wallet instance for this walletId
     *
     * @param walletId id of the wallet
     * @return ICoinapultWallet coinapult wallet instance or null
     */
    public CryptoWallet getCoinapultWallet(String walletId);

    /**
     * Method responsible for validate a bitcoin address
     *
     * @param bitcoinAddress Address to validate
     * @param walletId id of the wallet to use credential to connect to coinapult api
     * @return boolean (true/false)
     */
    public boolean validateBitcoinAddress(String bitcoinAddress, String walletId) throws CantValidateAddressException;
}
