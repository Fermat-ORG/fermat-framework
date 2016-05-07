package StockTransactionBankMoneyDestockManager;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyDestockManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

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
    private final ErrorManager mErrorManager = new ErrorManager() {
        @Override
        public void reportUnexpectedPlatformException(PlatformComponents exceptionSource, UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedPluginException(Plugins exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedWalletException(Wallets exceptionSource, UnexpectedWalletExceptionSeverity unexpectedWalletExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedAddonsException(Addons exceptionSource, UnexpectedAddonsExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedSubAppException(SubApps exceptionSource, UnexpectedSubAppExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedUIException(UISource exceptionSource, UnexpectedUIExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedPluginException(PluginVersionReference exceptionSource, UnexpectedPluginExceptionSeverity exceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedAddonsException(AddonVersionReference exceptionSource, UnexpectedAddonsExceptionSeverity exceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedEventException(FermatEvent exceptionSource, Exception exception) {

        }
    };


    @Test
    public void Construction_ValidParameters_NewObjectCreated(){

        StockTransactionBankMoneyDestockManager stockTransactionBankMoneyDestockManager = new StockTransactionBankMoneyDestockManager(
                this.pluginDatabaseSystem,
                this.pluginId,
                this.mErrorManager
        );
        assertThat(stockTransactionBankMoneyDestockManager).isNotNull();
    }
}
