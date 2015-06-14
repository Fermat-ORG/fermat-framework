package com.bitdubai.fermat_api.layer._13_basic_wallet.discount_wallet.interfaces;


import com.bitdubai.fermat_api.layer._13_basic_wallet.discount_wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer._13_basic_wallet.discount_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

import java.util.UUID;

/**
 * Created by loui on 16/02/15.
 */
public interface DiscountWalletManager {

    public void loadWallet (UUID walletId) throws CantLoadWalletException;

    public void createWallet (UUID walletId, FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency) throws CantCreateWalletException;
    
    public DiscountWallet getCurrentDiscountWallet();
}
