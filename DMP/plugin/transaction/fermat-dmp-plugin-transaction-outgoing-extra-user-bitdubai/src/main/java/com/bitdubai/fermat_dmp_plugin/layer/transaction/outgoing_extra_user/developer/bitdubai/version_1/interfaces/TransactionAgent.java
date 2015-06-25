package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.interfaces;


import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantStartAgentException;

/**
 * Created by eze on 2015.06.25..
 */
public interface TransactionAgent {
    public void start () throws CantStartAgentException;
    public void stop();
}
