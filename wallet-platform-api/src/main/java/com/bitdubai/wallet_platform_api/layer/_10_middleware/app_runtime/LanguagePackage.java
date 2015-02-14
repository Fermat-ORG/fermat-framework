package com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Languages;
import com.bitdubai.wallet_platform_api.layer._4_user.User;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public interface LanguagePackage {

    public User getAuthor();

    public String getName();

    public Languages getLanguage();

    public Map<String, String> getTranslation();
}
