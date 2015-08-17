/*
 * @#WalletPublisherMiddlewareDatabaseConstants.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletPublisherMiddlewareDatabaseConstants {

    /**
     * Represent the name of the data base
     */
    public static final String DATA_BASE_NAME  = "component_publisher_data_base";

    /**
     * INFORMATION PUBLISHED COMPONENTS database table definition.
     */
    static final String INFORMATION_PUBLISHED_COMPONENTS_TABLE_NAME = "information_published_components";

    static final String INFORMATION_PUBLISHED_COMPONENTS_ID_COLUMN_NAME = "id";
    static final String INFORMATION_PUBLISHED_COMPONENTS_DFP_ID_COLUMN_NAME = "dfp_id";
    static final String INFORMATION_PUBLISHED_COMPONENTS_DFP_NAME_COLUMN_NAME = "dfp_name";
    static final String INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME = "component_type";
    static final String INFORMATION_PUBLISHED_COMPONENTS_DESCRIPTIONS_COLUMN_NAME = "descriptions";
    static final String INFORMATION_PUBLISHED_COMPONENTS_ICON_IMG_COLUMN_NAME = "icon_img";
    static final String INFORMATION_PUBLISHED_COMPONENTS_MAIN_SCREEN_SHOT_IMG_COLUMN_NAME = "main_screen_shot_img";
    static final String INFORMATION_PUBLISHED_COMPONENTS_VIDEO_URL_COLUMN_NAME = "video_url";
    static final String INFORMATION_PUBLISHED_COMPONENTS_STATUS_COLUMN_NAME = "status";
    static final String INFORMATION_PUBLISHED_COMPONENTS_STATUS_TIMESTAMP_COLUMN_NAME = "status_timestamp";
    static final String INFORMATION_PUBLISHED_COMPONENTS_PUBLICATION_TIMESTAMP_COLUMN_NAME = "publication_timestamp";
    static final String INFORMATION_PUBLISHED_COMPONENTS_PUBLISHER_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "publisher_identity_public_key";
    static final String INFORMATION_PUBLISHED_COMPONENTS_SIGNATURE_COLUMN_NAME = "signature";

    static final String INFORMATION_PUBLISHED_COMPONENTS_FIRST_KEY_COLUMN = "id";

    /**
     * COMPONENT VERSIONS DETAILS database table definition.
     */
    static final String COMPONENT_VERSIONS_DETAILS_TABLE_NAME = "component_versions_details";

    static final String COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME = "id";
    static final String COMPONENT_VERSIONS_DETAILS_SCREEN_SIZE_COLUMN_NAME = "screen_size";
    static final String COMPONENT_VERSIONS_DETAILS_VERSION_COLUMN_NAME = "version";
    static final String COMPONENT_VERSIONS_DETAILS_VERSION_TIMESTAMP_COLUMN_NAME = "version_timestamp";
    static final String COMPONENT_VERSIONS_DETAILS_INITIAL_WALLET_VERSION_COLUMN_NAME = "initial_wallet_version";
    static final String COMPONENT_VERSIONS_DETAILS_FINAL_WALLET_VERSION_COLUMN_NAME = "final_wallet_version";
    static final String COMPONENT_VERSIONS_DETAILS_INITIAL_PLATFORM_VERSION_COLUMN_NAME = "initial_platform_version";
    static final String COMPONENT_VERSIONS_DETAILS_FINAL_PLATFORM_VERSION_COLUMN_NAME = "final_platform_version";
    static final String COMPONENT_VERSIONS_DETAILS_OBSERVATIONS_COLUMN_NAME = "observations";
    static final String COMPONENT_VERSIONS_DETAILS_CATALOG_ID_COLUMN_NAME = "catalog_id";
    static final String COMPONENT_VERSIONS_DETAILS_COMPONENT_ID_COLUMN_NAME = "component_id";

    static final String COMPONENT_VERSIONS_DETAILS_FIRST_KEY_COLUMN = "id";

    /**
     * SCREENS SHOTS COMPONENTS database table definition.
     */
    static final String SCREENS_SHOTS_COMPONENTS_TABLE_NAME = "screens_shots_components";

    static final String SCREENS_SHOTS_COMPONENTS_FILE_ID_COLUMN_NAME = "file_id";
    static final String SCREENS_SHOTS_COMPONENTS_COMPONENT_ID_COLUMN_NAME = "component_id";

    static final String SCREENS_SHOTS_COMPONENTS_FIRST_KEY_COLUMN = "file_id";

}