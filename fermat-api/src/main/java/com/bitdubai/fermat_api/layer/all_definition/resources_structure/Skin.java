package com.bitdubai.fermat_api.layer.all_definition.resources_structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters.VersionAdapter;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatResource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatSkin;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.List;
import java.util.UUID;

import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlElementWrapper;
import ae.javax.xml.bind.annotation.XmlElements;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin</code>
 * implements the functionality of a Fermat Skin.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
@XmlRootElement( name = "skin" )
public class Skin implements FermatSkin {

    /**
     * Skin Class private attributes
     */
    private UUID id;

    private String name;

    private List<FermatResource> resources;

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

    public Skin(UUID id, String name, List<FermatResource> resources, Version version) {
        this.id = id;
        this.name = name;
        this.resources = resources;
        this.version = version;
    }

    /**
     * Skin Class getters
     */
    @XmlAttribute( required=true )
    @Override
    public UUID getId() {
        return id;
    }

    @XmlElement(required=true )
    @Override
    public String getName() {
        return name;
    }

    @XmlElements({
            @XmlElement(name="resource", type=Resource.class),
    })
    @XmlElementWrapper
    public List<FermatResource> getResources() {
        return resources;
    }

    @XmlJavaTypeAdapter( VersionAdapter.class )
    @XmlElement( required=true )
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

    public void setResources(List<FermatResource> resources) {
        this.resources = resources;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
