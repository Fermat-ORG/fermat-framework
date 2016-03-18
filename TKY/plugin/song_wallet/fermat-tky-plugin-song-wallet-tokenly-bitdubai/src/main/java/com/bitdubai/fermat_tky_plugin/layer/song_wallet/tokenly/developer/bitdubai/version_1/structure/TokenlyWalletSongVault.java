package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.DownloadSong;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDownloadSongException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantDownloadFileException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
     * Default values
     */
    /**
     * Represents the directory to storage the song.
     */
    private final String DIRECTORY_NAME = "TokenlySongs";
    /**
     * Represents the file privacy.
     * TODO: for testing, I'll put this as public file.
     */
    private final FilePrivacy FILE_PRIVACY = FilePrivacy.PUBLIC;
    /**
     * Represents the file life span.
     */
    private final FileLifeSpan FILE_LIFE_SPAN = FileLifeSpan.PERMANENT;

    /**
     * Constructor with parameters
     */
    public TokenlyWalletSongVault(
            PluginFileSystem pluginFileSystem,
            TokenlyApiManager tokenlyApiManager){
        this.pluginFileSystem = pluginFileSystem;
        this.tokenlyApiManager = tokenlyApiManager;
    }

    /**
     * This method downloads a song to the wallet and the device storage.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param tokenlyId
     * @throws CantDownloadSongException
     */
    public void downloadSong(String tokenlyId) throws CantDownloadSongException {
        try{
            //Get DownloadSOng object from Tokenly public API
            DownloadSong downloadSong = this.tokenlyApiManager.getDownloadSongBySongId(tokenlyId);
            String downloadUrl = downloadSong.getDownloadURL();
            String songName = downloadSong.getName();
            downloadFile(downloadUrl,songName);
        } catch (CantGetSongException e) {
            throw new CantDownloadSongException(
                    e,
                    "Downloading song with id: "+tokenlyId,
                    "Cannot get Download Song object from Tokenly public API");
        } catch (CantDownloadFileException e) {
            throw new CantDownloadSongException(
                    e,
                    "Downloading song with id: "+tokenlyId,
                    "Cannot download Song Tokenly Music manager");
        }
    }

    private void downloadFile(String downloadUrl, String songName) throws CantDownloadFileException{
        try{
            URL url = new URL(downloadUrl);
            URLConnection urlCon = url.openConnection();
            //Get web access.
            InputStream is = urlCon.getInputStream();
            //Prepare the plugin file system to persist the file
            PluginBinaryFile pluginBinaryFile = pluginFileSystem.createBinaryFile(
                    pluginId,
                    DIRECTORY_NAME,
                    songName,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            //Reading buffer.
            byte [] array = new byte[1000];
            //Put the inputStream into the array bytes.
            is.read(array);
            pluginBinaryFile.setContent(array);
            pluginBinaryFile.persistToMedia();
            //Close connection
            is.close();
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

}
