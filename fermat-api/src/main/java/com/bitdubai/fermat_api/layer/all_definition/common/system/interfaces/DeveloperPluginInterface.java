package com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matias Furszyfer on 2016.06.24..
 */
public interface DeveloperPluginInterface {

    Collection<AbstractPlugin> getVersions();

    /**
     * Throw the method <code>registerVersion</code> you can add new versions to the plugin developer.
     * Here we'll corroborate too that the version is not added twice.
     *
     * @param abstractPlugin plugin in-self.
     * @throws CantRegisterVersionException if something goes wrong.
     */
    void registerVersion(final AbstractPlugin abstractPlugin) throws CantRegisterVersionException;

    AbstractPlugin getPluginByVersion(final PluginVersionReference pluginVersionReference) throws VersionNotFoundException;

    ConcurrentHashMap<PluginVersionReference, AbstractPlugin> listVersions();

    PluginDeveloperReference getPluginDeveloperReference();

    void start() throws CantStartPluginDeveloperException;

    Object getPluginByVersionMati(String platformCode, String layerCode, String pluginsCode, String developerCode, String version) throws VersionNotFoundException;

    List<PluginVersionReference> listVersionsMati();

    void setFermatContext(Object fermatContext);
}
