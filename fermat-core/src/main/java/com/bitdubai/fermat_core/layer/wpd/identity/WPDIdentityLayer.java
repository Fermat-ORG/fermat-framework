package com.bitdubai.fermat_core.layer.wpd.identity;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.WPDIdentitySubsystem;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.wpd.identity.publisher.PublisherIdentitySubsystem;

/**
 * Created by Nerio on 02/10/15.
 */
public class WPDIdentityLayer implements PlatformLayer {

    private Plugin mPublisherIdentity;

    /**
     * Each layer is started and by that time has the chance to initialize its services.
     */
    @Override
    public void start() throws CantStartLayerException {
        mPublisherIdentity = getPlugin(new PublisherIdentitySubsystem());

    }

    private Plugin getPlugin(WPDIdentitySubsystem wpdIdentitySubsystem) throws CantStartLayerException {
        try {
            wpdIdentitySubsystem.start();
            return wpdIdentitySubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getPublisherIdentity() {
        return mPublisherIdentity;
    }

}
