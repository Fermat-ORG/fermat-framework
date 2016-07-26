package StockTransactionCashMoneyDestockFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyDestockFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private final PluginDatabaseSystem pluginDatabaseSystem = new PluginDatabaseSystem() {
        @Override
        public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
            return null;
        }

        @Override
        public void deleteDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {

        }

        @Override
        public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
            return null;
        }
    };
    private final UUID pluginId = UUID.randomUUID();

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {

        StockTransactionCashMoneyDestockFactory stockTransactionCryptoMoneyDestockFactory = new StockTransactionCashMoneyDestockFactory(
                this.pluginDatabaseSystem,
                this.pluginId
        );
        assertThat(stockTransactionCryptoMoneyDestockFactory).isNotNull();
    }
}
