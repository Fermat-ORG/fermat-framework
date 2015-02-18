package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.LanguagePackage;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Languages;
import com.bitdubai.wallet_platform_api.layer._4_user.DeviceUser;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeLanguagePackage implements LanguagePackage {
    
    DeviceUser author;
    String name;
    Languages language;
    Map<String,String> translation;


    public void setAuthor(DeviceUser author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }

    public void setTranslation(Map<String, String> translation) {
        this.translation = translation;
    }


    /**
     * LanguagePackage Interface implementation.
     */

    @Override
    public DeviceUser getAuthor() {
        return author;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Languages getLanguage() {
        return language;
    }

    @Override
    public Map<String, String> getTranslation() {
        return translation;
    }
}
