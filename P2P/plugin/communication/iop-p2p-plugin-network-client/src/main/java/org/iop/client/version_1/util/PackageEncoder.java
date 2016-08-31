package org.iop.client.version_1.util;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.common.com.google.flatbuffers.FlatBufferBuilder;

import java.nio.ByteBuffer;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * MAtias Furszyfer
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PackageEncoder implements Encoder.Binary<Package>{

    /**
     * (non-javadoc)
     * @see Text#encode(Object)
     */
    @Override
    public ByteBuffer encode(Package packageToSend) throws EncodeException {
        try {
            FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder();
            int packageId = flatBufferBuilder.createString(packageToSend.getPackageId().toString());
            int content = flatBufferBuilder.createString(packageToSend.getContent());
            int destinationPublicKey = 0;
            if (packageToSend.getDestinationPublicKey()!=null) {
               destinationPublicKey = flatBufferBuilder.createString(packageToSend.getDestinationPublicKey());
            }
            int pack = com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.common.Package.createPackage(
                        flatBufferBuilder,
                        packageId,
                        content,
                        packageToSend.getPackageType().getPackageTypeAsShort(),
                        destinationPublicKey);

            flatBufferBuilder.finish(pack);
            return flatBufferBuilder.dataBuffer();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {

    }

}
