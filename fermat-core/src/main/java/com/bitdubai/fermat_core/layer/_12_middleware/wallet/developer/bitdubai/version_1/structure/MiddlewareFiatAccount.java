package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.FiatAccount;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;

import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */

public class MiddlewareFiatAccount implements DealsWithPluginDatabaseSystem, FiatAccount  {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * FiatAccount Interface member variables.
     */
    UUID id;
    String label = "";
    String name = "";
    long balance = 0;
    FiatCurrency fiatCurrency;
    
    public MiddlewareFiatAccount (UUID id){
        this.id = id;
    }

    /**
     * MiddlewareFiatAccount interface implementation.
     */
    public void setBalance(Long balance){
        this.balance = balance;
    }

    public void setFiatCurrency(FiatCurrency fiatCurrency) {
        this.fiatCurrency = fiatCurrency;
    }
    
    public void persistToMedia() {
        

    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
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
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public double availableBalance (){
        
        return 0;
    }
    
}
