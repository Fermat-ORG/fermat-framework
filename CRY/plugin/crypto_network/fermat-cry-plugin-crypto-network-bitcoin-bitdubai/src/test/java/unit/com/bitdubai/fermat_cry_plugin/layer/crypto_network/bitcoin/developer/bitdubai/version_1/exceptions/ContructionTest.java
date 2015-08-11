package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantCreateBlockStoreFileException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantDisconnectFromNetworkException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantStartPeerServiceException;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Nerio on 23/07/15.
 */
public class ContructionTest {

    CantCreateBlockStoreFileException createBlockStoreFile;
    String context, possibleReason;
    @Test
    public void testInitialize_CantCreateBlockStoreFileException_notNull() throws CantCreateBlockStoreFileException {
        createBlockStoreFile = new CantCreateBlockStoreFileException("Test error", null,context,possibleReason);
        assertNotNull(createBlockStoreFile.getClass());
    }

    CantDisconnectFromNetworkException disconnectFromNetwork;
    @Test
    public void testInitialize_CantDisconnectFromNetworkException_notNull() throws CantDisconnectFromNetworkException {
        disconnectFromNetwork = new CantDisconnectFromNetworkException("Test error", null,context,possibleReason);
        assertNotNull(disconnectFromNetwork.getClass());
    }

    CantStartPeerServiceException startPeerService;
    @Test
    public void testInitialize_CantStartPeerServiceException_notNull() throws CantStartPeerServiceException {
        startPeerService = new CantStartPeerServiceException("Test error", null,context,possibleReason);
        assertNotNull(startPeerService.getClass());
    }
}
