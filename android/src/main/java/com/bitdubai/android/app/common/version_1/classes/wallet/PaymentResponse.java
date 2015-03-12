package com.bitdubai.android.app.common.version_1.classes.wallet;

/**
 * Created by Natalia on 12/03/2015.
 */
/**
        * Used in response to the `send` and `sendMany` methods in the `Wallet` class.
        *
        */
public class PaymentResponse
{
    private String message;
    private String txHash;
    private String notice;

    public PaymentResponse(String message, String txHash, String notice)
    {
        this.message = message;
        this.txHash = txHash;
        this.notice = notice;
    }

    /**
     * @return Response message from the server
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @return Transaction hash
     */
    public String getTxHash()
    {
        return txHash;
    }

    /**
     * @return Additional response message from the server
     */
    public String getNotice()
    {
        return notice;
    }

}
