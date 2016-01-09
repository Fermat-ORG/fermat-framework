package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantConfirmNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantConstructNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantRegisterSendNegotiationTransmissionException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.12.15.
 */
public class NegotiationTransmissionManagerImpl implements NegotiationTransmissionManager{

    private NegotiationTransmissionNetworkServiceDatabaseDao negotiationTransmissionNetworkServiceDatabaseDao;
    
    
    public NegotiationTransmissionManagerImpl(
        NegotiationTransmissionNetworkServiceDatabaseDao negotiationTransmissionNetworkServiceDatabaseDao
    ){
        this.negotiationTransmissionNetworkServiceDatabaseDao = negotiationTransmissionNetworkServiceDatabaseDao;
    }

    @Override
    public void sendNegotiatioToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendNegotiationToCryptoCustomerException {

        try{

            PlatformComponentType actorSendType             = PlatformComponentType.ACTOR_CRYPTO_BROKER;
            NegotiationTransmissionType transmissionType    = NegotiationTransmissionType.TRANSMISSION_NEGOTIATION;
            NegotiationTransmissionState transmissionState  = NegotiationTransmissionState.PROCESSING_SEND;
            NegotiationTransmission negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            negotiationTransmissionNetworkServiceDatabaseDao.registerSendNegotiatioTransmission(negotiationTransmission, transmissionState);

            System.out.print("\n\n**** 1) MOCK NEGOTIATION TRANSMISSION SEND, MANAGER - SEND NEGOTIATION TO CRYPTO CUSTOMER ****\n");

        } catch (CantConstructNegotiationTransmissionException e){
            throw new CantSendNegotiationToCryptoCustomerException(CantSendNegotiationToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantSendNegotiationToCryptoCustomerException(CantSendNegotiationToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (Exception e){
            throw new CantSendNegotiationToCryptoCustomerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO CUSTOMER", "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER, UNKNOWN FAILURE.");
        }

    }

    @Override
    public void sendNegotiatioToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendNegotiationToCryptoBrokerException {

        try{

            PlatformComponentType           actorSendType           = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            NegotiationTransmissionType     transmissionType        = NegotiationTransmissionType.TRANSMISSION_NEGOTIATION;
            NegotiationTransmissionState    transmissionState       = NegotiationTransmissionState.PROCESSING_SEND;
            NegotiationTransmission         negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            negotiationTransmissionNetworkServiceDatabaseDao.registerSendNegotiatioTransmission(negotiationTransmission, transmissionState);

            System.out.print("\n\n**** 1) MOCK NEGOTIATION TRANSMISSION SEND, MANAGER - SEND NEGOTIATION TO CRYPTO BROKER ****\n");

        } catch (CantConstructNegotiationTransmissionException e){
            throw new CantSendNegotiationToCryptoBrokerException(CantSendNegotiationToCryptoBrokerException.DEFAULT_MESSAGE, e, "ERROR SEND NEGOTIATION TO CRYPTO BROKER", "");
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantSendNegotiationToCryptoBrokerException(CantSendNegotiationToCryptoBrokerException.DEFAULT_MESSAGE, e, "ERROR SEND NEGOTIATION TO CRYPTO BROKER", "");
        } catch (Exception e){
            throw new CantSendNegotiationToCryptoBrokerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND NEGOTIATION TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }

    }

    @Override
    public void sendConfirmNegotiatioToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendConfirmToCryptoCustomerException {

        try{

            PlatformComponentType           actorSendType           = PlatformComponentType.ACTOR_CRYPTO_BROKER;
            NegotiationTransmissionType     transmissionType        = NegotiationTransmissionType.TRANSMISSION_CONFIRM;
            NegotiationTransmissionState    transmissionState       = NegotiationTransmissionState.PROCESSING_SEND;
            NegotiationTransmission         negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            negotiationTransmissionNetworkServiceDatabaseDao.registerSendNegotiatioTransmission(negotiationTransmission, transmissionState);

        } catch (CantConstructNegotiationTransmissionException e){
            throw new CantSendConfirmToCryptoCustomerException(CantSendConfirmToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantSendConfirmToCryptoCustomerException(CantSendConfirmToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (Exception e){
            throw new CantSendConfirmToCryptoCustomerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO CUSTOMER", "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER, UNKNOWN FAILURE.");
        }

    }
    
    @Override
    public void sendConfirmNegotiatioToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendConfirmToCryptoBrokerException {

        try{

            PlatformComponentType           actorSendType           = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            NegotiationTransmissionType     transmissionType        = NegotiationTransmissionType.TRANSMISSION_CONFIRM;
            NegotiationTransmissionState    transmissionState       = NegotiationTransmissionState.PROCESSING_SEND;
            NegotiationTransmission         negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            negotiationTransmissionNetworkServiceDatabaseDao.registerSendNegotiatioTransmission(negotiationTransmission, transmissionState);

        } catch (CantConstructNegotiationTransmissionException e){
            throw new CantSendConfirmToCryptoBrokerException(CantSendConfirmToCryptoBrokerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO BROKER", "");
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantSendConfirmToCryptoBrokerException(CantSendConfirmToCryptoBrokerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO BROKER", "");
        } catch (Exception e){
            throw new CantSendConfirmToCryptoBrokerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND NEGOTIATION TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }

    }

    @Override
    public void confirmNegotiation(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantConfirmNegotiationException {

        try{

            PlatformComponentType           actorSendType           = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            NegotiationTransmissionType     transmissionType        = NegotiationTransmissionType.TRANSMISSION_CONFIRM;
            NegotiationTransmissionState    transmissionState       = NegotiationTransmissionState.PROCESSING_SEND;
            NegotiationTransmission         negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            negotiationTransmissionNetworkServiceDatabaseDao.registerSendNegotiatioTransmission(negotiationTransmission, transmissionState);

        } catch (CantConstructNegotiationTransmissionException e){
            throw new CantConfirmNegotiationException(CantConfirmNegotiationException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRM TO CRYPTO BROKER", "");
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantConfirmNegotiationException(CantConfirmNegotiationException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRM TO CRYPTO BROKER", "");
        } catch (Exception e){
            throw new CantConfirmNegotiationException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND CONFIRM TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }

    }

    @Override
    public void confirmReception(UUID transmissionId) throws CantConfirmTransactionException {
        try{
            negotiationTransmissionNetworkServiceDatabaseDao.confirmReception(transmissionId);
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantConfirmTransactionException("CAN'T CONFIRM THE RECEPTION OF NEGOTIATION TRANSMISSION", e, "ERROR SEND CONFIRM THE RECEPTION", "");
        } catch (Exception e){
            throw new CantConfirmTransactionException(e.getMessage(), FermatException.wrapException(e), "CAN'T CONFIRM THE RECEPTION OF NEGOTIATION TRANSMISSION", "ERROR SEND CONFIRM THE RECEPTION, UNKNOWN FAILURE.");
        }
    }

    @Override
    public List<Transaction<NegotiationTransmission>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        List<Transaction<NegotiationTransmission>> pendingTransaction=new ArrayList<>();
        try {

            List<NegotiationTransmission> negotiationTransmissionList = negotiationTransmissionNetworkServiceDatabaseDao.findAllByTransmissionState(NegotiationTransmissionState.PENDING_ACTION);
            if(!negotiationTransmissionList.isEmpty()){

                for(NegotiationTransmission negotiationTransmission : negotiationTransmissionList){
                    Transaction<NegotiationTransmission> transaction = new Transaction<>(
                            negotiationTransmission.getTransmissionId(),
                            negotiationTransmission,
                            Action.APPLY,
                            negotiationTransmission.getTimestamp());
                    pendingTransaction.add(transaction);
                }
            }
            return pendingTransaction;

        } catch (CantReadRecordDataBaseException e) {
            throw new CantDeliverPendingTransactionsException("CAN'T GET PENDING NOTIFICATIONS",e, "Negotiation Transmission network service", "database error");
        } catch (Exception e) {
            throw new CantDeliverPendingTransactionsException("CAN'T GET PENDING NOTIFICATIONS",e, "Negotiation Transmission network service", "database error");

        }
    }

    private NegotiationTransmission constructNegotiationTransmission(
            NegotiationTransaction negotiationTransaction,
            PlatformComponentType actorSendType,
            NegotiationTransactionType transactionType,
            NegotiationTransmissionType transmissionType
    ) throws CantConstructNegotiationTransmissionException{

        NegotiationTransmission negotiationTransmission = null;
        try{
            String                  publicKeyActorSend      = null;
            String                  publicKeyActorReceive   = null;
            PlatformComponentType   actorReceiveType        = null;
            Date time                    = new Date();

            UUID            transmissionId  = UUID.randomUUID();
            UUID            transactionId   = negotiationTransaction.getTransactionId();
            UUID            negotiationId   = negotiationTransaction.getTransactionId();
            NegotiationType negotiationType = negotiationTransaction.getNegotiationType();
            String          negotiationXML  = negotiationTransaction.getNegotiationXML();

            if(actorSendType == PlatformComponentType.ACTOR_CRYPTO_CUSTOMER){
                publicKeyActorSend      = negotiationTransaction.getPublicKeyCustomer();
                publicKeyActorReceive   = negotiationTransaction.getPublicKeyBroker();
                actorReceiveType        = PlatformComponentType.ACTOR_CRYPTO_BROKER;
            }else{
                publicKeyActorSend      = negotiationTransaction.getPublicKeyBroker();
                publicKeyActorReceive   = negotiationTransaction.getPublicKeyCustomer();
                actorReceiveType        = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            }

            long timestamp = time.getTime();

            NegotiationTransmissionState transmissionState = NegotiationTransmissionState.PROCESSING_SEND;

            negotiationTransmission = new NegotiationTransmissionImpl(
                    transmissionId,
                    transactionId,
                    negotiationId,
                    transactionType,
                    publicKeyActorSend,
                    actorSendType,
                    publicKeyActorReceive,
                    actorReceiveType,
                    transmissionType,
                    transmissionState,
                    negotiationType,
                    negotiationXML,
                    timestamp
            );
        } catch (Exception e) {
            throw new CantConstructNegotiationTransmissionException(e.getMessage(), FermatException.wrapException(e), "Network Service Negotiation Transmission", "Cant Construc Negotiation Transmission, unknown failure.");
        }
        return negotiationTransmission;

    }
}
