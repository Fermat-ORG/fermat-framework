package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel;

import java.util.concurrent.TimeUnit;

import javax.websocket.Session;

/**
 * The Class <code>CheckInActorRespondProcessor</code>
 * process all packages received the type <code>PackageType.CHECK_IN_ACTOR_RESPOND</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CheckInActorRespondProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param communicationsNetworkClientChannel register
     */
    public CheckInActorRespondProcessor(final CommunicationsNetworkClientChannel communicationsNetworkClientChannel) {
        super(
                communicationsNetworkClientChannel,
                PackageType.CHECK_IN_ACTOR_RESPOND
        );
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        CheckInProfileMsjRespond checkInProfileMsjRespond = CheckInProfileMsjRespond.parseContent(packageReceived.getContent());

        System.out.println(checkInProfileMsjRespond.toJson());

        if(checkInProfileMsjRespond.getStatus() == CheckInProfileMsjRespond.STATUS.SUCCESS){
            //raise event

            /* test unregister actor and networkservice */
//            try{
//
//                TimeUnit.SECONDS.sleep(2);
//
//                ActorProfile actorProfile = new ActorProfile();
//                actorProfile.setNsIdentityPublicKey("123456789321654987");
//                actorProfile.setIdentityPublicKey("147");
//                actorProfile.setName("Intra Actor");
//                actorProfile.setAlias("Actor");
//                actorProfile.setActorType("intra");
//                actorProfile.setExtraData("extradata");
//
//                getChannel().getNetworkClientCommunicationConnection().unregisterProfile(actorProfile);
//
//                TimeUnit.SECONDS.sleep(2);
//
//                NetworkServiceProfile ns = new NetworkServiceProfile();
//                ns.setClientIdentityPublicKey(getChannel().getNetworkClientCommunicationConnection().getClientProfile().getIdentityPublicKey());
//                ns.setNetworkServiceType(NetworkServiceType.INTRA_USER);
//                ns.setIdentityPublicKey("123456789321654987");
//
//                getChannel().getNetworkClientCommunicationConnection().unregisterProfile(ns);
//
//                TimeUnit.SECONDS.sleep(2);
//
//                getChannel().getNetworkClientCommunicationConnection().unregisterProfile(getChannel().getNetworkClientCommunicationConnection().getClientProfile());
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            /* test unregister actor and networkservice */

            /* test CheckInProfileDiscoveryQueryRequestProcessor */

           // DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters("intra","Actor",0.0,"extradata","147",null,0,"Intra Actor",null,0);
//            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(null,null,0.0,null,"123456789321654987",null,0,null,NetworkServiceType.INTRA_USER,0);
//            try {
//                getChannel().getNetworkClientCommunicationConnection().registeredProfileDiscoveryQuery(discoveryQueryParameters);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            /* test CheckInProfileDiscoveryQueryRequestProcessor */

        }else{
            //there is some wrong
        }

    }

}
