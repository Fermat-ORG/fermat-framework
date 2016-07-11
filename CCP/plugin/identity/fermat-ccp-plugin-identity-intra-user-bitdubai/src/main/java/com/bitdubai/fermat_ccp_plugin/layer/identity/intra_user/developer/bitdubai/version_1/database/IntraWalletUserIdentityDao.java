package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.IntraWalletUserIdentityPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraWalletUserIdentityPrivateKeyException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraWalletUserIdentityProfileImageException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraWalletUserIdentityDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantListIntraWalletUserIdentitiesException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateIntraUserIdentityException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The Class <code>IntraWalletUserIdentityDao</code>
 * has all methods related with database access.<p/>
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/08/15.
 * Modified by Natalia on 11/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraWalletUserIdentityDao implements DealsWithPluginDatabaseSystem {

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

    public IntraWalletUserIdentityDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    /**
     * This method open or creates the database i'll be working with     *

     * @throws CantInitializeIntraWalletUserIdentityDatabaseException
     */
    public void initializeDatabase() throws CantInitializeIntraWalletUserIdentityDatabaseException {
        try {
             /*
              * Open new database connection
              */

            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeIntraWalletUserIdentityDatabaseException(CantInitializeIntraWalletUserIdentityDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            IntraWalletUserIdentityDatabaseFactory databaseFactory = new IntraWalletUserIdentityDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = databaseFactory.createDatabase(this.pluginId, IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeIntraWalletUserIdentityDatabaseException(CantInitializeIntraWalletUserIdentityDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        } catch (Exception e) {

            throw new CantInitializeIntraWalletUserIdentityDatabaseException(CantInitializeIntraWalletUserIdentityDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Error.");

        }
    }

    /**
     * first i persist private key on a file
     * second i insert the record in database
     * third i save the profile image file
     *
     * @param alias
     * @param publicKey
     * @param privateKey
     * @param deviceUser
     * @param profileImage
     * @throws CantCreateNewDeveloperException
     */
    public void createNewUser (String alias, String phrase,String publicKey,String privateKey, DeviceUser deviceUser,byte[] profileImage, Long accuracy, Frequency frequency, Location location) throws CantCreateNewDeveloperException {

        try {
            if (aliasExists (alias)) {
                throw new CantCreateNewDeveloperException("Cant create new Intra User, alias exists.", "Intra User Identity", "Cant create new Intra User, alias exists.");
            }

            persistNewUserPrivateKeysFile(publicKey, privateKey);

            DatabaseTable table = this.database.getTable(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            double lat = 0;
            double lng = 0;

            if(location != null){
                lat = location.getLatitude();
                lng = location.getLongitude();
            }

            record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME, publicKey);
            record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_ALIAS_COLUMN_NAME, alias);
            record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_PHRASE_COLUMN_NAME, phrase);
            record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());
            record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_ACTIVE_COLUMN_NAME, "true");
            record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_ACCURACY_COLUMN, String.valueOf(accuracy));
            record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_FRECUENCY_COLUMN, frequency.getCode());
            record.setDoubleValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_LAT_COLUMN, lat);
            record.setDoubleValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_LONG_COLUMN, lng);

            table.insertRecord(record);

            if(profileImage!=null)
            persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantInsertRecordException e){
            // Cant insert record.
            throw new CantCreateNewDeveloperException (e.getMessage(), e, "Intra User Identity", "Cant create new Intra User, insert database problems.");

        } catch (CantPersistPrivateKeyException e){
            // Cant insert record.
            throw new CantCreateNewDeveloperException (e.getMessage(), e, "Intra User Identity", "Cant create new Intra User,persist private key error.");

        } catch (Exception e) {
            // Failure unknown.
            throw new CantCreateNewDeveloperException (e.getMessage(), FermatException.wrapException(e), "Intra User Identity", "Cant create new Intra User, unknown failure.");
        }
    }


    public void updateIdentity (String publicKey,String alias,String phrase, byte[] profileImage, Long accuracy, Frequency frequency, Location location) throws CantUpdateIntraUserIdentityException {

        try {

            /**
             * 1) Get the table.
             */
            DatabaseTable  table = this.database.getTable(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get intra user identity list, table not found.", "Intra User Identity", "Cant get Intra User identity list, table not found.");
            }


            // 2) Find the Intra users.
            table.addStringFilter(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords ()) {

                double lat = 0;
                double lng = 0;

                if(location != null){
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                }

                //set new values
                record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_ALIAS_COLUMN_NAME, alias);
                record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_PHRASE_COLUMN_NAME, phrase);
                record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_ACCURACY_COLUMN, String.valueOf(accuracy));
                record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_FRECUENCY_COLUMN, frequency.getCode());
                record.setDoubleValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_LAT_COLUMN, lat);
                record.setDoubleValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_LONG_COLUMN, lng);

                table.updateRecord(record);
            }

            if(profileImage!=null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantUpdateRecordException e){
            // Cant insert record.
            throw new CantUpdateIntraUserIdentityException(e.getMessage(), e, "Intra User Identity", "Cant update Intra User, database problems.");

        } catch (CantPersistProfileImageException e){
            // Cant insert record.
            throw new CantUpdateIntraUserIdentityException (e.getMessage(), e, "Intra User Identity", "Cant update Intra User,persist image error.");

        } catch (Exception e) {
            // Failure unknown.
            throw new CantUpdateIntraUserIdentityException (e.getMessage(), FermatException.wrapException(e), "Intra User Identity", "Cant update Intra User, unknown failure.");
        }
    }


    public void deleteIdentity (String publicKey) throws CantUpdateIntraUserIdentityException {

        try {

            /**
             * 1) Get the table.
             */
            DatabaseTable  table = this.database.getTable (IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException ("Cant get intra user identity list, table not found.", "Intra User Identity", "Cant get Intra User identity list, table not found.");
            }


            // 2) Find the Intra users.
            table.addStringFilter(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();


            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords ()) {

                //set new values
                record.setStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_ACTIVE_COLUMN_NAME, "false");


                table.updateRecord(record);
            }



        } catch (CantUpdateRecordException e){
            // Cant insert record.
            throw new CantUpdateIntraUserIdentityException(e.getMessage(), e, "Intra User Identity", "Cant update Intra User, database problems.");


        } catch (Exception e) {
            // Failure unknown.
            throw new CantUpdateIntraUserIdentityException (e.getMessage(), FermatException.wrapException(e), "Intra User Identity", "Cant update Intra User, unknown failure.");
        }
    }


    public ArrayList<IntraWalletUserIdentity> getAllIntraUserFromCurrentDeviceUser (DeviceUser deviceUser) throws CantListIntraWalletUserIdentitiesException {


        // Setup method.
        ArrayList<IntraWalletUserIdentity> list = new ArrayList<IntraWalletUserIdentity>(); // Intra User list.
        DatabaseTable table; // Intra User table.

        // Get Intra Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable (IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException ("Cant get intra user identity list, table not found.", "Intra User Identity", "Cant get Intra User identity list, table not found.");
            }


            // 2) Find the Intra users.
            table.addStringFilter(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.addStringFilter(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_ACTIVE_COLUMN_NAME, "true", DatabaseFilterType.EQUAL);

            table.loadToMemory();


            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords ()) {

                // Add records to list.
                list.add(buildIdentity(record));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListIntraWalletUserIdentitiesException(e.getMessage(), e, "Intra User Identity", "Cant load " + IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetIntraWalletUserIdentityPrivateKeyException e) {
            // Failure unknown.
            throw new CantListIntraWalletUserIdentitiesException(e.getMessage(), e, "Intra User Identity", "Can't get private key.");

        } catch (Exception e) {
            throw new CantListIntraWalletUserIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Intra User Identity", "Cant get Intra User identity list, unknown failure.");
        }

        // Return the list values.
        return list;
    }

    private com.bitdubai.fermat_ccp_api.layer.identity.intra_user.structure.IntraWalletUserIdentity buildIdentity(DatabaseTableRecord record) throws CantGetIntraWalletUserIdentityPrivateKeyException, CantGetIntraWalletUserIdentityProfileImageException {

        Frequency frequency = Frequency.NORMAL;

        try{
            frequency = Frequency.getByCode(record.getStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_FRECUENCY_COLUMN));
        }
        catch(InvalidParameterException e)
        {

        }

        Location location = new DeviceLocation(record.getDoubleValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_LAT_COLUMN),
                                                record.getDoubleValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_LONG_COLUMN),
                                                System.currentTimeMillis(),Double.parseDouble("0"),
                                                LocationSource.GPS);

        return new com.bitdubai.fermat_ccp_api.layer.identity.intra_user.structure.IntraWalletUserIdentity(record.getStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_ALIAS_COLUMN_NAME),
                record.getStringValue (IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_PHRASE_COLUMN_NAME),
                record.getStringValue (IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME),
                getIntraUserIdentityPrivateKey(record.getStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME)),
                getIntraUserProfileImagePrivateKey(record.getStringValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME)),
                record.getLongValue(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_ACCURACY_COLUMN), frequency,
                location);


    }



    public byte[] getIntraUserProfileImagePrivateKey(String publicKey) throws CantGetIntraWalletUserIdentityProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    IntraWalletUserIdentityPluginRoot.INTRA_WALLET_USERS_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );


            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);

        }
        catch (FileNotFoundException |CantCreateFileException e) {
            //Not image found return byte null
            profileImage = new byte[0];
           // throw new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        }
        catch (Exception e) {
            throw  new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ",FermatException.wrapException(e),"", "");
        }

        return profileImage;
    }


    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Private Methods
     */




    private void  persistNewUserPrivateKeysFile(String publicKey,String privateKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    IntraWalletUserIdentityPluginRoot.INTRA_WALLET_USERS_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(privateKey);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error persist file.", null);

        } catch (CantCreateFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error creating file.", null);
        }
        catch (Exception e) {
            throw  new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ",FermatException.wrapException(e),"", "");
        }
    }


    private void  persistNewUserProfileImage(String publicKey,byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    IntraWalletUserIdentityPluginRoot.INTRA_WALLET_USERS_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);

        } catch (CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        }
        catch (Exception e) {
            throw  new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ",FermatException.wrapException(e),"", "");
        }
    }


    public String getIntraUserIdentityPrivateKey(String publicKey) throws CantGetIntraWalletUserIdentityPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    IntraWalletUserIdentityPluginRoot.INTRA_WALLET_USERS_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );


            file.loadFromMedia();

            privateKey = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetIntraWalletUserIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);

        }
        catch (FileNotFoundException |CantCreateFileException e) {
            throw new CantGetIntraWalletUserIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        }
        catch (Exception e) {
            throw  new CantGetIntraWalletUserIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ",FermatException.wrapException(e),"", "");
        }

        return privateKey;
    }

    /**
     * <p>Method that check if alias exists.
     * @param alias
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean aliasExists (String alias) throws CantCreateNewDeveloperException {


        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = this.database.getTable (IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists", "Intra User Identity", "");
            }

            table.addStringFilter(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords ().size () > 0;


        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException (em.getMessage(), em, "Intra User Identity", "Cant load " + IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException (e.getMessage(), FermatException.wrapException(e), "Intra User Identity", "unknown failure.");
        }
    }

}
