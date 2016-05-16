package com.basic_test;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestAction;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.messages.RequestMessage;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * The class <code>com.basic_test.MessagesTest</code>
 * is used for basic message conversion testing.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/10/2015.
 */
public class MessagesTest {

    @Test
    public void GetPublicKeyAreEquals(){
      /*  InformationMessage informationMessageTest = new InformationMessage(
                UUID.randomUUID(),
                RequestAction.INFORM_RECEPTION
        );

        RequestMessage requestMessageTest = new RequestMessage(
                UUID.randomUUID(),
                "identityPublicKey",
                Actors.INTRA_USER,
                "actorPublicKey",
                Actors.INTRA_USER,
                "description",
                new CryptoAddress(
                        "hola man",
                        CryptoCurrency.BITCOIN
                ),
                12,
                0,
                RequestAction.REQUEST,
                BlockchainNetworkType.PRODUCTION,
                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                "bitcoin_wallet"
        );

        List<NetworkServiceMessage> messages = new ArrayList<>();

        messages.add(informationMessageTest);
        messages.add(requestMessageTest);


        List<String> jsonMessages = new ArrayList<>();

        for (NetworkServiceMessage message : messages)
            jsonMessages.add(message.toJson());


        /// given jsonMessages list of messages from network service

        Gson gson = new Gson();

        for (String jsonMessage : jsonMessages) {

            NetworkServiceMessage networkServiceMessage = gson.fromJson(jsonMessage, NetworkServiceMessage.class);

            switch (networkServiceMessage.getMessageType()) {
                case INFORMATION:
                    InformationMessage informationMessage = gson.fromJson(jsonMessage, InformationMessage.class);

                    break;
                case REQUEST:
                    RequestMessage requestMessage = gson.fromJson(jsonMessage, RequestMessage.class);

                    break;
            }

        }*/
    }
}
