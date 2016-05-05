package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSaveWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



/**
 * Created by natalia on 21/07/15.
 */
public class WalletSettingsSettings implements WalletSettings {

    private final String WALLET_SETTINGS_FILE_NAME = "settings";
    private final String LANGUAGE_FILENAME = "language";
    private final String SKIN_FILENAME = "skin";
    private final String PREFERENCE_FILENAME = "preference";

    private final String WALLET_SETTIGS_DIRECTORY= "walletPrefencesSettings";

    private final int DEFAULT_POSITION = 0;

    private PluginFileSystem pluginFileSystem;
    private UUID pluginId;
    private ErrorManager errorManager;
    private PluginTextFile walletSettingsXml;
    private String walletPublicKey;


    /**
     * Constructor
     *
     */

    public WalletSettingsSettings(String walletPublicKey,PluginFileSystem pluginFileSystem, UUID pluginId,ErrorManager errorManager){

        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.walletPublicKey = walletPublicKey;

    }

    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException, CantLoadWalletSettings {
        List<UUID> lstUUID = new ArrayList<UUID>();
        return  ((List<UUID>) XMLParser.parseXML(getStringXML(LANGUAGE_FILENAME+"_"+walletPublicKey,WALLET_SETTIGS_DIRECTORY),lstUUID)).get(DEFAULT_POSITION);
    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException, CantLoadWalletSettings {
        List<UUID> lstUUID = new ArrayList<UUID>();
        return  ((List<UUID>) XMLParser.parseXML(getStringXML(SKIN_FILENAME,WALLET_SETTIGS_DIRECTORY),lstUUID)).get(DEFAULT_POSITION);
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException, CantLoadWalletSettings {
        List<UUID> lstUUID = new ArrayList<UUID>();
        lstUUID =(List<UUID>) XMLParser.parseXML(getStringXML(LANGUAGE_FILENAME+"_"+walletPublicKey,WALLET_SETTIGS_DIRECTORY),lstUUID);

        if(lstUUID.contains(languageId)){
            int position = lstUUID.indexOf(languageId);
            UUID aux = lstUUID.get(position);
            lstUUID.remove(position);
            lstUUID.add(DEFAULT_POSITION,aux);

        }
    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException, CantLoadWalletSettings {
        List<UUID> lstUUID = new ArrayList<UUID>();
        lstUUID =(List<UUID>) XMLParser.parseXML(getStringXML(SKIN_FILENAME,WALLET_SETTIGS_DIRECTORY),lstUUID);

        if(lstUUID.contains(skinId)){
            int position = lstUUID.indexOf(skinId);
            UUID aux = lstUUID.get(position);
            lstUUID.remove(position);
            lstUUID.add(DEFAULT_POSITION,aux);
        }
    }

    /**
     * This method let us set the preference settings for a wallet
     *
     * @param
     * @throws CantSetDefaultSkinException
     */
    /*@Override
    public void setPreferenceSettings(PreferenceWalletSettings preferenceWalletSettings) throws CantSaveWalletSettings {
        String xml = XMLParser.parseObject(preferenceWalletSettings);
        recordStringXML(xml,PREFERENCE_FILENAME+"_"+walletPublicKey,WALLET_SETTIGS_DIRECTORY);
    }*/
    private void recordStringXML(String xml,String filename,String directory) throws CantSaveWalletSettings {
        try {

            //PluginTextFile pluginTextFile= pluginFileSystem.getTextFile(pluginId,WALLET_SETTINGS_FILE_NAME+"_"+walletPublicKey,WALLET_SETTIGS_DIRECTORY,FilePrivacy.PUBLIC,FileLifeSpan.PERMANENT);
            PluginTextFile pluginTextFile= pluginFileSystem.getTextFile(pluginId,filename,directory,FilePrivacy.PUBLIC,FileLifeSpan.PERMANENT);

            pluginTextFile.setContent(xml);

            pluginTextFile.persistToMedia();

        } catch (FileNotFoundException e) {

            try {

                pluginFileSystem.createTextFile(pluginId, filename, directory, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            } catch (CantCreateFileException e1) {
                throw new CantSaveWalletSettings("CANT SAVE WALLET SETTINGS",e1,"cant create "+WALLET_SETTIGS_DIRECTORY,"");
            }

        } catch (CantCreateFileException e) {
            throw new CantSaveWalletSettings("CANT SAVE WALLET SETTINGS",e,"cant create "+WALLET_SETTIGS_DIRECTORY,"");
        } catch (CantPersistFileException e) {
            throw new CantSaveWalletSettings("CANT SAVE WALLET SETTINGS",e,"cant create "+WALLET_SETTIGS_DIRECTORY,"");
        }
    }
    private String getStringXML(String filename,String directory) throws CantLoadWalletSettings {
        try {

            PluginTextFile pluginTextFile= pluginFileSystem.getTextFile(pluginId,filename,directory,FilePrivacy.PUBLIC,FileLifeSpan.PERMANENT);

            pluginTextFile.loadFromMedia();

            return  pluginTextFile.getContent();

        } catch (FileNotFoundException e) {
            throw new CantLoadWalletSettings("CANT LOAD WALLET SETTINGS",e,"cant LOAD "+filename,"");
        } catch (CantCreateFileException e) {
            throw new CantLoadWalletSettings("CANT LOAD WALLET SETTINGS",e,"cant LOAD "+filename,"");
        } catch (CantLoadFileException e) {
            throw new CantLoadWalletSettings("CANT LOAD WALLET SETTINGS",e,"cant LOAD "+filename,"");        }
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {

    }

    /**
     * This method let us get the preference settings for a wallet
     *
     * @return preference settings of a wallet
     * @throws CantGetDefaultSkinException
     */
    /*@Override
    public String getPreferenceSettings(PreferenceWalletSettings preferenceWalletSettings) throws CantLoadWalletSettings {
       return (String)XMLParser.parseXML(getStringXML(WALLET_SETTINGS_FILE_NAME+"_"+walletPublicKey,WALLET_SETTIGS_DIRECTORY),preferenceWalletSettings);
    }*/

}