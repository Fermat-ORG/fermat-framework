package com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wrd_api.all_definitions.interfaces.RemoteJSonProcessor;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.Bot;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.structure.TokenlyManager;
import com.google.gson.JsonElement;

public class TokenlyPluginRoot extends AbstractPlugin {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    TokenlyManager tokenlyManager;

    /**
     * Default constructor
     */
    public TokenlyPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    public FermatManager getManager(){
        return this.tokenlyManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try{
            this.tokenlyManager = new TokenlyManager();
            //Test Method
            //testURL();
            //testManagerByBotId();
            testManagerByUsername();
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.API_TOKENLY,
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

    private void testURL(){
        try{
            String URL= TokenlyConfiguration.URL_TOKENLY_SWAPBOT_API+"bot/17d47db1-6115-485d-bd62-bb965bb31867";
            String response=RemoteJSonProcessor.getJSonString(URL);
            System.out.println("WRD: Test response - " + response);
            JsonElement jsonElement=RemoteJSonProcessor.getJSonElement(URL);
            System.out.println(jsonElement);
        } catch (Exception e) {
            System.out.println("WRD: Test URL exception");
            e.printStackTrace();
        }
    }

    private void testManagerByBotId(){
        String id="17d47db1-6115-485d-bd62-bb965bb31867";
        try{
            Bot bot = this.tokenlyManager.getBotByBotId(id);
            System.out.println("WRD: Test response - " + bot);
        } catch (Exception e) {
            System.out.println("WRD: Test URL exception");
            e.printStackTrace();
        }

    }

    private void testManagerByUsername(){
        String username="tatiana";
        try{
            Bot bot = this.tokenlyManager.getBotBySwapbotUsername(username);
            System.out.println("WRD: Test response - " + bot);
        } catch (Exception e) {
            System.out.println("WRD: Test URL exception");
            e.printStackTrace();
        }

    }

}