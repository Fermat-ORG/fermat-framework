package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/03/16.
 */
public class TokenlySongWalletDatabaseConstants {

    public static final String DATABASE_NAME = "tokenly_song_wallet";
    /**
     * Song database table definition.
     */
    public static final String SONG_TABLE_NAME = "song";

    public static final String SONG_SONG_ID_COLUMN_NAME = "song_id";
    public static final String SONG_TOKENLY_ID_COLUMN_NAME = "tokenly_id";
    public static final String SONG_NAME_COLUMN_NAME = "name";
    public static final String SONG_TOKENS_COLUMN_NAME = "tokens";
    public static final String SONG_PERFORMERS_COLUMN_NAME = "performers";
    public static final String SONG_COMPOSERS_COLUMN_NAME = "composers";
    public static final String SONG_RELEASE_DATE_COLUMN_NAME = "release_date";
    public static final String SONG_LYRICS_COLUMN_NAME = "lyrics";
    public static final String SONG_CREDITS_COLUMN_NAME = "credits";
    public static final String SONG_COPYRIGHT_COLUMN_NAME = "copyright";
    public static final String SONG_OWNERSHIP_COLUMN_NAME = "ownership";
    public static final String SONG_USAGE_RIGHTS_COLUMN_NAME = "usage_rights";
    public static final String SONG_USAGE_PROHIBITIONS_COLUMN_NAME = "usage_prohibitions";
    public static final String SONG_DEVICE_PATH_COLUMN_NAME = "device_path";
    public static final String SONG_SONG_STATUS_COLUMN_NAME = "song_status";
    public static final String SONG_TOKENLY_USERNAME_COLUMN_NAME = "tokenly_username";

    public static final String SONG_FIRST_KEY_COLUMN = "song_id";

    /**
     * Synchronize table definition
     */
    public static final String SYNCHRONIZE_TABLE_NAME = "synchronize";

    public static final String SYNCHRONIZE_SYNC_ID_COLUMN_NAME = "sync_id";
    public static final String SYNCHRONIZE_DEVICE_SONGS_COLUMN_NAME = "device_songs";
    public static final String SYNCHRONIZE_TOKENLY_SONGS_COLUMN_NAME = "tokenly_songs";
    public static final String SYNCHRONIZE_TOKENLY_USERNAME_COLUMN_NAME = "username";
    public static final String SYNCHRONIZE_TIMESTAMP = "timestamp";

    public static final String SYNCHRONIZE_FIRST_KEY_COLUMN = "sync_id";

}
