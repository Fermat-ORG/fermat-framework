package com.bitdubai.fermat_api.layer.all_definition.resources_structure;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;



/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin</code>
 * implements the functionality of a Fermat Skin.
 * <p/>
 *
 * Created by Leon Acosta and Matias Furszyfer
 * @version 1.0
 * @since Java JDK 1.7
 */

public class Skin implements Serializable {//implements FermatSkin {

    private UUID id;

    private String name;

    private Version version;

    private ScreenSize screenSize;

    private List<Resource> lstPortraitResources;

    private List<Resource> lstLandscapeResources;

    private List<Layout> lstPortraitLayouts;

    private List<Layout> lstLandscapeLayouts;


    public Skin(UUID id, String name, Version version, ScreenSize screenSize, List<Resource> lstPortraitResources, List<Resource> lstLandscapeResources, List<Layout> lstPortraitLayouts, List<Layout> lstLandscapeLayouts) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.screenSize = screenSize;
        this.lstPortraitResources = lstPortraitResources;
        this.lstLandscapeResources = lstLandscapeResources;
        this.lstPortraitLayouts = lstPortraitLayouts;
        this.lstLandscapeLayouts = lstLandscapeLayouts;
    }

    public Skin() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public ScreenSize getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    public List<Resource> getLstPortraitResources() {
        return lstPortraitResources;
    }

    public void setLstPortraitResources(List<Resource> lstPortraitResources) {
        this.lstPortraitResources = lstPortraitResources;
    }

    public List<Resource> getLstLandscapeResources() {
        return lstLandscapeResources;
    }

    public void setLstLandscapeResources(List<Resource> lstLandscapeResources) {
        this.lstLandscapeResources = lstLandscapeResources;
    }

    public List<Layout> getLstPortraitLayouts() {
        return lstPortraitLayouts;
    }

    public void setLstPortraitLayouts(List<Layout> lstPortraitLayouts) {
        this.lstPortraitLayouts = lstPortraitLayouts;
    }

    public List<Layout> getLstLandscapeLayouts() {
        return lstLandscapeLayouts;
    }

    public void setLstLandscapeLayouts(List<Layout> lstLandscapeLayouts) {
        this.lstLandscapeLayouts = lstLandscapeLayouts;
    }

    @Override
    public String toString() {
        return "Skin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version=" + version +
                ", screenSize=" + screenSize +
                ", lstPortraitResources=" + lstPortraitResources +
                ", lstLandscapeResources=" + lstLandscapeResources +
                ", lstPortraitLayouts=" + lstPortraitLayouts +
                ", lstLandscapeLayouts=" + lstLandscapeLayouts +
                '}';
    }
}
