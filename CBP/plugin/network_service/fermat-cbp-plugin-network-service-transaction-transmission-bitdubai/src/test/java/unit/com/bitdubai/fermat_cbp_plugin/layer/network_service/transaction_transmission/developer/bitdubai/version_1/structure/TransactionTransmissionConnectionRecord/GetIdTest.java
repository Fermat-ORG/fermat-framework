package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.TransactionTransmissionConnectionRecord;

import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.TransactionTransmissionConnectionRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 21/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetIdTest {

    @Test
    public void getId() throws Exception {
        TransactionTransmissionConnectionRecord transactionTransmissionConnectionRecord = mock(TransactionTransmissionConnectionRecord.class);
        when(transactionTransmissionConnectionRecord.getId()).thenReturn(UUID.randomUUID());
        assertThat(transactionTransmissionConnectionRecord.getId()).isNotNull();
    }
}
