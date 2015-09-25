package com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantAddResourceException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantCloseWalletSkinException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantCopyWalletSkinException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantCreateEmptyWalletSkinException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantDeleteResourceException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantDeleteWalletSkinException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantGetWalletSkinException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantGetWalletSkinStructureException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantListWalletSkinsException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantSaveWalletSkinStructureException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.CantUpdateResourceException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.ResourceAlreadyExistsException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.ResourceNotFoundException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.exceptions.SkinNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.middleware.wallet_skin.interfaces.WalletSkinManager</code>
 * indicates the functionality of a WalletSkinManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) 29/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletSkinManager {

    /**
     * returns a list of instances of WalletSkins related to the proposal we are working with
     * (when we create an instance of this manager we pass throw parameters the walletProjectProposal).
     *
     * @return a list of instances of WalletSkins
     * @throws CantListWalletSkinsException if something goes wrong
     */
    List<WalletSkin> listSkins(String designerPublicKey) throws CantListWalletSkinsException;

    /**
     * get skin by skinid and version
     *
     * @param skinId of the skin
     * @param version of the skin
     * @return an instance of the WalletSkin
     * @throws CantGetWalletSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    WalletSkin getSkinBySkinIdAndVersion(UUID skinId, Version version) throws CantGetWalletSkinException, SkinNotFoundException;

    /**
     * get skin by id
     *
     * @param id of the skin
     * @return an instance of the WalletSkin
     * @throws CantGetWalletSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    WalletSkin getSkinById(UUID id) throws CantGetWalletSkinException, SkinNotFoundException;

    /**
     * get skins by id
     *
     * @param skinId of the skins
     * @return a list of instances of the WalletSkin related to this id
     * @throws CantListWalletSkinsException if something goes wrong
     */
    List<WalletSkin> getSkinsBySkinId(UUID skinId) throws CantListWalletSkinsException;

    /**
     * create a new empty skin with the given name
     *
     * @param name of the skin
     * @return an instance WalletSkin
     * @throws CantCreateEmptyWalletSkinException if something goes wrong
     */
    WalletSkin createEmptySkin(String name, String designerPublicKey) throws CantCreateEmptyWalletSkinException;

    /**
     * throw this method you can clone an existent WalletSkin with a new name
     * it creates a new skin id
     *
     * @param newName for the skin
     * @param walletSkin to copy
     * @return an instance of the new WalletSkin
     * @throws CantCopyWalletSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    WalletSkin copySkin(String newName, String designerPublicKey, WalletSkin walletSkin) throws CantCopyWalletSkinException, SkinNotFoundException;

    /**
     * throw this method you can clone an existent WalletSkin creating a new version of it
     * you can identify it throw the alias
     * this skin has to keep the skin id of the old one
     *
     * @param alias
     * @param walletSkin
     * @return
     * @throws CantCopyWalletSkinException
     */
    WalletSkin createNewVersion(String alias, WalletSkin walletSkin) throws CantCopyWalletSkinException;

    /**
     * close an existent skin so it can be published
     *
     * @param walletSkin you're trying to delete
     * @throws CantDeleteWalletSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    void closeSkin(WalletSkin walletSkin) throws CantCloseWalletSkinException, SkinNotFoundException;

    /**
     * delete an existent skin
     *
     * @param walletSkin you're trying to delete
     * @throws CantDeleteWalletSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    void deleteSkin(WalletSkin walletSkin) throws CantDeleteWalletSkinException, SkinNotFoundException;

    /**
     * brings the skin structure of the project skin
     *
     * @param walletSkin that you're trying to get the structure
     * @return an instance of the class structure of the Skin
     * @throws CantGetWalletSkinStructureException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    Skin getSkinStructure(WalletSkin walletSkin) throws CantGetWalletSkinStructureException, SkinNotFoundException;

    /**
     * converts an xml file in a skin structure
     *
     * @param skinStructure xmlstring of the skin
     * @return the class structure of the Skin
     * @throws CantGetWalletSkinStructureException if something goes wrong
     */
    Skin getSkinFromXmlString(String skinStructure) throws CantGetWalletSkinStructureException;

    /**
     * converts the given skin structure in an xml file.
     *
     * @param skin class structure to convert
     * @return xmlstring of the skin structure
     * @throws CantGetWalletSkinStructureException if something goes wrong
     */
    String getSkinXmlFromClassStructure(Skin skin) throws CantGetWalletSkinStructureException;

    /**
     * converts the given skin structure in an xml file and saves in the file structure
     *
     * @param skin you're trying to save
     * @param walletSkin in which you're trying to save
     * @throws CantSaveWalletSkinStructureException if something goes wrong
     */
    void saveSkinStructureXml(Skin skin, WalletSkin walletSkin) throws CantSaveWalletSkinStructureException;

    /**
     * add a new resource to a skin
     *
     * @param resource data
     * @param file you'll save
     * @param walletSkin in which you re going to save
     * @throws CantAddResourceException if something goes wrong
     * @throws ResourceAlreadyExistsException if i cant find the resource
     */
    void addResource(Resource resource, byte[] file, WalletSkin walletSkin) throws CantAddResourceException, ResourceAlreadyExistsException;

    /**
     * updates the file resource of a skin
     *
     * @param resource data
     * @param file you'll save
     * @param walletSkin in which you re going to save
     * @throws CantUpdateResourceException if something goes wrong
     * @throws ResourceNotFoundException if i cant find the resource
     */
    void updateResource(Resource resource, byte[] file, WalletSkin walletSkin) throws CantUpdateResourceException, ResourceNotFoundException;

    /**
     * delete an specific resource in the skin
     *
     * @param resource you're trying to delete
     * @param walletSkin in which you re going to delete
     * @throws CantDeleteResourceException if something goes wrong
     * @throws ResourceNotFoundException if i cant find the resource
     */
    void deleteResource(Resource resource, WalletSkin walletSkin) throws CantDeleteResourceException, ResourceNotFoundException;

}