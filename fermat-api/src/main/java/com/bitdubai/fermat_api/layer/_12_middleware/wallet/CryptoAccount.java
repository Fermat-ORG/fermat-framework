package com.bitdubai.fermat_api.layer._12_middleware.wallet;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.OperationFailed;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

/**
 * Created by ciencias on 2/15/15.
 */
public interface CryptoAccount {
    
    public long getBalance();

    public CryptoCurrency getCryptoCurrency() ;

    public String getLabel() ;

    public String getName() ;

    public void setLabel(String label) throws OperationFailed;

    public void setName(String name) throws OperationFailed;

    public AccountStatus getStatus();

    public void openAccount() throws OperationFailed;

    public void closeAccount() throws OperationFailed;

    public void deleteAccount() throws OperationFailed;
}
