package com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatLanguage</code>
 * indicates the functionality of a Fermat Language.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatLanguage {

    // TODO TRANSLATOR.

    UUID getId();

    // name of language
    String getName();

    // type of language
    Languages getType();

    // language strings
    Map<String, String> getStrings();

    // version of language
    Version getVersion();

    // TODO: WALLETVERSIONBETWEEN

    // add strings to the language
    void addString(String name, String value);

    // delete strings from the language
    void deleteString(String name);

}
