package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.interfaces;


import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

import java.util.UUID;

/**
 * Created by loui on 16/02/15.
 */
public interface DiscountWalletManager {

    void loadWallet(UUID walletId) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CantLoadWalletException;

    void createWallet(UUID walletId, FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency) throws CantCreateWalletException;
    
    DiscountWallet getCurrentDiscountWallet();
}
