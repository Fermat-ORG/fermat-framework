package com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources;



import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.InstalationProgress;

/**
 * Created by natalia on 2015.07.28..
 */
public interface SubAppResourcesInstalationManager {


    /**
     *
     * @param developer
     * @param screenSize
     * @param screenDensity
     * @param skinName
     * @param languageName
     * @param navigationStructureVersion
     */

    public void installResources(String subApp,String developer,String screenSize,String screenDensity,String skinName,String languageName,String navigationStructureVersion);


    /**
     *
     * @param subApp
     */

    public void unninstallResources(String subApp);

    /**
     *  Get enum instalation progress
     * @return
     */
    public InstalationProgress getInstalationProgress();

}
