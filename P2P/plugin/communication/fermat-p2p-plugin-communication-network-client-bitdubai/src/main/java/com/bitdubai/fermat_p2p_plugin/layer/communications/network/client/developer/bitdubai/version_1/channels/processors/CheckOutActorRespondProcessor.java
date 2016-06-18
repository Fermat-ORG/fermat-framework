/*
* @#CheckOutActorRespondProcessor.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckOutProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;

import javax.websocket.Session;

/**
 * The Class <code>CheckOutActorRespondProcessor</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 28/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckOutActorRespondProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public CheckOutActorRespondProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel) {
        super(
                networkClientCommunicationChannel,
                PackageType.CHECK_OUT_ACTOR_RESPONSE
        );
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        CheckOutProfileMsjRespond checkOutProfileMsjRespond = CheckOutProfileMsjRespond.parseContent(packageReceived.getContent());

        System.out.println(checkOutProfileMsjRespond.toJson());

        if(checkOutProfileMsjRespond.getStatus() == CheckOutProfileMsjRespond.STATUS.SUCCESS){

        }else{
            //there is some wrong
        }

    }

}
