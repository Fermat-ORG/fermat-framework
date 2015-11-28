package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NetworkServiceNegotiationTransmissio;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.NetworkServiceNegotiationTransmissionPluginRoot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 23.10.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class NetworkServiceNegotiationTransmissionPluginRootTest {

    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Test
    public void startRegistration(){
        System.out.println("\nPRUEBA DE REGISTRO....");
        try{
        new NetworkServiceNegotiationTransmissionPluginRoot().start();
        } catch (Exception e) {
            System.out.println("\nERROR EN PRUEBA DE REGISTRO: " +e);
        }
    }
}