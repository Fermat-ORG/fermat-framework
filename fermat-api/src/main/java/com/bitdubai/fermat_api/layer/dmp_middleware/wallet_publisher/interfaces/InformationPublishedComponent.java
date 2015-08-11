/*
 * @#InformationPublishedComponent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */

package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.InformationPublishedComponent</code> define
 * the static information about a published component.
 * <p/>
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 * @author Update by Roberto Requena - (rart3001@gmail.com) on 06/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface InformationPublishedComponent {

    /**
     * Get the id
     *
     * @return UUID
     */
    public UUID getId();

    /**
     * Get the Descriptor Factory Project Id
     *
     * @return UUID
     */
    public UUID getDescriptorFactoryProjectId();

    /**
     * Get the Descriptor Factory Project Name
     *
     * @return String
     */
    public String getDescriptorFactoryProjectName();

    /**
     * Get the Type
     *
     * @return DescriptorFactoryProjectType
     */
    public DescriptorFactoryProjectType getType();


    /**
     * Get the Status
     *
     * @return ComponentPublishedInformationStatus
     */
    public ComponentPublishedInformationStatus getStatus();

    /**
     * Get the Status Timestamp
     *
     * @return Timestamp
     */
    public Timestamp getStatusTimestamp();

    /**
     * Get the Publication Timestamp
     *
     * @return Timestamp
     */
    public Timestamp getPublicationTimestamp();

    /**
     * Get the Publisher Id
     *
     * @return UUID
     */
    public UUID getPublisherId();

}
