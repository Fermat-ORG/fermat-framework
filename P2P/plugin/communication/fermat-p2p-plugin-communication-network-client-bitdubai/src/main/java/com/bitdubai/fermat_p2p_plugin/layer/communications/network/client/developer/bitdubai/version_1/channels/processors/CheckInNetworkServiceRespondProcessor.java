package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.ProfileAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel;

import javax.websocket.Session;

/**
 * The Class <code>CheckInNetworkServiceRespondProcessor</code>
 * process all packages received the type <code>PackageType.CHECK_IN_NETWORK_SERVICE_RESPOND</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CheckInNetworkServiceRespondProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param communicationsNetworkClientChannel register
     */
    public CheckInNetworkServiceRespondProcessor(final CommunicationsNetworkClientChannel communicationsNetworkClientChannel) {
        super(
                communicationsNetworkClientChannel,
                PackageType.CHECK_IN_NETWORK_SERVICE_RESPOND
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

        if(checkInProfileMsjRespond.getStatus() == CheckInProfileMsjRespond.STATUS.SUCCESS){
            //raise event

            /* test resgiter actorProfile */
            ActorProfile actorProfile = new ActorProfile();
            actorProfile.setNsIdentityPublicKey("123456789321654987");
            actorProfile.setIdentityPublicKey("147");
            actorProfile.setName("Intra Actor");
            actorProfile.setAlias("Actor");
            actorProfile.setActorType("intra");
            actorProfile.setExtraData("extradata");

            try {
                getChannel().getNetworkClientCommunicationConnection().registerProfile(actorProfile);
            } catch (CantRegisterProfileException e) {
                e.printStackTrace();
            } catch (ProfileAlreadyRegisteredException e) {
                e.printStackTrace();
            }
            /*  test resgiter actorProfile  */

        }else{
            //there is some wrong
        }

    }

}
