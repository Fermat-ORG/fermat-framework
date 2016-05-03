package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;

import java.util.Map;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatLanguagePackage {

    String getName();

    Languages getLanguage();

    Map<String, String> getTranslation();
}
