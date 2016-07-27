package com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes;

import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.DeveloperPluginInterface;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>AbstractPluginDeveloper</code>
 * haves all the main functionality of a plugin developer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public abstract class AbstractPluginDeveloper implements DeveloperPluginInterface {

    private final ConcurrentHashMap<PluginVersionReference, AbstractPlugin> versions;

    private PluginDeveloperReference pluginDeveloperReference;

    private FermatContext fermatContext;

    /**
     * normal constructor with params.
     * assigns a developer to the plugin developer class
     *
     * @param pluginDeveloperReference a directly built developer reference.
     */
    public AbstractPluginDeveloper(PluginDeveloperReference pluginDeveloperReference) {

        this.pluginDeveloperReference = pluginDeveloperReference;

        this.versions = new ConcurrentHashMap<>();
    }

    public AbstractPluginDeveloper() {
        this.versions = new ConcurrentHashMap<>();
    }

    public AbstractPluginDeveloper(final PluginDeveloperReference pluginDeveloperReference, FermatContext fermatContext) {

        this.pluginDeveloperReference = pluginDeveloperReference;

        this.fermatContext = fermatContext;

        this.versions = new ConcurrentHashMap<>();
    }


    public Collection<AbstractPlugin> getVersions() {
        if (versions == null) {
            System.out.println(new StringBuilder().append("Versions null, pluginDeveloper code: ").append(pluginDeveloperReference.getPluginReference().getPlugin().getCode()).toString());
            return Collections.emptyList();
        }
        return versions.values();
    }

    /**
     * Throw the method <code>registerVersion</code> you can add new versions to the plugin developer.
     * Here we'll corroborate too that the version is not added twice.
     *
     * @param abstractPlugin plugin in-self.
     * @throws CantRegisterVersionException if something goes wrong.
     */
    public final void registerVersion(final AbstractPlugin abstractPlugin) throws CantRegisterVersionException {

        PluginVersionReference pluginVersionReference = abstractPlugin.getPluginVersionReference();

        pluginVersionReference.setPluginDeveloperReference(this.pluginDeveloperReference);

        if (versions.putIfAbsent(pluginVersionReference, abstractPlugin) != null)
            throw new CantRegisterVersionException(pluginVersionReference.toString3(), "Version already exists for this plugin developer.");

    }

    public final AbstractPlugin getPluginByVersion(final PluginVersionReference pluginVersionReference) throws VersionNotFoundException {
        if (versions.containsKey(pluginVersionReference)) {
            return versions.get(pluginVersionReference);
        } else {

            throw new VersionNotFoundException(pluginVersionReference.toString3(), "version not found in the specified plugin developer.");
        }
    }

    public final Object getPluginByVersionMati(String platformCode, String layerCode, String pluginsCode, String developerCode, String version) throws VersionNotFoundException {
        PluginVersionReference pluginVersionReference = null;
        try {
            pluginVersionReference = new PluginVersionReference(
                    Platforms.getByCode(platformCode),
                    Layers.getByCode(layerCode),
                    Plugins.getByCode(pluginsCode),
                    Developers.BITDUBAI,
                    new Version());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (versions.containsKey(pluginVersionReference)) {
                AbstractPlugin abstractPlugin = versions.get(pluginVersionReference);
                return abstractPlugin;
            } else {
                AbstractPlugin abstractPlugin = versions.values().iterator().next();
                return abstractPlugin;
            }
        } catch (Exception e) {
            //todo: mejorar esta captura de excepción
            System.err.println("Mejorar esta captura de excepción");
            throw new VersionNotFoundException(pluginVersionReference.toString3(), "version not found in the specified plugin developer.", e);
        }
    }


    public final ConcurrentHashMap<PluginVersionReference, AbstractPlugin> listVersions() {

        return versions;
    }

    @Override
    public List<PluginVersionReference> listVersionsMati() {
        List<PluginVersionReference> pluginVersionReferences = new ArrayList<>();
        if (versions != null) {
            if (!versions.isEmpty()) {
                Enumeration<PluginVersionReference> v = versions.keys();
                for (int i = 0; i < versions.size(); i++) {
                    if (v.hasMoreElements()) {
                        pluginVersionReferences.add(v.nextElement());
                    }
                }
//                for (PluginVersionReference pluginVersionReference = v.nextElement(); v.hasMoreElements(); ){
//                    pluginVersionReferences.add(pluginVersionReference);
//                }
            }
        } else {
            System.out.println(new StringBuilder().append("Versions null, pluginDeveloper code: ").append(pluginDeveloperReference.getPluginReference().getPlugin().getCode()).toString());
        }
        return pluginVersionReferences;
    }

    public final PluginDeveloperReference getPluginDeveloperReference() {
        return pluginDeveloperReference;
    }

    public FermatContext getFermatContext() {
        return fermatContext;
    }

    public void setFermatContext(Object fermatContext) {
        try {
            this.fermatContext = (FermatContext) fermatContext;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public abstract void start() throws CantStartPluginDeveloperException;

}
