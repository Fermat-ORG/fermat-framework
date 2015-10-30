package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
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
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.database.RedeemPointActorDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.database.RedeemPointActorDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantAddPendingRedeemPointException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantGetRedeemPointActorProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantGetRedeemPointsListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantInitializeRedeemPointActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantUpdateRedeemPointException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.RedeemPointNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 06/10/15.
 *
 * @throws NullPointerException if the constructor failed to initialize the
 * database and you ignored the exception and attempt to execute any method.
 */
public class RedeemPointActorDao implements Serializable {

    String REDEEM_POINT_PROFILE_IMAGE_FILE_NAME = "RedeemPointActorProfileImage";

    /**
     * Represent the Plugin Database.
     */

    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    /**
     * Constructor with parameters
     * Because all the methods in this class do need a database connction
     * This constructor initialize the database and throws the respective exception
     * if it can't be done you shouldn't continue with the use of this class
     * because every method is going to throw a {@link NullPointerException}
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */

    public RedeemPointActorDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) throws CantInitializeRedeemPointActorDatabaseException {
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
     * @throws CantInitializeRedeemPointActorDatabaseException
     */
    private void initializeDatabase() throws CantInitializeRedeemPointActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeRedeemPointActorDatabaseException(CantInitializeRedeemPointActorDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            RedeemPointActorDatabaseFactory databaseFactory = new RedeemPointActorDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = databaseFactory.createDatabase(this.pluginId, RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeRedeemPointActorDatabaseException(CantInitializeRedeemPointActorDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        } catch (Exception e) {
            throw new CantInitializeRedeemPointActorDatabaseException(CantInitializeRedeemPointActorDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a unknown problem and i cannot open the database.");
        }
    }


    /**
     * Método createNewRedeemPoint.
     * Crea un nuevo RedeemPoint y lo registra en la base de datos. Si ya existía un RedeemPoint
     * creado con el PublicKey suministrado entonces a éste se le actualiza su {@link ConnectionState}
     * al suministrado.
     *
     * @param redeemPoint                  Alguna implementación de {@link ActorAssetRedeemPoint} con los valores ya
     *                                     seteados que se insetarán en la base de datos.
     * @param redeemPointLoggedInPublicKey El PublicKey del usuario logeado.
     * @throws CantAddPendingRedeemPointException las razones para que se arroje esta excepción son las
     *                                            siguientes: -{@link CantInsertRecordException}
     *                                            -{@link CantUpdateRedeemPointException} en caso de que ya existiese el RedeemPoint y no
     *                                            se haya podido actualizar.
     *                                            O en caso de que haya sucedido alguna excepción no prevista.
     */
    public void createNewRedeemPoint(String redeemPointLoggedInPublicKey, ActorAssetRedeemPoint redeemPoint) throws CantAddPendingRedeemPointException {

        try {
            /**
             * if Redeem Point exist on table
             * change status
             */
            if (redeemPointExists(redeemPoint.getPublicKey())) {

                this.updateRedeemPointConnectionState(redeemPointLoggedInPublicKey, redeemPoint.getPublicKey(), redeemPoint.getConnectionState());

            } else {

                DatabaseTable table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, redeemPointLoggedInPublicKey);
                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, redeemPoint.getPublicKey());

                setValuesToRecord(record, redeemPoint);

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewRedeemPointProfileImage(redeemPoint.getPublicKey(), redeemPoint.getProfileImage());
            }
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingRedeemPointException("CAN'T INSERT REDEEM POINT", e, "", "Cant create new REDEEM POINT, insert database problems.");
        } catch (CantUpdateRedeemPointException e) {
            throw new CantAddPendingRedeemPointException("CAN'T INSERT REDEEM POINT", FermatException.wrapException(e), "", "Cant update exist REDEEM POINT state, unknown failure.");
        } catch (Exception e) {
            // Failure unknown.
            throw new CantAddPendingRedeemPointException("CAN'T INSERT REDEEM POINT", FermatException.wrapException(e), "", "Cant create new REDEEM POINT, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public void createNewRedeemPointRegisterInNetworkService(ActorAssetRedeemPoint redeemPoint) throws CantAddPendingRedeemPointException {
        try {
            /**
             * if Redeem Point exist on table
             * change status
             */
            if (redeemPointExists(redeemPoint.getPublicKey())) {
                updateRedeemPointRegisteredConnectionState(redeemPoint.getPublicKey(), ConnectionState.CONNECTED);
            } else {

                DatabaseTable table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, System.currentTimeMillis());
                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");
                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME, redeemPoint.getPublicKey());

                setValuesToRecordRegistered(record, redeemPoint);

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewRedeemPointProfileImage(redeemPoint.getPublicKey(), redeemPoint.getProfileImage());
            }
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingRedeemPointException("CAN'T INSERT REDEEM POINT", e, "", "Cant create new REDEEM POINT, insert database problems.");
        } catch (CantUpdateRedeemPointException e) {
            throw new CantAddPendingRedeemPointException("CAN'T INSERT REDEEM POINT", FermatException.wrapException(e), "", "Cant update exist REDEEM POINT state, unknown failure.");
        } catch (Exception e) {
            // Failure unknown.
            throw new CantAddPendingRedeemPointException("CAN'T INSERT REDEEM POINT", FermatException.wrapException(e), "", "Cant create new REDEEM POINT, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    /**
     * Método updateRedeemPoint:
     * este método actualiza todas las propiedades del , ActorAssetRedeemPoint incluyendo su imagen
     * de perfil, por motivos de eficiencia el único campo que se revisa si no ha sido cambiado
     * es la imagen de perfil, el resto simplemente se sobreescribe. El criterio para buscar al
     * redeemPoint a actualizar es simplemente su llave primaria: "publicKey".
     * <p></p>
     * this method updates all the properties of the ActorAssetRedeemPoint, including its profile image,
     * because of performance reasons the only field which check if it has been changed is the
     * profile image, all the other fields just get overwrite. The filter for search the specific
     * redeemPoint is its primary key: "publicKey".
     *
     * @param redeemPoint
     * @throws RedeemPointNotFoundException            Si se provee un ActorAssetRedeemPoint cuya publicKey no esté
     *                                                 registrada simplemente no se encontrarán registros, para esto se usa esta excepción para
     *                                                 notificar al consumidor de la API que esa publicKey no se encuentra registrada.
     *                                                 <p></p>
     *                                                 If an ActorAssetRedeemPoint which public key is not registered in the database is provided then no
     *                                                 records would be found thus no update would be made. I need to notify the consumer about that.
     * @throws CantUpdateRedeemPointException          En caso de que haya un error con la base de datos.
     *                                                 <p></p>
     *                                                 If there's an error with the database.
     * @throws CantGetUserDeveloperIdentitiesException Unchecked Exception.
     *                                                 En caso de que no se consiga la tabla del ActorAssetRedeemPoint.
     *                                                 <p></p>
     *                                                 If the ActorAssetRedeemPoint database table can't be found.
     */
    public void updateRedeemPoint(ActorAssetRedeemPoint redeemPoint) throws CantUpdateRedeemPointException, RedeemPointNotFoundException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Redeem Point actor list, table not found.", "Redeem Point Actor", "");
            }

            // 2) Find the Redeem Point , filter by keys.
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, redeemPoint.getPublicKey(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            if (table.getRecords().isEmpty()) {
                throw new RedeemPointNotFoundException("The following public key was not found: " + redeemPoint.getPublicKey());
            }

            // 3) Get Redeem Point record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                setValuesToRecord(record, redeemPoint);
                updateRedeemPointProfileImage(redeemPoint.getPublicKey(), redeemPoint.getProfileImage());
                table.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            throw new CantUpdateRedeemPointException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateRedeemPointException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    /**
     * Método updateRedeemPointConnectionState.
     * Este método busca los registros por los PublicKey suministrados
     * y luego actualiza su estado al suministrado.
     *
     * @param redeemPointLoggedInPublicKey
     * @param redeemPointToAddPublicKey
     * @param connectionState
     * @throws CantUpdateRedeemPointException esta excepción es arrojada por
     *                                        los siguientes motivos:
     *                                        - {@link CantGetUserDeveloperIdentitiesException} en caso de que la tabla no exista.
     *                                        - {@link CantLoadTableToMemoryException} en caso de que no se pueda cargar la tabla de la memoria.
     *                                        - {@link CantUpdateRecordException} cuando sucede algún error durante la actualización del registro
     */
    public void updateRedeemPointConnectionState(String redeemPointLoggedInPublicKey, String redeemPointToAddPublicKey, ConnectionState connectionState) throws CantUpdateRedeemPointException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Redeem Point actor list, table not found.", "Redeem Point Actor", "");
            }

            // 2) Find the Redeem Point , filter by keys.
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, redeemPointToAddPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, redeemPointLoggedInPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();


            // 3) Get Redeem Point record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateRedeemPointException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME + " table in memory.");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateRedeemPointException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateRedeemPointException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public void updateRedeemPointRegisteredConnectionState(String redeemPointToAddPublicKey, ConnectionState connectionState) throws CantUpdateRedeemPointException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Redeem Point actor list, table not found.", "Redeem Point Actor", "");
            }

            // 2) Find the Redeem Point , filter by keys.
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME, redeemPointToAddPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();


            // 3) Get Redeem Point record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_MODIFIED_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateRedeemPointException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME + " table in memory.");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateRedeemPointException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateRedeemPointException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    /**
     * Método getAllARedeemPoints.
     * Devuelve una {@link List} de {@link ActorAssetRedeemPoint} que
     * contiene todos los {@code ActorAssetRedeemPoint} para el Public Key
     * del usuario que se suministra.
     *
     * @param redeemPointLoggedInPublicKey El PublicKey del usuario logeado.
     * @param max                          Cantidad máxima de resultados
     * @param offset                       Cantidad de paginación del query.
     * @return Una lista con todos los récords encontrados, o una lista vacía si no se encontró ninguno.
     * @throws CantGetRedeemPointsListException las razones para que se arroje esta excepción son:
     *                                          - {@link CantLoadTableToMemoryException}
     *                                          - {@link CantGetRedeemPointActorProfileImageException}
     *                                          o que alguna excepción no prevista haya sucedido. Antes de arrojar esta excepción siempre se
     *                                          cierra la base de datos en la que se está trabajando.
     */
    public List<ActorAssetRedeemPoint> getAllARedeemPoints(String redeemPointLoggedInPublicKey, int max, int offset) throws CantGetRedeemPointsListException {

        // Setup method.
        List<ActorAssetRedeemPoint> list = new ArrayList<ActorAssetRedeemPoint>(); // Redeem Point Actor list.
        DatabaseTable table;

        // Get Redeem Points identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Redeem Point identity list, table not found.", "Plugin Identity", "Cant get Redeem Point identity list, table not found.");
            }

            // 2) Find all Redeem Points.
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, redeemPointLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONNECTION_STATE_COLUMN_NAME, ConnectionState.CONNECTED.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Redeem Points Recorod.

            addRecordsToList(list, table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME + " table in memory.");
        } catch (CantGetRedeemPointActorProfileImageException e) {
            // Failure unknown.
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetRedeemPointsListException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Actor", "Cant get Redeem Point Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    /**
     * Método getRedeemPoints.
     * Devuelve una {@link List} de {@code ActorAssetRedeemPoint}
     * que es construida haciendo un query con los parámetros de búsqueda
     * suministrados.
     *
     * @param redeemPointLoggedInPublicKey El PublicKey del usuario logeado.
     * @param connectionState              El estado de conexión del RedeemPoint.
     * @param max                          Cantidad máxima de resultados
     * @param offset                       Cantidad de paginación del query.
     * @return Una lista con todos los récords encontrados, o una lista vacía si no se encontró ninguno.
     * @throws CantGetRedeemPointsListException las razones para que se arroje esta excepción son:
     *                                          - {@link CantLoadTableToMemoryException}
     *                                          - {@link CantGetRedeemPointActorProfileImageException}
     *                                          o que alguna excepción no prevista haya sucedido. Antes de arrojar esta excepción siempre se
     *                                          cierra la base de datos en la que se está trabajando.
     */
    public List<ActorAssetRedeemPoint> getRedeemPoints(String redeemPointLoggedInPublicKey, ConnectionState connectionState, int max, int offset) throws CantGetRedeemPointsListException {

        // Setup method.
        List<ActorAssetRedeemPoint> list = new ArrayList<ActorAssetRedeemPoint>(); // Redeem Point Actor list.
        DatabaseTable table;

        // Get Redeem Points identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Redeem Point identity list, table not found.", "Plugin Identity", "Cant get Redeem Point identity list, table not found.");
            }
            // 2) Find  Redeem Points by state.
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, redeemPointLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Redeem Points Recorod.

            addRecordsToList(list, table.getRecords());

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME + " table in memory.");
        } catch (CantGetRedeemPointActorProfileImageException e) {
            // Failure unknown.
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetRedeemPointsListException(e.getMessage(), FermatException.wrapException(e), "Redeem Points Actor", "Cant get Redeem Point Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    public ActorAssetRedeemPoint getActorAssetRedeemPoint() throws CantGetRedeemPointsListException {

        ActorAssetRedeemPoint actorAssetRedeemPoint = new RedeemPointActorRecord();
        DatabaseTable table;

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            table.loadToMemory();
            // 3) Get Asset Users Record.
            actorAssetRedeemPoint = this.addRecords(table.getRecords());
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Asset User Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME + " table in memory.");
        } catch (CantGetRedeemPointActorProfileImageException e) {
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetRedeemPointsListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the values.
        return actorAssetRedeemPoint;
    }

    public List<ActorAssetRedeemPoint> getAllAssetRedeemPointActorRegistered() throws CantGetRedeemPointsListException {
        List<ActorAssetRedeemPoint> list = new ArrayList<>(); // Asset Issuer Actor list.

        DatabaseTable table;

        // Get Asset Issuer identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_TABLE_NAME);

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
                list.add(new RedeemPointActorRecord(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_NAME_COLUMN_NAME),
                        record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                        getRedeemPointProfileImagePrivateKey(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_REGISTRATION_DATE_COLUMN_NAME)));           }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor Registered", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetRedeemPointActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor Registered", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetRedeemPointsListException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Actor Registered", "Cant get Redeem Point Actor Registered list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetRedeemPoint> getAllAssetRedeemPointActorConnected() throws CantGetRedeemPointsListException {
        List<ActorAssetRedeemPoint> list = new ArrayList<>(); // Asset Issuer Actor list.

        DatabaseTable table;

        // Get Asset Issuer identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_TABLE_NAME);

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
                list.add(new RedeemPointActorRecord(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_NAME_COLUMN_NAME),
                        record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                        getRedeemPointProfileImagePrivateKey(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_REGISTRATION_DATE_COLUMN_NAME)));           }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor Registered", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetRedeemPointActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetRedeemPointsListException(e.getMessage(), e, "Redeem Point Actor Registered", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetRedeemPointsListException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Actor Registered", "Cant get Redeem Point Actor Registered list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    private void addRecordsToList(List<ActorAssetRedeemPoint> list, List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetRedeemPointActorProfileImageException {

        for (DatabaseTableRecord record : records) {
            // Add records to list.

            //INICIALIZAR, VALORES OBLIGATORIOS: Nombre y PublicKey
            RedeemPointActorRecord redeemPointActorRecord = new RedeemPointActorRecord(
                    record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_NAME_COLUMN_NAME));

            //SETEAR EL ADDRESS
            RedeemPointActorAddress address = new RedeemPointActorAddress();
            address.setCountryName(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_COUNTRY_NAME_COLUMN_NAME));
            address.setProvinceName(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_PROVINCE_NAME_COLUMN_NAME));
            address.setProvinceName(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_CITY_NAME_COLUMN_NAME));
            address.setStreetName(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_STREET_NAME_COLUMN_NAME));
            address.setPostalCode(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_POSTAL_CODE_COLUMN_NAME));
            address.setHouseNumber(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_HOUSE_NUMBER_COLUMN_NAME));
            redeemPointActorRecord.setAddress(address);

            //SETEAR EL CRYPTOADDRESS
            CryptoAddress cryptoAddress = new CryptoAddress();
            cryptoAddress.setAddress(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_ADDRESS_COLUMN_NAME));
            cryptoAddress.setCryptoCurrency(CryptoCurrency.getByCode(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_CURRENCY_COLUMN_NAME)));
            redeemPointActorRecord.setCryptoAddress(cryptoAddress);

            //SETEAR LOCATION
            DeviceLocation location = new DeviceLocation();
            try {
                location.setLatitude(record.getDoubleValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_LOCATION_LATITUDE_COLUMN_NAME));
                location.setLongitude(record.getDoubleValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_LOCATION_LONGITUDE_COLUMN_NAME));
            } catch (NumberFormatException e) {
                //If the saved location cannot be parsed to double ("-", "null") then I'll keep it as the default null value
            }
            redeemPointActorRecord.setLocation(location);
            //SETEAR EL CONECTIONSTATE, éste se registra en la BBDD con su código.
            redeemPointActorRecord.setConnectionState(ConnectionState.getByCode(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONNECTION_STATE_COLUMN_NAME)));

            //SETEAR LA IMAGEN
            redeemPointActorRecord.setProfileImage(getRedeemPointProfileImagePrivateKey(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME)));

            //SETEAR LOS OTROS ATRIBUTOS
            redeemPointActorRecord.setHoursOfOperation(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_HOURS_OF_OPERATION_COLUMN_NAME));
            redeemPointActorRecord.setContactInformation(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONTACT_INFORMATION_COLUMN_NAME));

            list.add(redeemPointActorRecord);
        }
    }

    /**
     * Private Methods
     */
    private void persistNewRedeemPointProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    REDEEM_POINT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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


    private void updateRedeemPointProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    REDEEM_POINT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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

    private void setValuesToRecord(DatabaseTableRecord record, ActorAssetRedeemPoint redeemPoint) {
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_NAME_COLUMN_NAME, redeemPoint.getName());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONNECTION_STATE_COLUMN_NAME, redeemPoint.getConnectionState().getCode());
        record.setLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME, System.currentTimeMillis());
        //LOCATION
//        record.setDoubleValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_LOCATION_LONGITUDE_COLUMN_NAME, redeemPoint.getLocation().getLongitude());
//        record.setDoubleValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_LOCATION_LATITUDE_COLUMN_NAME, redeemPoint.getLocation().getLatitude());
        //ADDRESS
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_COUNTRY_NAME_COLUMN_NAME, redeemPoint.getAddress().getCountryName());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_STREET_NAME_COLUMN_NAME, redeemPoint.getAddress().getStreetName());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_PROVINCE_NAME_COLUMN_NAME, redeemPoint.getAddress().getProvinceName());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_CITY_NAME_COLUMN_NAME, redeemPoint.getAddress().getCityName());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_POSTAL_CODE_COLUMN_NAME, redeemPoint.getAddress().getPostalCode());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_HOUSE_NUMBER_COLUMN_NAME, redeemPoint.getAddress().getHouseNumber());
        //CRYPTOADDRESS
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_CURRENCY_COLUMN_NAME, redeemPoint.getCryptoAddress().getCryptoCurrency().getCode());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_ADDRESS_COLUMN_NAME, redeemPoint.getCryptoAddress().getAddress());

        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONTACT_INFORMATION_COLUMN_NAME, redeemPoint.getContactInformation());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_HOURS_OF_OPERATION_COLUMN_NAME, redeemPoint.getHoursOfOperation());
    }


    private void setValuesToRecordRegistered(DatabaseTableRecord record, ActorAssetRedeemPoint redeemPoint) {
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_NAME_COLUMN_NAME, redeemPoint.getName());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CONNECTION_STATE_COLUMN_NAME, redeemPoint.getConnectionState().getCode());
        record.setLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_MODIFIED_DATE_COLUMN_NAME, System.currentTimeMillis());
        //LOCATION
//        record.setDoubleValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, redeemPoint.getLocation().getLongitude());
//        record.setDoubleValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, redeemPoint.getLocation().getLatitude());
        //ADDRESS
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_COUNTRY_NAME_COLUMN_NAME, redeemPoint.getAddress().getCountryName());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_STREET_NAME_COLUMN_NAME, redeemPoint.getAddress().getStreetName());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_PROVINCE_NAME_COLUMN_NAME, redeemPoint.getAddress().getProvinceName());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_CITY_NAME_COLUMN_NAME, redeemPoint.getAddress().getCityName());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_POSTAL_CODE_COLUMN_NAME, redeemPoint.getAddress().getPostalCode());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_HOUSE_NUMBER_COLUMN_NAME, redeemPoint.getAddress().getHouseNumber());
        //CRYPTOADDRESS
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME, redeemPoint.getCryptoAddress().getCryptoCurrency().getCode());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, redeemPoint.getCryptoAddress().getAddress());

        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CONTACT_INFORMATION_COLUMN_NAME, redeemPoint.getContactInformation());
        record.setStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_HOURS_OF_OPERATION_COLUMN_NAME, redeemPoint.getHoursOfOperation());
    }

    private byte[] getRedeemPointProfileImagePrivateKey(String publicKey) throws CantGetRedeemPointActorProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    REDEEM_POINT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetRedeemPointActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);

        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetRedeemPointActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting Redeem Point Actor private keys file.", null);
        } catch (Exception e) {
            throw new CantGetRedeemPointActorProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }
        return profileImage;
    }

    /**
     * <p>Method that check if Redeem Point public key exists.
     *
     * @param redeemPointToAddPublicKey
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean redeemPointExists(String redeemPointToAddPublicKey) throws CantCreateNewDeveloperException {

        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = this.database.getTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not  found.", "Redeem Point Actor", "Cant check if alias exists, table not found.");
            }
            table.setStringFilter(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, redeemPointToAddPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return !table.getRecords().isEmpty();

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Redeem Point Actor", "Cant load " + RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Actor", "Cant check if alias exists, unknown failure.");
        }
    }

    private ActorAssetRedeemPoint addRecords(List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetRedeemPointActorProfileImageException {
        RedeemPointActorRecord redeemPointActor = null;
        for (DatabaseTableRecord record : records) {

//            CryptoAddress cryptoAddress = new CryptoAddress(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_ADDRESS_COLUMN_NAME),
//                    CryptoCurrency.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_CURRENCY_COLUMN_NAME)));

            redeemPointActor = new RedeemPointActorRecord(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_NAME_COLUMN_NAME),
//                    record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_AGE_COLUMN_NAME),
//                    Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.REDEEM_POINT_GENDER_COLUMN_NAME)),
                    ConnectionState.getByCode(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_LOCATION_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_LOCATION_LONGITUDE_COLUMN_NAME),
//                    cryptoAddress,
                    record.getLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME),
//                    record.getLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_LAST_CONNECTION_DATE_COLUMN_NAME),
                    getRedeemPointProfileImagePrivateKey(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME)));
        }
        return redeemPointActor;
    }

    private void addRecordsTableRegisteredToList(List<ActorAssetRedeemPoint> list, List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetRedeemPointActorProfileImageException {

        for (DatabaseTableRecord record : records) {

//            CryptoAddress cryptoAddress = new CryptoAddress(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME),
//                    CryptoCurrency.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME)));

            list.add(new RedeemPointActorRecord(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_NAME_COLUMN_NAME),
//                    record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_AGE_COLUMN_NAME),
//                    Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME)),
                    ConnectionState.getByCode(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME),
//                    cryptoAddress,
                    record.getLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_REGISTRATION_DATE_COLUMN_NAME),
//                    record.getLongValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME),
                    getRedeemPointProfileImagePrivateKey(record.getStringValue(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME))));
        }
    }
}
