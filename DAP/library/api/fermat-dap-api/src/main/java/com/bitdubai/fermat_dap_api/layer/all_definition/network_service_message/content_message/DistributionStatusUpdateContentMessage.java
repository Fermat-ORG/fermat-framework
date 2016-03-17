package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/02/16.
 */
public class DistributionStatusUpdateContentMessage implements DAPContentMessage {

    //VARIABLE DECLARATION
    private DistributionStatus newStatus;
    private String genesisTransaction; //TODO REPLACE THIS BY METADATA ID.

    //CONSTRUCTORS
    public DistributionStatusUpdateContentMessage() {
    }

    public DistributionStatusUpdateContentMessage(DistributionStatus newStatus, String genesisTransaction) {
        this.newStatus = newStatus;
        this.genesisTransaction = genesisTransaction;
    }

    //PUBLIC METHODS

    /**
     * Every content message should have a unique type associate to it.
     *
     * @return {@link DAPMessageType} The message type that corresponds to this content message.
     */
    @Override
    public DAPMessageType messageType() {
        return DAPMessageType.DISTRIBUTION_STATUS_UPDATE;
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS
    public DistributionStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(DistributionStatus newStatus) {
        this.newStatus = newStatus;
    }

    public String getGenesisTransaction() {
        return genesisTransaction;
    }

    public void setGenesisTransaction(String genesisTransaction) {
        this.genesisTransaction = genesisTransaction;
    }

    //INNER CLASSES
}
