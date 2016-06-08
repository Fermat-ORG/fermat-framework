package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/06/16.
 */
public class CountryDependencyRecord implements CountryDependency, Serializable {

    private final String name;
    private final String toponymName;
    private final String countryCode;
    private final int countryId;
    private final int dependencyId;
    private final GeoRectangle geoRectangle;

    /**
     * Default constructor
     * @param name
     * @param toponymName
     * @param countryCode
     * @param countryId
     * @param dependencyId
     */
    public CountryDependencyRecord(
            String name,
            String toponymName,
            String countryCode,
            int countryId,
            int dependencyId) {
        this.name = name;
        this.toponymName = toponymName;
        this.countryCode = countryCode;
        this.countryId = countryId;
        this.dependencyId = dependencyId;
        //Empty Geo-rectangle
        this.geoRectangle = new GeoRectangleRecord();
    }

    /**
     * Constructor with parameters.
     * This must include a GeoRectangle implementation
     * @param name
     * @param toponymName
     * @param countryCode
     * @param countryId
     * @param dependencyId
     * @param geoRectangle
     */
    public CountryDependencyRecord(
            String name,
            String toponymName,
            String countryCode,
            int countryId,
            int dependencyId,
            GeoRectangle geoRectangle) {
        this.name = name;
        this.toponymName = toponymName;
        this.countryCode = countryCode;
        this.countryId = countryId;
        this.dependencyId = dependencyId;
        this.geoRectangle = geoRectangle;
    }

    /**
     * Constructor with parameters
     * @param countryDependency
     * @param geoRectangle
     */
    public CountryDependencyRecord(
            CountryDependency countryDependency,
            GeoRectangle geoRectangle){
        this.name = countryDependency.getName();
        this.toponymName = countryDependency.getToponymName();
        this.countryCode = countryDependency.getCountryCode();
        this.countryId = countryDependency.getCountryId();
        this.dependencyId = countryDependency.getDependencyId();
        this.geoRectangle = geoRectangle;
    }

    /**
     * This method returns the dependency name.
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * This method returns the dependency Toponym name.
     * @return
     */
    @Override
    public String getToponymName() {
        return toponymName;
    }

    /**
     * This method returns the country code.
     * @return
     */
    @Override
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * This method return the country geonames Id.
     * @return
     */
    @Override
    public int getCountryId() {
        return countryId;
    }

    /**
     * This method return the dependency Id.
     * @return
     */
    @Override
    public int getDependencyId() {
        return dependencyId;
    }

    /**
     * This method returns the dependency Geo Rectangle.
     * @return
     */
    public GeoRectangle getGeoRectangle(){
        return geoRectangle;
    }

    @Override
    public String toString() {
        return "CountryDependencyRecord{" +
                "name='" + name + '\'' +
                ", toponymName='" + toponymName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryId=" + countryId +
                ", dependencyId=" + dependencyId +
                ", geoRectangle=" + geoRectangle +
                '}';
    }
}
