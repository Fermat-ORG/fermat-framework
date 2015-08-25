package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.DeveloperUtils.DeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.WalletFactoryProjectState;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantSelectRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.developerUtils.DeveloperDatabaseFactory;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class DeveloperGetterTest {

    @Mock
    DeveloperDatabaseTable developerDatabaseTable;

    @Test
    public void getDatabaseListTest(){
        DeveloperDatabaseFactory developerDatabaseFactory = new DeveloperDatabaseFactory(UUID.randomUUID().toString(), "");
        DeveloperObjectFactory developerObjectFactory = new DeveloperObjectFactory() {
            @Override
            public DeveloperDatabase getNewDeveloperDatabase(String name, String Id) {
                return null;
            }

            @Override
            public DeveloperDatabaseTable getNewDeveloperDatabaseTable(String name, List<String> columnNames) {
                return null;
            }

            @Override
            public DeveloperDatabaseTableRecord getNewDeveloperDatabaseTableRecord(List<String> values) {
                return null;
            }
        };
        Assert.assertNotNull(developerDatabaseFactory.getDatabaseList(developerObjectFactory));
    }


    @Test
    public void getTableListTest(){
        DeveloperDatabaseFactory developerDatabaseFactory = new DeveloperDatabaseFactory(UUID.randomUUID().toString(), "");
        DeveloperObjectFactory mockedDeveloperObjectFactory = new DeveloperObjectFactory() {
            @Override
            public DeveloperDatabase getNewDeveloperDatabase(String name, String Id) {
                return null;
            }

            @Override
            public DeveloperDatabaseTable getNewDeveloperDatabaseTable(String name, List<String> columnNames) {
                return null;
            }

            @Override
            public DeveloperDatabaseTableRecord getNewDeveloperDatabaseTableRecord(List<String> values) {
                return null;
            }
        };
        Assert.assertNotNull(DeveloperDatabaseFactory.getDatabaseTableList(mockedDeveloperObjectFactory));
    }

    @Test
    public void getDatabaseTableRecordsTest(){
        DeveloperDatabaseFactory developerDatabaseFactory = new DeveloperDatabaseFactory(UUID.randomUUID().toString(), "");
        DeveloperObjectFactory mockedDeveloperObjectFactory = new DeveloperObjectFactory() {
            @Override
            public DeveloperDatabase getNewDeveloperDatabase(String name, String Id) {
                return null;
            }

            @Override
            public DeveloperDatabaseTable getNewDeveloperDatabaseTable(String name, List<String> columnNames) {
                return null;
            }

            @Override
            public DeveloperDatabaseTableRecord getNewDeveloperDatabaseTableRecord(List<String> values) {
                return null;
            }
        };
        MockedDatabase database = new MockedDatabase();

        Assert.assertNotNull(DeveloperDatabaseFactory.getDatabaseTableContent(mockedDeveloperObjectFactory, database, developerDatabaseTable));
    }

    private class MockedDatabase implements Database {

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
                    databaseTableRecords.add(new DatabaseTableRecord() {
                        @Override
                        public String getStringValue(String columnName) {
                            return "Prueba2";
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
                            DatabaseRecord databaseRecord = new DatabaseRecord() {
                                @Override
                                public String getName() {
                                    return "value";
                                }

                                @Override
                                public String getValue() {
                                    return "hola";
                                }

                                @Override
                                public boolean getChange() {
                                    return false;
                                }

                                @Override
                                public boolean getUseValueofVariable() {
                                    return false;
                                }

                                @Override
                                public void setName(String name) {

                                }

                                @Override
                                public void setValue(String value) {

                                }

                                @Override
                                public void setChange(boolean change) {

                                }

                                @Override
                                public void setUseValueofVariable(boolean ifvariable) {

                                }
                            };
                            List<DatabaseRecord> databaseRecords = new ArrayList<DatabaseRecord>();
                            databaseRecords.add(databaseRecord);
                            return databaseRecords;
                        }

                        @Override
                        public void setValues(List<DatabaseRecord> values) {

                        }
                    });
                    databaseTableRecords.add(new DatabaseTableRecord() {
                        @Override
                        public String getStringValue(String columnName) {
                            return "Prueba1";
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
                            DatabaseRecord databaseRecord = new DatabaseRecord() {
                                @Override
                                public String getName() {
                                    return "record";
                                }

                                @Override
                                public String getValue() {
                                    return "value";
                                }

                                @Override
                                public boolean getChange() {
                                    return false;
                                }

                                @Override
                                public boolean getUseValueofVariable() {
                                    return false;
                                }

                                @Override
                                public void setName(String name) {

                                }

                                @Override
                                public void setValue(String value) {

                                }

                                @Override
                                public void setChange(boolean change) {

                                }

                                @Override
                                public void setUseValueofVariable(boolean ifvariable) {

                                }
                            };
                            List<DatabaseRecord> databaseRecords = new ArrayList<DatabaseRecord>();
                            databaseRecords.add(databaseRecord);
                            return databaseRecords;
                        }

                        @Override
                        public void setValues(List<DatabaseRecord> values) {

                        }
                    });
                    return databaseTableRecords;
                }

                @Override
                public List<DatabaseVariable> getVariablesResult() {
                    return null;
                }

                @Override
                public DatabaseTableRecord getEmptyRecord() {
                    return null;
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
            return null;
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
    }
}
