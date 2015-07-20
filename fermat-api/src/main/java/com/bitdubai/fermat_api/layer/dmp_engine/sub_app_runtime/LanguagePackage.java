package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;


import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public interface LanguagePackage {

    /*
    Esto está mal, el autor no puede ser un device user. Luis.
    public DeviceUser getAuthor();
*/
    public String getName();

    public Languages getLanguage();

    public Map<String, String> getTranslation();
}
