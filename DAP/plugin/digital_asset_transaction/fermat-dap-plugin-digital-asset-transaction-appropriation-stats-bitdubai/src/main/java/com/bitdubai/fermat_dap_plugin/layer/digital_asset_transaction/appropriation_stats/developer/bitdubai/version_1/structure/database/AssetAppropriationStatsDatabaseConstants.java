package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.structure.database;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 18/11/15.
 */
public class AssetAppropriationStatsDatabaseConstants {

    //VARIABLE DECLARATION
    /*
     * AAS stands for Asset Appropriation Stats.
     */
    public static final String APPROPRIATION_STATS_DATABASE = "appropriation_stats_database";

    /*
     * Events recorded database table definition.
     */
    public static final String APPROPRIATION_STATS_EVENTS_RECORDED_TABLE_NAME = "aas_events_recorded";

    public static final String APPROPRIATION_STATS_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String APPROPRIATION_STATS_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String APPROPRIATION_STATS_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String APPROPRIATION_STATS_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String APPROPRIATION_STATS_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String APPROPRIATION_STATS_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = APPROPRIATION_STATS_EVENTS_RECORDED_ID_COLUMN_NAME;

    /*
     * Appropriated assets database table definition.
     */
    public static final String APPROPRIATION_STATS_APPROPRIATED_TABLE_NAME = "aas_appropriated";

    public static final String APPROPRIATION_STATS_APPROPRIATED_ID_COLUMN_NAME = "id";
    /**
     * Here I'd save the XML Representation for the asset that has been appropriated.
     */
    public static final String APPROPRIATION_STATS_APPROPRIATED_ASSET_COLUMN_NAME = "digital_asset";
    public static final String APPROPRIATION_STATS_APPROPRIATED_USER_COLUMN_NAME = "actor_user";
    public static final String APPROPRIATION_STATS_APPROPRIATED_TIME_COLUMN_NAME = "time_appropriation";

    //CONSTRUCTORS

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
