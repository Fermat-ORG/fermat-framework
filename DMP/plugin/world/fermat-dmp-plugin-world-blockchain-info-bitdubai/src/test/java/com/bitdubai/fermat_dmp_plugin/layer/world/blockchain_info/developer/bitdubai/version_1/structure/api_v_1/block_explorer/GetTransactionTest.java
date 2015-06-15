package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.block_explorer;

import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.blockexplorer.BlockExplorer;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.blockexplorer.Transaction;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

/**
 * Created by leon on 5/6/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetTransactionTest extends TestCase {

    String apiKey;
    BlockExplorer blockExplorer;

    String validHash;
    String badHash;

    @Before
    public void setUp(){
        apiKey = "91c646ef-c3fd-4dd0-9dc9-eba5c5600549";
        blockExplorer = new BlockExplorer(this.apiKey);
        validHash = "1ecb8b764df0c7559f4a8c01b9332985a955a2757c8623b487aa18be066c9488";
        badHash = "walter";
    }

    @Test
    public void testGetTransaction_NotNull() {
        Transaction transaction = null;
        try {
            transaction = blockExplorer.getTransaction(validHash);
        } catch (Exception e){
            System.out.println(e);
        }
        assertNotNull(transaction);
    }

    @Test(expected=APIException.class)
    public void testGetTransaction_APIException() throws APIException {
        try {
            blockExplorer.getTransaction(badHash);
        } catch (IOException e){
            System.out.println(e);
        } catch (APIException e){
            throw new APIException(e.toString());
        }
    }
}
