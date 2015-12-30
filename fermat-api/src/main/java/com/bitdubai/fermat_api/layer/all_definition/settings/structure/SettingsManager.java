package com.bitdubai.fermat_api.layer.all_definition.settings.structure;

import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantBuildSettingsObjectException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager</code>
 * contains all the basic functionality to manage a Fermat Settings.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public /*abstract */class SettingsManager<Z extends FermatSettings> {

    private static final String SETTINGS_DIRECTORY_NAME   = "settings";
    private static final String SETTINGS_FILE_NAME_PREFIX = "sFile_"  ;

    private final PluginFileSystem pluginFileSystem;
    private final UUID             pluginId        ;

    protected Z moduleSettings;

    public SettingsManager(final PluginFileSystem pluginFileSystem,
                           final UUID             pluginId        ) {

        this.pluginFileSystem = pluginFileSystem;
        this.pluginId         = pluginId        ;
    };

    /**
     * Through the method <code>persistSettings</code> we can persist the settings of the specific
     * wallet or sub-app in where we are working having in count the public its public key.
     *
     * @param publicKey  of the wallet or sub-app.
     * @param settings   instance of the settings of the specific sub-app or wallet.
     *
     * @throws CantPersistSettingsException if something goes wrong.
     */
    public final void persistSettings(final String publicKey,
                                      final Z      settings ) throws CantPersistSettingsException {

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

            moduleSettings = settings;

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

                moduleSettings = settings;

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
    public final Z loadAndGetSettings(final String publicKey) throws CantGetSettingsException  ,
                                                                     SettingsNotFoundException {

        if (moduleSettings != null)
            return moduleSettings;

        try {

            final PluginTextFile settingsFile = pluginFileSystem.getTextFile(
                    pluginId,
                    SETTINGS_DIRECTORY_NAME,
                    buildSettingsFileName(publicKey),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            final String identityFileContent = settingsFile.getContent();

            moduleSettings = (Z) XMLParser.parseXML(identityFileContent, moduleSettings);

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

    /**
     * Through the method <code>buildSettingsObject</code> you can build a new settings object.
     *
     * You must override this method to generate a settings object with the default behaviour.
     *
     * @param publicKey  of the wallet or sub-app.
     *
     * @return an instance of the settings object.
     *
     * @throws CantBuildSettingsObjectException if something goes wrong.
     */
    //public abstract Z buildSettingsObject(final String publicKey) throws CantBuildSettingsObjectException;

}
