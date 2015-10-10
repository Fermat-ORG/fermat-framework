package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSaveWalletSettings;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1.exceptions.CantLoadSettingsFileException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1.exceptions.InvalidWalletIdException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.UUID;



/**
 * Created by natalia on 21/07/15.
 */
public class WalletSettingsSettings implements WalletSettings {

    private final String WALLET_SETTINGS_FILE_NAME = "settings";

    private final String WALLET_SETTIGS_DIRECTORY= "walletPrefencesSettings";

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
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException {

        try {
            SAXBuilder builder = new SAXBuilder();

            /**
             * load file content
             */

            loadSettingsFile();

            /**
             * Get language settings on xml
             *
             * */

            Document document = (Document) builder.build(new StringReader(this.walletSettingsXml.getContent()));

            Element rootNode = document.getRootElement();

            /**
             * Check wallet id is equals to this wallet process
             */
            if (rootNode.getChildText(WalletSettingsConstants.WALLET_SETTINGS_XML_ID_NODE).equals(walletPublicKey)) {
                return UUID.fromString(rootNode.getChildText(WalletSettingsConstants.WALLET_SETTINGS_XML_LANGUAGE_NODE).toString());
            } else {
                //error invalid wallet id
                throw new InvalidWalletIdException(InvalidWalletIdException.DEFAULT_MESSAGE, null, "", "");
            }

        }
        catch(JDOMException|IOException e) {
            throw new CantGetDefaultLanguageException(CantGetDefaultLanguageException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        } catch (CantLoadSettingsFileException cantLoadSettingsFileException) {
            throw new CantGetDefaultLanguageException(CantGetDefaultLanguageException.DEFAULT_MESSAGE, cantLoadSettingsFileException, null, null);
        } catch(Exception exception){
            throw new CantGetDefaultLanguageException(CantGetDefaultLanguageException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException {
        try {
            SAXBuilder builder = new SAXBuilder();

            /**
             * load file content
             */

            loadSettingsFile();

            /**
             * Get language settings on xml
             */

            Document document = (Document) builder.build(new StringReader(this.walletSettingsXml.getContent()));

            Element rootNode = document.getRootElement();

            /**
             * Check wallet id is equals to this wallet process
             */
            if (rootNode.getChildText(WalletSettingsConstants.WALLET_SETTINGS_XML_ID_NODE).equals(walletPublicKey)) {
                return UUID.fromString(rootNode.getChildText(WalletSettingsConstants.WALLET_SETTINGS_XML_SKIN_NODE).toString());
            } else {
                //error invalid wallet id
                throw new InvalidWalletIdException(InvalidWalletIdException.DEFAULT_MESSAGE, null, "", "");

            }

        } catch (JDOMException | IOException e) {

            throw new CantGetDefaultSkinException(CantGetDefaultSkinException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);


        }
        catch (InvalidWalletIdException | CantLoadSettingsFileException e){
            throw new CantGetDefaultSkinException(CantGetDefaultSkinException.DEFAULT_MESSAGE, e, null, null);
        }
        catch(Exception exception){

            throw new CantGetDefaultSkinException(CantGetDefaultSkinException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }

    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException {
        try {
            SAXBuilder builder = new SAXBuilder();

            /**
             * load file content
             */

            loadSettingsFile();

            /**
             * Get language settings on xml
             */

            Document document = (Document) builder.build(new StringReader(this.walletSettingsXml.getContent()));

            Element rootNode = document.getRootElement();

            /**
             * Check wallet id is equals to this wallet process
             */
            if (rootNode.getChildText(WalletSettingsConstants.WALLET_SETTINGS_XML_ID_NODE).equals(walletPublicKey)) {
                rootNode.getChild(WalletSettingsConstants.WALLET_SETTINGS_XML_LANGUAGE_NODE).setText(languageId.toString());
            } else
            {
                throw new InvalidWalletIdException(InvalidWalletIdException.DEFAULT_MESSAGE,null,"","");
            }

            XMLOutputter xmOut = new XMLOutputter();

            walletSettingsXml.setContent(xmOut.outputString(document));

            /**
             * persist xml file
             */

            walletSettingsXml.persistToMedia();

        }
        catch(JDOMException|IOException e) {
            throw new CantSetDefaultLanguageException(CantSetDefaultLanguageException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
        catch (CantLoadSettingsFileException | InvalidWalletIdException | CantPersistFileException e) {
            throw new CantSetDefaultLanguageException(CantSetDefaultLanguageException.DEFAULT_MESSAGE, e, null, null);
        }
        catch(Exception exception){
            throw new CantSetDefaultLanguageException(CantSetDefaultLanguageException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException {
        try
        {
            SAXBuilder builder = new SAXBuilder();

            /**
             * load file content
             */

            loadSettingsFile();

            /**
             * Get language settings on xml
             */

            Document document = (Document) builder.build(new StringReader(this.walletSettingsXml.getContent()));

            Element rootNode = document.getRootElement();

            /**
             * Check wallet id is equals to this wallet process
             */
            if(rootNode.getChildText(WalletSettingsConstants.WALLET_SETTINGS_XML_ID_NODE).equals(walletPublicKey))
            {
                rootNode.getChild(WalletSettingsConstants.WALLET_SETTINGS_XML_SKIN_NODE).setText(skinId.toString());
            }else
            {
                //error invalid wallet id
                throw new InvalidWalletIdException(InvalidWalletIdException.DEFAULT_MESSAGE, null, "", "");

            }
            XMLOutputter xmOut=new XMLOutputter();

            walletSettingsXml.setContent(xmOut.outputString(document));

            /**
             * persist xml file
             */
            walletSettingsXml.persistToMedia();

        } catch(JDOMException|IOException e) {
            throw new CantSetDefaultSkinException(CantSetDefaultSkinException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        } catch (CantPersistFileException | CantLoadSettingsFileException | InvalidWalletIdException  e) {
            throw new CantSetDefaultSkinException(CantSetDefaultSkinException.DEFAULT_MESSAGE, e, null, null);
        } catch(Exception exception){
            throw new CantSetDefaultSkinException(CantSetDefaultSkinException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /**
     * This method let us set the preference settings for a wallet
     *
     * @param walletPreferenceSettings
     * @throws CantSetDefaultSkinException
     */
    @Override
    public void setPreferenceSettings(String walletPreferenceSettings,String walletPublicKey) throws CantSaveWalletSettings {
        String xml = XMLParser.parseObject(walletPreferenceSettings);

        try {

            PluginTextFile pluginTextFile= pluginFileSystem.getTextFile(pluginId,WALLET_SETTINGS_FILE_NAME+"_"+walletPublicKey,WALLET_SETTIGS_DIRECTORY,FilePrivacy.PUBLIC,FileLifeSpan.PERMANENT);

            pluginTextFile.setContent(xml);

            pluginTextFile.persistToMedia();

        } catch (FileNotFoundException e) {

            try {

                pluginFileSystem.createTextFile(pluginId, WALLET_SETTINGS_FILE_NAME, WALLET_SETTIGS_DIRECTORY, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            } catch (CantCreateFileException e1) {
                throw new CantSaveWalletSettings("CANT SAVE WALLET SETTINGS",e1,"cant create "+WALLET_SETTIGS_DIRECTORY,"");
            }

        } catch (CantCreateFileException e) {
            throw new CantSaveWalletSettings("CANT SAVE WALLET SETTINGS",e,"cant create "+WALLET_SETTIGS_DIRECTORY,"");
        } catch (CantPersistFileException e) {
            throw new CantSaveWalletSettings("CANT SAVE WALLET SETTINGS",e,"cant create "+WALLET_SETTIGS_DIRECTORY,"");
        }
    }

    /**
     * This method let us get the preference settings for a wallet
     *
     * @return preference settings of a wallet
     * @throws CantGetDefaultSkinException
     */
    @Override
    public String getPreferenceSettings(String walletPublicKey) throws CantLoadWalletSettings {

        try {

            PluginTextFile pluginTextFile= pluginFileSystem.getTextFile(pluginId,WALLET_SETTINGS_FILE_NAME+"_"+walletPublicKey,WALLET_SETTIGS_DIRECTORY,FilePrivacy.PUBLIC,FileLifeSpan.PERMANENT);

            return walletPublicKey;

        } catch (FileNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_SETTINGS_MIDDLEWARE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
        } catch (CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }


        throw new CantLoadWalletSettings("CANT LOAD WALLET SETTINGS",new Exception("cant load wallet settings"),"cannot get xml: "+walletPublicKey,"");
    }


    private void loadSettingsFile() throws CantLoadSettingsFileException {
        StringBuffer strXml = new StringBuffer();
        try
        {
            /**
             *  I check if the file containing  the wallets settings  already exists or not.
             * If not exists I created it.
             * * *
             */
            walletSettingsXml = pluginFileSystem.getTextFile(pluginId, walletPublicKey, WALLET_SETTINGS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            /**
             * Now I read the content of the file and place it in memory.
             */
            walletSettingsXml.loadFromMedia();


            //if context empty I create xml structure

            if(walletSettingsXml.getContent().equals("") ){
                /**
                 * make default xml structure
                 */
                strXml.append("<"+ WalletSettingsConstants.WALLET_SETTINGS_XML_ROOT +">");
                strXml.append("<"+ WalletSettingsConstants.WALLET_SETTINGS_XML_ID_NODE +">"+ walletPublicKey +"</"+ WalletSettingsConstants.WALLET_SETTINGS_XML_ID_NODE +">");
                strXml.append("<"+ WalletSettingsConstants.WALLET_SETTINGS_XML_LANGUAGE_NODE +"></"+ WalletSettingsConstants.WALLET_SETTINGS_XML_LANGUAGE_NODE +">");
                strXml.append("<"+ WalletSettingsConstants.WALLET_SETTINGS_XML_SKIN_NODE +"></"+ WalletSettingsConstants.WALLET_SETTINGS_XML_SKIN_NODE +">");
                strXml.append("</"+ WalletSettingsConstants.WALLET_SETTINGS_XML_ROOT +">");

                walletSettingsXml.setContent(strXml.toString());

                walletSettingsXml.persistToMedia();

            }
        } catch (FileNotFoundException fileNotFoundException) {
            /**
             * If the file did not exist it is not a problem. It only means this is the first time this plugin is running.
             *
             * I will create the file now, with an empty content so that when a new wallet is added we wont have to deal
             * with this file not existing again.
             * * * * *
             */

            try{
                walletSettingsXml = pluginFileSystem.createTextFile(pluginId, walletPublicKey, WALLET_SETTINGS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }
            catch (CantCreateFileException cantCreateFileException ) {
                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                throw new CantLoadSettingsFileException(CantLoadSettingsFileException.DEFAULT_MESSAGE, cantCreateFileException, null, null);
            }

            try {
                /**
                 * make default xml structure
                 */
                strXml.append("<"+ WalletSettingsConstants.WALLET_SETTINGS_XML_ROOT +">");
                strXml.append("<"+ WalletSettingsConstants.WALLET_SETTINGS_XML_ID_NODE +">"+ walletPublicKey +"</"+ WalletSettingsConstants.WALLET_SETTINGS_XML_ID_NODE +">");
                strXml.append("<"+ WalletSettingsConstants.WALLET_SETTINGS_XML_LANGUAGE_NODE +"></"+ WalletSettingsConstants.WALLET_SETTINGS_XML_LANGUAGE_NODE +">");
                strXml.append("<"+ WalletSettingsConstants.WALLET_SETTINGS_XML_SKIN_NODE +"></"+ WalletSettingsConstants.WALLET_SETTINGS_XML_SKIN_NODE +">");
                strXml.append("</"+ WalletSettingsConstants.WALLET_SETTINGS_XML_ROOT +">");

                walletSettingsXml.setContent(strXml.toString());

                walletSettingsXml.persistToMedia();
            }
            catch (CantPersistFileException cantPersistFileException ) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                throw new CantLoadSettingsFileException(CantLoadSettingsFileException.DEFAULT_MESSAGE, cantPersistFileException, null, null);
            }
        }
        catch (CantLoadFileException | CantCreateFileException e) {

            /**
             * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
             * to prevent the plug-in from running.
             *
             * In the future there should be implemented a method to deal with this situation.
             * * * *
             */
            throw new CantLoadSettingsFileException(CantLoadSettingsFileException.DEFAULT_MESSAGE, e, null, null);
        }
        catch(Exception ex)
        {
            throw new CantLoadSettingsFileException(CantLoadSettingsFileException.DEFAULT_MESSAGE, FermatException.wrapException(ex), null, null);
        }
    }
}