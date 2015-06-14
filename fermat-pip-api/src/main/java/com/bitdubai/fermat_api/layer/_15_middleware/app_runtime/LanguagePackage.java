package com.bitdubai.fermat_api.layer._15_middleware.app_runtime;

import com.bitdubai.fermat_api.layer._1_definition.enums.Languages;
import com.bitdubai.fermat_api.layer._5_user.device_user.DeviceUser;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public interface LanguagePackage {

    public DeviceUser getAuthor();

    public String getName();

    public Languages getLanguage();

    public Map<String, String> getTranslation();
}
