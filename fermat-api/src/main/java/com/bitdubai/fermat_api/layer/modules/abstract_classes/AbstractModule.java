package com.bitdubai.fermat_api.layer.modules.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.modules.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetModuleSettingsException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantPersistModuleSettingsException;
import com.bitdubai.fermat_api.layer.modules.exceptions.ModuleSettingsNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

/**
 * The class <code>com.bitdubai.fermat_api.layer.modules.abstract_classes.AbstractModule</code>
 * contains all the basic functionality of a Fermat Module.
 * A module is an intermediate point between the graphic user interface and the fermat system.
 * In which module we will provide all the needs of a sub-app or a wallet.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public abstract class AbstractModule<Z extends FermatSettings> extends AbstractPlugin implements ModuleManager {

    private static final String MODULE_SETTINGS_DIRECTORY_NAME   = "settings";
    private static final String MODULE_SETTINGS_FILE_NAME_PREFIX = "sFile_";

    protected Z moduleSettings;

    public AbstractModule(final PluginVersionReference pluginVersionReference) {

        super(pluginVersionReference);
    };

    /**
     * Through the method <code>persistSettings</code> we can persist the settings of the specific
     * wallet or sub-app in where we are working having in count the public its public key.
     *
     * @param publicKey  of the wallet or sub-app.
     * @param settings   instance of the settings of the specific sub-app or wallet.
     *
     * @throws CantPersistModuleSettingsException if something goes wrong.
     */
    public final void persistSettings(final String publicKey,
                                      final Z      settings ) throws CantPersistModuleSettingsException {

        try {

            final PluginTextFile settingsFile = getPluginFileSystem().getTextFile(pluginId, MODULE_SETTINGS_DIRECTORY_NAME, buildModuleSettingsFileName(publicKey), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            String settingsContent = XMLParser.parseObject(settings);

            settingsFile.setContent(settingsContent);

            settingsFile.persistToMedia();

            moduleSettings = settings;

        } catch(final CantCreateFileException |
                      CantPersistFileException e) {

            throw new CantPersistModuleSettingsException(e, "", "Cant persist settings file exception.");
        } catch(final FileNotFoundException e) {

            try {

                PluginTextFile pluginTextFile = getPluginFileSystem().createTextFile(pluginId, MODULE_SETTINGS_DIRECTORY_NAME, buildModuleSettingsFileName(publicKey), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                String settingsContent = XMLParser.parseObject(settings);

                pluginTextFile.setContent(settingsContent);
                pluginTextFile.persistToMedia();

                moduleSettings = settings;

            } catch (CantCreateFileException | CantPersistFileException z) {

                throw new CantPersistModuleSettingsException(z, "", "Cant create, persist or who knows what....");
            }
        }
    }

    /**
     * Through the method <code>getAndLoadSettingsFile</code> we can get the settings of the specific
     * wallet or sub-app in where we are working having in count the public its public key.
     *
     * @param publicKey  of the wallet or sub-app.
     *
     * @return an instance of the given fermat settings.
     *
     * @throws CantGetModuleSettingsException   if something goes wrong.
     * @throws ModuleSettingsNotFoundException  if we can't find a module settings for the given public key.
     */
    public final Z getAndLoadSettingsFile(final String publicKey) throws CantGetModuleSettingsException  ,
                                                                         ModuleSettingsNotFoundException {

        if (moduleSettings != null)
            return moduleSettings;

        try {

            final PluginTextFile settingsFile = getPluginFileSystem().getTextFile(pluginId, MODULE_SETTINGS_DIRECTORY_NAME, buildModuleSettingsFileName(publicKey), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            final String identityFileContent = settingsFile.getContent();

            moduleSettings = (Z) XMLParser.parseXML(identityFileContent, moduleSettings);

            return moduleSettings;

        } catch (CantCreateFileException e) {

            throw new CantGetModuleSettingsException(e, "", "Cant create module settings file exception.");
        } catch( FileNotFoundException e) {

            throw new ModuleSettingsNotFoundException(e, "", "Cant find module settings file exception.");
        }
    }

    private String buildModuleSettingsFileName(final String publicKey) {

        return MODULE_SETTINGS_FILE_NAME_PREFIX + "_" + publicKey;
    }

    protected abstract PluginFileSystem getPluginFileSystem();

}
