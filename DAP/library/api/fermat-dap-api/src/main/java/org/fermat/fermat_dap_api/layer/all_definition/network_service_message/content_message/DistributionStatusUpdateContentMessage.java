package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/02/16.
 */
public class DistributionStatusUpdateContentMessage implements DAPContentMessage {

    //VARIABLE DECLARATION
    private org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus newStatus;
    private String genesisTransaction; //TODO REPLACE THIS BY METADATA ID.

    //CONSTRUCTORS
    public DistributionStatusUpdateContentMessage() {
    }

    public DistributionStatusUpdateContentMessage(org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus newStatus, String genesisTransaction) {
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
    public org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus newStatus) {
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
