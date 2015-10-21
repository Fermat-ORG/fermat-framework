package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.interfaces;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.AccountStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

/**
 * Created by ciencias on 2/15/15.
 */
public interface Account {

    public long getBalance() ;

    public double getAvailableBalance ();

    public FiatCurrency getFiatCurrency() ;

    public String getLabel() ;

    public String getName() ;

    public void setLabel(String label) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.OperationFailed;

    public void setName(String name) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.OperationFailed;

    public AccountStatus getStatus();

    public void openAccount() throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.OperationFailed;
    
    public void closeAccount() throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.OperationFailed;
    
    public void deleteAccount() throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.OperationFailed;
}
