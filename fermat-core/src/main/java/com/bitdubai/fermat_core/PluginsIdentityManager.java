package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.CantInitializePluginsManagerException;
import com.bitdubai.fermat_api.PluginNotRecognizedException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
public class PluginsIdentityManager implements Serializable {

    private Map<Plugins, UUID> pluginIdsMap = new HashMap<>();

    public PluginsIdentityManager(PlatformFileSystem platformFileSystem) throws CantInitializePluginsManagerException {
        try {
            try {
                PlatformTextFile platformTextFile = platformFileSystem.getFile("Platform", "PluginsIds", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                String[] stringPluginIdPairs = platformTextFile.getContent().split("\\|");

                for (String stringPluginIdPair : stringPluginIdPairs) {
                    if (!stringPluginIdPair.equals("")) {
                        String[] pluginIdPair = stringPluginIdPair.split(";");
                        pluginIdsMap.put(Plugins.getByKey(pluginIdPair[0]), UUID.fromString(pluginIdPair[1]));
                    }
                }
            } catch (CantCreateFileException | InvalidParameterException e) {
                throw new CantInitializePluginsManagerException(CantInitializePluginsManagerException.DEFAULT_MESSAGE, e, "", "I don't know really what happened.");
            }
        } catch (FileNotFoundException fileNotFoundException) {
            try {
                PlatformTextFile platformTextFile = platformFileSystem.createFile("Platform", "PluginsIds", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                String fileContent = "";

                for (Plugins plugin : Plugins.values()) {
                    UUID newId = UUID.randomUUID();
                    fileContent = fileContent + plugin.getKey() + ";" + newId + "|";
                    pluginIdsMap.put(plugin, newId);
                }

                platformTextFile.setContent(fileContent);
                platformTextFile.persistToMedia();
            } catch (CantCreateFileException | CantPersistFileException e) {
                throw new CantInitializePluginsManagerException(CantInitializePluginsManagerException.DEFAULT_MESSAGE, e, "", "I don't know really what happened.");
            }
        }
    }

    public UUID getPluginId(Plugins descriptor) throws PluginNotRecognizedException {
        UUID pluginId = pluginIdsMap.get(descriptor);

        if (pluginId != null)
            return pluginId;
        else
            throw new PluginNotRecognizedException(PluginNotRecognizedException.DEFAULT_MESSAGE, null, "Plugin Descriptor: " + descriptor.getKey(), null);
    }
}