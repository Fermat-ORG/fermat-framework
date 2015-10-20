package com.bitdubai.fermat_ccp_core.test_classes;

import com.bitdubai.fermat_api.CantInitializePluginsManagerException;
import com.bitdubai.fermat_api.PluginNotRecognizedException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * The abstract class <code>com.bitdubai.fermat_ccp_core.test_classes.PluginIdsManager</code> haves all the main functionality
 * to manage fermat plugins ids.
 *
 * Created by Leon Acosta (laion.cj01@gmail.com) on 20/10/2015.
 */
public abstract class PluginIdsManager {

    private final String PLUGIN_IDS_DIRECTORY_NAME = DeviceDirectory.PLATFORM.getName();

    private final String PLUGIN_IDS_FILE_NAME = getPlatform().getCode()+"plugin_ids_";

    private final String PAIR_SEPARATOR   = ";";
    private final String PLUGIN_SEPARATOR = "|";

    private final PlatformFileSystem platformFileSystem;

    private Map<FermatPluginsEnum, UUID> pluginIdsMap = new HashMap<>();

    public PluginIdsManager(final PlatformFileSystem platformFileSystem) throws CantInitializePluginsManagerException {

        this.platformFileSystem = platformFileSystem;
        try {
            try {
                String[] stringPluginIdPairs = getPluginIdsFile().getContent().split(Pattern.quote(PLUGIN_SEPARATOR));
                boolean changed = false;

                for (String stringPluginIdPair : stringPluginIdPairs) {

                    if (!stringPluginIdPair.equals("")) {

                        String[] pluginIdPair = stringPluginIdPair.split(PAIR_SEPARATOR);

                        try {

                            pluginIdsMap.put(
                                    getPluginByKey(pluginIdPair[0]),
                                    UUID.fromString(pluginIdPair[1])
                            );
                        } catch (InvalidParameterException e) {

                            changed = true;
                        }
                    }
                }
                if (changed)
                    savePluginIdsFile(getPluginIdsFile());

            } catch (CantCreateFileException | CantPersistFileException e) {
                throw new CantInitializePluginsManagerException(e, "platform: "+getPlatform(), "Problem with plugins id file.");
            }
        } catch (FileNotFoundException fileNotFoundException) {
            try {
                PlatformTextFile platformTextFile = platformFileSystem.createFile(PLUGIN_IDS_DIRECTORY_NAME, PLUGIN_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                for (FermatPluginsEnum plugin : getAllPlugins())
                    pluginIdsMap.put(plugin, UUID.randomUUID());

                savePluginIdsFile(platformTextFile);

            } catch (CantCreateFileException | CantPersistFileException e) {
                throw new CantInitializePluginsManagerException(e, "platform: "+getPlatform(), "Problem with plugins id file.");
            }
        }
    }

    protected abstract FermatPluginsEnum getPluginByKey(String key) throws InvalidParameterException;

    protected abstract FermatPluginsEnum[] getAllPlugins();

    protected abstract Platforms getPlatform();

    /**
     * returns the id of the desired plugin.
     * if it doesn't exists, creates a new one and saves it in the plugins id file.
     *
     * @param plugin descriptor of the plugin of which you want to have the id.
     *
     * @return the id of the plugin
     *
     * @throws PluginNotRecognizedException if something goes wrong.
     */
    public UUID getPluginId(FermatPluginsEnum plugin) throws PluginNotRecognizedException {

        UUID pluginId = pluginIdsMap.get(plugin);

        if (pluginId != null)
            return pluginId;
        else
            return registerNewPlugin(plugin);
    }

    private UUID registerNewPlugin(final FermatPluginsEnum plugin) throws PluginNotRecognizedException {

        try {

            UUID newId = UUID.randomUUID();
            savePluginIdsFile(getPluginIdsFile());
            pluginIdsMap.put(plugin, newId);
            return newId;

        } catch(CantPersistFileException |
                CantCreateFileException  |
                FileNotFoundException    e) {

            throw new PluginNotRecognizedException(
                    "Plugin Descriptor: " + buildPluginKey(plugin),
                    "There is a problem with the plugins id file."
            );
        }
    }


    private PlatformTextFile getPluginIdsFile() throws CantCreateFileException,
                                                       FileNotFoundException  {

        return platformFileSystem.getFile(
                PLUGIN_IDS_DIRECTORY_NAME,
                PLUGIN_IDS_FILE_NAME     ,
                FilePrivacy.PRIVATE      ,
                FileLifeSpan.PERMANENT
        );
    }

    private void savePluginIdsFile(final PlatformTextFile platformTextFile) throws CantCreateFileException  ,
                                                                                   CantPersistFileException {

        String fileContent = "";
        for (Map.Entry<FermatPluginsEnum, UUID> plugin : pluginIdsMap.entrySet())
            fileContent = fileContent + buildPluginKey(plugin.getKey()) + PAIR_SEPARATOR + plugin.getValue() + PLUGIN_SEPARATOR;

        platformTextFile.setContent(fileContent);
        platformTextFile.persistToMedia();
    }

    private String buildPluginKey(final FermatPluginsEnum plugin) {

        return plugin.getPlatform() +
               plugin.getCode();
    }

}