package com.bitdubai.fermat_api.layer.all_definition.resources_structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatResource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatSkin;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin</code>
 * implements the functionality of a Fermat Skin.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Skin implements FermatSkin {

    /**
     * Skin Class private attributes
     */
    private UUID id;

    private String name;

    private Map<UUID, FermatResource> resources = new HashMap<>();

    private Version version;


    /**
     * Skin Class Constructors
     */
    public Skin() {
    }

    public Skin(String name, Version version) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.version = version;
    }

    public Skin(UUID id, String name, Map<UUID, FermatResource> resources, Version version) {
        this.id = id;
        this.name = name;
        this.resources = resources;
        this.version = version;
    }

    /**
     * FermatSkin interface implementation
     */

    @Override
    public void addResource(FermatResource fermatResource) {
        resources.put(fermatResource.getId(), fermatResource);
    }

    @Override
    public void deleteResource(FermatResource fermatResource) {
        resources.remove(fermatResource.getId());
    }

    /**
     * Skin Class getters
     */

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }


    public Map<UUID, FermatResource> getResources() {
        return resources;
    }


    @Override
    public Version getVersion() {
        return version;
    }

    /**
     * Skin Class setters
     */
    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResources(Map<UUID, FermatResource> resources) {
        this.resources = resources;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
