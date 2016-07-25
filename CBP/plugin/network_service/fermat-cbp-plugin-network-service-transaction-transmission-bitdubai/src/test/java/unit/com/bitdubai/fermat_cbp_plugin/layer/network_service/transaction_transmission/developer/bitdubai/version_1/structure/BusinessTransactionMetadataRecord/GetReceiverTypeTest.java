package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionMetadataRecord;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionMetadataRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetReceiverTypeTest {


    private PlatformComponentType platformComponentType = PlatformComponentType.ACTOR_CRYPTO_BROKER;

    @Test
    public void getReceiverType() throws Exception {

        BusinessTransactionMetadataRecord businessTransactionMetadataRecord = mock(BusinessTransactionMetadataRecord.class);
        assertNotNull(businessTransactionMetadataRecord);
        when(businessTransactionMetadataRecord.getReceiverType()).thenReturn(platformComponentType);
        assertThat(businessTransactionMetadataRecord.getReceiverType()).isNotNull();
    }
}
