package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_factory.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseSelectOperatorType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseVariable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantSelectRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginDatabaseSystem;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class updateTransactionProtocolStatusTest {

    @Mock
    ErrorManager errorManager;

    @Mock
    EventManager eventManager;

    MockedPluginDatabaseSystem mockedPluginDatabaseSystem;

    public void updateTest() throws CantCreateDatabaseException, CantExecuteQueryException {
        MockedDatabase mockedPluginDatabaseSystem = new MockedDatabase();
        Database database = mockedPluginDatabaseSystem.createDatabase(UUID.randomUUID(), "testInsert");

        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);
        cryptoVaultDatabaseActions.updateTransactionProtocolStatus(true);

    }


    @Test
    public void testWithValueTrue() throws CantCreateDatabaseException, CantExecuteQueryException {
        mockedPluginDatabaseSystem = new MockedPluginDatabaseSystem();
        Database database = mockedPluginDatabaseSystem.createDatabase(UUID.randomUUID(), "testok");

        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);
        cryptoVaultDatabaseActions.updateTransactionProtocolStatus(true);

    }

    @Test
    public void testWithValueFalse() throws CantCreateDatabaseException, CantExecuteQueryException {
        mockedPluginDatabaseSystem = new MockedPluginDatabaseSystem();
        Database database = mockedPluginDatabaseSystem.createDatabase(UUID.randomUUID(), "testok");

        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);
        cryptoVaultDatabaseActions.updateTransactionProtocolStatus(false);

    }

    @Test
    public void updateTransactionProtocolStatusTest() throws CantCreateDatabaseException, CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        mockedPluginDatabaseSystem = new MockedPluginDatabaseSystem();
        Database database = mockedPluginDatabaseSystem.createDatabase(UUID.randomUUID(), "testok");

        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);
        cryptoVaultDatabaseActions.updateTransactionProtocolStatus(UUID.randomUUID(), ProtocolStatus.SENDING_NOTIFIED) ;

    }

    private class MockedDatabase implements PluginDatabaseSystem {
        @Override
        public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
            throw new CantOpenDatabaseException("error");
        }

        @Override
        public void deleteDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {

        }

        @Override
        public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
            final Database database = new Database() {
                @Override
                public void executeQuery() {

                }

                @Override
                public DatabaseTable getTable(String tableName) {
                    DatabaseTable databaseTable = new DatabaseTable() {
                        @Override
                        public DatabaseTableColumn newColumn() {
                            return null;
                        }

                        @Override
                        public List<String> getColumns() {
                            return null;
                        }

                        @Override
                        public List<DatabaseTableRecord> getRecords() {
                            List<DatabaseTableRecord> databaseTableRecords = new ArrayList<DatabaseTableRecord>();
                            return databaseTableRecords;
                        }



                        @Override
                        public List<DatabaseVariable> getVariablesResult() {
                            return null;
                        }

                        @Override
                        public DatabaseTableRecord getEmptyRecord() {
                            DatabaseTableRecord databaseTableRecord = new DatabaseTableRecord() {
                                @Override
                                public String getStringValue(String columnName) {
                                    return null;
                                }

                                @Override
                                public UUID getUUIDValue(String columnName) {
                                    return null;
                                }

                                @Override
                                public long getLongValue(String columnName) {
                                    return 0;
                                }

                                @Override
                                public Integer getIntegerValue(String columnName) {
                                    return null;
                                }

                                @Override
                                public float getFloatValue(String columnName) {
                                    return 0;
                                }

                                @Override
                                public double getDoubleValue(String columnName) {
                                    return 0;
                                }

                                @Override
                                public String getVariableName(String columnName) {
                                    return null;
                                }

                                @Override
                                public void setStringValue(String columnName, String value) {

                                }

                                @Override
                                public void setUUIDValue(String columnName, UUID value) {

                                }

                                @Override
                                public void setLongValue(String columnName, long value) {

                                }

                                @Override
                                public void setIntegerValue(String columnName, Integer value) {

                                }

                                @Override
                                public void setFloatValue(String columnName, float value) {

                                }

                                @Override
                                public void setDoubleValue(String columnName, double value) {

                                }

                                @Override
                                public void setVariableValue(String columnName, String variableName) {

                                }

                                @Override
                                public void setSelectField(String columnName) {

                                }

                                @Override
                                public void setStateValue(String columnName, WalletFactoryProjectState state) {

                                }

                                @Override
                                public List<DatabaseRecord> getValues() {
                                    return null;
                                }

                                @Override
                                public void setValues(List<DatabaseRecord> values) {

                                }
                            };
                            return databaseTableRecord;
                        }

                        @Override
                        public void clearAllFilters() {

                        }

                        @Override
                        public List<DatabaseTableFilter> getFilters() {
                            return null;
                        }

                        @Override
                        public DatabaseTableFilterGroup getFilterGroup() {
                            return null;
                        }

                        @Override
                        public DatabaseTableFilter getEmptyTableFilter() {
                            return null;
                        }

                        @Override
                        public DatabaseTableFilterGroup getEmptyTableFilterGroup() {
                            return null;
                        }

                        @Override
                        public void selectRecord(DatabaseTableRecord record) throws CantSelectRecordException {

                        }

                        @Override
                        public void updateRecord(DatabaseTableRecord record) throws CantUpdateRecordException {

                        }

                        @Override
                        public void insertRecord(DatabaseTableRecord record) throws CantInsertRecordException {

                        }

                        @Override
                        public void loadToMemory() throws CantLoadTableToMemoryException {

                        }

                        @Override
                        public boolean isTableExists() {
                            return false;
                        }

                        @Override
                        public void setStringFilter(String columnName, String value, DatabaseFilterType type) {

                        }

                        @Override
                        public void setFilterGroup(List<DatabaseTableFilter> filters, List<DatabaseTableFilterGroup> subGroups, DatabaseFilterOperator type) {

                        }

                        @Override
                        public void setUUIDFilter(String columnName, UUID value, DatabaseFilterType type) {

                        }

                        @Override
                        public void setStateFilter(String columName, WalletFactoryProjectState factoryProjectState, DatabaseFilterType type) {

                        }

                        @Override
                        public void setFilterOrder(String columnName, DatabaseFilterOrder direction) {

                        }

                        @Override
                        public void setFilterTop(String top) {

                        }

                        @Override
                        public void setFilterOffSet(String offset) {

                        }

                        @Override
                        public void setVarialbesResult(List<DatabaseVariable> variables) {

                        }

                        @Override
                        public void setSelectOperator(String columnName, DataBaseSelectOperatorType operator, String alias) {

                        }

                        @Override
                        public void deleteRecord(DatabaseTableRecord record) throws CantDeleteRecordException {

                        }

                        @Override
                        public DatabaseTableRecord getRecordFromPk(String pk) throws Exception {
                            return null;
                        }
                    };
                    return databaseTable;
                }

                @Override
                public DatabaseTransaction newTransaction() {
                    DatabaseTransaction databaseTransaction = new DatabaseTransaction() {
                        @Override
                        public void addRecordToUpdate(DatabaseTable fromTable, DatabaseTableRecord fromRecord) {

                        }

                        @Override
                        public void addRecordToInsert(DatabaseTable transfersTable, DatabaseTableRecord transferRecord) {

                        }

                        @Override
                        public void addRecordToSelect(DatabaseTable selectTable, DatabaseTableRecord selectRecord) {

                        }

                        @Override
                        public List<DatabaseTableRecord> getRecordsToUpdate() {
                            return null;
                        }

                        @Override
                        public List<DatabaseTableRecord> getRecordsToSelect() {
                            return null;
                        }

                        @Override
                        public List<DatabaseTableRecord> getRecordsToInsert() {
                            return null;
                        }

                        @Override
                        public List<DatabaseTable> getTablesToUpdate() {
                            return null;
                        }

                        @Override
                        public List<DatabaseTable> getTablesToInsert() {
                            return null;
                        }

                        @Override
                        public List<DatabaseTable> getTablesToSelect() {
                            return null;
                        }
                    };
                    return databaseTransaction;
                }

                @Override
                public void executeTransaction(DatabaseTransaction transaction) throws DatabaseTransactionFailedException {

                }

                @Override
                public DatabaseFactory getDatabaseFactory() {
                    return null;
                }

                @Override
                public void openDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException {

                }

                @Override
                public void closeDatabase() {

                }
            };
            return database;
        }

        @Override
        public void setContext(Object context) {

        }
    }

    @Mock Database database;
    @Mock DatabaseTable databaseTable;
    @Mock DatabaseTableRecord databaseTableRecord;
    @Mock DatabaseTransaction transaction;

    @Test
    public void updateTransactionProtocolStatus_RecordEmpty_return0_Test() throws CantExecuteQueryException {
        List<DatabaseTableRecord> records = new ArrayList<DatabaseTableRecord>();
        //records.add(databaseTableRecord);
        when(database.getTable(anyString())).thenReturn(databaseTable);
        when(databaseTable.getRecords()).thenReturn(records);
        when(databaseTable.getEmptyRecord()).thenReturn(databaseTableRecord);
        when(database.newTransaction()).thenReturn(transaction);


        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);
        Assert.assertEquals(cryptoVaultDatabaseActions.updateTransactionProtocolStatus(true), 0);


    }

    @Test (expected =  CantExecuteQueryException.class)
    public void updateTransactionProtocolStatus_CantLoadTableToMemomyException() throws CantExecuteQueryException, CantLoadTableToMemoryException {
        when(database.getTable(anyString())).thenReturn(databaseTable);
        doThrow(new CantLoadTableToMemoryException()).when(databaseTable).loadToMemory();


        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);
        cryptoVaultDatabaseActions.updateTransactionProtocolStatus(true);


    }
}
