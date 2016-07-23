package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionMetadataRecord;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionMetadataRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 12/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private String contractHash = new String();

    private ContractTransactionStatus contractTransactionStatus = ContractTransactionStatus.CONTRACT_COMPLETED;

    private String receiverId = new String();

    private PlatformComponentType receiverType = PlatformComponentType.ACTOR_CRYPTO_BROKER;

    private String senderId = new String();

    private PlatformComponentType senderType = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;

    private String contractId = new String();

    private String negotiationId = new String();

    private BusinessTransactionTransactionType transactionType = BusinessTransactionTransactionType.CONTRACT_STATUS_UPDATE;

    private Long timestamp = new Long(1);

    private UUID transactionId = UUID.randomUUID();

    TransactionTransmissionStates transactionTransmissionStates = TransactionTransmissionStates.SENT;

    @Test
    public void Construction_ValidPArameters_NewObjectCreated() {

        BusinessTransactionMetadataRecord businessTransactionMetadataRecord = new BusinessTransactionMetadataRecord(
                this.contractHash,
                this.contractTransactionStatus,
                this.receiverId,
                this.receiverType,
                this.senderId,
                this.senderType,
                this.contractId,
                this.negotiationId,
                this.transactionType,
                this.timestamp,
                this.transactionId,
                this.transactionTransmissionStates,
                Plugins.TRANSACTION_TRANSMISSION
        );
        assertThat(businessTransactionMetadataRecord).isNotNull();
    }
}
