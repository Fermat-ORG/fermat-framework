package org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.MissingAssetDataException;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.AssetIssuerIdentity;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.functional.AssetFactoryRecord;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by franklin on 15/09/15.
 */
public class AssetFactoryMiddlewareDao {

    private Database database;
    private UUID pluginId;
    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    private PluginFileSystem pluginFileSystem;

    /**
     * Constructor
     */
    public AssetFactoryMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, AssetFactoryMiddlewareDatabaseConstant.DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            AssetFactoryMiddlewareDatabaseFactory assetFactoryMiddlewareDatabaseFactory = new AssetFactoryMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
            database = assetFactoryMiddlewareDatabaseFactory.createDatabase(this.pluginId, AssetFactoryMiddlewareDatabaseConstant.DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTableRecord getAssetFactoryProjectRecord(AssetFactory assetFactory) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ID_COLUMN, assetFactory.getFactoryId());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_PUBLIC_KEY_COLUMN, assetFactory.getAssetPublicKey());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NAME_COLUMN, assetFactory.getName());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_DESCRIPTION_COLUMN, assetFactory.getDescription());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_ALIAS_COLUMN, assetFactory.getIdentyAssetIssuer().getAlias());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_SIGNATURE_COLUMN, "signature");
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN, assetFactory.getIdentyAssetIssuer().getPublicKey());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_STATE_COLUMN, assetFactory.getState().getCode());
        record.setLongValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_FEE_COLUMN, assetFactory.getFee());
        record.setLongValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_AMOUNT_COLUMN, assetFactory.getAmount());
        record.setLongValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_QUANTITY_COLUMN, assetFactory.getQuantity());
        record.setLongValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TOTAL_QUANTITY_COLUMN, assetFactory.getTotalQuantity());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_REDEEMABLE, String.valueOf(assetFactory.getIsRedeemable()));
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_TRANSFERABLE, String.valueOf(assetFactory.getIsTransferable()));
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_EXCHANGEABLE, String.valueOf(assetFactory.getIsExchangeable()));

        if (assetFactory.getExpirationDate() != null) {
            record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_EXPIRATION_DATE, assetFactory.getExpirationDate().toString());
        } else
            record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_EXPIRATION_DATE, null);

        if (assetFactory.getCreationTimestamp() == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getDefault());
            calendar.setTime(new Date());
            assetFactory.setCreationTimestamp(new Timestamp(calendar.getTime().getTime()));
        }
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CREATION_TIME_COLUMN, assetFactory.getCreationTimestamp().toString());
        if (assetFactory.getLastModificationTimestamp() == null) {
            Date date = new Date();
            assetFactory.setLastModificationTimeststamp(new Timestamp(date.getTime()));
        }
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_LAST_UPDATE_TIME_COLUMN, assetFactory.getLastModificationTimestamp().toString());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_BEHAVIOR_COLUMN, assetFactory.getAssetBehavior().getCode());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_WALLET_PUBLIC_KEY, assetFactory.getWalletPublicKey());

        if (assetFactory.getNetworkType() == null)
            record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NETWORK_TYPE, BlockchainNetworkType.getDefaultBlockchainNetworkType().getCode());
        else
            record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NETWORK_TYPE, assetFactory.getNetworkType().getCode());

        return record;
    }

    private DatabaseTableRecord getResourceDataRecord(String assetPublicKey, Resource resource) throws DatabaseOperationException, MissingAssetDataException {
        DatabaseTable databaseTable = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_ID_COLUMN, resource.getId());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_NAME_COLUMN, resource.getName());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_ASSET_PUBLIC_KEY_COLUMN, assetPublicKey);
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_FILE_NAME_COLUMN, resource.getFileName());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_RESOURCE_DENSITY_COLUMN, resource.getResourceDensity().getCode());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_RESOURCE_TYPE_COLUMN, resource.getResourceType().value());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_PATH_COLUMN, "aca va el path del archivo");

        return record;
    }

    private DatabaseTableRecord getContractDataRecord(String assetPublicKey,
                                                      String name,
                                                      String value) throws DatabaseOperationException, MissingAssetDataException {
        DatabaseTable databaseTable = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_ID_COLUMN, UUID.randomUUID().toString());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_ASSET_PUBLIC_KEY_COLUMN, assetPublicKey);
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_NAME_COLUMN, name);
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_VALUE_COLUMN, value);

        return record;
    }

    private DatabaseTableRecord getIdentityIssuerDataRecord(String assetPublicKey,
                                                            String publicKey,
                                                            String name,
                                                            String signature) throws DatabaseOperationException, MissingAssetDataException {

        DatabaseTable databaseTable = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_TABLE_NAME);

        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ID_COLUMN, UUID.randomUUID().toString());
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_PUBLIC_KEY_COLUMN, publicKey);
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_NAME_COLUMN, name);
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_ASSET_PUBLIC_KEY_COLUMN, assetPublicKey);
        record.setStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_SIGNATURE_COLUMN, signature);

        return record;
    }

    private DatabaseTransaction addResourceRecordsToTransaction(DatabaseTransaction transaction, AssetFactory assetFactory)
            throws DatabaseOperationException, MissingAssetDataException, CantLoadTableToMemoryException {

        DatabaseTable table = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_TABLE_NAME);

        if (assetFactory.getResources() != null) {
            for (Resource resources : assetFactory.getResources()) {
                DatabaseTableRecord record = getResourceDataRecord(assetFactory.getAssetPublicKey(), resources);
                DatabaseTableFilter filter = getResourceFilter(resources.getId().toString());
                filter.setValue(resources.getId().toString());
                if (isNewRecord(table, filter)) {
                    //New Records
                    transaction.addRecordToInsert(table, record);
                } else {
                    //update Records
                    table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                    transaction.addRecordToUpdate(table, record);
                }
            }
        }
        return transaction;
    }

    private DatabaseTransaction addContractRecordsToTransaction(DatabaseTransaction transaction, AssetFactory assetFactory)
            throws DatabaseOperationException, MissingAssetDataException, CantLoadTableToMemoryException {

        DatabaseTable table = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_TABLE_NAME);

        if (assetFactory.getContractProperties() != null) {
            for (ContractProperty contractProperties : assetFactory.getContractProperties()) {
                DatabaseTableRecord record = getContractDataRecord(assetFactory.getAssetPublicKey(), contractProperties.getName(),
                        contractProperties.getValue() != null ? contractProperties.getValue().toString() : null);
                transaction.addRecordToInsert(table, record);
            }
        }

        return transaction;
    }

    private DatabaseTransaction addIdentityIssuerRecordsToTransaction(DatabaseTransaction transaction, AssetFactory assetFactory)
            throws DatabaseOperationException, MissingAssetDataException, CantLoadTableToMemoryException {

        DatabaseTable table = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_TABLE_NAME);

        DatabaseTableRecord record = getIdentityIssuerDataRecord(assetFactory.getAssetPublicKey(),
                assetFactory.getIdentyAssetIssuer().getPublicKey(), assetFactory.getIdentyAssetIssuer().getAlias(), "signature");

        DatabaseTableFilter filter = getIdentityAssetPublicKeyFilter(assetFactory.getAssetPublicKey());
        if (isNewRecord(table, filter))
            //New Records
            transaction.addRecordToInsert(table, record);
        else {
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
            transaction.addRecordToUpdate(table, record);
        }

        return transaction;
    }

    private DatabaseTableFilter getIdentityAssetPublicKeyFilter(String value) {
        DatabaseTableFilter filter = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_TABLE_NAME).getEmptyTableFilter();
        filter.setColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_ASSET_PUBLIC_KEY_COLUMN);
        filter.setType(DatabaseFilterType.EQUAL);
        filter.setValue(value);

        return filter;
    }

    private DatabaseTableFilter getContractFilter(String value) {
        DatabaseTableFilter filter = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_TABLE_NAME).getEmptyTableFilter();
        filter.setColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_NAME_COLUMN);
        filter.setType(DatabaseFilterType.EQUAL);
        filter.setValue(value);

        return filter;
    }

    private DatabaseTableFilter getResourceFilter(String value) {
        DatabaseTableFilter filter = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_TABLE_NAME).getEmptyTableFilter();
        filter.setColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_ID_COLUMN);
        filter.setType(DatabaseFilterType.EQUAL);
        filter.setValue(value);

        return filter;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    private List<DatabaseTableRecord> getResourcesData(String assetFactoryPublicKey) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_TABLE_NAME);

        table.addStringFilter(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_ASSET_PUBLIC_KEY_COLUMN, assetFactoryPublicKey, DatabaseFilterType.EQUAL);
        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getIdentityIssuerData(String assetFactoryPublicKey) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_TABLE_NAME);

        table.addStringFilter(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_ASSET_PUBLIC_KEY_COLUMN, assetFactoryPublicKey, DatabaseFilterType.EQUAL);
        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getContractsData(String assetFactoryPublicKey) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_TABLE_NAME);

        table.addStringFilter(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_ASSET_PUBLIC_KEY_COLUMN, assetFactoryPublicKey, DatabaseFilterType.EQUAL);
        table.loadToMemory();

        return table.getRecords();
    }

    private List<ContractProperty> getAssetFactoryContractList(String assetFactoryPublicKey) throws CantLoadTableToMemoryException {
        final List<DatabaseTableRecord> contractData = getContractsData(assetFactoryPublicKey);
        List<ContractProperty> contractProperties = new ArrayList<>();
        for (DatabaseTableRecord record : contractData) {
            String contractName = record.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_NAME_COLUMN);
            String contractValue = record.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_VALUE_COLUMN);
            if (contractName != null && contractName.length() > 0 && contractValue != null && contractValue.length() > 0) {
                if (contractName.equals(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE)) {
                    contractProperties.add(new ContractProperty(contractName, (contractValue == null || contractValue.equals("null")) ? contractValue : Timestamp.valueOf(contractValue)));
                } else if (contractName.equals(DigitalAssetContractPropertiesConstants.REDEEMABLE)) {
                    contractProperties.add(new ContractProperty(contractName, Boolean.valueOf(contractValue)));
                } else if (contractName.equals(DigitalAssetContractPropertiesConstants.TRANSFERABLE)) {
                    contractProperties.add(new ContractProperty(contractName, Boolean.valueOf(contractValue)));
                } else if (contractName.equals(DigitalAssetContractPropertiesConstants.SALEABLE)) {
                    contractProperties.add(new ContractProperty(contractName, Boolean.valueOf(contractValue)));
                }
            }
        }
        return contractProperties;
    }

    private List<DatabaseTableRecord> getAssetFactoryData(DatabaseTableFilter... filters) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TABLE_NAME);
        for (DatabaseTableFilter filter : filters) {
            if (filter != null)
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        }

        table.loadToMemory();

        return table.getRecords();
    }

    private AssetFactory getEmptyAssetFactory() {
        return new AssetFactoryRecord();
    }

    private AssetFactory getAssetFactory(final DatabaseTableRecord assetFactoriesRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {
        AssetFactory assetFactory = getEmptyAssetFactory();
        assetFactory.setFactoryId(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ID_COLUMN));
        assetFactory.setPublicKey(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_PUBLIC_KEY_COLUMN));
        assetFactory.setName(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NAME_COLUMN));
        assetFactory.setDescription(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_DESCRIPTION_COLUMN));
        assetFactory.setAmount(assetFactoriesRecord.getLongValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_AMOUNT_COLUMN));
        assetFactory.setWalletPublicKey(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_WALLET_PUBLIC_KEY);
        AssetIssuerIdentity assetIssuerIdentity = new AssetIssuerIdentity();
        assetIssuerIdentity.setAlias(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_ALIAS_COLUMN));
        assetIssuerIdentity.setPublicKey(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_PUBLIC_KEY_COLUMN));
        assetFactory.setIdentityAssetIssuer(assetIssuerIdentity);
        assetFactory.setState(State.getByCode(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_STATE_COLUMN)));
        assetFactory.setFee(assetFactoriesRecord.getLongValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_FEE_COLUMN));
        assetFactory.setQuantity(assetFactoriesRecord.getIntegerValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_QUANTITY_COLUMN));
        assetFactory.setTotalQuantity(assetFactoriesRecord.getIntegerValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TOTAL_QUANTITY_COLUMN));
        assetFactory.setCreationTimestamp(Timestamp.valueOf(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CREATION_TIME_COLUMN)));
        assetFactory.setLastModificationTimeststamp(Timestamp.valueOf(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_LAST_UPDATE_TIME_COLUMN)));
        assetFactory.setAssetBehavior(AssetBehavior.getByCode(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_BEHAVIOR_COLUMN)));
        assetFactory.setNetworkType(BlockchainNetworkType.getByCode(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NETWORK_TYPE)));
        //Object value = assetFactoriesRecord.getStringValue(AssertFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_EXPIRATION_DATE);

        String value = assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_EXPIRATION_DATE) != null ?
                !assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_EXPIRATION_DATE).equalsIgnoreCase("null") ? assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_EXPIRATION_DATE) : null : null;
        assetFactory.setExpirationDate(value != null ? Timestamp.valueOf(value) : null);


        assetFactory.setIsRedeemable(Boolean.valueOf(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_REDEEMABLE)));
        assetFactory.setIsTransferable(Boolean.valueOf(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_TRANSFERABLE)));
        assetFactory.setIsExchangeable(Boolean.valueOf(assetFactoriesRecord.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_EXCHANGEABLE)));

        return assetFactory;
    }

    private void removeAssetFactoryContractData(AssetFactory assetFactory) throws CantDeleteAsserFactoryException {
        try {
            database = openDatabase();

            DatabaseTable tableContracts = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_TABLE_NAME);
            tableContracts.addStringFilter(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_ASSET_PUBLIC_KEY_COLUMN, assetFactory.getAssetPublicKey(), DatabaseFilterType.EQUAL);
            tableContracts.loadToMemory();
            for (DatabaseTableRecord record : tableContracts.getRecords()) {
                tableContracts.deleteRecord(record);
            }
        } catch (Exception exception) {
            throw new CantDeleteAsserFactoryException(exception, "Error delete Contracts of the Asset Factory", "Asset Factory Contracts - Delete");
        }
    }

    public void removeAssetFactory(AssetFactory assetFactory, boolean removeResources) throws CantDeleteAsserFactoryException {
        try {
            database = openDatabase();

            DatabaseTable table = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TABLE_NAME);
            DatabaseTable tableContracts = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_TABLE_NAME);
            DatabaseTable tableResources = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_TABLE_NAME);
            DatabaseTable tableIdentityUser = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_TABLE_NAME);

            if (removeResources) {
                tableResources.addStringFilter(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_ASSET_PUBLIC_KEY_COLUMN, assetFactory.getAssetPublicKey(), DatabaseFilterType.EQUAL);
                tableResources.loadToMemory();
                for (DatabaseTableRecord record : tableResources.getRecords()) {
                    tableResources.deleteRecord(record);
                }

                tableContracts.addStringFilter(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_ASSET_PUBLIC_KEY_COLUMN, assetFactory.getAssetPublicKey(), DatabaseFilterType.EQUAL);
                tableContracts.loadToMemory();
                for (DatabaseTableRecord record : tableContracts.getRecords()) {
                    tableContracts.deleteRecord(record);
                }

                tableIdentityUser.addStringFilter(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_PUBLIC_KEY_COLUMN, assetFactory.getIdentyAssetIssuer().getPublicKey(), DatabaseFilterType.EQUAL);
                tableIdentityUser.loadToMemory();
                for (DatabaseTableRecord record : tableIdentityUser.getRecords()) {
                    tableIdentityUser.deleteRecord(record);
                }
            }

            table.addStringFilter(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ID_COLUMN, assetFactory.getFactoryId(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            for (DatabaseTableRecord record : table.getRecords()) {
                table.deleteRecord(record);
            }

        } catch (Exception exception) {
            throw new CantDeleteAsserFactoryException(exception, "Error delete Asset Factory", "Asset Factory - Delete");
        }
    }


    public void saveAssetFactoryData(AssetFactory assetFactory) throws DatabaseOperationException, MissingAssetDataException {

        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TABLE_NAME);
            DatabaseTableRecord assetFactoryRecord = getAssetFactoryProjectRecord(assetFactory);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(assetFactory.getFactoryId());
            filter.setColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ID_COLUMN);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, assetFactoryRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, assetFactoryRecord);
            }

            // I wil add the Contracts to the transaction if there are any
            if (assetFactory.getContractProperties() != null) {
                removeAssetFactoryContractData(assetFactory);
                transaction = addContractRecordsToTransaction(transaction, assetFactory);
            }
            // I wil add the resources to the transaction if there are any
            if (assetFactory.getResources() != null)
                transaction = addResourceRecordsToTransaction(transaction, assetFactory);
            // I wil add the identity issuer to the transaction if there are any
            if (assetFactory.getIdentyAssetIssuer() != null)
                transaction = addIdentityIssuerRecordsToTransaction(transaction, assetFactory);

            //I execute the transaction and persist the database side of the asset.
            database.executeTransaction(transaction);

        } catch (Exception e) {
            if (database != null)

                throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Asset Factory in the database.", null);
        }
    }

    public void markAssetFactoryData(AssetFactory assetFactory) throws DatabaseOperationException, MissingAssetDataException {
        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TABLE_NAME);
            DatabaseTableRecord assetFactoryRecord = getAssetFactoryProjectRecord(assetFactory);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(assetFactory.getFactoryId());
            filter.setColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ID_COLUMN);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, assetFactoryRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, assetFactoryRecord);
            }
            //I execute the transaction and persist the database side of the asset.
            database.executeTransaction(transaction);
        } catch (Exception e) {
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Asset Factory in the database.", null);
        }
    }

    public void persistNewImageFactory(AssetFactory assetFactory) throws CantPersistFileException {
        try {
//            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
//                    DeviceDirectory.LOCAL_USERS.getName(),
//                    AssetIssuerIdentityPluginRoot.ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
//                    FilePrivacy.PRIVATE,
//                    FileLifeSpan.PERMANENT
//            );
//
//            file.setContent(profileImage);
//
//            file.persistToMedia();
            if (assetFactory.getResources() != null) {
                for (Resource resource : assetFactory.getResources()) {
                    PluginBinaryFile imageFile = pluginFileSystem.createBinaryFile(pluginId,
                            DeviceDirectory.LOCAL_USERS.getName(),
//                        PATH_DIRECTORY,
                            resource.getId().toString(),
                            FilePrivacy.PRIVATE,
                            FileLifeSpan.PERMANENT);

                    imageFile.setContent(resource.getResourceBinayData());
                    imageFile.persistToMedia();
                }
            }
        } catch (CantPersistFileException e) {
            throw new CantPersistFileException("CAN'T PERSIST IMAGE ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistFileException("CAN'T PERSIST IMAGE ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw new CantPersistFileException("CAN'T PERSIST IMAGE ", FermatException.wrapException(e), "", "");
        }
    }

    public byte[] getImageFactory(Resource resource) throws CantGetResourcesException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    resource.getId().toString(),
//                    AssetIssuerIdentityPluginRoot.ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetResourcesException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            profileImage = new byte[0];
            // throw new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new CantGetResourcesException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }

        return profileImage;
    }

    public List<AssetFactory> getAssetFactoryList(DatabaseTableFilter... filters) throws DatabaseOperationException, InvalidParameterException, CantCreateFileException {
        try {
            openDatabase();
            List<AssetFactory> assetFactoryList = new ArrayList<>();

            // I will add the Asset Factory information from the database
            for (DatabaseTableRecord assetFactoriesRecord : getAssetFactoryData(filters)) {

                final AssetFactory assetFactory = getAssetFactory(assetFactoriesRecord);
                AssetIssuerIdentity assetIssuerIdentity = new AssetIssuerIdentity();
                // I will add the indetity issuer information from database
                for (final DatabaseTableRecord identityIssuerRecords : getIdentityIssuerData(assetFactory.getAssetPublicKey())) {
                    assetIssuerIdentity.setPublicKey(identityIssuerRecords.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_PUBLIC_KEY_COLUMN));
                    assetIssuerIdentity.setAlias(identityIssuerRecords.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_NAME_COLUMN));
                }

                List<Resource> resources = new ArrayList<>();
                // I will add the resource properties information from database
                for (DatabaseTableRecord resourceRecords : getResourcesData(assetFactory.getAssetPublicKey())) {

                    Resource resource = new Resource();

                    resource.setId(resourceRecords.getUUIDValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_ID_COLUMN));
                    resource.setName(resourceRecords.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_NAME_COLUMN));
                    resource.setFileName(resourceRecords.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_FILE_NAME_COLUMN));
                    resource.setName(resourceRecords.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_NAME_COLUMN));
                    try {
                        resource.setResourceDensity(ResourceDensity.getByCode(resourceRecords.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_RESOURCE_DENSITY_COLUMN)));
                    } catch (InvalidParameterException e) {
                        resource.setResourceDensity(ResourceDensity.HDPI);
                    }
                    try {
                        resource.setResourceType(ResourceType.getByCode(resourceRecords.getStringValue(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_RESOURCE_TYPE_COLUMN)));
                    } catch (InvalidParameterException e) {
                        resource.setResourceType(ResourceType.IMAGE);
                    }
//                    getImageFactory(resource);
//                    PluginBinaryFile imageFile = pluginFileSystem.getBinaryFile(pluginId,
//                            DeviceDirectory.LOCAL_USERS.getName(),
//                            resource.getId().toString(),
//                            FilePrivacy.PRIVATE,
//                            FileLifeSpan.PERMANENT);

                    resource.setResourceBinayData(getImageFactory(resource));//imageFile.getContent());
                    resources.add(resource);
                }

                assetFactory.setContractProperties(getAssetFactoryContractList(assetFactory.getAssetPublicKey()));

                assetFactory.setResources(resources);
                assetFactory.setIdentityAssetIssuer(assetIssuerIdentity);

                assetFactoryList.add(assetFactory);
            }

            return assetFactoryList;
        } catch (Exception e) {
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get assets factory from the database with filter: " + filters.toString(), null);
        }
    }
}
