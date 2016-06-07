package com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources;

import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetImageResourceException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetLanguageFileException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetSkinFileException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenOrientation;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

import java.util.UUID;

/**
 * <p>The abstract class <code>com.bitdubai.fermat_api.layer.network_service.wallet_resources.WalletResourcesInstalationManager/code> is a interface
 * that define the methods to retrieve wallets resource files.
 *
 * @author Matias Furszyfer
 * @version 1.0.0
 * @since 4/08/15.
 */
public interface SubAppResourcesProviderManager extends ResourceProviderManager {


    /**
     * This method returns the resourcesId
     *
     * @return the Id of resources being represented
     */
    UUID getResourcesId();


    /**
     * This method let us get an skin file referenced by its id
     *
     * @param skinId
     * @param walletPublicKey
     * @return
     * @throws CantGetSkinFileException
     * @throws CantGetResourcesException
     */
    Skin getSkinFile(UUID skinId, String walletPublicKey) throws CantGetSkinFileException, CantGetResourcesException;

    /**
     * This method let us get a language file referenced by a name
     *
     * @param fileName the name of the Language file (without the path structure).
     * @return The content of the file
     * @throws CantGetLanguageFileException
     */
    String getLanguageFile(UUID skinId, String walletPublicKey, String fileName) throws CantGetLanguageFileException;


    /**
     * This method let us get an image referenced by a name
     *
     * @param imageName the name of the image resource found in the skin file
     * @return the image represented as a byte array
     * @throws CantGetResourcesException
     */
    byte[] getImageResource(String imageName, UUID skinId, String walletPublicKey) throws CantGetImageResourceException;

    /**
     * This method let us get a video referenced by a name
     *
     * @param videoName the name of the video resource found in the skin file
     * @return the video represented as a byte array
     * @throws CantGetResourcesException
     */
    byte[] getVideoResource(String videoName, UUID skinId) throws CantGetResourcesException;

    /**
     * This method let us get a sound referenced by a name
     *
     * @param soundName the name of the sound resource found in the skin file
     * @return the sound represented as a byte array
     * @throws CantGetResourcesException
     */
    byte[] getSoundResource(String soundName, UUID skinId) throws CantGetResourcesException;

    /**
     * This method let us get a font style referenced by a name
     *
     * @param styleName the name of the font style resource found in the skin file
     * @return the font style represented as the content of a ttf file
     * @throws CantGetResourcesException
     */
    String getFontStyle(String styleName, UUID skinId);

    /**
     * This method let us get a layout referenced by a name
     *
     * @param layoutName the name of the layout resource found in the skin file
     * @return the layiut represented as String
     * @throws CantGetResourcesException
     */
    String getLayoutResource(String layoutName, ScreenOrientation orientation, UUID skinId, String walletPublicKey) throws CantGetResourcesException;


}
