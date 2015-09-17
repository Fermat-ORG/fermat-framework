package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.CantInitializePluginsManagerException;
import com.bitdubai.fermat_api.PluginNotRecognizedException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ciencias on 02.02.15.
 * Modified by Leon Acosta (laion.cj01@gmail.com) on 28/08/2015.
 */
public class PluginsIdentityManager implements DealsWithPlatformFileSystem, Serializable {

    private static final String PLUGIN_IDS_DIRECTORY_NAME = "Platform";

    private static final String PLUGIN_IDS_FILE_NAME = "PluginsIds";

    private static final String PAIR_SEPARATOR = "|";

    private PlatformFileSystem platformFileSystem;

    private Map<Plugins, UUID> pluginIdsMap = new HashMap<>();

    public PluginsIdentityManager(PlatformFileSystem platformFileSystem) throws CantInitializePluginsManagerException {
        this.platformFileSystem = platformFileSystem;
        try {
            try {
                String[] stringPluginIdPairs = getPluginIdsFile().getContent().split("\\|");
                boolean changed = false;
                for (String stringPluginIdPair : stringPluginIdPairs) {
                    if (!stringPluginIdPair.equals("")) {
                        String[] pluginIdPair = stringPluginIdPair.split(";");
                        try {
                            pluginIdsMap.put(Plugins.getByKey(pluginIdPair[0]), UUID.fromString(pluginIdPair[1]));
                        } catch (InvalidParameterException e) {
                            changed = true;
                        }
                    }
                }
                if (changed)
                    savePluginIdsFile(getPluginIdsFile());

            } catch (CantCreateFileException | CantPersistFileException e) {
                e.printStackTrace();
                throw new CantInitializePluginsManagerException(CantInitializePluginsManagerException.DEFAULT_MESSAGE, e, "", "I don't know really what happened.");
            }
        } catch (FileNotFoundException fileNotFoundException) {
            try {
                PlatformTextFile platformTextFile = platformFileSystem.createFile(PLUGIN_IDS_DIRECTORY_NAME, PLUGIN_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                for (Plugins plugin : Plugins.values())
                    pluginIdsMap.put(plugin, UUID.randomUUID());

                savePluginIdsFile(platformTextFile);

            } catch (CantCreateFileException | CantPersistFileException e) {
                throw new CantInitializePluginsManagerException(CantInitializePluginsManagerException.DEFAULT_MESSAGE, e, "", "I don't know really what happened.");
            }
        }
    }

    /**
     * returns the id of the desired plugin.
     * if the id doesn't exists, creates a new one and saves it in the fil.
     *
     * @param plugin descriptor of the plugin of which you want to have the id.
     * @return the id of the plugin
     * @throws PluginNotRecognizedException if something goes wrong.
     */
    public UUID getPluginId(Plugins plugin) throws PluginNotRecognizedException {
        UUID pluginId = pluginIdsMap.get(plugin);

        if (pluginId != null) {
            return pluginId;
        } else {
            try {
                UUID newId = UUID.randomUUID();
                pluginIdsMap.put(plugin, newId);
                savePluginIdsFile(getPluginIdsFile());
                return newId;
            } catch (CantPersistFileException | CantCreateFileException | FileNotFoundException e) {
                throw new PluginNotRecognizedException(PluginNotRecognizedException.DEFAULT_MESSAGE, null, "Plugin Descriptor: " + plugin.getKey(), null);
            }
        }
    }

    private PlatformTextFile getPluginIdsFile() throws CantCreateFileException, FileNotFoundException {
        return platformFileSystem.getFile(PLUGIN_IDS_DIRECTORY_NAME, PLUGIN_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
    }

    private void savePluginIdsFile(PlatformTextFile platformTextFile) throws CantCreateFileException, CantPersistFileException {
        String fileContent = "";
        for (Map.Entry<Plugins, UUID> plugin : pluginIdsMap.entrySet())
            fileContent = fileContent + plugin.getKey().getKey() + ";" + plugin.getValue() + "|";

        platformTextFile.setContent(fileContent);
        platformTextFile.persistToMedia();
    }

    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
        this.platformFileSystem = platformFileSystem;
    }
}