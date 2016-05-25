package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetWalletSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CanGetTokensArrayFromSongWalletException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantGetLastUpdateDateException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantGetSongNameException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantGetSongTokenlyIdException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongDevicePathException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantGetStoragePathException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantPersistSongException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantPersistSynchronizeDateException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.structure.records.WalletSongRecord;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/03/16.
 */
public class TokenlySongWalletDao {

    /**
     * Represents the plugin database.
     */
    private Database database;
    /**
     * Represents the plugin database system.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    /**
     * Represents the plugin Id
     */
    private UUID pluginId;
    /**
     * Represents the Error Manager.
     */
    private ErrorManager errorManager;

    /**
     * This variables are used as a default values to build a WalletSongRecord object.
     * This values don't exists in database.
     */
    private final String DEFAULT_BITCOIN_ADDRESS="N/A";
    private final String DEFAULT_OTHERS_FIELD = "N/A";
    public final long DEFAULT_TIME_STAMP = 0;

    /**
     * Constructor
     */
    public TokenlySongWalletDao(PluginDatabaseSystem pluginDatabaseSystem,
                                     UUID pluginId,
                                     Database database,
                                     ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId;
        this.database             = database;
        this.errorManager         = errorManager;
    }

    private Database openDatabase() throws
            CantOpenDatabaseException,
            CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    TokenlySongWalletDatabaseConstants.DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            TokenlySongWalletDatabaseFactory tokenlySongWalletDatabaseFactory =
                    new TokenlySongWalletDatabaseFactory(pluginDatabaseSystem);
            database = tokenlySongWalletDatabaseFactory.createDatabase(
                    this.pluginId,
                    TokenlySongWalletDatabaseConstants.DATABASE_NAME);
        }
        return database;
    }

    /**
     * This method returns a Database Table by table name.
     * The table must be included in TokenlySongWalletDatabaseConstants.
     * @param tableName
     * @return
     */
    private DatabaseTable getDatabaseTable(String tableName) {
        return database.getTable(tableName);
    }

    /**
     * This method checks if the result set from database is correct.
     * In this version, checks if the result set returns 1 record.
     * @param records
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws
            UnexpectedResultReturnedFromDatabaseException {
        /**
         * Represents the maximum number of records in <code>records</code>
         * I'm gonna set this number in 1 for now, because I want to check the records object has
         * one only result.
         */
        int VALID_RESULTS_NUMBER=1;
        int recordsSize;
        if(records.isEmpty()){
            return;
        }
        recordsSize=records.size();
        if(recordsSize>VALID_RESULTS_NUMBER){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    "I excepted "+VALID_RESULTS_NUMBER+", but I got "+recordsSize);
        }
    }

    /**
     * This method retuns a WalletSong from a proper DatabaseTableRecord.
     * @param databaseTableRecord
     * @return
     * @throws CantGetWalletSongException
     */
    private WalletSong buildWalletSong(DatabaseTableRecord databaseTableRecord) throws
            CantGetWalletSongException {
        try{
            /**
             * WALLET SONG FIELDS
             */
            //Song status
            SongStatus songStatus;
            String songStatusString = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_SONG_STATUS_COLUMN_NAME);
            if(songStatusString==null||songStatusString.isEmpty()){
                throw new CantGetWalletSongException("The song status is empty");
            } else{
                songStatus = SongStatus.getByCode(songStatusString);
            }
            //Wallet song id
            UUID songId = databaseTableRecord.getUUIDValue(
                    TokenlySongWalletDatabaseConstants.SONG_SONG_ID_COLUMN_NAME);
            /**
             * TOKENLY SONG FIELDS
             */
            //Tokenly song id
            String id = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_TOKENLY_ID_COLUMN_NAME);
            //Song name
            String name = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_NAME_COLUMN_NAME);
            //Tokens (I'll pass a String, but, the WalletSong converts it in array).
            String tokens =  databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_TOKENS_COLUMN_NAME);
            //Performers
            String performers =  databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_PERFORMERS_COLUMN_NAME);
            //Composers
            String composers = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_COMPOSERS_COLUMN_NAME);
            //Release date
            String stringDate = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_RELEASE_DATE_COLUMN_NAME);
            Date releaseDate = getDateFromString(stringDate);
            //Lyrics.
            String lyrics = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_LYRICS_COLUMN_NAME);
            //Credits
            String credits = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_CREDITS_COLUMN_NAME);
            //Copyright
            String copyright = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_COPYRIGHT_COLUMN_NAME);
            //Ownership
            String ownership = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_OWNERSHIP_COLUMN_NAME);
            //Usage Rights
            String usageRights = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_USAGE_RIGHTS_COLUMN_NAME);
            //USage prohibitions
            String usageProhibitions = databaseTableRecord.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_USAGE_PROHIBITIONS_COLUMN_NAME);
            //Build record
            WalletSong walletSong = new WalletSongRecord(
                    songStatus,
                    songId,
                    id,
                    name,
                    tokens,
                    performers,
                    composers,
                    releaseDate,
                    lyrics,
                    credits,
                    copyright,
                    ownership,
                    usageRights,
                    usageProhibitions,
                    DEFAULT_BITCOIN_ADDRESS,
                    DEFAULT_OTHERS_FIELD);
            return walletSong;
        } catch (InvalidParameterException e) {
            throw new CantGetWalletSongException(
                    e,
                    "Building Wallet Song from Database",
                    "There's some wrong enum code");
        } catch (CanGetTokensArrayFromSongWalletException e) {
            throw new CantGetWalletSongException(
                    e,
                    "Building Wallet Song from Database",
                    "Cannot set Tokens Array");
        }

    }

    private DatabaseTableRecord getDatabaseTableRecordFromWalletSong(
            DatabaseTableRecord databaseTableRecord,
            WalletSong walletSong){
        /**
         * WALLET SONG FIELDS
         */
        //SongStatus
        SongStatus songStatus = walletSong.getSongStatus();
        if(songStatus==null){
            songStatus = SongStatus.NOT_AVAILABLE;
        }
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_SONG_ID_COLUMN_NAME,
                songStatus.getCode());
        //songId
        databaseTableRecord.setUUIDValue(
                TokenlySongWalletDatabaseConstants.SONG_SONG_ID_COLUMN_NAME,
                walletSong.getSongId());
        /**
         * TOKENLY SONG FIELDS
         */
        //id
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_TOKENLY_ID_COLUMN_NAME,
                walletSong.getId());
        //name
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_NAME_COLUMN_NAME,
                walletSong.getName().replace("'","\'"));
        //tokens
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_TOKENS_COLUMN_NAME,
                walletSong.getTokensXML());
        //performers
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_PERFORMERS_COLUMN_NAME,
                walletSong.getPerformers().replace("'", "\'"));
        //composers
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_COMPOSERS_COLUMN_NAME,
                walletSong.getComposers().replace("'", "\'"));
        //release Date
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_RELEASE_DATE_COLUMN_NAME,
                walletSong.getReleaseDate().toString());
        //Lyrics
        /**
         * TODO: I don't want to persists the lyrics in this version
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_LYRICS_COLUMN_NAME,
                walletSong.getLyrics().replace("'", "\'").replace("\n", "\\n"));*/
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_LYRICS_COLUMN_NAME,
                "");
        //credits
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_TOKENS_COLUMN_NAME,
                walletSong.getCredits().replace("'", "\'"));
        //copyright
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_COPYRIGHT_COLUMN_NAME,
                walletSong.getCopyright().replace("'", "\'"));
        //ownership
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_OWNERSHIP_COLUMN_NAME,
                walletSong.getOwnership().replace("'", "\'"));
        //usageRights
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_USAGE_RIGHTS_COLUMN_NAME,
                walletSong.getUsageRights().replace("'", "\'"));
        //usageProhibitions
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_USAGE_PROHIBITIONS_COLUMN_NAME,
                walletSong.getUsageProhibitions().replace("'","\'"));
        return databaseTableRecord;
    }

    /**
     * This method returns a songs list by SongStatus enum
     * @param songStatus
     * @return
     * @throws CantGetSongListException
     */
    public List<WalletSong> getSongsBySongStatus(SongStatus songStatus)
            throws CantGetSongListException {
        try{
            openDatabase();
            List<WalletSong> songList = new ArrayList<>();
            WalletSong walletSong;
            DatabaseTable databaseTable = getDatabaseTable(
                    TokenlySongWalletDatabaseConstants.SONG_TABLE_NAME);
            databaseTable.addStringFilter(
                    TokenlySongWalletDatabaseConstants.SONG_SONG_STATUS_COLUMN_NAME,
                    songStatus.getCode(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //I'll return an empty list
                return songList;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                walletSong = buildWalletSong(databaseTableRecord);
                songList.add(walletSong);
            }
            return songList;
        } catch (CantCreateDatabaseException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot create database");
        } catch (CantOpenDatabaseException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot open database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot load database table");
        } catch (CantGetWalletSongException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot get song from database");
        }
    }

    /**
     * This method returns a songs list by SongStatus enum
     * @return
     * @throws CantGetSongListException
     */
    public List<String> getSongsTokenlyIdDeleted()
            throws CantGetSongListException {
        try{
            openDatabase();
            List<String> songList = new ArrayList<>();
            WalletSong walletSong;
            DatabaseTable databaseTable = getDatabaseTable(
                    TokenlySongWalletDatabaseConstants.SONG_TABLE_NAME);
            databaseTable.addStringFilter(
                    TokenlySongWalletDatabaseConstants.SONG_SONG_STATUS_COLUMN_NAME,
                    SongStatus.DELETED.getCode(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //I'll return an empty list
                return songList;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                walletSong = buildWalletSong(databaseTableRecord);
                songList.add(walletSong.getId());
            }
            return songList;
        } catch (CantCreateDatabaseException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot create database");
        } catch (CantOpenDatabaseException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot open database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot load database table");
        } catch (CantGetWalletSongException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot get song from database");
        }
    }

    /**
     * This method returns a AVAILABLE song list
     * @return
     * @throws CantGetSongListException
     */
    public List<String> getAvailableSongsTokenlyId()
            throws CantGetSongListException {
        try{
            openDatabase();
            List<String> songList = new ArrayList<>();
            WalletSong walletSong;
            DatabaseTable databaseTable = getDatabaseTable(
                    TokenlySongWalletDatabaseConstants.SONG_TABLE_NAME);
            databaseTable.addStringFilter(
                    TokenlySongWalletDatabaseConstants.SONG_SONG_STATUS_COLUMN_NAME,
                    SongStatus.AVAILABLE.getCode(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //I'll return an empty list
                return songList;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                walletSong = buildWalletSong(databaseTableRecord);
                songList.add(walletSong.getId());
            }
            return songList;
        } catch (CantCreateDatabaseException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot create database");
        } catch (CantOpenDatabaseException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot open database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot load database table");
        } catch (CantGetWalletSongException e) {
            throw new CantGetSongListException(
                    e,
                    "Building Wallet Song List by Status from Database",
                    "Cannot get song from database");
        }
    }

    /**
     * This method persist a Song in plugin database.
     * @param song
     * @param songPath
     * @param username
     * @param songStatus
     * @param songId
     * @throws CantPersistSongException
     */
    public void saveSong(
            Song song,
            String songPath,
            String username,
            SongStatus songStatus,
            UUID songId) throws
            CantPersistSongException {
        try{
            openDatabase();
            //Get WalletSong object from given song.
            WalletSong walletSong = new WalletSongRecord(
                    song,
                    SongStatus.AVAILABLE,
                    songId);
            //Build record
            DatabaseTable databaseTable = getDatabaseTable(
                    TokenlySongWalletDatabaseConstants.SONG_TABLE_NAME);
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = getDatabaseTableRecordFromWalletSong(
                    databaseTableRecord,
                    walletSong);
            //Set username
            databaseTableRecord.setStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_TOKENLY_USERNAME_COLUMN_NAME, username);
            //Set SongStatus
            databaseTableRecord.setStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_SONG_STATUS_COLUMN_NAME,
                    songStatus.getCode());
            //Set song path
            databaseTableRecord.setStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_DEVICE_PATH_COLUMN_NAME,
                    songPath);
            //Insert record in database.
            databaseTable.insertRecord(databaseTableRecord);
        } catch (CantCreateDatabaseException e) {
            throw new CantPersistSongException(
                    e,
                    "Persisting song in database",
                    "Cannot create database");
        } catch (CantOpenDatabaseException e) {
            throw new CantPersistSongException(
                    e,
                    "Persisting song in database",
                    "Cannot open database");
        } catch (CantInsertRecordException e) {
            throw new CantPersistSongException(
                    e,
                    "Persisting song in database",
                    "Cannot insert record database");
        }

    }


    /**
     * This method returns a SongStatus by songId.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @return
     * @throws CantGetSongStatusException
     */
    public SongStatus getSongStatus(UUID songId) throws CantGetSongStatusException {
        try{
            WalletSong walletSong = getWalletSongArgumentBySongId(songId);
            return walletSong.getSongStatus();
        } catch (CantGetWalletSongException e) {
            throw new CantGetSongStatusException(
                    e,
                    "Getting Song Status from Database",
                    "Cannot get song from database");
        }
    }

    /**
     * This method returns a Song name by songId.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @return
     * @throws CantGetSongStatusException
     */
    public String getSongName(UUID songId) throws CantGetSongNameException {
        try{
            WalletSong walletSong = getWalletSongArgumentBySongId(songId);
            return walletSong.getName();
        } catch (CantGetWalletSongException e) {
            throw new CantGetSongNameException(
                    e,
                    "Getting Song name from Database",
                    "Cannot get song from database");
        }
    }

    /**
     * This method returns a WalletSong by SongId
     * @param songId
     * @return
     * @throws CantOpenDatabaseException
     * @throws CantCreateDatabaseException
     * @throws CantLoadTableToMemoryException
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetWalletSongException
     */
    public WalletSong getWalletSongArgumentBySongId(UUID songId)
            throws
            CantGetWalletSongException {
        try{
            openDatabase();
            WalletSong walletSong;
            DatabaseTable databaseTable = getDatabaseTable(
                    TokenlySongWalletDatabaseConstants.SONG_TABLE_NAME);
            databaseTable.addStringFilter(
                    TokenlySongWalletDatabaseConstants.SONG_SONG_ID_COLUMN_NAME,
                    songId.toString(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            if(records.isEmpty()){
                //I'll return null
                return null;
            }
            walletSong = buildWalletSong(records.get(0));
            return walletSong;
        } catch (CantCreateDatabaseException e) {
            throw new CantGetWalletSongException(
                    e,
                    "Getting Song from Database",
                    "Cannot create database");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CantGetWalletSongException(
                    e,
                    "Getting Song from Database",
                    "Unexpected error from database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetWalletSongException(
                    e,
                    "Getting Song from Database",
                    "Cannot load table from database");
        } catch (CantOpenDatabaseException e) {
            throw new CantGetWalletSongException(
                    e,
                    "Getting Song from Database",
                    "Cannot open the database");
        }
    }

    /**
     * This method returns a SongStatus by songId.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @return
     * @throws CantGetSongStatusException
     */
    public String getSongTokenlyId(UUID songId) throws CantGetSongTokenlyIdException {
        try{
            WalletSong walletSong = getWalletSongArgumentBySongId(songId);
            //This Id represents the tokenly Id.
            return walletSong.getId();
        } catch (CantGetWalletSongException e) {
            throw new CantGetSongTokenlyIdException(
                    e,
                    "Getting Tokenly Id from Database",
                    "Cannot get song from database");
        }
    }

    /**
     * This method returns a Song Storage path by songId.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @return
     * @throws CantGetSongStatusException
     */
    public String getSongStoragePath(UUID songId) throws CantGetStoragePathException {
        try{
            openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(
                    TokenlySongWalletDatabaseConstants.SONG_TABLE_NAME);
            databaseTable.addStringFilter(
                    TokenlySongWalletDatabaseConstants.SONG_SONG_ID_COLUMN_NAME,
                    songId.toString(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            if(records.isEmpty()){
                //I'll return null
                return null;
            }
            DatabaseTableRecord record = records.get(0);
            String storagePath = record.getStringValue(
                    TokenlySongWalletDatabaseConstants.SONG_DEVICE_PATH_COLUMN_NAME);
            return storagePath;
        } catch (CantCreateDatabaseException e) {
            throw new CantGetStoragePathException(
                    e,
                    "Getting Storage path from Database",
                    "Cannot create database");
        } catch (CantOpenDatabaseException e) {
            throw new CantGetStoragePathException(
                    e,
                    "GettingStorage path from Database",
                    "Cannot open database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetStoragePathException(
                    e,
                    "Getting Storage path from Database",
                    "Cannot load database table");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CantGetStoragePathException(
                    e,
                    "Getting Storage path from Database",
                    "Unexpected results from database");
        }
    }

    /**
     * This method updates a record by given arguments and keys.
     * @param songId
     * @param columnName
     * @param value
     * @throws CantOpenDatabaseException
     * @throws CantCreateDatabaseException
     * @throws CantLoadTableToMemoryException
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    private void updateStringValue(
            UUID songId,
            String columnName,
            String value) throws
            CantOpenDatabaseException,
            CantCreateDatabaseException,
            CantLoadTableToMemoryException,
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        openDatabase();
        DatabaseTable databaseTable = getDatabaseTable(
                TokenlySongWalletDatabaseConstants.SONG_TABLE_NAME);
        databaseTable.addStringFilter(
                TokenlySongWalletDatabaseConstants.SONG_SONG_ID_COLUMN_NAME,
                songId.toString(),
                DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();
        List<DatabaseTableRecord> records = databaseTable.getRecords();
        checkDatabaseRecords(records);
        DatabaseTableRecord record = records.get(0);
        record.setStringValue(
                columnName,
                value);
        databaseTable.updateRecord(record);
    }

    /**
     * This method updates the song record in database with the storage path
     * @param songId
     * @param songStoragePath
     * @throws CantUpdateSongDevicePathException
     */
    public void updateSongStoragePath(
            UUID songId,
            String songStoragePath)
            throws CantUpdateSongDevicePathException {
        try{
            updateStringValue(
                    songId,
                    TokenlySongWalletDatabaseConstants.SONG_DEVICE_PATH_COLUMN_NAME,
                    songStoragePath);
        } catch (CantCreateDatabaseException e) {
            throw new CantUpdateSongDevicePathException(
                    e,
                    "Updating storage path in database",
                    "Cannot create database");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CantUpdateSongDevicePathException(
                    e,
                    "Updating storage path in database",
                    "Unexpected results from database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateSongDevicePathException(
                    e,
                    "Updating storage path in database",
                    "Cannot load database table");
        } catch (CantOpenDatabaseException e) {
            throw new CantUpdateSongDevicePathException(
                    e,
                    "Updating storage path in database",
                    "Cannot open database");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateSongDevicePathException(
                    e,
                    "Updating storage path in database",
                    "Cannot update record");
        }
    }

    /**
     * This method persist the result of a synchronize process.
     * @param username
     * @param deviceSongs
     * @param tokenlySongs
     * @throws CantPersistSynchronizeDateException
     */
    public void registerSynchronizeProcess(
            String username,
            int deviceSongs,
            int tokenlySongs) throws
            CantPersistSynchronizeDateException {
        try{
            openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(
                    TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TABLE_NAME);
            databaseTable.addStringFilter(
                    TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TOKENLY_USERNAME_COLUMN_NAME,
                    username,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            //Get the actual timestamp
            long timestamp = System.currentTimeMillis();
            if(records.isEmpty()){
                //There's no synchronize process registered in database for this username.
                databaseTableRecord = databaseTable.getEmptyRecord();
                //Assign Sync Id
                UUID syncId = UUID.randomUUID();
                databaseTableRecord.setUUIDValue(
                        TokenlySongWalletDatabaseConstants.SYNCHRONIZE_SYNC_ID_COLUMN_NAME,
                        syncId);
                //Device songs
                databaseTableRecord.setIntegerValue(
                        TokenlySongWalletDatabaseConstants.SYNCHRONIZE_DEVICE_SONGS_COLUMN_NAME,
                        deviceSongs);
                //Tokenly songs
                databaseTableRecord.setIntegerValue(
                        TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TOKENLY_SONGS_COLUMN_NAME,
                        tokenlySongs);
                //Timestamp
                databaseTableRecord.setLongValue(
                        TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TIMESTAMP,
                        timestamp);
                //Username
                databaseTableRecord.setStringValue(
                        TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TOKENLY_USERNAME_COLUMN_NAME,
                        username);
                databaseTable.insertRecord(databaseTableRecord);
            } else{
                //First I'll check if there's a wrong result in result set.
                checkDatabaseRecords(records);
                databaseTableRecord = records.get(0);
                //Device songs
                databaseTableRecord.setIntegerValue(
                        TokenlySongWalletDatabaseConstants.SYNCHRONIZE_DEVICE_SONGS_COLUMN_NAME,
                        deviceSongs);
                //Tokenly songs
                databaseTableRecord.setIntegerValue(
                        TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TOKENLY_SONGS_COLUMN_NAME,
                        tokenlySongs);
                //Timestamp
                databaseTableRecord.setLongValue(
                        TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TIMESTAMP,
                        timestamp);
                //Update table
                databaseTable.updateRecord(databaseTableRecord);
            }
        } catch (CantCreateDatabaseException e) {
            throw new CantPersistSynchronizeDateException(
                    e,
                    "Persisting the Synchronize process",
                    "Cannot create database");
        } catch (CantOpenDatabaseException e) {
            throw new CantPersistSynchronizeDateException(
                    e,
                    "Persisting the Synchronize process",
                    "Cannot open the database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantPersistSynchronizeDateException(
                    e,
                    "Persisting the Synchronize process",
                    "Cannot open the database");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CantPersistSynchronizeDateException(
                    e,
                    "Persisting the Synchronize process",
                    "Unexpected result in database result set");
        } catch (CantUpdateRecordException e) {
            throw new CantPersistSynchronizeDateException(
                    e,
                    "Persisting the Synchronize process",
                    "Cannot update the database");
        } catch (CantInsertRecordException e) {
            throw new CantPersistSynchronizeDateException(
                    e,
                    "Persisting the Synchronize process",
                    "Cannot insert a new record in the database");
        }
    }

    /**
     * This method returns the last sync update from database from a given tokenly username.
     * @param username
     * @return
     * @throws CantGetLastUpdateDateException
     */
    public long getLastUpdateDate(String username) throws CantGetLastUpdateDateException {
        try{
            openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(
                    TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TABLE_NAME);
            databaseTable.addStringFilter(
                    TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TOKENLY_USERNAME_COLUMN_NAME,
                    username,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            if(records.isEmpty()){
                return DEFAULT_TIME_STAMP;
            }
            DatabaseTableRecord record = records.get(0);
            long timestamp = record.getLongValue(
                    TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TIMESTAMP);
            return timestamp;
        } catch (CantCreateDatabaseException e) {
            throw new CantGetLastUpdateDateException(
                    e,
                    "Getting sync timestamp from database for user "+username,
                    "Cannot create database");
        } catch (CantOpenDatabaseException e) {
            throw new CantGetLastUpdateDateException(
                    e,
                    "Getting sync timestamp from database for user "+username,
                    "Cannot open database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetLastUpdateDateException(
                    e,
                    "Getting sync timestamp from database for user "+username,
                    "Cannot load table");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CantGetLastUpdateDateException(
                    e,
                    "Getting sync timestamp from database for user "+username,
                    "Unexpected result from database");
        }
    }

    /**
     * This method updates the song record in database with the storage path
     * @param songId
     * @param songStatus
     * @throws CantUpdateSongDevicePathException
     */
    public void updateSongStatus(
            UUID songId,
            SongStatus songStatus)
            throws CantUpdateSongStatusException {
        try{
            updateStringValue(
                    songId,
                    TokenlySongWalletDatabaseConstants.SONG_SONG_STATUS_COLUMN_NAME,
                    songStatus.getCode());
        } catch (CantCreateDatabaseException e) {
            throw new CantUpdateSongStatusException(
                    e,
                    "Updating SongStatus in database",
                    "Cannot create database");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CantUpdateSongStatusException(
                    e,
                    "Updating SongStatus in database",
                    "Unexpected results from database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateSongStatusException(
                    e,
                    "Updating SongStatus in database",
                    "Cannot load database table");
        } catch (CantOpenDatabaseException e) {
            throw new CantUpdateSongStatusException(
                    e,
                    "Updating SongStatus in database",
                    "Cannot open database");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateSongStatusException(
                    e,
                    "Updating SongStatus in database",
                    "Cannot update record");
        }
    }

    /**
     * This method returns a Date object from a String
     * @param stringDate
     * @return
     */
    private Date getDateFromString(String stringDate){
        if(stringDate==null||stringDate.isEmpty()){
            return new Date(1961);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Date dateFromString;
        try {
            java.util.Date utilDate = simpleDateFormat.parse(stringDate);
            dateFromString = new Date(utilDate.getTime());
        } catch (ParseException e) {
            //Default date
            return new Date(2016);
        }
        return dateFromString;
    }

}
