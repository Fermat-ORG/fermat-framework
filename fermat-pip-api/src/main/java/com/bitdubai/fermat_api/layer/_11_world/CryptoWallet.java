package com.bitdubai.fermat_api.layer._11_world;

import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantGetAddressBalanceException;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantGetWalletBalanceException;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantStartBlockchainInfoWallet;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * Created by ciencias on 3/12/15.
 */
public interface CryptoWallet {

    
    public void start() throws CantStartBlockchainInfoWallet;
    
    public void stop();
    
    public long getWalletBalance(CryptoCurrency cryptoCurrency) throws CantGetWalletBalanceException;
    
    public long getAddressBalance(CryptoAddress cryptoAddress) throws CantGetAddressBalanceException;
    
    public void sendCrypto (CryptoCurrency cryptoCurrency, long amount, CryptoAddress cryptoAddress) throws CantSendCryptoException;
    
    
}
