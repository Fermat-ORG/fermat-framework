package com.bitdubai.fermat_api.layer._12_basic_wallet.discount_wallet.interfaces;


import com.bitdubai.fermat_api.layer._12_basic_wallet.discount_wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer._12_basic_wallet.discount_wallet.exceptions.CantLoadWalletException;

import java.util.UUID;

/**
 * Created by loui on 16/02/15.
 */
public interface DiscountWalletManager {

    public void loadWallet (UUID walletId) throws CantLoadWalletException;

    public void createWallet (UUID walletId) throws CantCreateWalletException;
    
    public DiscountWallet getCurrentDiscountWallet();
}
