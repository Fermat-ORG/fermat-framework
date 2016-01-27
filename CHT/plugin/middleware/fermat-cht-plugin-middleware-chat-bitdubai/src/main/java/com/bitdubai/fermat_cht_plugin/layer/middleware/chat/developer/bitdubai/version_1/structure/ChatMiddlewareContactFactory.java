package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetCompatiblesActorNetworkServiceListException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/01/16.
 */
public class ChatMiddlewareContactFactory {

    /**
     * Compatible platforms.
     * This object contains the compatible or implemented platforms in this version.
     * To make the code more readable, please keep the compatible platforms. sorted alphabetically.
     */
    Platforms[] compatiblePlatforms={
            Platforms.CRYPTO_CURRENCY_PLATFORM,
            Platforms.DIGITAL_ASSET_PLATFORM};

    /**
     * This object contains all the compatible actor network service in this version.
     * Please, use the Platforms enum code as key in this HashMap
     */
    HashMap<String, Object> actorNetworkServiceMap;

    HashMap<String, Object> compatiblesActorNetworkServiceMap;

    public ChatMiddlewareContactFactory(
            HashMap<String, Object> actorNetworkServiceMap) throws
            CantGetCompatiblesActorNetworkServiceListException {
        this.actorNetworkServiceMap = actorNetworkServiceMap;
        this.compatiblesActorNetworkServiceMap =getCompatiblesActorNetworkService();
    }

    /**
     * This method returns a HashMap with the compatibles actor Network Services.
     * @return
     */
    private HashMap<String, Object> getCompatiblesActorNetworkService() throws CantGetCompatiblesActorNetworkServiceListException {
        HashMap<String, Object> compatiblesActorNetworkServiceList=new HashMap<>();
        String platformEnumCode;
        Object objectFromHashMap;
        try{
            for(Platforms platform : compatiblePlatforms){
                /**
                 * Please add the logic to add compatible platforms here
                 * To make the code more readable, please keep the compatible platforms. sorted alphabetically.
                 */
                //CCP
                if(platform==Platforms.CRYPTO_CURRENCY_PLATFORM){
                    platformEnumCode=platform.getCode();
                    objectFromHashMap=this.actorNetworkServiceMap.get(platformEnumCode);
                    if(objectFromHashMap!=null){

                        if(objectFromHashMap instanceof IntraWalletUserActorManager){
                            compatiblesActorNetworkServiceList.put(
                                    Platforms.CRYPTO_CURRENCY_PLATFORM.getCode(),
                                    objectFromHashMap);
                        } else {
                            //Please, not throw an exception, in this version, make a logcat report.
                            System.out.println(
                                    "CHAT Middleware: For "+platform+" we need " +
                                            ""+IntraUserManager.class+" and we get from PluginRoot " +
                                            ""+objectFromHashMap.getClass());
                        }

                    }else{
                        //Please, not throw an exception, this means that the actorNetworkService is not set in this plugin.
                        System.out.println(
                                "CHAT Middleware: The actor network service from "+platform+" is null");
                    }
                }
                //DAP USERS
                if(platform==Platforms.DIGITAL_ASSET_PLATFORM){
                    platformEnumCode=platform.getCode();
                    objectFromHashMap=this.actorNetworkServiceMap.get(platformEnumCode);
                    if(objectFromHashMap!=null){

                        if(objectFromHashMap instanceof AssetUserActorNetworkServiceManager){
                            compatiblesActorNetworkServiceList.put(
                                    Platforms.DIGITAL_ASSET_PLATFORM.getCode(),
                                    objectFromHashMap);
                        } else {
                            //Please, not throw an exception, in this version, make a logcat report.
                            System.out.println(
                                    "CHAT Middleware: For "+platform+" we need " +
                                            ""+IntraUserManager.class+" and we get from PluginRoot " +
                                            ""+objectFromHashMap.getClass());
                        }

                    }else{
                        //Please, not throw an exception, this means that the actorNetworkService is not set in this plugin.
                        System.out.println(
                                "CHAT Middleware: The actor network service from "+platform+" is null");
                    }
                }
            }
            if(compatiblesActorNetworkServiceList.isEmpty()){
                throw new CantGetCompatiblesActorNetworkServiceListException(
                        "There's no actor network services in the HashMap");
            } else {
                return compatiblesActorNetworkServiceList;
            }
        } catch (Exception exception){
            throw new CantGetCompatiblesActorNetworkServiceListException(
                    FermatException.wrapException(exception),
                    "Cannot get the compatibles actor network service list",
                    "Unexpected error");
        }

    }

    /**
     * This method returns the active registered actors to contact list.
     * @return
     * @throws CantGetContactException
     */
    public List<Contact> discoverDeviceActors() throws CantGetContactException {
        List<Contact> contactList=new ArrayList<>();
        Contact contact;
        Set<String> keySet= compatiblesActorNetworkServiceMap.keySet();
        Object value;
        String remoteName;
        String alias;
        String actorPublicKey;
        Date date=new Date();
        try{
            for(String key : keySet){
                value=this.compatiblesActorNetworkServiceMap.get(key);
                /**
                 * Please add the logic to get the actor network service information here
                 * To make the code more readable, please keep the compatible platforms, sorted alphabetically.
                 */
                //CCP
                if(key.equals(Platforms.CRYPTO_CURRENCY_PLATFORM.getCode())){
                    IntraUserModuleManager intraActorManager = (IntraUserModuleManager) value;
                    List<IntraUserInformation> ccpActorList=intraActorManager.getAllIntraUsers(
                            intraActorManager.getActiveIntraUserIdentity().getPublicKey(), 20, 0);
                    for(IntraUserInformation intraUserInformation : ccpActorList){
                        remoteName=intraUserInformation.getName();
                        alias=intraUserInformation.getName();
                        actorPublicKey=intraUserInformation.getPublicKey();
                        contact=new ContactImpl(
                                UUID.randomUUID(),
                                remoteName,
                                alias,
                                PlatformComponentType.ACTOR_INTRA_USER,
                                actorPublicKey,
                                date
                        );
                        contactList.add(contact);
                    }
                }
                //DAP USERS
                if(key.equals(Platforms.DIGITAL_ASSET_PLATFORM.getCode())){
                    AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager =
                            (AssetUserActorNetworkServiceManager) value;
                    List<ActorAssetUser> dapActorList=
                            assetUserActorNetworkServiceManager.getListActorAssetUserRegistered();
                    for(ActorAssetUser actorAssetUser : dapActorList){
                        remoteName=actorAssetUser.getName();
                        alias=actorAssetUser.getName();
                        actorPublicKey=actorAssetUser.getActorPublicKey();
                        contact=new ContactImpl(
                                UUID.randomUUID(),
                                remoteName,
                                alias,
                                PlatformComponentType.ACTOR_ASSET_USER,
                                actorPublicKey,
                                date
                        );
                        contactList.add(contact);
                    }

                }
            }
            return contactList;
        } catch (ClassCastException exception){
            throw new CantGetContactException(
                    exception,
                    "Discovering the connected actors",
                    "Something goes wrong with the casting");
        } catch (CantGetActiveLoginIdentityException e) {
            throw new CantGetContactException(
                    e,
                    "Discovering the connected actors",
                    "Cannot get the active login identity");
        } catch (CantGetIntraUsersListException e) {
            throw new CantGetContactException(
                    e,
                    "Discovering the connected actors",
                    "Cannot get the intra user list identity");
        } catch (CantRequestListActorAssetUserRegisteredException e) {
            throw new CantGetContactException(
                    e,
                    "Discovering the connected actors",
                    "Cannot request actor user registered list");
        } catch(Exception exception){
            throw new CantGetContactException(
                    exception,
                    "Discovering the connected actors",
                    "Unexpected Exception");
        }
    }

}
