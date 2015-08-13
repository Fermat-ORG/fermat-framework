package com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;

import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantSaveProfileImageException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantShowLoginIdentitiesException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantSolveRequestLaterException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantStartRequestException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CouldNotCreateIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.IntraUserCancellingFailedException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.IntraUserConectionDenegationFailedException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.IntraUserDisconnectingFailedException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserSearch;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;

import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * This plug-in provides the methods for the Intra Users sub app.
 * To manage Intra User information and intra users connections
 * Created by loui on 22/02/15.
 * Modified by Natalia Cortez on 11/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IntraUserModulePluginRoot implements  DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, DealsWithPluginDatabaseSystem, IntraUserModuleManager, Plugin, Service  {



    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;



    /**
     *DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * IntraUserModuleManager Interface implementation.
     */


    /**
     * That method is used to create a new intra user
     *
     * @param intraUserName the name of the intra user to create
     * @param profileImage  the profile image of the intra user to create
     * @return the login identity generated for the said intra user.
     * @throws CouldNotCreateIntraUserException
     */
    @Override
    public IntraUserLoginIdentity createIntraUser(String intraUserName, byte[] profileImage) throws CouldNotCreateIntraUserException {
        return null;
    }

    /**
     * That method let the current logged in intra user set its profile
     * picture.
     * @param image the profile picture to set
     * @throws CantSaveProfileImageException
     */
    @Override
    public void setNewProfileImage(byte[] image, String intraUserPublicKey) throws CantSaveProfileImageException {

    }


    /**
     * That method lists the login identities that can be used
     * to log in as an Intra User for the current Device User.
     *
     * @return the list of identities the current Device User can use to log in
     * @throws CantShowLoginIdentitiesException
     */
    @Override
    public List<IntraUserLoginIdentity> showAvailableLoginIdentities() throws CantShowLoginIdentitiesException {
        return null;
    }


    /**
     * That method let an intra user log in
     *
     * @param intraUserPublicKey the public key of the intra user to log in
     */
    @Override
    public void login(String intraUserPublicKey) {

    }

    /**
     * That method searches for intra users that the logged in
     * intra user could be interested to add.
     *
     * @return a list with information of intra users
     * @throws CantGetIntraUsersListException
     */
    @Override
    public List<IntraUserInformation> getSuggestionsToContact() throws CantGetIntraUsersListException {
        return null;
    }


    /**
     * That method gives us an interface to manage a search for a particular
     * intra user
     *
     * @return a searching interface
     */
    @Override
    public IntraUserSearch searchIntraUser() {
        return null;
    }

    /**
     * That method initialize the request of contact between
     * two intra users.
     *
     * @param intraUserToAddName      The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param profileImage            The profile image that the intra user has
     * @throws CantStartRequestException
     */

    @Override
    public void askIntraUserForAcceptance(String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantStartRequestException {

    }

    /**
     * That method takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserToAddName      The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param profileImage            The profile image that the intra user has
     * @throws CantAcceptRequestException
     */
    @Override
    public void acceptIntraUser(String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantAcceptRequestException {

    }

    /**
     * That method marks the user information to decide its
     * acceptance later.
     *
     * @param intraUserToAddName      The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param profileImage            The profile image that the intra user has
     * @throws CantSolveRequestLaterException
     */
    @Override
    public void decideAcceptanceLater(String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantSolveRequestLaterException {

    }

    /**
     * That method denies a conection request from other intra user
     *
     * @param intraUserToRejectPublicKey the public key of the user to deny its connection request
     * @throws IntraUserConectionDenegationFailedException
     */
    @Override
    public void denyConnection(String intraUserToRejectPublicKey) throws IntraUserConectionDenegationFailedException {

    }

    /**
     * That method disconnect an intra user from the list managed by this
     * plugin
     *
     * @param intraUserToDisconnectPublicKey the public key of the intra user to disconnect
     * @throws IntraUserDisconnectingFailedException
     */
    @Override
    public void disconnectIntraUSer(String intraUserToDisconnectPublicKey) throws IntraUserDisconnectingFailedException {

    }


    /**
     * That method cancels an intra user from the list managed by this
     * @param intraUserToCancelPublicKey
     * @throws IntraUserCancellingFailedException
     */
    @Override
    public void cancelIntraUser(String intraUserToCancelPublicKey) throws IntraUserCancellingFailedException {

    }


    /**
     * That method returns the list of all intra users registered by the
     * logged in intra user
     *
     * @return the list of intra users connected to the logged in intra user
     * @throws CantGetIntraUsersListException
     */
    @Override
    public List<IntraUserInformation> getAllIntraUsers() throws CantGetIntraUsersListException {
        return null;
    }


    /**
     * That method returns the list of intra users waiting to be accepted
     * or rejected by the logged in intra user
     *
     * @return the list of intra users waiting to be accepted or rejected by the  logged in intra user
     * @throws CantGetIntraUsersListException
     */
    @Override
    public List<IntraUserInformation> getIntraUsersWaitingYourAcceptance() throws CantGetIntraUsersListException {
        return null;
    }


    /**
     * That method list the intra users that haven't
     * answered to a sent connection request by the current logged in intra user.
     *
     * @return the list of intra users that haven't answered to a sent connection request by the current
     * logged in intra user.
     * @throws CantGetIntraUsersListException
     */
    @Override
    public List<IntraUserInformation> getIntraUsersWaitingTheirAcceptance() throws CantGetIntraUsersListException {
        return null;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */


    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleInformation");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleLoginIdentity");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleSearch");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * Modify by Manuel on 25/07/2015
         * I will wrap all this method within a try, I need to catch any generic java Exception
         */
        try{

            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (IntraUserModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IntraUserModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IntraUserModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IntraUserModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        }catch (Exception exception){
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: "+ IntraUserModulePluginRoot.newLoggingLevel ,"Check the cause");
            this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }

    }

    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return IntraUserModulePluginRoot.newLoggingLevel.get(correctedClass[0]);

        } catch (Exception exception){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }



    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        this.serviceStatus = ServiceStatus.STARTED;



    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }


    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }



}
