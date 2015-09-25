package com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet;

/**
 * Created by Natalia on 12/03/2015.
 */
/**
 * Used in combination with the `Wallet` class
 *
 */
public class Address
{
    private long balance;
    private String address;
    private String label;
    private long totalReceived;

    public Address(long balance, String address, String label, long totalReceived)
    {
        this.balance = balance;
        this.address = address;
        this.label = label;
        this.totalReceived = totalReceived;
    }

    /**
     * @return Balance in satoshi
     */
    public long getBalance()
    {
        return balance;
    }

    /**
     * @return String representation of the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @return Label attached to the address
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * @return Total received amount in satoshi
     */
    public long getTotalReceived()
    {
        return totalReceived;
    }
}
