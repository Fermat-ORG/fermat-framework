package com.bitdubai.fermat_api.layer.all_definition.resources_structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin</code>
 * implements the functionality of a Fermat Skin.
 * <p/>
 * <p/>
 * Created by Leon Acosta and Matias Furszyfer
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class Skin implements Serializable {//implements FermatSkin {

    private UUID id;

    private String name;

    private Version version;

    private VersionCompatibility navigationStructureCompatibility;

    private Map<String, Resource> resources;

    private Map<String, Layout> portraitLayouts;

    private Map<String, Layout> landscapeLayouts;

    private ScreenSize screenSize;

    private DesignerIdentity designeDesignerIdentity;

    private int size;

    public Skin(UUID id, String name, Version version, VersionCompatibility navigationStructureCompatibility, Map<String, Resource> resources, Map<String, Layout> portraitLayouts, Map<String, Layout> landscapeLayouts) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.setNavigationStructureCompatibility(navigationStructureCompatibility);
        this.setResources(resources);
        this.setPortraitLayouts(portraitLayouts);
        this.setLandscapeLayouts(landscapeLayouts);
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


    public VersionCompatibility getNavigationStructureCompatibility() {
        return navigationStructureCompatibility;
    }

    public void setNavigationStructureCompatibility(VersionCompatibility navigationStructureCompatibility) {
        this.navigationStructureCompatibility = navigationStructureCompatibility;
    }

    public Map<String, Resource> getResources() {
        return resources;
    }

    public void setResources(Map<String, Resource> resources) {
        this.resources = resources;
    }

    public Map<String, Layout> getPortraitLayouts() {
        return portraitLayouts;
    }

    public void setPortraitLayouts(Map<String, Layout> portraitLayouts) {
        this.portraitLayouts = portraitLayouts;
    }

    public Map<String, Layout> getLandscapeLayouts() {
        return landscapeLayouts;
    }

    public void setLandscapeLayouts(Map<String, Layout> landscapeLayouts) {
        this.landscapeLayouts = landscapeLayouts;
    }


    public ScreenSize getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    public DesignerIdentity getDesigner() {
        return designeDesignerIdentity;
    }

    public void setDesigner(DesignerIdentity designer) {
        this.designeDesignerIdentity = designer;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Skin{")
                .append("id=").append(id)
                .append(", name='").append(name)
                .append('\'')
                .append(", version=").append(version)
                .append(", navigationStructureCompatibility=").append(navigationStructureCompatibility)
                .append(", resources=").append(resources)
                .append(", portraitLayouts=").append(portraitLayouts)
                .append(", landscapeLayouts=").append(landscapeLayouts)
                .append('}').toString();
    }
}
