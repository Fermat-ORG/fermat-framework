package com.bitdubai.fermat_api.layer.all_definition.resources_structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters.LanguagesAdapter;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters.StringsMapAdapter;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters.VersionAdapter;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatLanguage;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language</code>
 * implements the functionality of a Fermat Language.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
@XmlRootElement( name = "language" )
public class Language implements FermatLanguage {

    /**
     * Language Class private attributes
     */
    private UUID id;

    private String name;

    private Languages type;

    private Map<String, String> strings = new HashMap<>();

    private Version version;


    /**
     * Language Class Constructors
     */
    public Language() {
    }

    public Language(UUID id, String name, Languages type, Map<String, String> strings, Version version) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.strings = strings;
        this.version = version;
    }


    /**
     * Language Class getters
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

    @XmlJavaTypeAdapter( LanguagesAdapter.class )
    @XmlAttribute( required=true )
    @Override
    public Languages getType() {
        return type;
    }

    @XmlJavaTypeAdapter( StringsMapAdapter.class )
    @Override
    public Map<String, String> getStrings() {
        return strings;
    }

    @XmlJavaTypeAdapter( VersionAdapter.class )
    @XmlElement( required=true )
    @Override
    public Version getVersion() {
        return version;
    }


    /**
     * Language Class setters
     */
    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Languages type) {
        this.type = type;
    }

    public void setStrings(Map<String, String> strings) {
        this.strings = strings;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
