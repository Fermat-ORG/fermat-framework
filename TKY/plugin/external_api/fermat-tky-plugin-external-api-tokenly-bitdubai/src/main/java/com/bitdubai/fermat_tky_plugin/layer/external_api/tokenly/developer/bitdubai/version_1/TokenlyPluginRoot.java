package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyAPIStatus;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyAPIStatus;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyRequestMethod;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.RemoteJSonProcessor;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Album;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.DownloadSong;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music.TokenlyMusicUserProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music.TokenlySongProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.UserRecord;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.structure.TokenlyAuthenticationComponentGenerator;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.structure.TokenlyManager;
import com.google.gson.JsonElement;

import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/03/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH, maintainerMail = "darkpriestrelative@gmail.com", createdBy = "darkestpriest", layer = Layers.EXTERNAL_API, platform = Platforms.TOKENLY, plugin = Plugins.TOKENLY_API)
public class TokenlyPluginRoot extends AbstractPlugin {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    /**
     * Represents the plugin manager.
     */
    TokenlyManager tokenlyManager;

    /**
     * Default constructor
     */
    public TokenlyPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    public FermatManager getManager() {
        return this.tokenlyManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.tokenlyManager = new TokenlyManager();
            //Test Method
            //testURL();
            //testManagerByBotId();
            //testManagerByUsername();
            //testGetAlbum();
            //testGetDownloadSong();
            //testCURLRequest();
            //getMusicUserTest();
            //signatureAuthenticateTest();
            //signatureAndGETTest();
            //getSongsTest();
            //getSongTest();
            //getTokenlyAPIStatusTest();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.TOKENLY_API,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e, "Cant start API Tokenly plugin.",
                    null);
        }
        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    private void testURL() {
        try {
            String URL = TokenlyConfiguration.URL_TOKENLY_SWAPBOT_API + "bot/17d47db1-6115-485d-bd62-bb965bb31867";
            String response = RemoteJSonProcessor.getJSonString(URL);
            System.out.println("WRD: Test response - " + response);
            JsonElement jsonElement = RemoteJSonProcessor.getJSonElement(URL);
            System.out.println(jsonElement);
        } catch (Exception e) {
            System.out.println("WRD: Test URL exception");
            e.printStackTrace();
        }
    }

    private void testManagerByBotId() {
        String id = "17d47db1-6115-485d-bd62-bb965bb31867";
        try {
            Bot bot = this.tokenlyManager.getBotByBotId(id);
            System.out.println("WRD: Test response - " + bot);
        } catch (Exception e) {
            System.out.println("WRD: Test URL exception");
            e.printStackTrace();
        }

    }

    private void testManagerByUsername() {
        String username = "tatiana";
        try {
            Bot bot = this.tokenlyManager.getBotBySwapbotUsername(username);
            System.out.println("TKY: Test response - " + bot);
        } catch (Exception e) {
            System.out.println("TKY: Test URL exception");
            e.printStackTrace();
        }

    }

    private void testGetAlbum(){
        try{
            Album[] albums = this.tokenlyManager.getAlbums();
            int counter=0;
            for(Album album : albums){
                System.out.println(counter+" - TKY: Test response - " + album);
                counter++;
            }
        } catch (Exception e) {
            System.out.println("TKY: Test URL exception");
            e.printStackTrace();
        }
    }

    private void testGetDownloadSong(){
        try{
            DownloadSong downloadSong = this.tokenlyManager.getDownloadSongBySongId("TestId");
            System.out.println("TKY: Test response - " + downloadSong);
            System.out.println("TKY: Test response URL - " + downloadSong.getDownloadURL());
        } catch (Exception e) {
            System.out.println("TKY: Test URL exception");
            e.printStackTrace();
        }
    }

    private void testCURLRequest(){
        try{
            //Test data
            String url = "https://music-stage.tokenly.com/api/v1/account/login";
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("curl", "-X");
            parameters.put("Content-Type", "application/x-www-form-urlencoded");
            parameters.put("Accept", "application/json");
            String urlParameters = "username=perezilla&password=milestone";
            JsonElement response = RemoteJSonProcessor.getJsonElementByPOSTCURLRequest(
                    url,
                    parameters,
                    urlParameters);
            System.out.println("TKY: Test cURL response - " + response);
        }catch (Exception e) {
            System.out.println("TKY: Test cURL exception");
            e.printStackTrace();
        }
    }

    private void getMusicUserTest(){
        try{
            MusicUser musicUser = TokenlyMusicUserProcessor.getAuthenticatedMusicUser(
                    "perezilla",
                    "milestone");
            System.out.println("TKY: Test Music User response - " + musicUser);
        }catch (Exception e) {
            System.out.println("TKY: Test Music User exception");
            e.printStackTrace();
        }
    }

    private void signatureAuthenticateTest(){
        try{
            //TODO: make a test with a real user (right now I don't have electric power)
            /*MusicUser musicUser = TokenlyMusicUserProcessor.getAuthenticatedMusicUser(
                    "perezilla",
                    "milestone");*/
            User user = new UserRecord(
                    "123",
                    "test",
                    "x@x.com",
                    "TWKTkwIQDTvirh6D",
                    "Kun2M2UladalYAeUvXyiKWhFuwrsmSreM841K45O");
            //X-Tokenly-Auth-Nonce
            long nonce = System.currentTimeMillis();
            String signature = TokenlyAuthenticationComponentGenerator.generateTokenlyAuthSignature(
                    user,
                    "https://www.example.com/api/v1/mystuff",
                    nonce,
                    TokenlyRequestMethod.GET);
            System.out.println("TKY: Test signature authenticate"+signature);
        }catch (Exception e) {
            System.out.println("TKY: Test signature authenticate exception");
            e.printStackTrace();
        }
    }

    private void signatureAndGETTest(){
        try{
            //Test data
            MusicUser musicUser = TokenlyMusicUserProcessor.getAuthenticatedMusicUser(
                    "username",
                    "password");
            //X-Tokenly-Auth-Nonce
            long nonce = TokenlyAuthenticationComponentGenerator.convertTimestamp(
                    System.currentTimeMillis());
            String url = "https://music-stage.tokenly.com/api/v1/music/mysongs";
            String signature = TokenlyAuthenticationComponentGenerator.generateTokenlyAuthSignature(
                    musicUser,
                    url,
                    nonce,
                    TokenlyRequestMethod.GET);
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("curl", "-X");
            parameters.put("Accept", "application/json");
            parameters.put("X-Tokenly-Auth-Api-Token", musicUser.getApiToken());
            parameters.put("X-Tokenly-Auth-Nonce", ""+nonce);
            parameters.put("X-Tokenly-Auth-Signature", signature);
            JsonElement response = RemoteJSonProcessor.getJsonElementByGETCURLRequest(
                    url,
                    parameters,
                    "");
            System.out.println("TKY: Test cURL response - " + response);
        }catch (Exception e) {
            System.out.println("TKY: Test signature authenticate and GET Test exception");
            e.printStackTrace();
        }
    }

    private void getSongsTest(){
        try{
            //Test data
            MusicUser musicUser = TokenlyMusicUserProcessor.getAuthenticatedMusicUser(
                    "username",
                    "password");
            Song[] songs = TokenlySongProcessor.getSongsByAuthenticatedUser(musicUser);
            int n=0;
            for(Song song : songs){
                System.out.println("TKY - Song "+n+": "+song);
                n++;
            }
        }catch (Exception e) {
            System.out.println("TKY: Test get songs from Tokenly exception");
            e.printStackTrace();
        }
    }

    private void getSongTest(){
        try{
            MusicUser musicUser = TokenlyMusicUserProcessor.getAuthenticatedMusicUser(
                    "username",
                    "password");
            Song song = TokenlySongProcessor.getSongByAuthenticatedUser(musicUser,
                    "3438022c-ec0f-4e57-abb4-8e0b2ea503e0");
            System.out.println("TKY - song: "+song);
        } catch (Exception e) {
            System.out.println("TKY: Test get song from Tokenly exception");
            e.printStackTrace();
        }
    }

    private void getTokenlyAPIStatusTest(){
        try{
            //Check Music API
            TokenlyAPIStatus tokenlyAPIStatus = this.tokenlyManager.getMusicAPIStatus();
            System.out.println("TKY: Test Music API status - "+tokenlyAPIStatus);
            tokenlyAPIStatus = this.tokenlyManager.getSwapBotAPIStatus();
            System.out.println("TKY: Test Swapbot API status - "+tokenlyAPIStatus);
        } catch (Exception e) {
            System.out.println("TKY: Test get API status exception:"+e);
            e.printStackTrace();
        }
    }

}

