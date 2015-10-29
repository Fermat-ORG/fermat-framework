package com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.database.AssetIssuerActorDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.database.AssetIssuerActorDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.AssetIssuerNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantAddPendingAssetIssuerException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantGetAssetIssuerActorProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantGetAssetIssuersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantUpdateAssetIssuerException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 06/10/15.
 *
 * @throws NullPointerException if the constructor failed
 * to initalize the database and you ignored that exception.
 */
public class AssetIssuerActorDao implements Serializable {

    String ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME = "assetIssuerActorProfileImage";

    /**
     * Represent the Plugin Database.
     */

    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */

    public AssetIssuerActorDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) throws CantInitializeAssetIssuerActorDatabaseException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        initializeDatabase();
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    /**
     * This method open or creates the database i'll be working with     *
     *
     * @throws CantInitializeAssetIssuerActorDatabaseException
     */
    private void initializeDatabase() throws CantInitializeAssetIssuerActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeAssetIssuerActorDatabaseException(CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetIssuerActorDatabaseFactory databaseFactory = new AssetIssuerActorDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = databaseFactory.createDatabase(this.pluginId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetIssuerActorDatabaseException(CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        } catch (Exception e) {
            throw new CantInitializeAssetIssuerActorDatabaseException(CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a unknown problem and i cannot open the database.");
        }
    }

    public void createNewAssetIssuer(ActorAssetIssuer assetIssuerActorRecord) throws CantAddPendingAssetIssuerException {
        try {
            /**
             * if Asset Issuer exist on table
             * change status
             */
//            if (assetIssuerExists(assetIssuerActorRecord.getPublicKey())) {
//                this.updateAssetIssuerConnectionState(assetIssuerLoggedInPublicKey, assetIssuerActorRecord.getPublicKey(), assetIssuerActorRecord.getConnectionState());
//            } else {
                /**
                 * Get actual date
                 */
            DatabaseTable table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("CANT GET ASSET ISSUER ACTOR, TABLE NOT FOUND.", " ASSET ISSUER ACTOR", "");
            } else {
                table.loadToMemory();
                if (table.getRecords().size() == 0) {
                    DatabaseTableRecord record = table.getEmptyRecord();

                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");

                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerActorRecord.getPublicKey());
                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME, assetIssuerActorRecord.getName());

                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, assetIssuerActorRecord.getConnectionState().getCode());

                    record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLatitude());
                    record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLongitude());

                    record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME, assetIssuerActorRecord.getRegistrationDate());
                    record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_MODIFIED_DATE_COLUMN_NAME, System.currentTimeMillis());

                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_EXTENDED_COLUMN_NAME, "-");
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getCryptoCurrency().getCode());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getAddress());
                    record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, assetIssuerActorRecord.getDescription());

                    table.insertRecord(record);
                    /**
                     * Persist profile image on a file
                     */
                    persistNewAssetIssuerProfileImage(assetIssuerActorRecord.getPublicKey(), assetIssuerActorRecord.getProfileImage());
                }
            }
            database.closeDatabase();
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", e, "", "Cant create new ASSET ISSUER, insert database problems.");
        } catch (Exception e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET ISSUER, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    /**
     * Crea un nuevo registro en la tabla de Actor AssetIssuer con los
     * parámetros suministrados.
     * Creates a new entry in the Actor Asset Issuer table with the given parameters.
     *
     * @param assetIssuerLoggedInPublicKey El LoggedIn Public Key del issuer que se va a insertar
     *                                     The LoggedIn Public Key from the issuer to be inserted.
     * @param assetIssuerActorRecord       Toda la información correspondiente al Asset Issuer, incluyendo
     *                                     el public key que es su llave primaria.
     *                                     The information corresponding to the Asset Issuer, including
     *                                     the public key, which is its primary key.
     * @throws CantAddPendingAssetIssuerException En caso de haber algún error en la inserción a la
     *                                            base de datos o que se no se le suministre un public
     *                                            key al Asset Issuer.
     *                                            When an error is found during the insert, or when
     *                                            the given public key is null.
     */
    public void createNewAssetIssuer(String assetIssuerLoggedInPublicKey, ActorAssetIssuer assetIssuerActorRecord) throws CantAddPendingAssetIssuerException {
        try {
            /**
             * if Asset Issuer exist on table
             * change status
             */
            if (assetIssuerExists(assetIssuerActorRecord.getPublicKey())) {
                this.updateAssetIssuerConnectionState(assetIssuerLoggedInPublicKey, assetIssuerActorRecord.getPublicKey(), assetIssuerActorRecord.getConnectionState());
            } else {
                /**
                 * Get actual date
                 */
                DatabaseTable table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerActorRecord.getPublicKey());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey);
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME, assetIssuerActorRecord.getName());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, assetIssuerActorRecord.getConnectionState().getCode());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_MODIFIED_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_EXTENDED_COLUMN_NAME, "-");
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getCryptoCurrency().getCode());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getAddress());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocation().getLatitude());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocation().getLongitude());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, assetIssuerActorRecord.getDescription());

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetIssuerProfileImage(assetIssuerActorRecord.getPublicKey(), assetIssuerActorRecord.getProfileImage());
            }
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", e, "", "Cant create new ASSET ISSUER, insert database problems.");
        } catch (CantUpdateAssetIssuerException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant update exist ASSET ISSUER state, unknown failure.");
        } catch (Exception e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET ISSUER, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public void createNewAssetIssuerRegistered(ActorAssetIssuer assetIssuerActorRecord) throws CantAddPendingAssetIssuerException {
        try {
            /**
             * if Asset Issuer exist on table
             * change status
             */
            if (assetIssuerExists(assetIssuerActorRecord.getPublicKey())) {
                updateAssetIssuerConnectionStateRegistered(assetIssuerActorRecord.getPublicKey(), ConnectionState.CONNECTED);
            } else {
                /**
                 * Get actual date
                 */
                DatabaseTable table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetIssuerActorRecord.getPublicKey());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME, assetIssuerActorRecord.getName());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, assetIssuerActorRecord.getConnectionState().getCode());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_MODIFIED_DATE_COLUMN_NAME, System.currentTimeMillis());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getCryptoCurrency().getCode());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME, assetIssuerActorRecord.getCryptoAddress().getAddress());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLatitude());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, assetIssuerActorRecord.getLocationLongitude());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME, assetIssuerActorRecord.getDescription());

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetIssuerProfileImage(assetIssuerActorRecord.getPublicKey(), assetIssuerActorRecord.getProfileImage());

            }
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", e, "", "Cant create new ASSET ISSUER, insert database problems.");
        } catch (CantUpdateAssetIssuerException e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant update exist ASSET ISSUER state, unknown failure.");
        } catch (Exception e) {
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET ISSUER, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    /**
     * Método updateAssetIssuer:
     * este método actualiza todas las propiedades del actorAssetIssuer, incluyendo su imagen
     * de perfil, por motivos de eficiencia el único campo que se revisa si no ha sido cambiado
     * es la imagen de perfil, el resto simplemente se sobreescribe. El criterio para buscar al
     * actorAssetIssuer a actualizar es simplemente su llave primaria: "publicKey".
     * <p></p>
     * this method updates all the properties of the actorAssetIssuer, including its profile image,
     * because of performance reasons the only field which check if it has been changed is the
     * profile image, all the other fields just get overwrite. The filter for search the specific
     * AssetIssuer is its primary key: "publicKey".
     *
     * @param actorAssetIssuer
     * @throws AssetIssuerNotFoundException            Si se provee un ActorAssetIssuer cuya publicKey no esté
     *                                                 registrada simplemente no se encontrarán registros, para esto se usa esta excepción para
     *                                                 notificar al consumidor de la API que esa publicKey no se encuentra registrada.
     *                                                 <p></p>
     *                                                 If an AssetIssuer which public key is not registered in the database is provided then no
     *                                                 records would be found thus no update would be made. I need to notify the consumer about that.
     * @throws CantUpdateAssetIssuerException          En caso de que haya un error con la base de datos.
     *                                                 <p></p>
     *                                                 If there's an error with the database.
     * @throws CantGetUserDeveloperIdentitiesException Unchecked Exception.
     *                                                 En caso de que no se consiga la tabla del AssetIssuer.
     *                                                 <p></p>
     *                                                 If the AssetIssuer table can't be found.
     */
    public void updateAssetIssuer(ActorAssetIssuer actorAssetIssuer) throws CantUpdateAssetIssuerException, AssetIssuerNotFoundException {

        DatabaseTable table;

        try {
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, actorAssetIssuer.getPublicKey(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            if (table.getRecords().isEmpty()) {
                throw new AssetIssuerNotFoundException("The following public key was not found: " + actorAssetIssuer.getPublicKey());
            }

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                //UPDATE PROPERTIES

                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, actorAssetIssuer.getConnectionState().getCode());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME, actorAssetIssuer.getName());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME, actorAssetIssuer.getLocation().getLatitude());
                record.setDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetIssuer.getLocation().getLongitude());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME, actorAssetIssuer.getCryptoAddress().getAddress());
//                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME, actorAssetIssuer.getCryptoAddress().getCryptoCurrency().getCode());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, actorAssetIssuer.getDescription());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_MODIFIED_DATE_COLUMN_NAME, System.currentTimeMillis());

                //UPDATE PROFILE IMAGE
                updateAssetIssuerProfileImage(actorAssetIssuer.getPublicKey(), actorAssetIssuer.getProfileImage());

                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }


    public void updateAssetIssuerConnectionState(String assetIssuerLoggedInPublicKey, String assetIssuerToAddPublicKey, ConnectionState connectionState) throws CantUpdateAssetIssuerException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_MODIFIED_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }


    public void updateAssetIssuerConnectionStateRegistered(String assetIssuerToAddPublicKey, ConnectionState connectionState) throws CantUpdateAssetIssuerException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_MODIFIED_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), e, "asset issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetIssuerException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public List<ActorAssetIssuer> getAllAssetIssuers(String assetIssuerLoggedInPublicKey, int max, int offset) throws CantGetAssetIssuersListException {

        // Setup method.
        List<ActorAssetIssuer> list = new ArrayList<ActorAssetIssuer>(); // Asset Issuer Actor list.
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer identity list, table not found.", "Plugin Identity", "Cant get Asset Issuer identity list, table not found.");
            }

            // 2) Find all Asset Users.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, ConnectionState.CONNECTED.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Recorod.

            this.addRecordsToList(list, table.getRecords());

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            // Failure unknown.
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetIssuer> getAssetIssuers(String aasetUserLoggedInPublicKey, ConnectionState connectionState, int max, int offset) throws CantGetAssetIssuersListException {

        // Setup method.
        List<ActorAssetIssuer> list = new ArrayList<ActorAssetIssuer>(); // Asset Issuer Actor list.
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer identity list, table not found.", "Plugin Identity", "Cant get asset issuer identity list, table not found.");
            }
            // 2) Find  Asset Users by state.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, aasetUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Recorod.
            this.addRecordsToList(list, table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            // Failure unknown.
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    public ActorAssetIssuer getActorAssetIssuer() throws CantGetAssetIssuersListException {

        ActorAssetIssuer assetIssuerActorRecord = new AssetIssuerActorRecord();
        DatabaseTable table;

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            table.loadToMemory();
            // 3) Get Asset Users Record.
            assetIssuerActorRecord = this.addRecords(table.getRecords());
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the values.
        return assetIssuerActorRecord;
    }

    public List<ActorAssetIssuer> getAllAssetIssuerActorConnected() throws CantGetAssetIssuersListException {
        List<ActorAssetIssuer> list = new ArrayList<>(); // Asset User Actor list.
        DatabaseTable table;
        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            // 2) Find  Asset Users by Connection State.
//            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, "-", DatabaseFilterType.GRATER_THAN);
//            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME, assetUserToAddPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            this.addRecordsTableRegisteredToList(list, table.getRecords());

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    private void updateAssetIssuerProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            //If the profileImage hasn't been update don't modify it.
            if (file.getContent().equals(profileImage)) {
                return;
            }
            file.delete();
            file.setContent(profileImage);
            file.persistToMedia();
        } catch (CantPersistFileException | CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (Exception e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * Private Methods
     */
    private void persistNewAssetIssuerProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);

        } catch (CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
        }
    }

    private void addRecordsToList(List<ActorAssetIssuer> list, List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetIssuerActorProfileImageException {
        for (DatabaseTableRecord record : records) {

            //Inicializar el AssetIssuerActorRecord, nombre y public key son obligatorios.
            AssetIssuerActorRecord assetIssuerActorRecord = new AssetIssuerActorRecord(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME), record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME));

            assetIssuerActorRecord.setContactState(ConnectionState.getByCode(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME)));
            assetIssuerActorRecord.setProfileImage(getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME)));
            assetIssuerActorRecord.setDescription(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME));
            assetIssuerActorRecord.setRegistrationDate(record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME));

            //CRYPTOADDRESS
//            CryptoAddress cryptoAddress = new CryptoAddress();
//            cryptoAddress.setCryptoCurrency(CryptoCurrency.getByCode(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME)));
//            cryptoAddress.setAddress(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME));
//            assetIssuerActorRecord.setCryptoAddress(cryptoAddress);

            //LOCATION
            DeviceLocation deviceLocation = new DeviceLocation();
            deviceLocation.setLatitude(record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME));
            deviceLocation.setLongitude(record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME));
            assetIssuerActorRecord.setLocation(deviceLocation);
            // Add records to list. AssetIssuerActorRecord

            list.add(assetIssuerActorRecord);

        }
    }

    private byte[] getAssetIssuerProfileImagePrivateKey(String publicKey) throws CantGetAssetIssuerActorProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetAssetIssuerActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);

        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetAssetIssuerActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting Asset Issuer Actor private keys file.", null);
        } catch (Exception e) {
            throw new CantGetAssetIssuerActorProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }
        return profileImage;
    }

    /**
     * <p>Method that check if Asset Issuer public key exists.
     *
     * @param assetIssuerToAddPublicKey
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean assetIssuerExists(String assetIssuerToAddPublicKey) throws CantCreateNewDeveloperException {

        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */
        try {
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not  found.", "Asset Issuer Actor", "Cant check if alias exists, table not found.");
            }
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant check if alias exists, unknown failure.");
        }
    }

    public List<ActorAssetIssuer> getAllAssetIssuerActorRegistered() throws CantGetAssetIssuersListException {
        List<ActorAssetIssuer> list = new ArrayList<>(); // Asset Issuer Actor list.

        DatabaseTable table;

        // Get Asset Issuer identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset Issuer identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }

            table.loadToMemory();

            // 3) Get Asset Issuer Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {
                // Add records to list.
                list.add(new AssetIssuerActorRecord(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME),
                        record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                        getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME)));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor Registered", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor Registered", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor Registered", "Cant get Asset Issuer Actor Registered list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    private ActorAssetIssuer addRecords(List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetIssuerActorProfileImageException {
        AssetIssuerActorRecord actorAssetIssuer = null;
        for (DatabaseTableRecord record : records) {

//            CryptoAddress cryptoAddress = new CryptoAddress(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_USER_CRYPTO_ADDRESS_COLUMN_NAME),
//                    CryptoCurrency.getByCode(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_USER_CRYPTO_CURRENCY_COLUMN_NAME)));

            actorAssetIssuer = new AssetIssuerActorRecord(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME),
//                    record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_AGE_COLUMN_NAME),
//                    Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GENDER_COLUMN_NAME)),
                    ConnectionState.getByCode(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME),
//                    cryptoAddress,
                    record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME),
//                    record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME),
                    getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME)),
                    record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME));
        }
        return actorAssetIssuer;
    }

    private void addRecordsTableRegisteredToList(List<ActorAssetIssuer> list, List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetIssuerActorProfileImageException {

        for (DatabaseTableRecord record : records) {

//            CryptoAddress cryptoAddress = new CryptoAddress(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME),
//                    CryptoCurrency.getByCode(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME)));

            list.add(new AssetIssuerActorRecord(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME),
//                    record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_AGE_COLUMN_NAME),
//                    Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME)),
                    ConnectionState.getByCode(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME),
//                    cryptoAddress,
                    record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME),
//                    record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME),
                    getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME)),
                    record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME)));
        }
    }
}
