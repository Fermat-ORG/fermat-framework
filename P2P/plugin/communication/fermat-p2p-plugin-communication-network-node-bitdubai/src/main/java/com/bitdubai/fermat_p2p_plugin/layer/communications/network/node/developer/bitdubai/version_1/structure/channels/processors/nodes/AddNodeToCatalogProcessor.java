package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.AddNodeToCatalogMsgRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.AddNodeToCatalogMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransactionsPendingForPropagation;

import org.jboss.logging.Logger;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.AddNodeToCatalogProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AddNodeToCatalogProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(AddNodeToCatalogProcessor.class.getName());

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public AddNodeToCatalogProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.ADD_NODE_TO_CATALOG_REQUEST);
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
        NodeProfile nodeProfile = null;
        AddNodeToCatalogMsjRespond addNodeToCatalogMsjRespond = null;

        try {

            AddNodeToCatalogMsgRequest messageContent = AddNodeToCatalogMsgRequest.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getNodeProfile()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON){

                /*
                 * Obtain the profile of the node
                 */
                nodeProfile = messageContent.getNodeProfile();


                if (exist(nodeProfile)){

                    /*
                     * Notify the node already exist
                     */
                    addNodeToCatalogMsjRespond = new AddNodeToCatalogMsjRespond(CheckInProfileMsjRespond.STATUS.FAIL, "The node profile already exist", nodeProfile.getIdentityPublicKey());

                }else {

                    /*
                     * Insert NodesCatalog into data base
                     */
                    insertNodesCatalog(nodeProfile);

                    /*
                     * Insert NodesCatalogTransaction into data base
                     */
                    insertNodesCatalogTransaction(nodeProfile);

                    /*
                     * Insert NodesCatalogTransactionsPendingForPropagation into data base
                     */
                    insertNodesCatalogTransactionsPendingForPropagation(nodeProfile);

                    /*
                     * If all ok, respond whit success message
                     */
                    addNodeToCatalogMsjRespond = new AddNodeToCatalogMsjRespond(CheckInProfileMsjRespond.STATUS.SUCCESS, CheckInProfileMsjRespond.STATUS.SUCCESS.toString(), nodeProfile.getIdentityPublicKey());

                }

                Package packageRespond = Package.createInstance(addNodeToCatalogMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.ADD_NODE_TO_CATALOG_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
                addNodeToCatalogMsjRespond = new AddNodeToCatalogMsjRespond(AddNodeToCatalogMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), nodeProfile.getIdentityPublicKey());
                Package packageRespond = Package.createInstance(addNodeToCatalogMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.ADD_NODE_TO_CATALOG_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
     * Validate if the node exist into the catalog
     *
     * @param nodeProfile
     * @return boolean
     */
    private boolean exist(NodeProfile nodeProfile) throws CantReadRecordDataBaseException, RecordNotFoundException {

        /*
         * Search in the data base
         */
        NodesCatalog nodesCatalog = getDaoFactory().getNodesCatalogDao().findById(nodeProfile.getIdentityPublicKey());

        if (nodesCatalog != null){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }

    /**
     * Create a new row into the data base
     *
     * @param nodeProfile
     * @throws CantInsertRecordDataBaseException
     */
    private void insertNodesCatalog(NodeProfile nodeProfile) throws CantInsertRecordDataBaseException {

        /*
         * Create the NodesCatalog
         */
        NodesCatalog nodeCatalog = new NodesCatalog();
        nodeCatalog.setIp(nodeProfile.getIp());
        nodeCatalog.setDefaultPort(nodeProfile.getDefaultPort());
        nodeCatalog.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
        nodeCatalog.setName(nodeProfile.getName());
        nodeCatalog.setOfflineCounter(0);

        //Validate if location are available
        if (nodeProfile.getLocation() != null){
            nodeCatalog.setLastLatitude(nodeProfile.getLocation().getLatitude());
            nodeCatalog.setLastLongitude(nodeProfile.getLocation().getLongitude());
        }

        /*
         * Save into the data base
         */
        getDaoFactory().getNodesCatalogDao().create(nodeCatalog);
    }

    /**
     * Create a new row into the data base
     *
     * @param nodeProfile
     * @throws CantInsertRecordDataBaseException
     */
    private void insertNodesCatalogTransaction(NodeProfile nodeProfile) throws CantInsertRecordDataBaseException {

        /*
         * Create the NodesCatalog
         */
        NodesCatalogTransaction transaction = new NodesCatalogTransaction();
        transaction.setIp(nodeProfile.getIp());
        transaction.setDefaultPort(nodeProfile.getDefaultPort());
        transaction.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
        transaction.setName(nodeProfile.getName());
        transaction.setTransactionType(NodesCatalogTransaction.ADD_TRANSACTION_TYPE);
        transaction.setHashId(transaction.getHashId());

        //Validate if location are available
        if (nodeProfile.getLocation() != null){
            transaction.setLastLatitude(nodeProfile.getLocation().getLatitude());
            transaction.setLastLongitude(nodeProfile.getLocation().getLongitude());
        }

        /*
         * Save into the data base
         */
        getDaoFactory().getNodesCatalogTransactionDao().create(transaction);
    }

    /**
     * Create a new row into the data base
     *
     * @param nodeProfile
     * @throws CantInsertRecordDataBaseException
     */
    private void insertNodesCatalogTransactionsPendingForPropagation(NodeProfile nodeProfile) throws CantInsertRecordDataBaseException {

        /*
         * Create the NodesCatalog
         */
        NodesCatalogTransactionsPendingForPropagation transaction = new NodesCatalogTransactionsPendingForPropagation();
        transaction.setIp(nodeProfile.getIp());
        transaction.setDefaultPort(nodeProfile.getDefaultPort());
        transaction.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
        transaction.setName(nodeProfile.getName());
        transaction.setTransactionType(NodesCatalogTransaction.ADD_TRANSACTION_TYPE);
        transaction.setHashId(transaction.getHashId());

        //Validate if location are available
        if (nodeProfile.getLocation() != null){
            transaction.setLastLatitude(nodeProfile.getLocation().getLatitude());
            transaction.setLastLongitude(nodeProfile.getLocation().getLongitude());
        }

        /*
         * Save into the data base
         */
        getDaoFactory().getNodesCatalogTransactionsPendingForPropagationDao().create(transaction);
    }

}
