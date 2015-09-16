package com.bitdubai.fermat_api.layer.all_definition.enums.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum</code>
 * haves the representation of the basic functionality of a Fermat Enum.
 *
 * Created by Leon Acosta (laion.cj91@gmail.com) on 16/09/2015.
 */
public interface FermatEnum {

    /**
     * Throw the method <code>getPlatform</code> you can know to which platform the enum belongs.
     *
     * @return an instance of Platforms enum.
     */
    Platforms getPlatform();

    /**
     * Throw the method <code>getCode</code> you can get the code of the specific element of the enum.
     *
     * @return the code of the enum.
     */
    String getCode();

}
