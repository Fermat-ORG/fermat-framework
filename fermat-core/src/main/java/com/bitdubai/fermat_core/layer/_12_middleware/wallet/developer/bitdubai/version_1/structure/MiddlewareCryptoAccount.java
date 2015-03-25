package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.AccountStatus;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.CryptoAccount;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.OperationFailed;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantNotUpdateRecord;

import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */

class MiddlewareCryptoAccount implements CryptoAccount{

    
    /**
     * MiddlewareCryptoAccount Interface member variables.
     */
    private DatabaseTable table;
    private DatabaseTableRecord record;

    /**
     * FiatAccount Interface member variables.
     */
    private UUID id;
    private String label = "";
    private String name = "";
    private long balance = 0;
    private CryptoCurrency cryptoCurrency;
    private AccountStatus status;
    
    /**
     * Class constructor.
     */
    MiddlewareCryptoAccount (UUID id){
        this.id = id;
    }

    /**
     * MiddlewareCryptoAccount interface implementation.
     *
     * Note that the following methods are package-private.
     * * * * 
     */
    UUID getId(){
        return this.id;
    }
    
    void setBalance(long balance){
        this.balance = balance;
    }

    void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    void setStatus(AccountStatus status) {
        this.status = status;
    }
    
    void setTable (DatabaseTable table) {
        this.table = table;
    }

    DatabaseTable getTable(){
        return this.table;
    }

    void setRecord (DatabaseTableRecord record){
        
        this.record = record;

        this.label = record.getStringValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_NAME);
        this.name = record.getStringValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_NAME);
        this.cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME));
        this.balance = record.getlongValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME);
        this.status = AccountStatus.getByCode(record.getStringValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_STATUS_COLUMN_NAME));

    }

    DatabaseTableRecord getRecord(){
        return this.record;
    }

    /**
     * CryptoAccount interface implementation.
     */
    public long getBalance() {
        return balance;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public void setLabel(String label) throws OperationFailed {

        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, label);
            this.table.updateRecord(this.record);
            this.label = label;
        }
        catch (CantNotUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }

    public void setName(String name) throws OperationFailed {

        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME_COLUMN_NAME, name);
            this.table.updateRecord(this.record);
            this.name = name;
        }
        catch (CantNotUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }

    public AccountStatus getStatus() {
        return this.status;
    }

    @Override
    public void openAccount() throws OperationFailed {

        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, AccountStatus.OPEN.getCode());
            this.table.updateRecord(this.record);
            this.status = AccountStatus.OPEN;
        }
        catch (CantNotUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }

    @Override
    public void closeAccount() throws OperationFailed {

        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, AccountStatus.CLOSED.getCode());
            this.table.updateRecord(this.record);
            this.status = AccountStatus.CLOSED;
        }
        catch (CantNotUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }

    @Override
    public void deleteAccount() throws OperationFailed {
        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, AccountStatus.DELETED.getCode());
            this.table.updateRecord(this.record);
            this.status = AccountStatus.DELETED;
        }
        catch (CantNotUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }

}
