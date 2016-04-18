package com.bitdubai.fermat_api.layer.all_definition.resources_structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatLanguage;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language</code>
 * implements the functionality of a Fermat Language.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Language implements FermatLanguage {

    /**
     * Language Class private attributes
     */
    private UUID id;

    private String name;

    private Languages type;

    private Map<String, String> strings = new HashMap<>();

    private Version version;

    private TranslatorIdentity translator;

    private int size;


    /**
     * Language Class Constructors
     */
    public Language() {
    }

    public Language(String name, Languages type, Version version) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.version = version;
    }

    public Language(UUID id, String name, Languages type, Map<String, String> strings, Version version) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.strings = strings;
        this.version = version;
    }

    /**
     * FermatLanguage interface implementation
     */

    @Override
    public void addString(String name, String value) {
        strings.put(name, value);
    }

    @Override
    public void deleteString(String name) {
        strings.remove(name);
    }

    /**
     * Language Class getters
     */
    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public Languages getType() {
        return type;
    }


    @Override
    public Map<String, String> getStrings() {
        return strings;
    }

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

    public TranslatorIdentity getTranslator() {
        return translator;
    }

    public void setTranslator(TranslatorIdentity translator) {
        this.translator = translator;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
