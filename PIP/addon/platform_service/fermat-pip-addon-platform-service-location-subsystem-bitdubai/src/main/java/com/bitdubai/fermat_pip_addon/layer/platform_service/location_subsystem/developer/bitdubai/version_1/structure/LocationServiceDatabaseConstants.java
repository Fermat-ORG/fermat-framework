package com.bitdubai.fermat_pip_addon.layer.platform_service.location_subsystem.developer.bitdubai.version_1.structure;

/**
 * Created by firuzzz on 5/3/15.
 */
class LocationServiceDatabaseConstants {
    static final String LOCATION_SERVICE_TABLE_NAME = "Location";
    static final String LOCATION_SERVICE_TABLE_ID_COLUMN = "id";
    /**
     * possible values: GPS, NETWORK
     */
    static final String LOCATION_SERVICE_TABLE_PROVIDER_COLUMN = "provider";
    /**
     * The timestamp when the device got the location data
     */
    static final String LOCATION_SERVICE_TABLE_TIME_COLUMN = "time";
    static final String LOCATION_SERVICE_TABLE_LATITUDE_COLUMN = "latitude";
    static final String LOCATION_SERVICE_TABLE_LONGITUDE_COLUMN = "longitude";
    static final String LOCATION_SERVICE_TABLE_ALTITUDE_COLUMN = "altitude";
    static final String LOCATION_SERVICE_TABLE_ACCURACY_COLUMN = "accuracy";
}
