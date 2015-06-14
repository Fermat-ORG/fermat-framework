package com.bitdubai.fermat_api.layer._12_world;

import com.bitdubai.fermat_api.layer._12_world.wallet.exceptions.CantGetAddressBalanceException;
import com.bitdubai.fermat_api.layer._12_world.wallet.exceptions.CantGetAddressesException;
import com.bitdubai.fermat_api.layer._12_world.wallet.exceptions.CantGetNewAddressException;
import com.bitdubai.fermat_api.layer._12_world.wallet.exceptions.CantGetWalletBalanceException;
import com.bitdubai.fermat_api.layer._12_world.wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_api.layer._12_world.wallet.exceptions.CantStartWallet;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;

import java.util.List;

/**
 * Created by ciencias on 3/12/15.
 */
public interface CryptoWallet {


    public void start() throws CantStartWallet;

    public void stop();

    public long getWalletBalance(CryptoCurrency cryptoCurrency) throws CantGetWalletBalanceException;

    public long getAddressBalance(CryptoAddress cryptoAddress) throws CantGetAddressBalanceException;

    // to get all the addresses in a wallet
    public List<CryptoAddress> getAddresses() throws CantGetAddressesException;

    // to create new address in a wallet
    public CryptoAddress getNewAddress() throws CantGetNewAddressException;

    // added CryptoAddressFrom to choose the address where i sending from in my wallet
    // can be null, and the method haves to use the first or primary address in wallet
    public void sendCrypto (CryptoAddress cryptoAddressFrom, CryptoCurrency cryptoCurrency, long amount, CryptoAddress cryptoAddressTo) throws CantSendCryptoException;

}