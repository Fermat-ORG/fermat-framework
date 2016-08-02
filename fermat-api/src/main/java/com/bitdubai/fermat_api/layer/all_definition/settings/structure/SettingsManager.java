package com.bitdubai.fermat_api.layer.all_definition.settings.structure;

import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager</code>
 * contains all the basic functionality to manage a Fermat Settings.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public /*abstract */class SettingsManager<Z extends FermatSettings> implements Serializable {

    private static final String SETTINGS_DIRECTORY_NAME   = "settings";
    private static final String SETTINGS_FILE_NAME_PREFIX = "sFile_"  ;

    private final PluginFileSystem pluginFileSystem;
    private final UUID             pluginId        ;

    protected Map<String, Z> moduleSettingsMap;

    public SettingsManager(final PluginFileSystem pluginFileSystem,
                           final UUID             pluginId        ) {

        this.pluginFileSystem = pluginFileSystem;
        this.pluginId         = pluginId        ;

        this.moduleSettingsMap = new ConcurrentHashMap<>();
    }

    /**
     * Through the method <code>persistSettings</code> we can persist the settings of the specific
     * wallet or sub-app in where we are working having in count the public its public key.
     *
     * @param publicKey  of the wallet or sub-app.
     * @param settings   instance of the settings of the specific sub-app or wallet.
     *
     * @throws CantPersistSettingsException if something goes wrong.
     */
    public final void persistSettings(      String publicKey,
                                      final Z      settings ) throws CantPersistSettingsException {


        if (publicKey == null)
            publicKey = "default";

        try {

            final PluginTextFile settingsFile = pluginFileSystem.getTextFile(
                    pluginId,
                    SETTINGS_DIRECTORY_NAME,
                    buildSettingsFileName(publicKey),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            String settingsContent = XMLParser.parseObject(settings);

            settingsFile.setContent(settingsContent);

            settingsFile.persistToMedia();

            moduleSettingsMap.put(publicKey, settings);

        } catch(final CantCreateFileException |
                      CantPersistFileException e) {

            throw new CantPersistSettingsException(e, "", "Cant persist settings file exception.");
        } catch(final FileNotFoundException e) {

            try {

                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(
                        pluginId,
                        SETTINGS_DIRECTORY_NAME,
                        buildSettingsFileName(publicKey),
                        FilePrivacy.PRIVATE,
                        FileLifeSpan.PERMANENT
                );

                String settingsContent = XMLParser.parseObject(settings);

                pluginTextFile.setContent(settingsContent);
                pluginTextFile.persistToMedia();

                moduleSettingsMap.put(publicKey, settings);

            } catch (CantCreateFileException | CantPersistFileException z) {

                throw new CantPersistSettingsException(z, "", "Cant create, persist or who knows what....");
            }
        }
    }

    /**
     * Through the method <code>loadAndGetSettings</code> we can get the settings of the specific
     * wallet or sub-app in where we are working having in count the public its public key.
     *
     * @param publicKey  of the wallet or sub-app.
     *
     * @return an instance of the given fermat settings.
     *
     * @throws CantGetSettingsException   if something goes wrong.
     * @throws SettingsNotFoundException  if we can't find a module settings for the given public key.
     */
    @SuppressWarnings("unchecked")
    public final Z loadAndGetSettings(String publicKey) throws CantGetSettingsException  ,
                                                                     SettingsNotFoundException {

        if (publicKey == null)
            publicKey = "default";

        if (moduleSettingsMap.get(publicKey) != null)
            return moduleSettingsMap.get(publicKey);

        try {

            final PluginTextFile settingsFile = pluginFileSystem.getTextFile(
                    pluginId,
                    SETTINGS_DIRECTORY_NAME,
                    buildSettingsFileName(publicKey),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            final String identityFileContent = settingsFile.getContent();

            Z moduleSettings = null;

            moduleSettings = (Z) XMLParser.parseXML(identityFileContent, moduleSettings);

            moduleSettingsMap.put(publicKey, moduleSettings);

            return moduleSettings;

        } catch (CantCreateFileException e) {
            throw new CantGetSettingsException(e, "", "Cant create module settings file exception.");
        } catch( FileNotFoundException e) {
           throw new SettingsNotFoundException(e, "", "Cant find module settings file exception.");

        }

    }

    private String buildSettingsFileName(final String publicKey) {
        return SETTINGS_FILE_NAME_PREFIX + "_" + publicKey;
    }

    @Override
    public String toString() {
        return "SettingsManager{" +
                "pluginId=" + pluginId +
                '}';
    }



}
