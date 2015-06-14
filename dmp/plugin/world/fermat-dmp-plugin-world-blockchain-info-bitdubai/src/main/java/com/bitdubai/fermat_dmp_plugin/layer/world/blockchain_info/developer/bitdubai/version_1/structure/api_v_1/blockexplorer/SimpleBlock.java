package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.blockexplorer;

import com.google.gson.JsonObject;

/**
 * Simple representation of a block
 *
 */
public class SimpleBlock
{
    private long height;
    private String hash;
    private long time;
    private boolean mainChain;

    public SimpleBlock(long height, String hash, long time, boolean mainChain)
    {
        this.height = height;
        this.hash = hash;
        this.time = time;
        this.mainChain = mainChain;
    }

    public SimpleBlock(JsonObject b)
    {
        this(	b.get("height").getAsLong(),
                b.get("hash").getAsString(),
                b.get("time").getAsLong(),
                b.get("main_chain").getAsBoolean());
    }

    /**
     * @return Block height
     */
    public long getHeight()
    {
        return height;
    }

    /**
     * @return Block hash
     */
    public String getHash()
    {
        return hash;
    }

    /**
     * @return Block timestamp set by the miner (unix time in seconds)
     */
    public long getTime()
    {
        return time;
    }

    /**
     * @return Whether the block is on the main chain
     */
    public boolean isMainChain()
    {
        return mainChain;
    }
}
