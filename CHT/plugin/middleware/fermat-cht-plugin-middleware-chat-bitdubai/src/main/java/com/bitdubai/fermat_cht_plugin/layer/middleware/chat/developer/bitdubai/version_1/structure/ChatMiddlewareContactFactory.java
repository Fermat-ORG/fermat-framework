package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
//import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
//import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
//import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
//import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
//import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
//import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySearch;
//import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
//import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySearch;
//import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
//import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
//import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
//import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
//import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
//import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
//import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
//import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
//import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
//import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
//import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
//import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
//import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces.AssetRedeemPointActorNetworkServiceManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/01/16.
 */
public class ChatMiddlewareContactFactory {

    //TODO:Eliminar
    /**
     * Compatible platforms.
     * This object contains the compatible or implemented platforms in this version.
     * To make the code more readable, please keep the compatible platforms. sorted alphabetically.
     */
    Platforms[] compatiblePlatforms={
            Platforms.CRYPTO_CURRENCY_PLATFORM
            //Platforms.DIGITAL_ASSET_PLATFORM
            //Platforms.CRYPTO_BROKER_PLATFORM
    };

    //TODO:Eliminar
    /**
     * This object contains all the compatible actor network service in this version.
     * Please, use the Platforms enum code as key in this HashMap
     */
    HashMap<String, Object> actorNetworkServiceMap;

    //TODO:Eliminar
    /**
     * This object contains all the compatibles checked actor network service in this version.
     */
    HashMap<String, Object> compatiblesActorNetworkServiceMap;

    /**
     * Represents the ErrorManager
     */
    ErrorManager errorManager;

    //TODO:Eliminar
//    /**
//     * This represents the ActorAssetUserManager.
//     * actorAssetUserManager is used to determinate the own DAP identity.
//     */
//    ActorAssetUserManager actorAssetUserManager;
//
//    /**
//     * This represents the ActorAssetIssuerManager.
//     * actorAssetIssuerManager is used to determinate the own DAP identity.
//     */
//    ActorAssetIssuerManager actorAssetIssuerManager;
//
//    /**
//     * This represents the ActorAssetRedeemPointManager.
//     * actorAssetRedeemPointManager is used to determinate the own DAP identity.
//     */
//    ActorAssetRedeemPointManager actorAssetRedeemPointManager;
//
//    /**
//     * This represents the CryptoBrokerIdentityManager.
//     * cryptoBrokerIdentityManager is used to determinate the own CBP identity.
//     */
//    CryptoBrokerIdentityManager cryptoBrokerIdentityManager;
//
//    /**
//     * This represents the CryptoCustomerIdentityManager.
//     * cryptoCustomerIdentityManager is used to determinate the own CBP identity.
//     */
//    CryptoCustomerIdentityManager cryptoCustomerIdentityManager;
//
//    public ChatMiddlewareContactFactory(
//            HashMap<String, Object> actorNetworkServiceMap,
//            ErrorManager errorManager) throws
//            CantGetCompatiblesActorNetworkServiceListException {
//        this.actorNetworkServiceMap = actorNetworkServiceMap;
//        this.compatiblesActorNetworkServiceMap = getCompatiblesActorNetworkService();
//        this.errorManager = errorManager;
//    }
//
//    /**
//     * This method returns a HashMap with the compatibles actor Network Services.
//     * @return
//     */
//    private HashMap<String, Object> getCompatiblesActorNetworkService() throws
//            CantGetCompatiblesActorNetworkServiceListException {
//        HashMap<String, Object> compatiblesActorNetworkServiceList=new HashMap<>();
//        String platformEnumCode;
//        Object objectFromHashMap;
//        try{
//            for(Platforms platform : compatiblePlatforms){
//                /**
//                 * Please add the logic to add compatible platforms here
//                 * To make the code more readable, please keep the compatible platforms. sorted alphabetically.
//                 */
//                //CCP
//                if(platform==Platforms.CRYPTO_CURRENCY_PLATFORM){
//                    platformEnumCode=platform.getCode();
//                    objectFromHashMap=this.actorNetworkServiceMap.get(platformEnumCode);
//                    if(objectFromHashMap!=null){
//
//                        if(objectFromHashMap instanceof IntraUserModuleManager){
//                            compatiblesActorNetworkServiceList.put(
//                                    Platforms.CRYPTO_CURRENCY_PLATFORM.getCode(),
//                                    objectFromHashMap);
//                        } else {
//                            //Please, not throw an exception, in this version, make a logcat report.
//                            System.out.println(
//                                    "CHAT Middleware: For "+platform+" we need " +
//                                            ""+IntraUserModuleManager.class+" and we get from PluginRoot " +
//                                            ""+objectFromHashMap.getClass());
//                        }
//
//                    }else{
//                        //Please, not throw an exception, this means that the actorNetworkService is not set in this plugin.
//                        System.out.println(
//                                "CHAT Middleware: The actor network service from "+platform+" is null");
//                    }
//                }
//                //DAP PLATFORM
//                if(platform==Platforms.DIGITAL_ASSET_PLATFORM){
//                    platformEnumCode=platform.getCode();
//                    objectFromHashMap=this.actorNetworkServiceMap.get(platformEnumCode);
//                    if(objectFromHashMap!=null){
//                        List dapManagers= (List) objectFromHashMap;
//                        List dapCompatiblesManagers = new ArrayList();
//                        for(Object manager : dapManagers){
//                            if(manager instanceof AssetUserActorNetworkServiceManager){
//                                dapCompatiblesManagers.add(manager);
//                                continue;
//                            }
//                            if(manager instanceof AssetIssuerActorNetworkServiceManager){
//                                dapCompatiblesManagers.add(manager);
//                                continue;
//                            }
//                            if(manager instanceof AssetRedeemPointActorNetworkServiceManager){
//                                dapCompatiblesManagers.add(manager);
//                                continue;
//                            }
//                        }
//                        compatiblesActorNetworkServiceList.put(
//                                Platforms.DIGITAL_ASSET_PLATFORM.getCode(),
//                                dapCompatiblesManagers);
//
//                    }else{
//                        //Please, not throw an exception, this means that the actorNetworkService is not set in this plugin.
//                        System.out.println(
//                                "CHAT Middleware: The actor network service from "+platform+" is null");
//                    }
//                }
//                //CBP
//                if(platform==Platforms.CRYPTO_BROKER_PLATFORM){
//                    platformEnumCode=platform.getCode();
//                    objectFromHashMap=this.actorNetworkServiceMap.get(platformEnumCode);
//                    if(objectFromHashMap!=null){
//                        List cbpManagers= (List) objectFromHashMap;
//                        List cbpCompatiblesManagers = new ArrayList();
//                        for(Object manager : cbpManagers){
//                            if(manager instanceof CryptoBrokerCommunitySearch){
//                                cbpCompatiblesManagers.add(manager);
//                                continue;
//                            }
//                            if(manager instanceof CryptoCustomerCommunitySearch){
//                                cbpCompatiblesManagers.add(manager);
//                                continue;
//                            }
//                        }
//                        compatiblesActorNetworkServiceList.put(
//                                Platforms.CRYPTO_BROKER_PLATFORM.getCode(),
//                                cbpCompatiblesManagers);
//
//                    }else{
//                        //Please, not throw an exception, this means that the actorNetworkService is not set in this plugin.
//                        System.out.println(
//                                "CHAT Middleware: The actor network service from "+platform+" is null");
//                    }
//                }
//
//            }
//            if(compatiblesActorNetworkServiceList.isEmpty()){
//                throw new CantGetCompatiblesActorNetworkServiceListException(
//                        "There's no actor network services in the HashMap");
//            } else {
//                return compatiblesActorNetworkServiceList;
//            }
//        } catch (Exception exception){
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    FermatException.wrapException(exception));
//            throw new CantGetCompatiblesActorNetworkServiceListException(
//                    FermatException.wrapException(exception),
//                    "Cannot get the compatibles actor network service list",
//                    "Unexpected error");
//        }
//
//    }
//
//    /**
//     * This method returns the active registered actors to contact list.
//     * @return
//     * @throws CantGetContactException
//     */
//    public List<ContactConnection> discoverDeviceActors() throws
//            CantGetContactException {
//        List<ContactConnection> contactList=new ArrayList<>();
//        ContactConnection contact;
//        Set<String> keySet= compatiblesActorNetworkServiceMap.keySet();
//        Object value;
//        String remoteName;
//        String alias;
//        String actorPublicKey;
//        Date date=new Date();
//        try{
//            for(String key : keySet){
//                value=this.compatiblesActorNetworkServiceMap.get(key);
//                /**
//                 * Please add the logic to get the actor network service information here
//                 * To make the code more readable, please keep the compatible platforms, sorted alphabetically.
//                 */
//                //CCP
//                if(key.equals(Platforms.CRYPTO_CURRENCY_PLATFORM.getCode())){
//                    IntraUserModuleManager intraActorManager = (IntraUserModuleManager) value;
//                    IntraUserLoginIdentity identity=intraActorManager.getActiveIntraUserIdentity();
//                    if(identity==null){
//                        continue;
//                    }
//                    String appPublicKey=identity.getPublicKey();
//                    if(appPublicKey==null){
//                        continue;
//                    }
//                    //With this we can discover the intra users registered
//                    List<IntraUserInformation> ccpActorList=intraActorManager.getAllIntraUsers(
//                            appPublicKey
//                            , 20, 0);
//                    for(IntraUserInformation intraUserInformation : ccpActorList){
//                        remoteName=intraUserInformation.getName();
//                        alias=intraUserInformation.getName();
//                        actorPublicKey=intraUserInformation.getPublicKey();
//                        contact=new ContactConnectionImpl(
//                                UUID.randomUUID(),
//                                remoteName,
//                                alias,
//                                PlatformComponentType.ACTOR_INTRA_USER,
//                                actorPublicKey,
//                                date.getTime(),
//                                intraUserInformation.getProfileImage() != null ? intraUserInformation.getProfileImage() : new byte[0],
//                                ContactStatus.AVAILABLE
//                        );
//                        contactList.add(contact);
//                    }
//                    //With this we can get the possible CCP contacts
//                    ccpActorList=intraActorManager.getSuggestionsToContact(20, 0);
//                    for(IntraUserInformation intraUserInformation : ccpActorList){
//                        remoteName=intraUserInformation.getName();
//                        alias=intraUserInformation.getName();
//                        actorPublicKey=intraUserInformation.getPublicKey();
//                        contact=new ContactConnectionImpl(
//                                UUID.randomUUID(),
//                                remoteName,
//                                alias,
//                                PlatformComponentType.ACTOR_INTRA_USER,
//                                actorPublicKey,
//                                date.getTime(),
//                                intraUserInformation.getProfileImage() != null ? intraUserInformation.getProfileImage() : new byte[0],
//                                ContactStatus.AVAILABLE
//                        );
//                        contactList.add(contact);
//                    }
//                }
//                //DAP PLATFORM
//                if(key.equals(Platforms.DIGITAL_ASSET_PLATFORM.getCode())){
//                    List dapContacts= (List) value;
//                    for(Object manager : dapContacts){
//                        //DAP USERS
//                        if(manager instanceof AssetUserActorNetworkServiceManager){
//                            AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager =
//                                    (AssetUserActorNetworkServiceManager) manager;
//                            List<ActorAssetUser> dapActorList=
//                                    assetUserActorNetworkServiceManager.getListActorAssetUserRegistered();
//                            for(ActorAssetUser actorAssetUser : dapActorList){
//                                remoteName=actorAssetUser.getName();
//                                alias=actorAssetUser.getName();
//                                actorPublicKey=actorAssetUser.getActorPublicKey();
//                                contact=new ContactConnectionImpl(
//                                        UUID.randomUUID(),
//                                        remoteName,
//                                        alias,
//                                        PlatformComponentType.ACTOR_ASSET_USER,
//                                        actorPublicKey,
//                                        date.getTime(),
//                                        actorAssetUser.getProfileImage() != null ? actorAssetUser.getProfileImage() : new byte[0],
//                                        ContactStatus.AVAILABLE
//                                );
//                                contactList.add(contact);
//                            }
//                        }
//                        //DAP ISSUERS
//                        if(manager instanceof AssetIssuerActorNetworkServiceManager){
//                            AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager =
//                                    (AssetIssuerActorNetworkServiceManager) manager;
//                            List<ActorAssetIssuer> dapIssuerList=
//                                    assetIssuerActorNetworkServiceManager.getListActorAssetIssuerRegistered();
//                            for(ActorAssetIssuer actorAssetIssuer : dapIssuerList){
//                                remoteName=actorAssetIssuer.getName();
//                                alias=actorAssetIssuer.getName();
//                                actorPublicKey=actorAssetIssuer.getActorPublicKey();
//                                contact=new ContactConnectionImpl(
//                                        UUID.randomUUID(),
//                                        remoteName,
//                                        alias,
//                                        PlatformComponentType.ACTOR_ASSET_ISSUER,
//                                        actorPublicKey,
//                                        date.getTime(),
//                                        actorAssetIssuer.getProfileImage() != null ? actorAssetIssuer.getProfileImage() : new byte[0],
//                                        ContactStatus.AVAILABLE
//                                );
//                                contactList.add(contact);
//                            }
//                        }
//                        //DAP REDEEM POINT
//                        if(manager instanceof AssetRedeemPointActorNetworkServiceManager){
//                            AssetRedeemPointActorNetworkServiceManager assetRedeemPointActorNetworkServiceManager =
//                                    (AssetRedeemPointActorNetworkServiceManager) manager;
//                            List<ActorAssetRedeemPoint> dapRedeemPointList=
//                                    assetRedeemPointActorNetworkServiceManager.getListActorAssetRedeemPointRegistered();
//                            for(ActorAssetRedeemPoint actorAssetRedeemPoint : dapRedeemPointList){
//                                remoteName=actorAssetRedeemPoint.getName();
//                                alias=actorAssetRedeemPoint.getName();
//                                actorPublicKey=actorAssetRedeemPoint.getActorPublicKey();
//                                contact=new ContactConnectionImpl(
//                                        UUID.randomUUID(),
//                                        remoteName,
//                                        alias,
//                                        PlatformComponentType.ACTOR_ASSET_REDEEM_POINT,
//                                        actorPublicKey,
//                                        date.getTime(),
//                                        actorAssetRedeemPoint.getProfileImage() != null ? actorAssetRedeemPoint.getProfileImage() : new byte[0],
//                                        ContactStatus.AVAILABLE
//                                );
//                                contactList.add(contact);
//                            }
//                        }
//                    }
//
//                }
//                //CBP PLATFORM
//                if(key.equals(Platforms.CRYPTO_BROKER_PLATFORM.getCode())){
//                    List cbpContacts= (List) value;
//                    for(Object manager : cbpContacts){
//                        //CBP Brokers
//                        if(manager instanceof CryptoBrokerCommunitySearch){
//                            CryptoBrokerCommunitySearch cryptoBrokerCommunitySearch =
//                                    (CryptoBrokerCommunitySearch) manager;
//                            List<CryptoBrokerCommunityInformation> cbpActorList=
//                                    cryptoBrokerCommunitySearch.getResult();
//                            for(CryptoBrokerCommunityInformation actorBroker : cbpActorList){
//                                remoteName=actorBroker.getAlias();
//                                alias=actorBroker.getAlias();
//                                actorPublicKey=actorBroker.getPublicKey();
//                                contact=new ContactConnectionImpl(
//                                        UUID.randomUUID(),
//                                        remoteName,
//                                        alias,
//                                        PlatformComponentType.ACTOR_CRYPTO_BROKER,
//                                        actorPublicKey,
//                                        date.getTime(),
//                                        actorBroker.getImage() != null ? actorBroker.getImage() : new byte[0],
//                                        ContactStatus.AVAILABLE
//                                );
//                                contactList.add(contact);
//                            }
//                        }
//                        //CBP Customer
//                        if(manager instanceof CryptoCustomerCommunitySearch){
//                            CryptoCustomerCommunitySearch cryptoCustomerCommunitySearch =
//                                    (CryptoCustomerCommunitySearch) manager;
//                            List<CryptoCustomerCommunityInformation> cbpActorList=
//                                    cryptoCustomerCommunitySearch.getResult();
//                            for(CryptoCustomerCommunityInformation actorCustomer : cbpActorList){
//                                remoteName=actorCustomer.getAlias();
//                                alias=actorCustomer.getAlias();
//                                actorPublicKey=actorCustomer.getPublicKey();
//                                contact=new ContactConnectionImpl(
//                                        UUID.randomUUID(),
//                                        remoteName,
//                                        alias,
//                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
//                                        actorPublicKey,
//                                        date.getTime(),
//                                        actorCustomer.getImage() != null ? actorCustomer.getImage() : new byte[0],
//                                        ContactStatus.AVAILABLE
//                                );
//                                contactList.add(contact);
//                            }
//                        }
//                    }
//
//                }
//            }
//            return contactList;
//        } catch (ClassCastException exception){
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    FermatException.wrapException(exception));
//            throw new CantGetContactException(
//                    FermatException.wrapException(exception),
//                    "Discovering the connected actors",
//                    "Something goes wrong with the casting");
//        } catch (CantGetActiveLoginIdentityException e) {
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    e);
//            throw new CantGetContactException(
//                    e,
//                    "Discovering the connected actors",
//                    "Cannot get the active login identity");
//        } catch (CantGetIntraUsersListException e) {
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    e);
//            throw new CantGetContactException(
//                    e,
//                    "Discovering the connected actors",
//                    "Cannot get the intra user list identity");
//        } catch (CantRequestListActorAssetUserRegisteredException e) {
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    e);
//            throw new CantGetContactException(
//                    e,
//                    "Discovering the connected actors",
//                    "Cannot request actor user registered list");
//        } catch(Exception exception){
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    FermatException.wrapException(exception));
//            throw new CantGetContactException(
//                    FermatException.wrapException(exception),
//                    "Discovering the connected actors",
//                    "Unexpected Exception");
//        }
//    }
//
//    /**
//     * This method return a HashMap with the possible self identities.
//     * The HashMap contains a Key-value like PlatformComponentType-ActorPublicKey.
//     * If there no identities created in any platform, this hashMaps contains the public chat Network
//     * Service.
//     * This method must be called when I need to create a new chat object, to discover my onw chat
//     * identity to avoid to create an unregistered contact in the message receptor.
//     * @return
//     */
//    public HashMap<PlatformComponentType, Object> getSelfIdentities() //TODO:Cambiar el parametro del mapa del String por byte[]
//            throws CantGetOwnIdentitiesException {
//        Set<String> keySet= compatiblesActorNetworkServiceMap.keySet();
//        Object value;
//        HashMap<PlatformComponentType, Object> selfIdentitiesMap=new HashMap<>(); //TODO:Cambiar el parametro del mapa del String por byte[]
//        try{
//            for(String key : keySet){
//                value=this.compatiblesActorNetworkServiceMap.get(key);
//                /**
//                 * Please add the logic to get the actor network service information here
//                 * To make the code more readable, please keep the compatible platforms, sorted alphabetically.
//                 */
//                //CCP
//                if(key.equals(Platforms.CRYPTO_CURRENCY_PLATFORM.getCode())){
//                    IntraUserModuleManager intraActorManager = (IntraUserModuleManager) value;
//                    IntraUserLoginIdentity identity=intraActorManager.getActiveIntraUserIdentity();
//                    if(identity==null){
//                        continue;
//                    }
//                    String appPublicKey= identity.getPublicKey();
//                    if(appPublicKey==null){
//                        continue;
//                    }
//                    selfIdentitiesMap.put(PlatformComponentType.ACTOR_INTRA_USER, identity);
//                }
//                //DAP ACTORS
//                if(key.equals(Platforms.DIGITAL_ASSET_PLATFORM.getCode())){
//                    if(actorAssetUserManager!=null){
//                        ActorAssetUser actorAssetUser=actorAssetUserManager.getActorAssetUser();
//                        if(actorAssetUser!=null){
//                            String dapUserPublicKey=actorAssetUser.getActorPublicKey();
//                            selfIdentitiesMap.put(
//                                    PlatformComponentType.ACTOR_ASSET_USER,
//                                    actorAssetUser);
//                        }
//
//                    }
//                    if(actorAssetIssuerManager!=null){
//                        ActorAssetIssuer actorAssetIssuer=actorAssetIssuerManager.getActorAssetIssuer();
//                        if(actorAssetIssuer!=null){
//                            String dapIssuerPublicKey=actorAssetIssuer.getActorPublicKey();
//                            selfIdentitiesMap.put(
//                                    PlatformComponentType.ACTOR_ASSET_ISSUER,
//                                    actorAssetIssuer);
//                        }
//
//                    }
//                    if(actorAssetRedeemPointManager!=null){
//                        ActorAssetRedeemPoint actorAssetRedeemPoint=actorAssetRedeemPointManager.getActorAssetRedeemPoint();
//                        if(actorAssetRedeemPoint!=null){
//                            String dapRedeemPointPublicKey=actorAssetRedeemPoint.getActorPublicKey();
//                            selfIdentitiesMap.put(
//                                    PlatformComponentType.ACTOR_ASSET_REDEEM_POINT,
//                                    actorAssetRedeemPoint);
//                        }
//
//                    }
//
//                }
//                //CBP ACTORS
//                if(key.equals(Platforms.CRYPTO_BROKER_PLATFORM.getCode())){
//                    if(cryptoBrokerIdentityManager!=null){
//                        List<CryptoBrokerIdentity> brokerIdentities=
//                                cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
//                        String brokerPublicKey;
//                        for(CryptoBrokerIdentity brokerIdentity : brokerIdentities){
//                            brokerPublicKey=brokerIdentity.getPublicKey();
//                            selfIdentitiesMap.put(
//                                    PlatformComponentType.ACTOR_CRYPTO_BROKER,
//                                    brokerIdentity);
//                            break;
//                        }
//                    }
//                    if(cryptoCustomerIdentityManager!=null){
//                        List<CryptoCustomerIdentity> customerIdentities=
//                                cryptoCustomerIdentityManager.listAllCryptoCustomerFromCurrentDeviceUser();
//                        String customerPublicKey;
//                        for(CryptoCustomerIdentity customerIdentity : customerIdentities){
//                            customerPublicKey=customerIdentity.getPublicKey();
//                            selfIdentitiesMap.put(
//                                    PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
//                                    customerIdentity);
//                            break;
//                        }
//                    }
//                }
//            }
//
//            return selfIdentitiesMap;
//        } catch (ClassCastException exception){
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    FermatException.wrapException(exception));
//            throw new CantGetOwnIdentitiesException(
//                    FermatException.wrapException(exception),
//                    "Discovering the own identities",
//                    "Something goes wrong with the casting");
//        } catch (CantGetActiveLoginIdentityException e) {
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    e);
//            throw new CantGetOwnIdentitiesException(
//                    e,
//                    "Discovering the own identities",
//                    "Cannot get the active login identity");
//        } catch (CantGetAssetUserActorsException e) {
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    e);
//            throw new CantGetOwnIdentitiesException(
//                    e,
//                    "Discovering the own identities",
//                    "Cannot get the asset user identity");
//        }  catch(Exception exception){
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.CHAT_MIDDLEWARE,
//                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
//                    FermatException.wrapException(exception));
//            throw new CantGetOwnIdentitiesException(
//                    FermatException.wrapException(exception),
//                    "Discovering the own identities",
//                    "Unexpected Exception");
//        }
//    }
//
//    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) {
//        this.actorAssetUserManager = actorAssetUserManager;
//    }
//
//    public void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) {
//        this.actorAssetIssuerManager = actorAssetIssuerManager;
//    }
//
//    public void setActorAssetRedeemPointManager(ActorAssetRedeemPointManager actorAssetRedeemPointManager) {
//        this.actorAssetRedeemPointManager = actorAssetRedeemPointManager;
//    }
//
//    public void setCryptoBrokerIdentityManager(CryptoBrokerIdentityManager cryptoBrokerIdentityManager) {
//        this.cryptoBrokerIdentityManager = cryptoBrokerIdentityManager;
//    }
//
//    public void setCryptoCustomerIdentityManager(CryptoCustomerIdentityManager cryptoCustomerIdentityManager) {
//        this.cryptoCustomerIdentityManager = cryptoCustomerIdentityManager;
//    }
}
