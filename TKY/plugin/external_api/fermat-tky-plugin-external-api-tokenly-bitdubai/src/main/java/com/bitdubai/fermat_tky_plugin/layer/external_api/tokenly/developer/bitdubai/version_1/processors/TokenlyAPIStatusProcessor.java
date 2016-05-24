package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors;

import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyAPIStatus;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.HTTPErrorResponseException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TokenlyAPINotAvailableException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.RemoteJSonProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/04/16.
 */
public class TokenlyAPIStatusProcessor extends AbstractTokenlyProcessor {

    /**
     * This method checks if the Tokenly Music API is available.
     * @return
     * @throws TokenlyAPINotAvailableException
     */
    public static TokenlyAPIStatus getMusicAPIStatus() throws TokenlyAPINotAvailableException {

        return getAPIStatus(TokenlyConfiguration.URL_TOKENLY_MUSIC_ALL_ALBUMS_AVAILABLE);

    }

    /**
     * This method checks if the Tokenly Swapbot API is available.
     * @return
     * @throws TokenlyAPINotAvailableException
     */
    public static TokenlyAPIStatus getSwapBotAPIStatus() throws TokenlyAPINotAvailableException {

        return getAPIStatus(TokenlyConfiguration.URL_TOKENLY_SWAPBOT_DEVELOPER_TEAM);

    }

    /**
     * This method checks if the selected API is available.
     * The API is selected by the url passed in url parameter.
     * @return
     * @throws TokenlyAPINotAvailableException
     */
    private static TokenlyAPIStatus getAPIStatus(String url) throws TokenlyAPINotAvailableException {
        try{
            /**
             * We'll make a Tokenly API call, if we got an exception, the API will be UNDEFINED.
             * When we got a proper JsonElement, we got a AVAILABLE status.
             */
            RemoteJSonProcessor.getJsonElementByGETCURLRequest(
                    url,
                    null,
                    "");
            return TokenlyAPIStatus.AVAILABLE;
        } catch (CantGetJSonObjectException e) {
            //Cannot get the API status
            return TokenlyAPIStatus.UNDEFINED;
        } catch (HTTPErrorResponseException e) {
            //Here we got an API error, we gonna notify as an exception.
            throw new TokenlyAPINotAvailableException(e);
        }
    }

}
