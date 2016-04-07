package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

import java.io.Serializable;

/**
 * This is a marker interface used to represent that an object's purpose is to
 * be used as an content message.
 * <p/>
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 25/11/15.
 */
public interface DAPContentMessage extends Serializable {
    /**
     * Every content message should have a unique type associate to it.
     *
     * @return {@link DAPMessageType} The message type that corresponds to this content message.
     */
    DAPMessageType messageType();
}
