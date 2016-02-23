package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.interfaces;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.AccountStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

/**
 * Created by ciencias on 2/15/15.
 */
public interface Account {

    long getBalance() ;

    double getAvailableBalance();

    FiatCurrency getFiatCurrency() ;

    String getLabel() ;

    String getName() ;

    void setLabel(String label) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.OperationFailed;

    void setName(String name) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.OperationFailed;

    AccountStatus getStatus();

    void openAccount() throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.OperationFailed;
    
    void closeAccount() throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.OperationFailed;
    
    void deleteAccount() throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.OperationFailed;
}
