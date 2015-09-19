package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;

import java.util.Map;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatLanguagePackage {

    public String getName();

    public Languages getLanguage();

    public Map<String, String> getTranslation();
}
