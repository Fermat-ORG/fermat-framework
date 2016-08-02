package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class LanguagePackage implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatLanguagePackage,Serializable {

   // DeviceUser author;
    String name;
    Languages language;
    Map<String,String> translation;


    //public void setAuthor(DeviceUser author) {
       // this.author = author;
   // }

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
/*
Esto está mal, el autor no puede ser un device user. Luis.
    @Override
    public DeviceUser getAuthor() {
        return author;
    }
*/
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
