package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.enums.FactoryProjectType;
import com.bitdubai.fermat_wpd_api.all_definition.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.MissingProjectDataException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 8/17/15.
 */
public class WalletFactoryMiddlewareDao implements DealsWithPluginDatabaseSystem {
    Database database;
    UUID pluginId;
    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Constructor
     */
    public WalletFactoryMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, WalletFactoryMiddlewareDatabaseConstants.DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {
            /**
             * if the database is not found, then I will create it
             */
            WalletFactoryMiddlewareDatabaseFactory databaseFactory = new WalletFactoryMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
            database = databaseFactory.createDatabase(this.pluginId, WalletFactoryMiddlewareDatabaseConstants.DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTableRecord getWalletFactoryProjectRecord(WalletFactoryProject walletFactoryProject) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLICKEY_COLUMN_NAME, walletFactoryProject.getProjectPublicKey());
        record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, walletFactoryProject.getName());
        record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME, walletFactoryProject.getDescription());
        if (walletFactoryProject.getProjectState() != null)
            record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME, walletFactoryProject.getProjectState().value());
        if (walletFactoryProject.getWalletCategory() != null)
            record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLETCATEGORY_COLUMN_NAME, walletFactoryProject.getWalletCategory().name());
        if (walletFactoryProject.getFactoryProjectType() != null)
            record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_FACTORYPROJECTTYPE_COLUMN_NAME, walletFactoryProject.getFactoryProjectType().getCode());
        if (walletFactoryProject.getWalletType() != null)
            record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLETTYPE_COLUMN_NAME, walletFactoryProject.getWalletType().getCode());
        if (walletFactoryProject.getCreationTimestamp() != null)
            record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_CREATION_TIMESTAMP_COLUMN_NAME, walletFactoryProject.getCreationTimestamp().toString());
        if (walletFactoryProject.getLastModificationTimestamp() != null)
            record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_MODIFICATION_TIMESTAMP_COLUMN_NAME, walletFactoryProject.getLastModificationTimestamp().toString());

        record.setIntegerValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SIZE_COLUMN_NAME, walletFactoryProject.getSize());

        return record;
    }

    private DatabaseTableRecord getSkinDataRecord(String projectPublicKey, UUID id, boolean isDefault) throws DatabaseOperationException, MissingProjectDataException {
        DatabaseTable databaseTable = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.SKIN_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.SKIN_PROJECT_PUBLICKEY_COLUMN_NAME, projectPublicKey);
        record.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.SKIN_SKIN_ID_COLUMN_NAME, id);
        record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.SKIN_DEFAULT_COLUMN_NAME, String.valueOf(isDefault));
        return record;
    }

    private DatabaseTableRecord getLanguageDataRecord(String projectPublicKey, UUID id, boolean isDefault) throws DatabaseOperationException, MissingProjectDataException{
        DatabaseTable databaseTable = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_PROJECT_PUBLICKEY_COLUMN_NAME, projectPublicKey);
        record.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_LANGUAGE_ID_COLUMN_NAME, id);
        record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_DEFAULT_COLUMN_NAME, String.valueOf(isDefault));
        return record;
    }

    private DatabaseTableRecord getNavigationStructureDataRecord(String projectPublicKey, String publicKey) throws DatabaseOperationException, MissingProjectDataException{
        DatabaseTable databaseTable = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_PROJECT_PUBLICKEY_COLUMN_NAME, projectPublicKey);
        record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_PUBLICKEY_COLUMN_NAME, publicKey);
        return record;
    }

    private DatabaseTransaction addSkinRecordsToTransaction(DatabaseTransaction transaction, WalletFactoryProject walletFactoryProject) throws DatabaseOperationException, MissingProjectDataException, CantLoadTableToMemoryException {
        Skin defaultSkin = null;

        defaultSkin = walletFactoryProject.getDefaultSkin();
        if (defaultSkin != null){
            // if a skin was defined in the project, then I will prepare the database record and add it to the transaction
            DatabaseTable table = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.SKIN_TABLE_NAME);

            DatabaseTableRecord defaultSkinRecord = getSkinDataRecord(walletFactoryProject.getProjectPublicKey(), defaultSkin.getId(), true);
            DatabaseTableFilter filter = getSkinFilter(defaultSkin.getId().toString());

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, defaultSkinRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, defaultSkinRecord);
            }


            // I will add all the skins defined, if there are more than one.
            if (walletFactoryProject.getSkins() != null){
                for (Skin skin : walletFactoryProject.getSkins()){
                    DatabaseTableRecord skinRecord = getSkinDataRecord(walletFactoryProject.getProjectPublicKey(), skin.getId(), false);
                    filter.setValue(skin.getId().toString());
                    if (isNewRecord(table, filter))
                        transaction.addRecordToInsert(table, skinRecord);
                    else {
                        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                        transaction.addRecordToUpdate(table, skinRecord);
                    }
                }
            }

        }

        return transaction;
    }

    private DatabaseTableFilter getSkinFilter(String value) {
        DatabaseTableFilter filter = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.SKIN_TABLE_NAME).getEmptyTableFilter();
        filter.setColumn(WalletFactoryMiddlewareDatabaseConstants.SKIN_SKIN_ID_COLUMN_NAME);
        filter.setType(DatabaseFilterType.EQUAL);
        filter.setValue(value);

        return filter;
    }

    private DatabaseTransaction addLanguageRecordsToTransaction(DatabaseTransaction transaction, WalletFactoryProject walletFactoryProject) throws DatabaseOperationException, MissingProjectDataException, CantLoadTableToMemoryException {
        Language defaultLanguage = null;

        defaultLanguage = walletFactoryProject.getDefaultLanguage();

        if (defaultLanguage != null){
            DatabaseTable table = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_TABLE_NAME);
            DatabaseTableRecord defaultLanguageRecord = getLanguageDataRecord(walletFactoryProject.getProjectPublicKey(), defaultLanguage.getId(), true);

            DatabaseTableFilter filter = getLanguageFilter(defaultLanguage.getId().toString());
            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, defaultLanguageRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, defaultLanguageRecord);
            }

            //I will add any other language defined
            if (walletFactoryProject.getLanguages() != null){
                for (Language language : walletFactoryProject.getLanguages()){
                    DatabaseTableRecord record = getLanguageDataRecord(walletFactoryProject.getProjectPublicKey(), language.getId(), false);
                    filter.setValue(language.getId().toString());
                    if (isNewRecord(table, filter))
                        transaction.addRecordToInsert(table, record);
                    else{
                        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                        transaction.addRecordToUpdate(table, record);
                    }

                }
            }

        }
        return transaction;
    }

    private DatabaseTableFilter getLanguageFilter(String value) {
        DatabaseTableFilter filter = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_TABLE_NAME).getEmptyTableFilter();
        filter.setColumn(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_LANGUAGE_ID_COLUMN_NAME);
        filter.setType(DatabaseFilterType.EQUAL);
        filter.setValue(value);

        return filter;
    }

    private DatabaseTransaction addNavigationStructureRecordToTransaction(DatabaseTransaction transaction, WalletFactoryProject walletFactoryProject) throws DatabaseOperationException, MissingProjectDataException, CantLoadTableToMemoryException {
        AppNavigationStructure navigationStructure = null;

        navigationStructure = walletFactoryProject.getNavigationStructure();
        if (navigationStructure != null){
            DatabaseTableRecord record = getNavigationStructureDataRecord(walletFactoryProject.getProjectPublicKey(), navigationStructure.getPublicKey());
            DatabaseTable table = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_TABLE_NAME);
            DatabaseTableFilter filter = getNavigationStructureFilter(navigationStructure.getPublicKey());
            if (isNewRecord(table, filter)){
                //If it is a new record, then I ll insert it
                transaction.addRecordToInsert(table, record);
            } else {
                //if it exists, then I will update it.
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, record);
            }
        }
        return transaction;
    }

    private DatabaseTableFilter getNavigationStructureFilter(String publicKey) {
        DatabaseTableFilter filter = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_TABLE_NAME).getEmptyTableFilter();
        filter.setColumn(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_PUBLICKEY_COLUMN_NAME);
        filter.setType(DatabaseFilterType.EQUAL);
        filter.setValue(publicKey);

        return filter;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    /**
     * Saves the Wallet Factory Project in the database.
     * If it doesn't exists, it insert, if they already exists, everything is updated.
     * If it has skins and languages, it persists all of them.
     * @param walletFactoryProject
     * @throws DatabaseOperationException
     * @throws MissingProjectDataException
     */
    public void saveWalletFactoryProjectData(WalletFactoryProject walletFactoryProject) throws DatabaseOperationException, MissingProjectDataException{
        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            // add the Wallet factory project database record to a transaction
            DatabaseTable table = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            DatabaseTableRecord walletFactoryRecord = getWalletFactoryProjectRecord(walletFactoryProject);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(walletFactoryProject.getProjectPublicKey());
            filter.setColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLICKEY_COLUMN_NAME);
            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, walletFactoryRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, walletFactoryRecord);
            }


            // I wil add the skins to the transaction if there are any
            transaction = addSkinRecordsToTransaction(transaction, walletFactoryProject);

            // I wil add the Languages to the transaction if there are any
            transaction = addLanguageRecordsToTransaction(transaction, walletFactoryProject);

            //I will add the navigation structure inside the transaction if there is any
            transaction = addNavigationStructureRecordToTransaction(transaction, walletFactoryProject);

            //I execute the transaction and persist the database side of the project.
            database.executeTransaction(transaction);
            database.closeDatabase();

        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Factory project in the database.", null);
        }
    }

    private List<DatabaseTableRecord> getSkinsData(String walletFactoryPublicKey) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.SKIN_TABLE_NAME);
        table.addStringFilter(WalletFactoryMiddlewareDatabaseConstants.SKIN_PROJECT_PUBLICKEY_COLUMN_NAME, walletFactoryPublicKey, DatabaseFilterType.EQUAL);
        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getLanguagesData(String walletFactoryPublicKey) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_TABLE_NAME);
        table.addStringFilter(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_PROJECT_PUBLICKEY_COLUMN_NAME, walletFactoryPublicKey, DatabaseFilterType.EQUAL);
        table.loadToMemory();

        return table.getRecords();
    }

    private DatabaseTableRecord getNavigationStructureData(String walletFactoryPublicKey) throws CantLoadTableToMemoryException, DatabaseOperationException {
        DatabaseTable table = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_TABLE_NAME);
        table.addStringFilter(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_PROJECT_PUBLICKEY_COLUMN_NAME, walletFactoryPublicKey, DatabaseFilterType.EQUAL);
        table.loadToMemory();

        // I should only have 1 or none navigation structure for each project.
        if (table.getRecords().size() > 1)
            throw new DatabaseOperationException("Multiples navigation structure for single project found in database." , null, "Project key: " + walletFactoryPublicKey, null);

        if (table.getRecords().size() == 1) {
            return table.getRecords().get(0);
        }else {
            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            return null;
        }
    }

    private List<DatabaseTableRecord> getWalletFactoryProjectsData (DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private WalletFactoryProject getEmptyWalletFactoryProject(){
        WalletFactoryProject walletFactoryProject = new WalletFactoryProject() {
            String publicKey;
            String name;
            String description;
            WalletType walletType;
            WalletFactoryProjectState walletFactoryProjectState;
            Timestamp creationTimestamp;
            Timestamp lastModificationTimestamp;
            Skin skin;
            List<Skin> skins;
            Language language;
            List<Language> languages;
            AppNavigationStructure navigationStructure;
            int size;
            WalletCategory walletCategory;
            FactoryProjectType factoryProjectType;


            @Override
            public String getProjectPublicKey() {
                return publicKey;
            }

            @Override
            public void setProjectPublicKey(String publickKey) {
                this.publicKey = publickKey;
            }

            @Override
            public String getName() {
                return this.name;
            }

            @Override
            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String getDescription() {
                return this.description;
            }

            @Override
            public void setDescription(String description) {
                this.description = description;
            }

            @Override
            public WalletType getWalletType() {
                return walletType;
            }

            @Override
            public void setWalletType(WalletType walletType) {
                this.walletType = walletType;
            }

            @Override
            public WalletFactoryProjectState getProjectState() {
                return walletFactoryProjectState;
            }

            @Override
            public void setProjectState(WalletFactoryProjectState projectState) {
                this.walletFactoryProjectState = projectState;
            }

            @Override
            public Timestamp getCreationTimestamp() {
                return creationTimestamp;
            }

            @Override
            public void setCreationTimestamp(Timestamp timestamp) {
                this.creationTimestamp = timestamp;
            }

            @Override
            public Timestamp getLastModificationTimestamp() {
                return lastModificationTimestamp;
            }

            @Override
            public void setLastModificationTimeststamp(Timestamp timestamp) {
                lastModificationTimestamp = timestamp;
            }

            @Override
            public Skin getDefaultSkin() {
                return skin;
            }

            @Override
            public void setDefaultSkin(Skin skin) {
                this.skin = skin;
            }

            @Override
            public List<Skin> getSkins() {
                return this.skins;
            }

            @Override
            public Language getDefaultLanguage() {
                return language;
            }

            @Override
            public void setDefaultLanguage(Language language) {
                this.language = language;
            }

            @Override
            public List<Language> getLanguages() {
                return languages;
            }

            @Override
            public AppNavigationStructure getNavigationStructure() {
                return navigationStructure;
            }

            @Override
            public void setNavigationStructure(AppNavigationStructure navigationStructure) {
                this.navigationStructure = navigationStructure;
            }


            @Override
            public void setSkins(List<Skin> skins) {
                this.skins = skins;
            }

            @Override
            public void setLanguages(List<Language> languages) {
                this.languages = languages;
            }

            @Override
            public int getSize() {
                return size;
            }

            @Override
            public void setSize(int size) {
                this.size = size;
            }

            @Override
            public WalletCategory getWalletCategory() {
                return walletCategory;
            }

            @Override
            public void setWalletCategory(WalletCategory walletCategory) {
                this.walletCategory = walletCategory;
            }

            @Override
            public FactoryProjectType getFactoryProjectType() {
                return factoryProjectType;
            }

            @Override
            public void setFactoryProjectType(FactoryProjectType factoryProjectType) {
                this.factoryProjectType = factoryProjectType;

            }
        };
        return walletFactoryProject;
    }

    private WalletFactoryProject getWalletFactoryProjectHeader(DatabaseTableRecord projectsRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {
        WalletFactoryProject walletFactoryProject = getEmptyWalletFactoryProject();
        walletFactoryProject.setProjectPublicKey(projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLICKEY_COLUMN_NAME));
        walletFactoryProject.setName(projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME));
        if (projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME) != null)
            walletFactoryProject.setDescription(projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME));
        if (projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME) != null)
            walletFactoryProject.setProjectState(WalletFactoryProjectState.getByCode(projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME)));
        if (projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLETTYPE_COLUMN_NAME) != null){
            try {
                walletFactoryProject.setWalletType(WalletType.getByCode(projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLETTYPE_COLUMN_NAME)));
            } catch (InvalidParameterException e) {
                // If I couldn't get the walletType I will define it Reference and continue
                walletFactoryProject.setWalletType(WalletType.REFERENCE);
            }
        }

        if (projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLETCATEGORY_COLUMN_NAME) != null){
            try {
                walletFactoryProject.setWalletCategory(WalletCategory.getByCode(projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLETCATEGORY_COLUMN_NAME)));
            } catch (InvalidParameterException e) {
                walletFactoryProject.setWalletCategory(WalletCategory.REFERENCE_WALLET);
            }
        }

        if (projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_FACTORYPROJECTTYPE_COLUMN_NAME) != null)
            try {
                walletFactoryProject.setFactoryProjectType(FactoryProjectType.getByCode(projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_FACTORYPROJECTTYPE_COLUMN_NAME)));
            } catch (InvalidParameterException e) {
                walletFactoryProject.setFactoryProjectType(FactoryProjectType.WALLET);
            }
        if (projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_CREATION_TIMESTAMP_COLUMN_NAME) != null)
            walletFactoryProject.setCreationTimestamp(Timestamp.valueOf(projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_CREATION_TIMESTAMP_COLUMN_NAME)));
        if (projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_MODIFICATION_TIMESTAMP_COLUMN_NAME) != null)
            walletFactoryProject.setLastModificationTimeststamp(Timestamp.valueOf(projectsRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_MODIFICATION_TIMESTAMP_COLUMN_NAME)));
        if (projectsRecord.getIntegerValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SIZE_COLUMN_NAME) != null)
            walletFactoryProject.setSize(projectsRecord.getIntegerValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SIZE_COLUMN_NAME));

        return walletFactoryProject;
    }
    /**
     * Gets the WalletFactoryProject filled with information from the database that matches the specified filter.
     * @param filter
     * @return
     * @throws CantLoadTableToMemoryException
     * @throws DatabaseOperationException
     */
    public List<WalletFactoryProject> getWalletFactoryProjects (DatabaseTableFilter filter) throws DatabaseOperationException {
        Database database= null;
        try{
            database = openDatabase();

            List<WalletFactoryProject> walletFactoryProjects = new ArrayList<>();

            // I will add the WalletFactoryProject header information from the database
            for (DatabaseTableRecord projectsRecord : getWalletFactoryProjectsData(filter)){
                WalletFactoryProject walletFactoryProject = getWalletFactoryProjectHeader (projectsRecord);

                // I will add the Skin information from database
                List<Skin> skins = new ArrayList<>();
                for (DatabaseTableRecord skinRecords : getSkinsData (walletFactoryProject.getProjectPublicKey())){
                    Skin skin = new Skin();
                    skin.setId(skinRecords.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.SKIN_SKIN_ID_COLUMN_NAME));
                    boolean isDefaultSkin = Boolean.valueOf(skinRecords.getStringValue(WalletFactoryMiddlewareDatabaseConstants.SKIN_DEFAULT_COLUMN_NAME));
                    if (isDefaultSkin)
                        walletFactoryProject.setDefaultSkin(skin);
                    else
                        skins.add(skin);
                }
                walletFactoryProject.setSkins(skins);

                // I will add the language information from database
                List<Language> languages = new ArrayList<>();
                for (DatabaseTableRecord languageRecords : getLanguagesData(walletFactoryProject.getProjectPublicKey())){
                    Language language = new Language();
                    language.setId(languageRecords.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_LANGUAGE_ID_COLUMN_NAME));
                    boolean isDefaultLanguage = Boolean.valueOf(languageRecords.getStringValue(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_DEFAULT_COLUMN_NAME));
                    if (isDefaultLanguage)
                        walletFactoryProject.setDefaultLanguage(language);
                    else
                        languages.add(language);
                }
                walletFactoryProject.setLanguages(languages);

                // I will add the navigation structure information from the database
                DatabaseTableRecord navigationStructureRecord = getNavigationStructureData(walletFactoryProject.getProjectPublicKey());
                if (navigationStructureRecord != null){
                    AppNavigationStructure navigationStructure = new AppNavigationStructure();
                    navigationStructure.setPublicKey(navigationStructureRecord.getStringValue(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_PUBLICKEY_COLUMN_NAME));
                    walletFactoryProject.setNavigationStructure(navigationStructure);
                }

                // At this point I have all the info from the database, I will add it to the list I will be returning.
                walletFactoryProjects.add(walletFactoryProject);
            }
            database.closeDatabase();
            return walletFactoryProjects;
        } catch (Exception e){
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get projects from the database with filter: " + filter.toString(), null);
        }
    }
}
