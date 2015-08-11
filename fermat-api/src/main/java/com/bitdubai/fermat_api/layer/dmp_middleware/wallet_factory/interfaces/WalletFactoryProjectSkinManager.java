package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddResourceToSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCopyWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateEmptyWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteResourceFromSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSetWalletFactoryProjectSkinStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateResourceToSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ResourceAlreadyExistsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ResourceNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.SkinNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletFactoryProjectSkinManager</code>
 * indicates the functionality of a WalletFactoryProjectSkinManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletFactoryProjectSkinManager {

    /**
     * returns a list of instances of WalletFactoryProjectSkins related to the proposal we are working with
     * (when we create an instance of this manager we pass throw parameters the walletProjectProposal).
     *
     * @return a list of instances of WalletFactoryProjectSkins
     * @throws CantGetWalletFactoryProjectSkinsException if something goes wrong
     */
    List<WalletFactoryProjectSkin> getSkins() throws CantGetWalletFactoryProjectSkinsException;

    /**
     * get skin by id
     *
     * @param id of the skin
     * @return an instance of the WalletFactoryProjectSkin
     * @throws CantGetWalletFactoryProjectSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    WalletFactoryProjectSkin getSkinById(UUID id) throws CantGetWalletFactoryProjectSkinException, SkinNotFoundException;

    /**
     * create a new empty skin with the given name
     *
     * @param name of the skin
     * @return an instance WalletFactoryProjectSkin
     * @throws CantCreateEmptyWalletFactoryProjectSkinException if something goes wrong
     */
    WalletFactoryProjectSkin createEmptySkin(String name) throws CantCreateEmptyWalletFactoryProjectSkinException;

    /**
     * copies an existent skin and creates a new one
     *
     * @param newName for the skin
     * @param walletFactoryProjectSkin to copy
     * @return an instance of the new WalletFactoryProjectSkin
     * @throws CantCopyWalletFactoryProjectSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    WalletFactoryProjectSkin copySkin(String newName, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantCopyWalletFactoryProjectSkinException, SkinNotFoundException;

    /**
     * delete an existent skin
     *
     * @param walletFactoryProjectSkin you're trying to delete
     * @throws CantDeleteWalletFactoryProjectSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    void deleteSkin(WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantDeleteWalletFactoryProjectSkinException, SkinNotFoundException;

    /**
     * brings the skin structure of the project skin
     *
     * @param walletFactoryProjectSkin that you're trying to get the structure
     * @return an instance of the class structure of the Skin
     * @throws CantGetWalletFactoryProjectSkinStructureException if something goes wrong
     */
    Skin getSkinStructure(WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantGetWalletFactoryProjectSkinStructureException;

    /**
     * converts an xml file in a skin structure
     *
     * @param skinStructure xmlstring of the skin
     * @return the class structure of the Skin
     * @throws CantGetWalletFactoryProjectSkinStructureException if something goes wrong
     */
    Skin getSkinStructure(String skinStructure) throws CantGetWalletFactoryProjectSkinStructureException;

    /**
     * converts the given skin structure in an xml file.
     *
     * @param skin class structure to convert
     * @return xmlstring of the skin structure
     * @throws CantGetWalletFactoryProjectSkinStructureException if something goes wrong
     */
    String getSkinStructureXml(Skin skin) throws CantGetWalletFactoryProjectSkinStructureException;

    /**
     * converts the given skin structure in an xml file and saves in the file structure
     *
     * @param skin you're trying to save
     * @param walletFactoryProjectSkin in which you're trying to save
     * @throws CantSetWalletFactoryProjectSkinStructureException if something goes wrong
     */
    void setSkinStructureXml(Skin skin, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantSetWalletFactoryProjectSkinStructureException;

    /**
     * add a new resource to a skin
     *
     * @param resource data
     * @param file you'll save
     * @param walletFactoryProjectSkin in which you re going to save
     * @throws CantAddResourceToSkinException if something goes wrong
     * @throws ResourceAlreadyExistsException if i cant find the resource
     */
    void addResourceToSkin(Resource resource, byte[] file, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantAddResourceToSkinException, ResourceAlreadyExistsException;

    /**
     * updates the file resource of a skin
     *
     * @param resource data
     * @param file you'll save
     * @param walletFactoryProjectSkin in which you re going to save
     * @throws CantUpdateResourceToSkinException if something goes wrong
     * @throws ResourceNotFoundException if i cant find the resource
     */
    void updateResourceToSkin(Resource resource, byte[] file, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantUpdateResourceToSkinException, ResourceNotFoundException;

    /**
     * delete an specific resource in the skin
     *
     * @param resource you're trying to delete
     * @param walletFactoryProjectSkin in which you re going to delete
     * @throws CantDeleteResourceFromSkinException if something goes wrong
     * @throws ResourceNotFoundException if i cant find the resource
     */
    void deleteResourceFromSkin(Resource resource, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantDeleteResourceFromSkinException, ResourceNotFoundException;

}