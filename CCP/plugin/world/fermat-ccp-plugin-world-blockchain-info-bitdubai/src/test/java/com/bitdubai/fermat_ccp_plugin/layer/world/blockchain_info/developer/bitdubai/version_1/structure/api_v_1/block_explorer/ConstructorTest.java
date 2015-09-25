package com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.block_explorer;

import com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.blockexplorer.BlockExplorer;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by leon on 5/6/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructorTest extends TestCase {

    String apiKey;

    @Before
    public void setUp(){
        apiKey = "91c646ef-c3fd-4dd0-9dc9-eba5c5600549";
    }

    @Test
    public void testConstructor_NotNull() {
        BlockExplorer blockExplorer = new BlockExplorer(this.apiKey);
        assertNotNull(blockExplorer);
    }
}
