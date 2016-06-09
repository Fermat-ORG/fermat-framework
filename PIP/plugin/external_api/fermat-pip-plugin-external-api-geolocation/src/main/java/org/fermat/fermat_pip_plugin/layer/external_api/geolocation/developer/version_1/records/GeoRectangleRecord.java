package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateGeoRectangleException;

import java.io.Serializable;

/**
 * A GeoRectangle object contains a rectangle that evolves the location.
 * This rectangle can be drawn with 4 points: North, South, East and West.
 * Also, the GeoRectangle contains the latitude and longitude of the center of the rectangle.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/06/16.
 */
public class GeoRectangleRecord implements GeoRectangle, Serializable {

    private final float north;
    private final float south;
    private final float west;
    private final float east;
    private final float latitude;
    private final float longitude;

    private final int COORDINATES_ARRAY_SIZE = 4;

    /**
     * Default constructor.
     * For proper work the order in the coordinates array must be:
     * 0 - North
     * 1 - South
     * 2 - West
     * 3 - East
     * @param coordinates
     * @throws CantCreateGeoRectangleException
     */
    public GeoRectangleRecord(float[] coordinates) throws CantCreateGeoRectangleException {
        if(isArrayValid(coordinates)){
            north = coordinates[0];
            south = coordinates[1];
            west = coordinates[2];
            east = coordinates[3];
            latitude = 0.0f;
            longitude = 0.0f;
        } else {
            throw new CantCreateGeoRectangleException("The coordinates array is not valid");
        }
    }

    /**
     * Constructor with parameters
     * @param north
     * @param south
     * @param west
     * @param east
     */
    public GeoRectangleRecord(
            float north,
            float south,
            float west,
            float east,
            float latitude,
            float longitude){
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * This constructor is only to avoid null implementations.
     */
    public GeoRectangleRecord(){
        north = 0.0f;
        south = 0.0f;
        west = 0.0f;
        east = 0.0f;
        latitude = 0.0f;
        longitude = 0.0f;
    }

    /**
     * This method valids if the array coordinates are valid
     * @param coordinates
     * @return
     * @throws CantCreateGeoRectangleException
     */
    private boolean isArrayValid(float[] coordinates) throws CantCreateGeoRectangleException {
        if(coordinates==null){
            throw new CantCreateGeoRectangleException("The coordinates array is null");
        }
        int size = coordinates.length;
        if(size==COORDINATES_ARRAY_SIZE){
            return true;
        }
        if(size>COORDINATES_ARRAY_SIZE){
            System.out.println("Geo-rectangle record note: I got an array with "+size+" I only need " +
                    ""+COORDINATES_ARRAY_SIZE+" the elements after the maximum will be ignored");
            return true;
        }
        if(size<COORDINATES_ARRAY_SIZE){
            throw new CantCreateGeoRectangleException(
                    "The coordinate array has "+size+" elements, I need "+COORDINATES_ARRAY_SIZE);
        }
        return false;
    }

    /**
     * This method returns the North coordinate.
     * @return
     */
    @Override
    public float getNorth() {
        return north;
    }

    /**
     * This method returns the South Coordinate.
     * @return
     */
    @Override
    public float getSouth() {
        return south;
    }

    /**
     * This method returns the west coordinate.
     * @return
     */
    @Override
    public float getWest() {
        return west;
    }

    /**
     * This method returns the east coordinate.
     * @return
     */
    @Override
    public float getEast() {
        return east;
    }

    /**
     * This method returns the GeoRectangle latitude
     * @return
     */
    @Override
    public float getLatitude() {
        return latitude;
    }

    /**
     * This method returns the GeoRectangle longitude
     * @return
     */
    @Override
    public float getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "GeoRectangleRecord{" +
                "north=" + north +
                ", south=" + south +
                ", west=" + west +
                ", east=" + east +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
