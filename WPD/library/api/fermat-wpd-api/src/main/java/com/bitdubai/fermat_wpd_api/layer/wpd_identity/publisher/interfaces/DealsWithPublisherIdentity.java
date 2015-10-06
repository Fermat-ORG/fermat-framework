package com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.interfaces;

/**
 * The Class <code>DealsWithPublisherIdentity</code>
 * indicates that the plugin needs the functionality of a PublisherIdentityManager
 * <p/>
 *
 * Created by Nerio on 13/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithPublisherIdentity {
    void setPublisherIdentityManager(PublisherIdentityManager publisherIdentityManager);
}
