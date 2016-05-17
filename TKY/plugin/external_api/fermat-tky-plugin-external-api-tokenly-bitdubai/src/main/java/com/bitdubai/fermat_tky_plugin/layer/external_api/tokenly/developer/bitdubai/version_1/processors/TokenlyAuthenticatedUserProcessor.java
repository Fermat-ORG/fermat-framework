package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors;

import com.bitdubai.fermat_tky_api.all_definitions.enums.HTTPErrorResponse;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.HTTPErrorResponseException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.RemoteJSonProcessor;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetUserException;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.music.TokenlyMusicUserJSonAttNames;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.UserRecord;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public abstract class TokenlyAuthenticatedUserProcessor extends AbstractTokenlyProcessor implements Serializable {

    /**
     * This method returns an user from Tokenly protected API.
     * @param url
     * @param parameters
     * @param username
     * @param password
     * @return
     * @throws CantGetUserException
     */
    public static User getAuthenticatedUser(
            String url,
            HashMap<String, String> parameters,
            String username,
            String password) throws
            CantGetUserException,
            WrongTokenlyUserCredentialsException {
        try{
            //Create String urlParameters
            String urlParameters = "username="+username+"&password="+password;
            JsonElement jsonElement = RemoteJSonProcessor.getJsonElementByPOSTCURLRequest(
                    url,
                    parameters,
                    urlParameters);
            User user = getAuthenticatedUserFromJsonObject(jsonElement.getAsJsonObject());
            return user;
        } catch (CantGetJSonObjectException e) {
            throw new CantGetUserException(
                    e,
                    "Authenticating the user "+username,
                    "Cannot get JSon from tokenly API using URL "+url);
        } catch (HTTPErrorResponseException e) {
            //We will try to determinate if the error is thrown by submit wrong credentials.
            int errorCode = e.getErrorCode();
            if(errorCode == TokenlyConfiguration.TOKENLY_WRONG_CREDENTIALS_HTTP_RESPONSE_CODE){
                throw new WrongTokenlyUserCredentialsException(
                        e,
                        "Authenticating the user "+username,
                        "Invalid credentials submitted, check the response - " +
                                "Code:"+errorCode+" - " +
                                "Message:"+e.getErrorMessage());
            }
            String errorMessage = e.getErrorMessage();
            HTTPErrorResponse httpErrorResponse = e.getHttpErrorResponse();
            throw new CantGetUserException(
                    e,
                    "\"Authenticating the user \"+username",
                    "Get an error response - " +
                            "Code:"+errorCode+" - " +
                            "Message:"+errorMessage,
                    errorMessage,
                    errorCode,
                    httpErrorResponse
                    );
        }
    }

    /**
     * This method returns a User object get by Protected Tokenly API
     * @param jsonObject
     * @return
     */
    private static User getAuthenticatedUserFromJsonObject(
            JsonObject jsonObject){
        //Id
        String id = getStringFromJsonObject(jsonObject, TokenlyMusicUserJSonAttNames.ID);
        //username
        String username = getStringFromJsonObject(jsonObject, TokenlyMusicUserJSonAttNames.USERNAME);
        //Email
        String email = getStringFromJsonObject(jsonObject, TokenlyMusicUserJSonAttNames.EMAIL);
        //Api token
        String apiToken = getStringFromJsonObject(jsonObject, TokenlyMusicUserJSonAttNames.API_TOKEN);
        //Api secret key
        String apiSecretKey = getStringFromJsonObject(
                jsonObject,
                TokenlyMusicUserJSonAttNames.API_SECRET_KEY);
        User user = new UserRecord(
                id,
                username,
                email,
                apiToken,
                apiSecretKey);
        return user;
    }

}
