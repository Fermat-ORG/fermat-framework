package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantDeleteIdentityException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantUpdateIdentityException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraUserIdentitySettings;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantCreateNewIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantDeleteIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantGetIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantListIntraUsersIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantUpdateIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserModuleIdentity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by mati on 2016.04.11..
 */
public class IntraUserIdentityModuleManager extends ModuleManagerImpl<IntraUserIdentitySettings> implements com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserIdentityModuleManager {

    private ErrorManager errorManager;
    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    private LocationManager locationManager;


    public IntraUserIdentityModuleManager(UUID pluginId,ErrorManager errorManager, PluginFileSystem pluginFileSystem, IntraWalletUserIdentityManager intraWalletUserIdentityManager,LocationManager locationManager) {
        super(pluginFileSystem,pluginId);
        this.errorManager = errorManager;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
        this.locationManager = locationManager;
    }

    @Override
    public ArrayList<IntraUserModuleIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListIntraUsersIdentityException {
        try {

            ArrayList<IntraUserModuleIdentity> intraUserModuleIdentityList = new ArrayList<>();

            ArrayList<IntraWalletUserIdentity> intraWalletUserIdentityList = intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();

            for (IntraWalletUserIdentity intraUser : intraWalletUserIdentityList) {

                intraUserModuleIdentityList.add(new IntraUserIdentity(intraUser.getAlias(),intraUser.getPhrase(),intraUser.getPublicKey(),intraUser.getImage(),intraUser.getActorType()));
            }

            return intraUserModuleIdentityList;

        } catch (CantListIntraWalletUsersException e) {
            throw new CantListIntraUsersIdentityException(CantListIntraUsersIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantListIntraUsersIdentityException(CantListIntraUsersIdentityException.DEFAULT_MESSAGE, FermatException.wrapException(e),"","Unknown error");
        }
    }

    @Override
    public IntraWalletUserIdentity getIntraWalletUsers() throws CantListIntraUsersIdentityException {
        try {

            return intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().get(0);



        } catch (CantListIntraWalletUsersException e) {
            throw new CantListIntraUsersIdentityException(CantListIntraUsersIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantListIntraUsersIdentityException(CantListIntraUsersIdentityException.DEFAULT_MESSAGE, FermatException.wrapException(e),"","Unknown error");
        }
    }


    @Override
    public IntraUserModuleIdentity createNewIntraWalletUser(String alias, String phrase, byte[] profileImage, long accuracy, Frequency frequency, Location location) throws CantCreateNewIntraUserIdentityException {
        try {

            IntraWalletUserIdentity intraWalletUserIdentity =  intraWalletUserIdentityManager.createNewIntraWalletUser(alias, phrase,  profileImage,  accuracy, frequency,location);

            return new IntraUserIdentity( alias,  phrase,intraWalletUserIdentity.getPublicKey(), profileImage,intraWalletUserIdentity.getActorType());

        } catch (CantCreateNewIntraWalletUserException e) {
            throw new CantCreateNewIntraUserIdentityException(CantCreateNewIntraUserIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantCreateNewIntraUserIdentityException(CantCreateNewIntraUserIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }
    }

    @Override
    public IntraUserModuleIdentity createNewIntraWalletUser(String alias, byte[] profileImage, long accuracy, Frequency frequency, Location location) throws CantCreateNewIntraUserIdentityException {
        try {

            IntraWalletUserIdentity intraWalletUserIdentity =  intraWalletUserIdentityManager.createNewIntraWalletUser(alias, profileImage,accuracy, frequency,location);

            return new IntraUserIdentity( alias, "",intraWalletUserIdentity.getPublicKey(), profileImage,intraWalletUserIdentity.getActorType());

        } catch (CantCreateNewIntraWalletUserException e) {
            throw new CantCreateNewIntraUserIdentityException(CantCreateNewIntraUserIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantCreateNewIntraUserIdentityException(CantCreateNewIntraUserIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }
    }

    @Override
    public boolean hasIntraUserIdentity() throws CantGetIntraUserIdentityException {
        try {
            return intraWalletUserIdentityManager.hasIntraUserIdentity();

        } catch (CantListIntraWalletUsersException e) {
            throw new CantGetIntraUserIdentityException(CantGetIntraUserIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantGetIntraUserIdentityException(CantGetIntraUserIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }

    }

    @Override
    public void updateIntraUserIdentity(String identityPublicKey, String identityAlias, String phrase, byte[] profileImage, long accuracy, Frequency frequency, Location location) throws CantUpdateIntraUserIdentityException {

        try
        {
            intraWalletUserIdentityManager.updateIntraUserIdentity(identityPublicKey, identityAlias, phrase, profileImage, accuracy, frequency, location);


        } catch (CantUpdateIdentityException e) {
            throw new CantUpdateIntraUserIdentityException(CantUpdateIntraUserIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantUpdateIntraUserIdentityException(CantUpdateIntraUserIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }
    }

    @Override
    public void deleteIntraUserIdentity(String identityPublicKey) throws CantDeleteIntraUserIdentityException {
        try
        {
            intraWalletUserIdentityManager.deleteIntraUserIdentity(identityPublicKey);
        } catch (CantDeleteIdentityException e) {
            throw new CantDeleteIntraUserIdentityException(CantDeleteIntraUserIdentityException.DEFAULT_MESSAGE,e,"","Identity plugin error");
        }
        catch (Exception e) {
            throw new CantDeleteIntraUserIdentityException(CantDeleteIntraUserIdentityException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","Unknown error");
        }
    }



    /**
     * SettingsManager implementation.
     * */



//    private SettingsManager<IntraUserIdentitySettings> settingsManager;
//
//    @Override
//    public SettingsManager<IntraUserIdentitySettings> getSettingsManager() {
//        System.out.println("IntraUser module settings manager: "+settingsManager);
//        if (this.settingsManager != null)
//            return this.settingsManager;
//
//        this.settingsManager = new SettingsManager<>(
//                pluginFileSystem,
//                pluginId
//        );
//
//        System.out.println("IntraUser module settings manager is loaded: "+settingsManager.toString());
//
//        return this.settingsManager;
//    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        ActiveActorIdentityInformation activeActorIdentityInformation = null;
        try {
            ArrayList<IntraUserModuleIdentity> list = getAllIntraWalletUsersFromCurrentDeviceUser();
            if (list != null && !list.isEmpty())
                activeActorIdentityInformation = list.get(0);
        } catch (CantListIntraUsersIdentityException e) {
            e.printStackTrace();
        }
        return activeActorIdentityInformation;
    }


    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        createNewIntraWalletUser(name,phrase,profile_img, (long)100, Frequency.NORMAL,null);
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    @Override
    public Location getLocationManager() throws CantGetDeviceLocationException
    {
        return locationManager.getLocation();
    }

}
