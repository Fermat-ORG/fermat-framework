/*
 * @#Test.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.WsCommunicationsCloudClientPluginRoot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.ext.json.JsonConverter;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.Test</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Test {

    private static Client client = new Client(new Context(), Protocol.HTTP);

    public static void main(String [ ] args){

        String URL = WsCommunicationsCloudClientPluginRoot.HTTP_PROTOCOL + WsCommunicationsCloudClientPluginRoot.SERVER_IP + ":" + WsCommunicationsCloudClientPluginRoot.WEB_SERVICE_PORT + "/fermat/cloud-server/v1/components/registered/";

        List<PlatformComponentProfile> resultList = new ArrayList<>();

        try {

            /*
             * Construct a jsonObject whit the parameters
             */
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.NAME_IDENTITY, "09A3B707D1461B3B12C7CC626BCD7CF19EA8813B1B56A1B75E1C27335F8086C7ED588A7A06BCA67A289B73097FF67F5B1A0844FF2D550A6FCEFB66277EFDEB13A1");
            jsonObject.addProperty(JsonAttNamesConstants.DISCOVERY_PARAM, "{\"networkServiceType\":\"UNDEFINED\",\"platformComponentType\":\"ACTOR_INTRA_USER\"}");

            /*
             * Construct the web service client
             */
            Engine.getInstance().getRegisteredConverters().add(new JsonConverter());
            ClientResource clientResource = new ClientResource(WsCommunicationsCloudClientPluginRoot.HTTP_PROTOCOL + WsCommunicationsCloudClientPluginRoot.SERVER_IP + ":" + WsCommunicationsCloudClientPluginRoot.WEB_SERVICE_PORT + "/fermat/cloud-server/v1/components/registered/");
            clientResource.setNext(client);
            clientResource.setRequestEntityBuffering(true);
            clientResource.accept(MediaType.APPLICATION_JSON);

            /*
             * Construct the parameters JsonRepresentation
             */
            JsonRepresentation parameters = new JsonRepresentation(gson.toJson(jsonObject));

            /*
             * Do the request via post and obtain the result
             */
            Representation respond = clientResource.post(parameters);

            System.out.println("WsCommunicationsCloudClientConnection - respond:" + respond);

            InputStream in = respond.getStream();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            // int index = 0;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);

             /* for (Character character:line.toCharArray()) {
                    System.out.print(index + " -- character = " + character);
                    System.out.println("");
                    index++;
                } */

            }

            System.out.println("WsCommunicationsCloudClientConnection - jsonString length:" + stringBuilder.length());
            System.out.println("WsCommunicationsCloudClientConnection - Respond getSize:" + respond.getSize());
            System.out.println("WsCommunicationsCloudClientConnection - respond.getAvailableSize() :" + respond.getAvailableSize());

            /*
             * if respond have the result list
             */
            if (respond.getSize() > 39){

                /*
                 * Decode into a json object
                 */
                JsonParser parser = new JsonParser();
                JsonObject respondJsonObject = (JsonObject) parser.parse(stringBuilder.toString());

                //JsonObject respondJsonObject = (JsonObject) parser.parse(stringRepresentation.getText());

                 /*
                 * Get the receivedList
                 */
                resultList = gson.fromJson(respondJsonObject.get(JsonAttNamesConstants.RESULT_LIST).getAsString(), new TypeToken<List<PlatformComponentProfileCommunication>>() {
                }.getType());

                System.out.println("resultList.size() = " + resultList.size());

                for (PlatformComponentProfile componentProfile:resultList) {
                    System.out.println("componentProfile.getIdentityPublicKey() = " + componentProfile.getIdentityPublicKey());
                    System.out.println("componentProfile.getAlias() = " + componentProfile.getAlias());
                    System.out.println("componentProfile.getName() = " + componentProfile.getName());
                    System.out.println("componentProfile.getExtraData() = " + componentProfile.getExtraData().length());
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("\n");
                }

            }else {
                System.out.println("WsCommunicationsCloudClientConnection - Requested list is not available, resultList.size() = " + resultList.size());
            }

        }catch (Exception e){

            e.printStackTrace();

        }


    }
}
