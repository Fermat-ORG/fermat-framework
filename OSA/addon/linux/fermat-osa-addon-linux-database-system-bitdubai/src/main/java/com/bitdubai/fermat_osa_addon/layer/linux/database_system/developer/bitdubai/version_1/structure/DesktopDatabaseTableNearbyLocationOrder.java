package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabaseTableNearbyLocationOrder</code>
 * Contains all the necessary attributes to generate the proper order function.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/06/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabaseTableNearbyLocationOrder {

    /**
     * Field representing the latitude in the table
     */
    private final String latitudeField;

    /**
     * field representing the longitude in the table
     */
    private final String longitudeField;

    /**
     * Point to get the near locations
     */
    private final Location point;

    /**
     * Direction of the order (DESC/ASC) by default is ASC
     */
    private final DatabaseFilterOrder direction;

    /**
     * Label for the field of the returning distance between the point and the record
     */
    private final String distanceField;

    public DesktopDatabaseTableNearbyLocationOrder(final String latitudeField,
                                                   final String longitudeField,
                                                   final Location point,
                                                   final DatabaseFilterOrder direction,
                                                   final String distanceField) {

        this.latitudeField = latitudeField;
        this.longitudeField = longitudeField;
        this.point = point;
        this.direction = direction;
        this.distanceField = distanceField;
    }

    public String getLatitudeField() {
        return latitudeField;
    }

    public String getLongitudeField() {
        return longitudeField;
    }

    public Location getPoint() {
        return point;
    }

    public DatabaseFilterOrder getDirection() {
        return direction;
    }

    public String getDistanceField() {
        return distanceField;
    }

    @Override
    public String toString() {
        return "DesktopDatabaseTableNearbyLocationOrder{" +
                "latitudeField='" + latitudeField + '\'' +
                ", longitudeField='" + longitudeField + '\'' +
                ", point=" + point +
                ", direction=" + direction +
                ", distanceField='" + distanceField + '\'' +
                '}';
    }
}
