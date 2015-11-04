package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenOrientation;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetLanguageFileException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetSkinFileException;

import java.util.UUID;

/**
 * This interface let the client access to the resources of a wallet
 *
 * @author Ezequiel Postan
 */
public interface WalletResources {

    /**
     * This method returns the resourcesId
     *
     * @return the Id of resources being represented
     */
    public UUID getResourcesId();


    /**
     * This method let us get an skin file referenced by its name
     *
     * @param fileName the name of the Skin file (without the path structure).
     * @return The content of the file
     * @throws CantGetSkinFileException
     */
    public Skin getSkinFile(String fileName) throws CantGetSkinFileException;

    /**
     * This method let us get a language file referenced by a name
     *
     * @param fileName the name of the Language file (without the path structure).
     * @return The content of the file
     * @throws CantGetLanguageFileException
     */
    public String getLanguageFile(String fileName) throws CantGetLanguageFileException;


    /**
     * This method let us get an image referenced by a name
     *
     * @param imageName the name of the image resource found in the skin file
     * @return the image represented as a byte array
     * @throws CantGetResourcesException
     */
    public byte[] getImageResource(String imageName,ScreenOrientation orientation) throws CantGetResourcesException;

    /**
     * This method let us get a video referenced by a name
     *
     * @param videoName the name of the video resource found in the skin file
     * @return the video represented as a byte array
     * @throws CantGetResourcesException
     */
    public byte[] getVideoResource(String videoName) throws CantGetResourcesException;

    /**
     * This method let us get a sound referenced by a name
     *
     * @param soundName the name of the sound resource found in the skin file
     * @return the sound represented as a byte array
     * @throws CantGetResourcesException
     */
    public byte[] getSoundResource(String soundName) throws CantGetResourcesException;

    /**
     * This method let us get a font style referenced by a name
     *
     * @param styleName the name of the font style resource found in the skin file
     * @return the font style represented as the content of a ttf file
     * @throws CantGetResourcesException
     */
    public String getFontStyle(String styleName);

    /**
     * This method let us get a layout referenced by a name
     *
     * @param layoutName the name of the layout resource found in the skin file
     * @return the layiut represented as String
     * @throws CantGetResourcesException
     */
    public String getLayoutResource(String layoutName) throws CantGetResourcesException;
}
