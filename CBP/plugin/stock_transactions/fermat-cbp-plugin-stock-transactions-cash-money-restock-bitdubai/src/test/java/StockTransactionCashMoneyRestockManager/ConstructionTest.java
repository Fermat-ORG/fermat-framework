package StockTransactionCashMoneyRestockManager;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyRestockManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.junit.Before;
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
    private PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    private ErrorManager errorManager;


    private UUID pluginId;

    @Before
    public void setUp(){
        pluginId = UUID.randomUUID();
    }

    @Test
    public void Construction_ValidParameters_NewObjectCreated(){

        StockTransactionCashMoneyRestockManager stockTransactionCashMoneyDestockManager = new StockTransactionCashMoneyRestockManager(
                pluginDatabaseSystem,
                pluginId,
                errorManager
        );
        assertThat(stockTransactionCashMoneyDestockManager).isNotNull();
    }
}
