package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.UUID;

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
     * Represent the id
     * si es un ack el package id es el id del paquete enviado
     */
    private UUID packageId;

    /**
     * Represent the content value
     */
    private String content;

    /**
     * Represent the packageType value
     */
    private PackageType packageType;

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
    protected Package(
            final UUID packageId,
            final String             content                 ,
            final NetworkServiceType networkServiceTypeSource,
            final PackageType        packageType             ,
            final String             signature               ,
            final String             destinationPublicKey    ) {

        if (content == null)
            throw new InvalidParameterException("Content can't be null.");

        //esto no es necesario..
//        if (networkServiceTypeSource == null)
//            throw new InvalidParameterException("networkServiceTypeSource can't be null.");

        if (packageType == null)
            throw new InvalidParameterException("packageType can't be null. ns type: "+networkServiceTypeSource);

//        if (signature == null)
//            throw new InvalidParameterException("signature can't be null.");

        this.packageId = packageId;
        this.content                  = content                 ;
        this.packageType              = packageType             ;
        this.signature                = signature               ;
        this.destinationPublicKey     = destinationPublicKey    ;
    }

    protected Package(
            final UUID packageId,
            final String             content                 ,
            final PackageType        packageType             ,
            final String             signature               ,
            final String             destinationPublicKey    ) {

        if (content == null)
            throw new InvalidParameterException("Content can't be null.");

        //esto no es necesario..

        if (packageType == null)
            throw new InvalidParameterException("packageType can't be null. content: "+content);

//        if (signature == null)
//            throw new InvalidParameterException("signature can't be null.");

        this.packageId = packageId;
        this.content                  = content                 ;
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

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    /**
     * Construct a package instance encrypted with the destination identity public key and signed
     * whit the private key passed as an argument
     *
     * @param content                       content of the package.
     * @param packageType                   package type.
     * @param senderPrivateKey              the private key of the sender.
     * @param destinationIdentityPublicKey  the public key of the receiver.
     *
     * @return Package signed instance
     */
    public static Package createInstance(final String             content                     ,
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
                UUID.randomUUID(),
                content                     ,
                packageType                 ,
                signature                   ,
                destinationIdentityPublicKey
        );
    }

    public static Package createInstance(final UUID packageId,
                                         final String             content                     ,
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
                packageId,
                content                     ,
                packageType                 ,
                signature                   ,
                destinationIdentityPublicKey
        );
    }


//    public static Package rebuildInstance(final UUID packageId,
//                                          final String             content                     ,
////                                          final NetworkServiceType networkServiceTypeSource    ,
//                                          final PackageType        packageType                 ,
//                                          final String             destinationIdentityPublicKey) {
//
//
////        String messageHash = AsymmetricCryptography.encryptMessagePublicKey(
////                content,
////                destinationIdentityPublicKey
////        );
////
////        String signature   = AsymmetricCryptography.createMessageSignature(
////                messageHash,
////                senderPrivateKey
////        );
//
//
//        return new Package(
//                packageId,
//                content                     ,
////                networkServiceTypeSource    ,
//                packageType                 ,
//                null                   ,
//                destinationIdentityPublicKey
//        );
//    }

    public static Package rebuildInstance(final UUID packageId,
                                          final String             content                     ,
                                          final PackageType        packageType                 ,
                                          final String             destinationIdentityPublicKey) {


//        String messageHash = AsymmetricCryptography.encryptMessagePublicKey(
//                content,
//                destinationIdentityPublicKey
//        );
//
//        String signature   = AsymmetricCryptography.createMessageSignature(
//                messageHash,
//                senderPrivateKey
//        );


        return new Package(
                packageId,
                content                     ,
                packageType                 ,
                null                   ,
                destinationIdentityPublicKey
        );
    }


    @Override
    public String toString() {
        return "Package{" +
                "packageId=" + packageId +
                ", content='" + content + '\'' +
                ", packageType=" + packageType +
                ", destinationPublicKey='" + destinationPublicKey + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
