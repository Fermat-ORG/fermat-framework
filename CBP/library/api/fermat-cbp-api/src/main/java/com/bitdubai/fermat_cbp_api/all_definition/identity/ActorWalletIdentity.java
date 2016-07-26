package com.bitdubai.fermat_cbp_api.all_definition.identity;

import java.util.List;
import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.09.18..
 */
public interface ActorWalletIdentity {

    ActorIdentity getIdentity();

    List<UUID> getListWallets();
}
