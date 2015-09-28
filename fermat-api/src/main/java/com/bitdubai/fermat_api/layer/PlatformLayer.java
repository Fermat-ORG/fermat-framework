package com.bitdubai.fermat_api.layer;

/**
 * Created by ciencias on 30.12.14.
 */
public interface PlatformLayer {

    /**
     * Each layer is started and by that time has the chance to initialize its services.
     */

    public void start() throws CantStartLayerException;

}
