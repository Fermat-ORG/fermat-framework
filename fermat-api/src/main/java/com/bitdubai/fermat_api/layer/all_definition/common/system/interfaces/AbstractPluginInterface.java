package com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantAssignReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantCollectReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantListNeededReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.IncompatibleReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.LayerReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import java.util.List;
import java.util.UUID;

/**
 * Created by mati on 2016.06.26..
 */
public interface AbstractPluginInterface extends FermatManager, Plugin, Service {

    PluginVersionReference getPluginVersionReference();


    ServiceStatus getStatus();

    boolean isStarted();

    boolean isCreated();

    boolean isStopped();

    boolean isPaused();

    List<DevelopersUtilReference> getAvailableDeveloperUtils();

    FeatureForDevelopers getFeatureForDevelopers(DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException;

    void startPlugin() throws CantStartPluginException;

    FermatManager getManager();

    void start() throws CantStartPluginException;


    void pause();

    void resume();

    void stop();

    void setId(UUID pluginId);


    List<AddonVersionReference> getNeededAddons() throws CantListNeededReferencesException;

    List<PluginVersionReference> getNeededPlugins() throws CantListNeededReferencesException;

    List<PluginVersionReference> getNeededIndirectPlugins() throws CantListNeededReferencesException;

    void collectReferences() throws CantCollectReferencesException;

    void collectReferences(Class toCollectClass) throws CantCollectReferencesException;

    void assignAddonReference(AddonVersionReference avr,
                              FermatManager fermatManager) throws CantAssignReferenceException,
            IncompatibleReferenceException;

    void assignAddonReferenceMati(String platformCode, String layerCode, String addonCode, String developerCode, String version, Object fermatManager) throws CantAssignReferenceException, IncompatibleReferenceException;

    void assignPluginReference(AbstractPluginInterface abstractPlugin) throws CantAssignReferenceException,
            IncompatibleReferenceException;

    void assignPluginReference(PluginVersionReference pluginVersion,
                               FermatManager fermatManager) throws CantAssignReferenceException,
            IncompatibleReferenceException;

    void assignLayerReference(LayerReference layerReference,
                              FermatManager fermatManager) throws CantAssignReferenceException,
            IncompatibleReferenceException;

    ErrorManager getErrorManager();

    void reportError(UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception);
}
