package com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.exception;

/**
 * Addon internal exceptions
 * Created by firuzzz on 5/12/15.
 */
public class LocationServiceException extends Exception {
    public LocationServiceException(String message) {
        super(message);
    }

    public LocationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
