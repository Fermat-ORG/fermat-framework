package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.AccountStatus;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.FiatAccount;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;

import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */

class MiddlewareFiatAccount implements  FiatAccount  {


    /**
     * MiddlewareFiatAccount Interface member variables.
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
    private FiatCurrency fiatCurrency;
    private AccountStatus status;

    /**
     * Class constructor.
     */
    MiddlewareFiatAccount (UUID id){
        this.id = id;
    }

    /**
     * MiddlewareFiatAccount interface implementation.
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

    void setFiatCurrency(FiatCurrency fiatCurrency) {
        this.fiatCurrency = fiatCurrency;
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
    }
    
    DatabaseTableRecord getRecord(){
        return this.record;
    }

    /**
     * FiatAccount interface implementation.
     */
    public long getBalance() {
        return balance;
    }

    public FiatCurrency getFiatCurrency() {
        return fiatCurrency;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public void setLabel(String label) {
        this.label = label;
        this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, this.label);
        this.table.updateRecord(this.record);
    }

    public void setName(String name) {
        this.name = name;
        this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME_COLUMN_NAME, this.name);
        this.table.updateRecord(this.record);
    }
    
    public AccountStatus getStatus() {
        return this.status;
    }
    
    public double availableBalance (){
        
        return 0;
    }

    @Override
    public void openAccount() {
        this.status = AccountStatus.OPEN;
        this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, this.status.getCode());
        this.table.updateRecord(this.record);
    }

    @Override
    public void closeAccount() {
        this.status = AccountStatus.CLOSED;
        this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, this.status.getCode());
        this.table.updateRecord(this.record);
    }

    @Override
    public void deleteAccount() {
        this.status = AccountStatus.DELETED;
        this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, this.status.getCode());
        this.table.updateRecord(this.record);
    }

}
