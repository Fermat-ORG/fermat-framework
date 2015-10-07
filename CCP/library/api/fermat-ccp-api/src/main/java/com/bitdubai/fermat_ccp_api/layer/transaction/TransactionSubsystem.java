package com.bitdubai.fermat_ccp_api.layer.transaction;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface TransactionSubsystem {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();

}
