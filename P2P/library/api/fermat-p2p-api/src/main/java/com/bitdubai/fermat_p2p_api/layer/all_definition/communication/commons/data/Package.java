package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;

import java.io.Serializable;
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
public class Package implements Serializable {

    /**
     * Represent the content value
     */
    private String content;

    /**
     * Represent the packageType value
     */
    private PackageType packageType;

    /**
     * Represent the networkServiceTypeSource value
     */
    private NetworkServiceType networkServiceTypeSource;

    /**
     * Represent the destinationPublicKey
     */
    private String destinationPublicKey;

    /**
     * Represent the signature value
     */
    private String signature;

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
    protected Package(final String             content                 ,
                      final NetworkServiceType networkServiceTypeSource,
                      final PackageType        packageType             ,
                      final String             signature               ,
                      final String             destinationPublicKey    ) {

        if (content == null)
            throw new InvalidParameterException("Content can't be null.");

        if (networkServiceTypeSource == null)
            throw new InvalidParameterException("networkServiceTypeSource can't be null.");

        if (packageType == null)
            throw new InvalidParameterException("packageType can't be null.");

        if (signature == null)
            throw new InvalidParameterException("signature can't be null.");

        this.content                  = content                 ;
        this.networkServiceTypeSource = networkServiceTypeSource;
        this.packageType              = packageType             ;
        this.signature                = signature               ;
        this.destinationPublicKey     = destinationPublicKey    ;
    }

    /**
     * Gets the value of content and returns
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the value of signature and returns
     *
     * @return signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Gets the value of packageType and returns
     *
     * @return packageType
     */
    public PackageType getPackageType() {
        return packageType;
    }

    /**
     * Gets the value of networkServiceTypeSource and returns
     *
     * @return networkServiceTypeSource
     */
    public NetworkServiceType getNetworkServiceTypeSource() {
        return networkServiceTypeSource;
    }

    /**
     * Set the ClientDestination
     * @param destinationPublicKey
     */
    protected void setDestinationPublicKey(String destinationPublicKey) {
        this.destinationPublicKey = destinationPublicKey;
    }

    /**
     * Get the ClientDestination
     * @return String
     */
    public String getDestinationPublicKey() {
        return destinationPublicKey;
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
    public static Package createInstance(final String             content                     ,
                                         final NetworkServiceType networkServiceTypeSource    ,
                                         final PackageType        packageType                 ,
                                         final String             senderPrivateKey            ,
                                         final String             destinationIdentityPublicKey) {


        String messageHash = AsymmetricCryptography.encryptMessagePublicKey(
                content,
                destinationIdentityPublicKey
        );

        String signature   = AsymmetricCryptography.createMessageSignature(
                messageHash,
                senderPrivateKey
        );

        return new Package(
                content                     ,
                networkServiceTypeSource    ,
                packageType                 ,
                signature                   ,
                destinationIdentityPublicKey
        );
    }
}
