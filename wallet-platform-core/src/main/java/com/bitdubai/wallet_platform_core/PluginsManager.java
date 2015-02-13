package com.bitdubai.wallet_platform_core;

import com.bitdubai.wallet_platform_api.CantInitializePluginsManagerException;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.PluginNotRecognizedException;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.*;
import com.bitdubai.wallet_platform_core.layer._7_crypto_network.bitcoin.BitcoinSubsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 02.02.15.
 */
public class PluginsManager {

    private PlatformFileSystem platformFileSystem;
    private final Integer AMOUNT_OF_KNOWN_PLUGINS = 6;
    private List<UUID> pluginIds = new ArrayList<>();


    public  PluginsManager (PlatformFileSystem platformFileSystem) throws CantInitializePluginsManagerException {

        this.platformFileSystem = platformFileSystem;

        PlatformDataFile platformDataFile;

        try
        {
            platformDataFile =  platformFileSystem.getFile("Platform", "PluginsIds", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            try
            {
                platformDataFile.loadToMemory();
            }
            catch (CantLoadFileException cantLoadFileException)
            {
                /**
                 * Until we implement some kind of backup of this critical file, there is no possible recovery from this 
                 * situation, 
                 */
                System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                cantLoadFileException.printStackTrace();
                throw new CantInitializePluginsManagerException();
            }

            String[] stringPluginIds = platformDataFile.getContent().split(";" , -1);

            Integer arrayPosition = 0;

            for (String stringPluginId : stringPluginIds ) {

                pluginIds.add(arrayPosition, UUID.fromString(stringPluginId));

                arrayPosition++;
            }

            if (arrayPosition < AMOUNT_OF_KNOWN_PLUGINS)
            {
                /**
                 * Under this condition, means that since the last time the platform start, new plugins were added to the
                 * platform, and as these new plugins needs new ids, we are going to create one for each of them. 
                 */

                for (int index = arrayPosition; index < AMOUNT_OF_KNOWN_PLUGINS; index++) {

                    UUID newId =  UUID.randomUUID();

                    pluginIds.add(index, newId);
                }

                try
                {
                    savePluginIds(platformDataFile);
                }
                catch (CantPersistFileException cantPersistFileException )
                {
                    /**
                     * If I cannot save this file, It means the Plugin Manager cannot start,
                     */
                    System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
                    cantPersistFileException.printStackTrace();
                    cantPersistFileException.getFileName();
                    throw new CantInitializePluginsManagerException();
                }

            }

        }
        catch (FileNotFoundException fileNotFoundException)
        {
            platformDataFile =  platformFileSystem.createFile("Platform", "PluginsIds", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);


            for (int arrayPosition = 0; arrayPosition < AMOUNT_OF_KNOWN_PLUGINS; arrayPosition++) {

                UUID newId =  UUID.randomUUID();

                pluginIds.add(arrayPosition, newId);
            }

            try
            {
                savePluginIds(platformDataFile);
            }
            catch (CantPersistFileException cantPersistFileException )
            {
                /**
                 * If I cannot save this file, It means the Plugin Manager cannot start,
                 */
                System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
                cantPersistFileException.printStackTrace();
                cantPersistFileException.getFileName();
                throw new CantInitializePluginsManagerException();
            }

        }

    }

    public UUID getPluginId(Plugin plugin) throws PluginNotRecognizedException{

        UUID pluginId;
        Integer pluginIndex = 0;

        if (pluginIndex == 0) {
            try
            {
                BitcoinSubsystem tryType;
                tryType = (BitcoinSubsystem) plugin;
                pluginIndex = 1;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex > 0) {
            return pluginIds.get(pluginIndex);
        }
        else
        {
            throw new PluginNotRecognizedException();
        }

    }



    private void savePluginIds(PlatformDataFile platformDataFile) throws CantPersistFileException{

        String fileContent = "";

        for (int arrayPosition = 0; arrayPosition < AMOUNT_OF_KNOWN_PLUGINS; arrayPosition++) {

            fileContent = fileContent + pluginIds.get(arrayPosition).toString() + ";";

        }

        platformDataFile.setContent(fileContent);

        try
        {
            platformDataFile.persistToMedia();// Natalia TODO: Debugear desde aca
        }
        catch (CantPersistFileException cantPersistFileException )
        {
            /**
             * If I cannot save this file, I cant handle the situation,
             */
            System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
            cantPersistFileException.printStackTrace();
            cantPersistFileException.getFileName();
            throw new CantPersistFileException(cantPersistFileException.getFileName());
        }


    }

}
