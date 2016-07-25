package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionMetadataRecord;

import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionMetadataRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 14/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetTransactionIdTest {

    private UUID transactionId = UUID.randomUUID();

    @Test
    public void getTransactionId() throws Exception {
        BusinessTransactionMetadataRecord businessTransactionMetadataRecord = mock(BusinessTransactionMetadataRecord.class);
        when(businessTransactionMetadataRecord.getTransactionId()).thenReturn(transactionId);
        assertThat(businessTransactionMetadataRecord.getTransactionId()).isNotNull();
        System.out.println("Test run");
    }

}
