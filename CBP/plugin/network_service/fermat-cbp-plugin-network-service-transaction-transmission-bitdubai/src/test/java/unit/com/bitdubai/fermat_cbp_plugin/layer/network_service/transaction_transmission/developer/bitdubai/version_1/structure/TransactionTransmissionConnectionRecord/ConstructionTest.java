package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.TransactionTransmissionConnectionRecord;

import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.TransactionTransmissionConnectionRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 21/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private UUID id = UUID.randomUUID();

    private String actorPublicKey;

    private String ipkNetworkService;

    private String lastConnection;

    @Test
    public void Construction() throws Exception {
        TransactionTransmissionConnectionRecord transactionTransmissionConnectionRecord = new TransactionTransmissionConnectionRecord(
                this.id,
                this.actorPublicKey,
                this.ipkNetworkService,
                this.lastConnection
        );
        assertThat(transactionTransmissionConnectionRecord).isNotNull();
    }
}
