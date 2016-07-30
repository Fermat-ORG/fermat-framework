package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_cht_api.layer.middleware.enums.ActionState;

import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/05/16.
 */
public interface ActionOnline {
    UUID getId();

    void setId(UUID actionId);

    String getPublicKey();

    void setPublicKey(String publicKey);

    ActionState getActionState();

    void setActionState(ActionState actionState);

    boolean getValue();

    void setValue(boolean value);

    boolean getLastOn();

    void setLastOn(boolean lastOn);

    String getLastConnection();

    void setLastConnection(String lastConnection);
}
