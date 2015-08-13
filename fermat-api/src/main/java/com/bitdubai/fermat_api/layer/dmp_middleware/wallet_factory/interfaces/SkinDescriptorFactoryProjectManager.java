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
 * The interface <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.SkinDescriptorFactoryProjectManager</code>
 * indicates the functionality of a SkinDescriptorFactoryProjectManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface SkinDescriptorFactoryProjectManager {

    /**
     * returns a list of instances of WalletFactoryProjectSkins related to the proposal we are working with
     * (when we create an instance of this manager we pass throw parameters the walletProjectProposal).
     *
     * @return a list of instances of WalletFactoryProjectSkins
     * @throws CantGetWalletFactoryProjectSkinsException if something goes wrong
     */
    List<SkinDescriptorFactoryProject> getSkins() throws CantGetWalletFactoryProjectSkinsException;

    /**
     * get skin by id
     *
     * @param id of the skin
     * @return an instance of the SkinDescriptorFactoryProject
     * @throws CantGetWalletFactoryProjectSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    SkinDescriptorFactoryProject getSkinById(UUID id) throws CantGetWalletFactoryProjectSkinException, SkinNotFoundException;

    /**
     * create a new empty skin with the given name
     *
     * @param name of the skin
     * @return an instance SkinDescriptorFactoryProject
     * @throws CantCreateEmptyWalletFactoryProjectSkinException if something goes wrong
     */
    SkinDescriptorFactoryProject createEmptySkin(String name) throws CantCreateEmptyWalletFactoryProjectSkinException;

    /**
     * copies an existent skin and creates a new one
     *
     * @param newName for the skin
     * @param skinDescriptorFactoryProject to copy
     * @return an instance of the new SkinDescriptorFactoryProject
     * @throws CantCopyWalletFactoryProjectSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    SkinDescriptorFactoryProject copySkin(String newName, SkinDescriptorFactoryProject skinDescriptorFactoryProject) throws CantCopyWalletFactoryProjectSkinException, SkinNotFoundException;

    /**
     * delete an existent skin
     *
     * @param skinDescriptorFactoryProject you're trying to delete
     * @throws CantDeleteWalletFactoryProjectSkinException if something goes wrong
     * @throws SkinNotFoundException if i cant find the skin
     */
    void deleteSkin(SkinDescriptorFactoryProject skinDescriptorFactoryProject) throws CantDeleteWalletFactoryProjectSkinException, SkinNotFoundException;

    /**
     * brings the skin structure of the project skin
     *
     * @param skinDescriptorFactoryProject that you're trying to get the structure
     * @return an instance of the class structure of the Skin
     * @throws CantGetWalletFactoryProjectSkinStructureException if something goes wrong
     */
    Skin getSkinStructure(SkinDescriptorFactoryProject skinDescriptorFactoryProject) throws CantGetWalletFactoryProjectSkinStructureException;

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
     * @param skinDescriptorFactoryProject in which you're trying to save
     * @throws CantSetWalletFactoryProjectSkinStructureException if something goes wrong
     */
    void setSkinStructureXml(Skin skin, SkinDescriptorFactoryProject skinDescriptorFactoryProject) throws CantSetWalletFactoryProjectSkinStructureException;

    /**
     * add a new resource to a skin
     *
     * @param resource data
     * @param file you'll save
     * @param skinDescriptorFactoryProject in which you re going to save
     * @throws CantAddResourceToSkinException if something goes wrong
     * @throws ResourceAlreadyExistsException if i cant find the resource
     */
    void addResourceToSkin(Resource resource, byte[] file, SkinDescriptorFactoryProject skinDescriptorFactoryProject) throws CantAddResourceToSkinException, ResourceAlreadyExistsException;

    /**
     * updates the file resource of a skin
     *
     * @param resource data
     * @param file you'll save
     * @param skinDescriptorFactoryProject in which you re going to save
     * @throws CantUpdateResourceToSkinException if something goes wrong
     * @throws ResourceNotFoundException if i cant find the resource
     */
    void updateResourceToSkin(Resource resource, byte[] file, SkinDescriptorFactoryProject skinDescriptorFactoryProject) throws CantUpdateResourceToSkinException, ResourceNotFoundException;

    /**
     * delete an specific resource in the skin
     *
     * @param resource you're trying to delete
     * @param skinDescriptorFactoryProject in which you re going to delete
     * @throws CantDeleteResourceFromSkinException if something goes wrong
     * @throws ResourceNotFoundException if i cant find the resource
     */
    void deleteResourceFromSkin(Resource resource, SkinDescriptorFactoryProject skinDescriptorFactoryProject) throws CantDeleteResourceFromSkinException, ResourceNotFoundException;

}