package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetNodeCatalogMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetNodeCatalogMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.util.List;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.GetNodeCatalogProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class GetNodeCatalogProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(GetNodeCatalogProcessor.class));

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public GetNodeCatalogProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.GET_NODE_CATALOG_REQUEST);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);
        GetNodeCatalogMsjRespond getNodeCatalogMsjRespond = null;
        List<NodesCatalog> nodesCatalogList = null;

        try {

            GetNodeCatalogMsjRequest messageContent = GetNodeCatalogMsjRequest.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.OBJECT){


                nodesCatalogList = loadData(messageContent.getOffset(), messageContent.getMax());

                long count = getDaoFactory().getNodesCatalogDao().getAllCount();

                /*
                 * If all ok, respond whit success message
                 */
                getNodeCatalogMsjRespond = new GetNodeCatalogMsjRespond(GetNodeCatalogMsjRespond.STATUS.SUCCESS, GetNodeCatalogMsjRespond.STATUS.SUCCESS.toString(), nodesCatalogList, count);
                Package packageRespond = Package.createInstance(getNodeCatalogMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.GET_NODE_CATALOG_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getAsyncRemote().sendObject(packageRespond);

            }


        } catch (Exception exception){

            try {

                LOG.error(exception.getMessage());

                /*
                 * Respond whit fail message
                 */
                getNodeCatalogMsjRespond = new GetNodeCatalogMsjRespond(GetNodeCatalogMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), nodesCatalogList, new Long(0));
                Package packageRespond = Package.createInstance(getNodeCatalogMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.GET_NODE_CATALOG_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getAsyncRemote().sendObject(packageRespond);

            } catch (Exception e) {
                LOG.error(e.getMessage());
            }

        }

    }

    /**
     * Load the data from database
     *
     * @param offset
     * @param max
     * @return List<NodeProfile>
     */
    public List<NodesCatalog> loadData(Integer offset, Integer max) throws CantReadRecordDataBaseException {

        List<NodesCatalog> nodesCatalogList = null;

        if (offset > 0 && max > 0){

            nodesCatalogList = getDaoFactory().getNodesCatalogDao().findAll(offset, max);

        }else {

            nodesCatalogList = getDaoFactory().getNodesCatalogDao().findAll();

        }

        return nodesCatalogList;

    }


}
