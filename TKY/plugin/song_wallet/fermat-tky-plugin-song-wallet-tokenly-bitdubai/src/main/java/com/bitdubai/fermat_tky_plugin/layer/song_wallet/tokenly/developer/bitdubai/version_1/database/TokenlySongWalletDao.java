package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetWalletSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CanGetTokensArrayFromSongWalletException;
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

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
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
                    TokenlySongWalletDatabaseConstants.SONG_SONG_ID_COLUMN_NAME);
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

    private DatabaseTableRecord getDatabaseTablerecordFromWalletSong(
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
                walletSong.getName());
        //tokens
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_TOKENS_COLUMN_NAME,
                walletSong.getTokensXML());
        //performers
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_PERFORMERS_COLUMN_NAME,
                walletSong.getPerformers());
        //composers
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_COMPOSERS_COLUMN_NAME,
                walletSong.getComposers());
        //release Date
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_RELEASE_DATE_COLUMN_NAME,
                walletSong.getReleaseDate().toString());
        //Lyrics
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_LYRICS_COLUMN_NAME,
                walletSong.getLyrics());
        //credits
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_TOKENS_COLUMN_NAME,
                walletSong.getLyrics());
        //copyright
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_COPYRIGHT_COLUMN_NAME,
                walletSong.getCopyright());
        //ownership
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_OWNERSHIP_COLUMN_NAME,
                walletSong.getOwnership());
        //usageRights
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_USAGE_RIGHTS_COLUMN_NAME,
                walletSong.getUsageRights());
        //usageProhibitions
        databaseTableRecord.setStringValue(
                TokenlySongWalletDatabaseConstants.SONG_USAGE_PROHIBITIONS_COLUMN_NAME,
                walletSong.getUsageRights());
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
     * This method returns a SongStatus by songId.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @return
     * @throws CantGetSongStatusException
     */
    public SongStatus getSongStatus(UUID songId) throws CantGetSongStatusException {
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
            return walletSong.getSongStatus();
        } catch (CantCreateDatabaseException e) {
            throw new CantGetSongStatusException(
                    e,
                    "Getting Song Status from Database",
                    "Cannot create database");
        } catch (CantOpenDatabaseException e) {
            throw new CantGetSongStatusException(
                    e,
                    "Getting Song Status from Database",
                    "Cannot open database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetSongStatusException(
                    e,
                    "Getting Song Status from Database",
                    "Cannot load database table");
        } catch (CantGetWalletSongException e) {
            throw new CantGetSongStatusException(
                    e,
                    "Getting Song Status from Database",
                    "Cannot get song from database");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CantGetSongStatusException(
                    e,
                    "Getting Song Status from Database",
                    "Cannot get song from database");
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
        Date dateFromString = new Date(2014);
        try {
            java.util.Date utilDate = simpleDateFormat.parse(stringDate);
            utilDate = new Date(utilDate.getTime());
        } catch (ParseException e) {
            //Default date
            return new Date(2016);
        }
        return dateFromString;
    }

}
