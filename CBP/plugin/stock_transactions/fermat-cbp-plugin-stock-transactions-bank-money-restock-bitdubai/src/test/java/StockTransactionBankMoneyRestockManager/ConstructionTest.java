package StockTransactionBankMoneyRestockManager;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyRestockManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    @Mock
    private ErrorManager errorManager;

    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;

    private final UUID pluginId = UUID.randomUUID();

    @Test
    public void Construction_ValidParameters_NewObjectCreated(){

        StockTransactionBankMoneyRestockManager stockTransactionBankMoneyRestockManager = new StockTransactionBankMoneyRestockManager(
                this.pluginDatabaseSystem,
                this.pluginId,
                errorManager
        );
        assertThat(stockTransactionBankMoneyRestockManager).isNotNull();
    }
}
