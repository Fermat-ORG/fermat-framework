package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetActorCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetActorCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.util.List;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.GetNodeCatalogTransactionsProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class GetActorCatalogTransactionsProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(GetActorCatalogTransactionsProcessor.class));

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public GetActorCatalogTransactionsProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.GET_ACTOR_CATALOG_TRANSACTIONS_REQUEST);
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
        GetActorCatalogTransactionsMsjRespond getActorCatalogTransactionsMsjRespond = null;
        List<ActorsCatalogTransaction> actorsCatalogTransactionList = null;

        try {

            GetActorCatalogTransactionsMsjRequest messageContent = GetActorCatalogTransactionsMsjRequest.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.OBJECT){


                actorsCatalogTransactionList = loadData(messageContent.getOffset(), messageContent.getMax());

                Long count = getDaoFactory().getActorsCatalogTransactionDao().getAllCount();

                /*
                 * If all ok, respond whit success message
                 */
                getActorCatalogTransactionsMsjRespond = new GetActorCatalogTransactionsMsjRespond(GetActorCatalogTransactionsMsjRespond.STATUS.SUCCESS, GetActorCatalogTransactionsMsjRespond.STATUS.SUCCESS.toString(), actorsCatalogTransactionList, count);
                Package packageRespond = Package.createInstance(getActorCatalogTransactionsMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.GET_ACTOR_CATALOG_TRANSACTIONS_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
                getActorCatalogTransactionsMsjRespond = new GetActorCatalogTransactionsMsjRespond(GetActorCatalogTransactionsMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), actorsCatalogTransactionList, new Long(0));
                Package packageRespond = Package.createInstance(getActorCatalogTransactionsMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.GET_ACTOR_CATALOG_TRANSACTIONS_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
    public List<ActorsCatalogTransaction> loadData(Integer offset, Integer max) throws CantReadRecordDataBaseException {

        LOG.info("offset = "+offset);
        LOG.info("max = "+max);

        List<ActorsCatalogTransaction> transactionList = null;

        if (offset > 0 && max > 0){

            transactionList = getDaoFactory().getActorsCatalogTransactionDao().findAll(offset, max);

        }else {

            transactionList = getDaoFactory().getActorsCatalogTransactionDao().findAll();

        }

        return transactionList;

    }
}
