package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces;

import java.util.UUID;

/**
 * Created by rodrigo on 8/13/15.
 */
public interface SkinLayout {
    public UUID getid();
    public String getFileName();

    public void setId(UUID id);
    public void setFileName(String filename);
}
