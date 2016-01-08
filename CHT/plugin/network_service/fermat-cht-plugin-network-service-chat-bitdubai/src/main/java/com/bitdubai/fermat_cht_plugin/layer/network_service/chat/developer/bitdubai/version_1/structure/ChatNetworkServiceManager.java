package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetada;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.OutgoinChatMetaDataDao;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;

import java.sql.Timestamp;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public class ChatNetworkServiceManager implements ChatManager{

    private OutgoinChatMetaDataDao outgoingMetaDataDaossageDao;
    private CommunicationNetworkServiceLocal communicationNetworkServiceLocal;
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    public ChatNetworkServiceManager(OutgoinChatMetaDataDao outgoingMetaDataDaossageDao,CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager){
        this.outgoingMetaDataDaossageDao = outgoingMetaDataDaossageDao;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
    }
    @Override
    public void sendChatMetadata(Integer idChat, Integer idObjecto, String localActorType, String localActorPubKey, String remoteActorType, String remoteActorPubKey, String chatName, ChatMessageStatus chatStatus, Timestamp date, Integer idMessage, String message, DistributionStatus distributionStatus) throws CantSendChatMessageMetadataException {

        communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteActorPubKey);
        ChatMetadaTransactionRecord chatMetadaTransactionRecord = new ChatMetadaTransactionRecord();
        chatMetadaTransactionRecord.setIdChat(idChat);
        chatMetadaTransactionRecord.setIdObjecto(idObjecto);
        chatMetadaTransactionRecord.setLocalActorType(localActorType);
        chatMetadaTransactionRecord.setLocalActorPubKey(localActorPubKey);
        chatMetadaTransactionRecord.setRemoteActorType(remoteActorType);
        chatMetadaTransactionRecord.setRemoteActorPubKey(remoteActorPubKey);
        chatMetadaTransactionRecord.setChatName(chatName);
        chatMetadaTransactionRecord.setChatMessageStatus(chatStatus);
        chatMetadaTransactionRecord.setDate(date);
        chatMetadaTransactionRecord.setIdMessage(idMessage);
        chatMetadaTransactionRecord.setMessage(message);
        chatMetadaTransactionRecord.setDistributionStatus(distributionStatus);
        try {
          outgoingMetaDataDaossageDao.create(chatMetadaTransactionRecord);
            /*
             * If not null
             */
//            System.out.println("ChatPluginRoot - Sending message.....");
//            if (communicationNetworkServiceLocal != null) {
//
//                //Send the message
//                communicationNetworkServiceLocal.sendMessage(chatMetadaTransactionRecord.getLocalActorPubKey(), chatMetadaTransactionRecord.getRemoteActorPubKey(), chatMetadaTransactionRecord.getMessage());
//
//            } else {
//
//                /*
//                 * Created the message
//                 */
//                FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(chatMetadaTransactionRecord.getLocalActorPubKey(),//Sender
//                        chatMetadaTransactionRecord.getRemoteActorPubKey(), //Receiver
//                        chatMetadaTransactionRecord.getMessage(), //Message Content
//                        FermatMessageContentType.TEXT);//Type
//                /*
//                 * Configure the correct status
//                 */
//                ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);
//
//                /*
//                 * Save to the data base table
//                 */
//                OutgoingMessageDao outgoingMessageDao = communicationNetworkServiceConnectionManager.getOutgoingMessageDao();
//                outgoingMessageDao.create(fermatMessage);
//
//                /*
//                 * Create the sender basic profile
//                 */
//                PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(actorSender.getActorPublicKey(), NetworkServiceType.UNDEFINED, senderType);
//
//                /*
//                 * Create the receiver basic profile
//                 */
//                PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(actorReceiver.getActorPublicKey(), NetworkServiceType.UNDEFINED, receiverType);
//
//                /*
//                 * Ask the client to connect
//                 */
//                communicationNetworkServiceConnectionManager.connectTo(sender, platformComponentProfile, receiver);
//            }
//            System.out.println("AssetTransmissionNetworkServicePluginRoot - Message sent.");
//        } catch (Exception e) {
//
//            StringBuilder contextBuffer = new StringBuilder();
//            contextBuffer.append("Plugin ID: " + pluginId);
//            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
//            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
//            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//            contextBuffer.append("errorManager: " + errorManager);
//            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//            contextBuffer.append("eventManager: " + eventManager);
//
//            String context = contextBuffer.toString();
//            String possibleCause = "Plugin was not registered";
//
//            CantSendDigitalAssetMetadataException pluginStartException = new CantSendDigitalAssetMetadataException(CantSendDigitalAssetMetadataException.DEFAULT_MESSAGE, e, context, possibleCause);
//
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, pluginStartException);
//
//            throw pluginStartException;
//        }
        }catch(CantInsertRecordDataBaseException e){
            throw new CantSendChatMessageMetadataException(e);
        }catch (Exception e){
            throw new CantSendChatMessageMetadataException(e);
        }

    }

    @Override
    public void sendChatMessageNewStatusNotification() throws CantSendChatMessageNewStatusNotificationException {

    }

    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {

    }

    @Override
    public List<Transaction<ChatMetada>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        return null;
    }
}
