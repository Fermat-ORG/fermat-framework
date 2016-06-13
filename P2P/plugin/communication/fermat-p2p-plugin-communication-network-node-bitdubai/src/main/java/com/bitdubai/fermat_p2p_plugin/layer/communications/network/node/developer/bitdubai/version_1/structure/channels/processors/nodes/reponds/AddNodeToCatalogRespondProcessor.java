package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.reponds;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.AddNodeToCatalogMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConfigurationManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.HexadecimalConverter;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.reponds.AddNodeToCatalogRespondProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AddNodeToCatalogRespondProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(AddNodeToCatalogRespondProcessor.class));

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public AddNodeToCatalogRespondProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.ADD_NODE_TO_CATALOG_RESPONSE);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);

        try {

            AddNodeToCatalogMsjRespond messageContent = AddNodeToCatalogMsjRespond.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(messageContent.toJson(), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.OBJECT){

                if (messageContent.getStatus() == MsgRespond.STATUS.SUCCESS){

                    LOG.info("MsgRespond status "+messageContent.getStatus());
                    ConfigurationManager.updateValue(ConfigurationManager.REGISTERED_IN_CATALOG, String.valueOf(Boolean.TRUE));
                    ConfigurationManager.updateValue(ConfigurationManager.LAST_REGISTER_NODE_PROFILE, HexadecimalConverter.convertHexString(messageContent.getNodeProfileAdded().toJson().getBytes("UTF-8")));

                }else if (messageContent.getStatus() == MsgRespond.STATUS.FAIL) {

                    LOG.info("MsgRespond status "+messageContent.getStatus());
                    LOG.info("MsgRespond details "+messageContent.getDetails());

                    if (messageContent.getAlreadyExists()){
                        ConfigurationManager.updateValue(ConfigurationManager.REGISTERED_IN_CATALOG, String.valueOf(Boolean.TRUE));
                        ConfigurationManager.updateValue(ConfigurationManager.LAST_REGISTER_NODE_PROFILE, HexadecimalConverter.convertHexString(messageContent.getNodeProfileAdded().toJson().getBytes("UTF-8")));
                    }

                } else {

                    LOG.info("MsgRespond status "+messageContent.getStatus());
                    LOG.info("MsgRespond details "+messageContent.getDetails());
                }

                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Finish add node to catalog process"));

            }

        } catch (Exception exception){

            try {
                exception.printStackTrace();
                LOG.error(exception.getMessage());
                session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "Can't process respond: "+ exception.getMessage()));

            } catch (Exception e) {
                LOG.error(e.getMessage());
            }

        }

    }

}
