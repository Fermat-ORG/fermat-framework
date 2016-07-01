package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_tky_api.all_definitions.enums.BroadcasterNotificationType;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDeleteSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDownloadSongException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CancelDownloadException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantDownloadFileException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/03/16.
 */
public class TokenlyWalletSongVault {

    /**
     * Represents the plugin file system.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Represents the plugin id.
     */
    UUID pluginId;

    /**
     * Represents the tokenlyApiManager.
     */
    TokenlyApiManager tokenlyApiManager;

    /**
     * Represents the broadcaster.
     */
    private Broadcaster broadcaster;

    /**
     * Default values
     */

    /**
     * Represents the directory to storage the song.
     */
    private final String DIRECTORY_NAME = "tokenly-wallet-song";
    /**
     * Represents the file privacy.
     * TODO: for testing, I'll put this as private file.
     */
    private final FilePrivacy FILE_PRIVACY = FilePrivacy.PRIVATE;

    /**
     * Represents the file life span.
     */
    private final FileLifeSpan FILE_LIFE_SPAN = FileLifeSpan.PERMANENT;

    /**
     * Represents the percentage step
     */
    private final int PERCENTAGE_STEP = 1;

    /**
     * Represents if the download must be canceled
     */
    private boolean CANCEL_DOWNLOAD = false;

    /**
     * Constructor with parameters
     */
    public TokenlyWalletSongVault(
            PluginFileSystem pluginFileSystem,
            TokenlyApiManager tokenlyApiManager,
            UUID pluginId,
            Broadcaster broadcaster){
        this.pluginFileSystem = pluginFileSystem;
        this.tokenlyApiManager = tokenlyApiManager;
        this.pluginId = pluginId;
        this.broadcaster = broadcaster;
    }

    /**
     * This method downloads a song to the wallet and the device storage.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param song
     * @throws CantDownloadSongException
     * @throws CancelDownloadException
     */
    public String downloadSong(Song song, UUID songId) throws
            CantDownloadSongException,
            CancelDownloadException {
        try{
            //Get DownloadSOng object from Tokenly public API
            String downloadUrl = song.getDownloadUrl();
            String songName = song.getName();
            downloadFile(
                    downloadUrl,
                    songName,
                    songId,
                    song);
            return DIRECTORY_NAME+"/"+songName.replace(" ","_");
        } catch (CantDownloadFileException e) {
            throw new CantDownloadSongException(
                    e,
                    "Downloading song with id: "+song.getId(),
                    "Cannot download Song Tokenly Music manager");
        }
    }

    /**
     * This method delete the song from device storage
     * @param songName
     * @throws CantDeleteSongException
     */
    public void deleteSong(String songName) throws CantDeleteSongException {
        try{
            //Remove spaces
            songName = songName.replace(" ","_");
            //Prepare the plugin file system to persist the file
            PluginBinaryFile pluginBinaryFile = pluginFileSystem.createBinaryFile(
                    pluginId,
                    DIRECTORY_NAME,
                    songName,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            pluginBinaryFile.delete();
        } catch (CantCreateFileException e) {
            throw new CantDeleteSongException(
                    e,
                    "Deleting song "+songName,
                    "Cannot create the file");
        } catch (FileNotFoundException e) {
            throw new CantDeleteSongException(
                    e,
                    "Deleting song "+songName,
                    "File not found");
        }
    }

    /**
     * This method contains the basic logic to download a file.
     * Is required to review the tokenly API to use the download URL.
     * @param downloadUrl
     * @param fileName
     * @param songId
     * @param song
     * @throws CantDownloadFileException
     * @throws CancelDownloadException
     */
    public void downloadFile(String downloadUrl, String fileName, UUID songId, Song song) throws
            CantDownloadFileException,
            CancelDownloadException {
        try{
            //TODO: study if this method will fix better in external API
            //Remove spaces
            fileName = fileName.replace(" ","_");
            URL url = new URL(downloadUrl);
            URLConnection urlCon = url.openConnection();
            //Get web access.
            InputStream is = urlCon.getInputStream();
            //Get file size
            int size = urlCon.getContentLength();
            System.out.println("TKY - Download size: "+size);
            //Calculate 1% to show in UI progress bar
            int percentStep = size/(PERCENTAGE_STEP*100);
            //Reading buffer.
            byte [] data = new byte[1024];
            //Put the inputStream into the array bytes.
            int bytesRead = is.read(data);
            //Set the cancel download flag in true
            CANCEL_DOWNLOAD=false;
            //File counter
            int reader = 0;
            List<Byte> byteList = new ArrayList<>();
            int downloadPercentage=0;
            int calculate;
            FermatBundle fermatBundle;
            while(bytesRead != -1) {
                if(CANCEL_DOWNLOAD){
                    fermatBundle = new FermatBundle();
                    fermatBundle.put(BroadcasterNotificationType.SONG_CANCEL.getCode(), "Download canceled by user request");
                    fermatBundle.put(BroadcasterNotificationType.SONG_ID.getCode(), songId);
                    fermatBundle.put(BroadcasterNotificationType.FAN_WALLET_BROADCAST_NOTIFICATION.getCode(),
                            BroadcasterNotificationType.FAN_WALLET_BROADCAST_NOTIFICATION.getCode());
                    broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle,"acá va el chanell en un futuro o reestructuralo para la pk de la app");
                    throw new CancelDownloadException();
                }
                for(byte byteRead : data){
                    byteList.add(byteRead);
                }
                reader+=bytesRead;
                calculate = reader/percentStep;
                if(calculate>downloadPercentage){
                    downloadPercentage=calculate;
                    fermatBundle = new FermatBundle();
                    fermatBundle.put(BroadcasterNotificationType.DOWNLOAD_PERCENTAGE.getCode(),
                            downloadPercentage+"%");
                    fermatBundle.put(BroadcasterNotificationType.SONG_ID.getCode(), songId);
                    fermatBundle.put(BroadcasterNotificationType.SONG_INFO.getCode(), song);
                    fermatBundle.put(BroadcasterNotificationType.FAN_WALLET_BROADCAST_NOTIFICATION.getCode(),
                            BroadcasterNotificationType.FAN_WALLET_BROADCAST_NOTIFICATION.getCode());
                    try {
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle,"acá va el chanell en un futuro o reestructuralo para la pk de la app");
                    }catch (Exception ex){
                        System.out.println("TKY_BROADCAST_PERCENTAGE:"+ex);
                    }

                }
                //System.out.println("TKY - Download "+reader+" from "+size);
                bytesRead = is.read(data);
            }
            int byteListSize = byteList.size();
            byte[] array = new byte[byteListSize];
            Object[] objectArray = byteList.toArray();
            int loopCounter = 0;
            for(Object object : objectArray){
                array[loopCounter] = Byte.parseByte(object.toString());
                loopCounter++;
            }
            //Prepare the plugin file system to persist the file
            PluginBinaryFile pluginBinaryFile = pluginFileSystem.createBinaryFile(
                    pluginId,
                    DIRECTORY_NAME,
                    fileName,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            pluginBinaryFile.setContent(array);
            pluginBinaryFile.persistToMedia();
            //Close connection
            is.close();
            //Notify that the process is finished
            fermatBundle = new FermatBundle();
            fermatBundle.put(BroadcasterNotificationType.DOWNLOAD_PERCENTAGE.getCode(), "100%");
            fermatBundle.put(BroadcasterNotificationType.SONG_ID.getCode(), songId);
            fermatBundle.put(BroadcasterNotificationType.SONG_INFO.getCode(), song);
            fermatBundle.put(BroadcasterNotificationType.FAN_WALLET_BROADCAST_NOTIFICATION.getCode(),
                    BroadcasterNotificationType.FAN_WALLET_BROADCAST_NOTIFICATION.getCode());
            try {
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle,"acá va el chanell en un futuro o reestructuralo para la pk de la app");
            }catch (Exception ex){
                System.out.println("TKY_BROADCAST_PERCENTAGE_COMPLETE:"+ex);
            }
            //Only for testing:
            //testReadFile(fileName);
        } catch (MalformedURLException e) {
            throw new CantDownloadFileException(
                    e,
                    "Downloading file from URL "+downloadUrl,
                    "Malformed URL");
        } catch (IOException e) {
            throw new CantDownloadFileException(
                    e,
                    "Downloading file from URL "+downloadUrl,
                    "IO error");
        } catch (CantPersistFileException e) {
            throw new CantDownloadFileException(
                    e,
                    "Downloading file from URL "+downloadUrl,
                    "Cannot persist the file");
        } catch (CantCreateFileException e) {
            throw new CantDownloadFileException(
                    e,
                    "Downloading file from URL "+downloadUrl,
                    "Cannot create the file");
        }
    }

    /**
     * This method sets the CANCEL_DOWNLOAD variable in true.
     */
    public void cancelDownload(){
        this.CANCEL_DOWNLOAD=true;
    }

    /**
     * This method returns a byte arrays from the dice storage that represents the song ready to be
     * played.
     * @param fileName
     * @return
     * @throws CantGetSongException
     */
    public byte[] getSongFromDeviceStorage(String fileName)throws CantGetSongException{
        try{
            //Remove spaces
            fileName = fileName.replace(" ","_");
            System.out.println("TKY: READ " + DIRECTORY_NAME + "/"+fileName);
            PluginBinaryFile pluginBinaryFile=pluginFileSystem.getBinaryFile(
                    pluginId,
                    DIRECTORY_NAME,
                    fileName,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            pluginBinaryFile.loadFromMedia();
            byte[] bytes = pluginBinaryFile.getContent();
            return bytes;
        } catch (FileNotFoundException e) {
            throw new CantGetSongException(
                    e,
                    "Getting file from path "+DIRECTORY_NAME + "/fileName",
                    "File not found");
        } catch (CantLoadFileException e) {
            throw new CantGetSongException(
                    e,
                    "Getting file from path "+DIRECTORY_NAME + "/fileName",
                    "Cannot load the file");
        } catch (CantCreateFileException e) {
            throw new CantGetSongException(
                    e,
                    "Getting file from path "+DIRECTORY_NAME + "/fileName",
                    "Cannot create the file");
        }
    }

    //Test method
    private void testReadFile(String fileName){
        try{
            System.out.println("TKY: READ " + DIRECTORY_NAME + "/fileName");
            PluginBinaryFile pluginBinaryFile=pluginFileSystem.getBinaryFile(
                    pluginId,
                    DIRECTORY_NAME,
                    fileName,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            pluginBinaryFile.loadFromMedia();
            byte[] bytes = pluginBinaryFile.getContent();
            System.out.println("TKY: CONTENT SIZE" + bytes.length);
            int loopCounter = 0;
            for(byte byteRead : bytes){
                System.out.println("TKY: "+loopCounter+" CONTENT "+byteRead);
                loopCounter++;
            }

        } catch (Exception e){
            System.out.println("TKY: Test Read exception");
            e.printStackTrace();
        }
    }

}
