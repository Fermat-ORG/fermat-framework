/*
 * @#DigitalAssetMetadataTransaction.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction</code> represent the
 * transaction make by the Asset Transmission Network Service
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 15/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DigitalAssetMetadataTransaction {

    /**
     * Get the Digital Asset Metadata
     *
     * @return DigitalAssetMetadata
     */
    DigitalAssetMetadata getDigitalAssetMetadata();

    /**
     * Get the Distribution Status
     *
     * @return DistributionStatus
     */
    DistributionStatus getDistributionStatus();

    /**
     * Get the Receiver Id
     *
     * @return String
     */
    String getReceiverId();

    /**
     * The platform component that this event is destinated for.
     *
     * @return {@link PlatformComponentType}
     */
    PlatformComponentType getReceiverType();

    /**
     * Get the Sender Id
     *
     * @return String
     */
    String getSenderId();

    /**
     * The platform component that send this event.
     *
     * @return {@link PlatformComponentType}
     */
    PlatformComponentType getSenderType();

    /**
     * Get the GenesisTransaction
     *
     * @return String
     */
    String getGenesisTransaction();

    /**
     * Get the Digital Asset Metadata Transaction Type
     *
     * @return DigitalAssetMetadataTransactionType
     */
    DigitalAssetMetadataTransactionType getType();

    /**
     * Get the Timestamp
     *
     * @return Long
     */
    Long getTimestamp();

}
