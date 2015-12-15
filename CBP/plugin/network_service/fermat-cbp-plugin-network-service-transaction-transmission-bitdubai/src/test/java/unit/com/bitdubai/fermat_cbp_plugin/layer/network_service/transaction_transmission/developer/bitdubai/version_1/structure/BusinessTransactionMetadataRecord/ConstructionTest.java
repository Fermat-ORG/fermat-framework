package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionMetadataRecord;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionMetadataRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by root on 12/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private String contractHash     = new String();

    @Mock
    private ContractTransactionStatus contractTransactionStatus;

    private String receiverId       = new String();

    @Mock
    private PlatformComponentType receiverType;

    private String senderId         = new String();

    @Mock
    private PlatformComponentType senderType;

    private String contractId       = new String();

    private String negotiationId    = new String();

    @Mock
    private BusinessTransactionTransactionType transactionType;

    private Long timestamp          = new Long(1);

    @Mock
    private UUID transactionId;

    @Mock
    TransactionTransmissionStates transactionTransmissionStates;

    @Test
    public void Construction_ValidPArameters_NewObjectCreated(){

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
                this.transactionTransmissionStates
        );
        assertThat(businessTransactionMetadataRecord).isNotNull();
    }
}
