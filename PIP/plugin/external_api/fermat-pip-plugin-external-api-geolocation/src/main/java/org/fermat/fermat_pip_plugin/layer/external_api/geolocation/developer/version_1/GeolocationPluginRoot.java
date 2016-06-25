package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.structure.GeolocationPluginManager;

import java.util.List;

@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM, maintainerMail = "darkpriestrelative@gmail.com", createdBy = "darkestpriest", layer = Layers.EXTERNAL_API, platform = Platforms.PLUG_INS_PLATFORM, plugin = Plugins.GEOLOCATION)
public class GeolocationPluginRoot extends AbstractPlugin {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    /**
     * Represents the plugin manager.
     */
    GeolocationPluginManager geolocationPluginManager;

    /**
     * Constructor with parameters.
     */
    public GeolocationPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * This method returns the plugin manager.
     * @return
     */
    public FermatManager getManager() {
        return this.geolocationPluginManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.geolocationPluginManager = new GeolocationPluginManager(
                    this,
                    pluginFileSystem,
                    this.pluginId);
            //Test Methods
            //testListCountries();
            //testListDependencies();
            //testCitiesByCountry();
            //testCitiesByDependency();
            //testGetGeoRectangle();
            //testAddress();
            //testRandomAddress();
            //testDouble();
            //testFilteredList();
        } catch (Exception e) {
            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e, "Cant start API Nominatim plugin.",
                    null);
        }
        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * Test methods.
     * I commented this method to help to improve the compilation time.
     */
    /*private void testListCountries(){
        try {
            HashMap<String, Country> countriesHashMap = this.geolocationPluginManager.getCountryList();
            System.out.println("GEOLOCATION:" + countriesHashMap);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GEOLOCATION: Exception " + e);
        }
    }

    private void testListDependencies(){
        try {
            String countryCode = "VE";
            List<CountryDependency> countriesHashMap = this.geolocationPluginManager.
                    getCountryDependencies(countryCode);
            System.out.println("GEOLOCATION:"+countriesHashMap);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GEOLOCATION: Exception "+e);
        }
    }

    private void testCitiesByCountry(){
        try {
            String countryCode = "VE";
            List<City> cities = this.geolocationPluginManager.
                    getCitiesByCountryCode(countryCode);
            System.out.println("GEOLOCATION:"+cities);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GEOLOCATION: Exception "+e);
        }
    }

    private void testCitiesByDependency(){
        try{
            String dependencyName = "Carabobo";
            String countryCode = "VE";
            List<City> cities = this.geolocationPluginManager.
                    getCitiesByCountryCodeAndDependencyName(countryCode, dependencyName);
            System.out.println("GEOLOCATION:"+cities);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GEOLOCATION: Exception "+e);
        }
    }

    private void testGetGeoRectangle(){
        try{
            String location = "Maracay";
            GeoRectangle geoRectangle = this.geolocationPluginManager.
                    getGeoRectangleByLocation(location);
            System.out.println("GEOLOCATION:"+geoRectangle);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GEOLOCATION: Exception "+e);
        }
    }

    private void testAddress(){
        try{
            float latitude = 9.91861f;
            float longitude = -68.30472f;
            Address address = this.geolocationPluginManager.
                    getAddressByCoordinate(latitude, longitude);
            System.out.println("GEOLOCATION:"+address);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GEOLOCATION: Exception "+e);
        }
    }

    private void testRandomAddress(){
        try{
            GeoRectangle geoRectangle = this.geolocationPluginManager.
                    getRandomGeoLocation();
            System.out.println("GEOLOCATION:"+geoRectangle);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GEOLOCATION: Exception "+e);
        }
    }

    private void testDouble(){
        try{
            double latitude = 9.91861;
            double longitude = -68.30472;
            Address address = this.geolocationPluginManager.
                    getAddressByCoordinate(latitude, longitude);
            System.out.println("GEOLOCATION:"+address);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GEOLOCATION: Exception "+e);
        }
    }

    private void testFilteredList(){
        try{
            String filter = "Mar";
            List<ExtendedCity> extendedCityList =
                    this.geolocationPluginManager.getExtendedCitiesByFilter(filter);
            System.out.println("GEOLOCATION: "+extendedCityList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GEOLOCATION: Exception "+e);
        }
    }*/

}