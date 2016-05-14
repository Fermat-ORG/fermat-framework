package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.ns;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;


import java.security.InvalidParameterException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package</code> wrap
 * all message send by the communication chanel
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/12/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 13/04/2016.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PackageMessage extends Package{

    /**
     * Private constructor with parameters.
     *
     * @param content                   content of the package.
     * @param networkServiceTypeSource  type of network service who is sending the package.
     * @param packageType               package type.
     * @param signature                 signature of the package.
     *
     * @throws InvalidParameterException if the parameters are bad.
     */
    private PackageMessage(final String content,
                           final NetworkServiceType networkServiceTypeSource,
                           final PackageType packageType,
                           final String signature,
                           final String clientDestination
    ) {

        super(content, networkServiceTypeSource, packageType, signature);

        if (clientDestination == null)
            throw new InvalidParameterException("clientDestination can't be null.");

        setClientDestination(clientDestination);
    }

    /**
     * Construct a package instance encrypted with the destination identity public key and signed
     * whit the private key passed as an argument
     *
     * @param content                       content of the package.
     * @param networkServiceTypeSource      type of network service who is sending the package.
     * @param packageType                   package type.
     * @param senderPrivateKey              the private key of the sender.
     * @param destinationIdentityPublicKey  the public key of the receiver.
     *
     * @return Package signed instance
     */
    public static PackageMessage createInstance(final String             content              ,
                                                final NetworkServiceType networkServiceTypeSource    ,
                                                final PackageType        packageType                 ,
                                                final String             senderPrivateKey            ,
                                                final String             destinationIdentityPublicKey,
                                                final String clientDestination) {

        String messageHash = AsymmetricCryptography.encryptMessagePublicKey(
                content,
                destinationIdentityPublicKey
        );

        String signature   = AsymmetricCryptography.createMessageSignature(
                messageHash,
                senderPrivateKey
        );

        return new PackageMessage(
                content                 ,
                networkServiceTypeSource,
                packageType             ,
                signature,
                clientDestination
        );
    }
}
