package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.AccountStatus;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.FiatAccount;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.OperationFailed;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantUpdateRecord;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.exceptions.CantStartAccountException;
import com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.interfaces.AccountService;

import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */

class MiddlewareFiatAccount implements  AccountService, DealsWithEvents,FiatAccount  {


    /**
     * MiddlewareFiatAccount Interface member variables.
     */
    private DatabaseTable table;
    private DatabaseTableRecord record;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

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

        this.label= record.getStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME);
        this.name = record.getStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME_COLUMN_NAME);
        this.fiatCurrency = FiatCurrency.getByCode(record.getStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME));
        this.balance = record.getlongValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME);
        this.status = AccountStatus.getByCode(record.getStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME));
    
    }
    
    DatabaseTableRecord getRecord(){
        return this.record;
    }

    /**
     * AccountService interface implementation.
     */
    @Override
    public void start() throws CantStartAccountException {

        // LOUI TODO; tiene que escuchar el evento
        
        
    }

    @Override
    public void stop() {

    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
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

    public void setLabel(String label) throws OperationFailed {

        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, label);
            this.table.updateRecord(this.record);
            this.label = label;
        }
        catch (CantUpdateRecord cantUpdateRecord) {
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
        catch (CantUpdateRecord cantUpdateRecord) {
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
    
    public double availableBalance (){
        
        return 0;
    }

    @Override
    public void openAccount() throws OperationFailed {

        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, AccountStatus.OPEN.getCode());
            this.table.updateRecord(this.record);
            this.status = AccountStatus.OPEN;
        }
        catch (CantUpdateRecord cantUpdateRecord) {
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
        catch (CantUpdateRecord cantUpdateRecord) {
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
        catch (CantUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }


}
