/*
 * @#Package.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package</code> wrap
 * all message send by the communication chanel
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Package {

    /**
     * Represent the PackageContent value
     */
    private PackageContent content;

    /**
     * Represent the packageType value
     */
    private PackageType packageType;

    /**
     * Represent the networkServiceTypeSource value
     */
    private NetworkServiceType networkServiceTypeSource;

    /**
     * Represent the signature value
     */
    private String signature;

    /**
     * Constructor whit parameters
     *
     * @param content
     * @param networkServiceTypeSource
     * @param packageType
     * @param signature
     */
    Package(PackageContent content, NetworkServiceType networkServiceTypeSource, PackageType packageType, String signature) {
        this.content = content;
        this.networkServiceTypeSource = networkServiceTypeSource;
        this.packageType = packageType;
        this.signature = signature;
    }

    /**
     * Gets the value of content and returns
     *
     * @return content
     */
    public PackageContent getContent() {
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
     * Construct a package instance encrypted with the destination identity public key and signed
     * whit the private key passed as an argument
     *
     * @param content
     * @param networkServiceTypeSource
     * @param packageType
     * @return Package signed instance
     */
    public static Package createInstance(PackageContent content, NetworkServiceType networkServiceTypeSource, PackageType packageType, String senderPrivateKey, String destinationIdentityPublicKey) {

        String messageHash = AsymmetricCryptography.encryptMessagePublicKey(content.toString(), destinationIdentityPublicKey);
        String signature   = AsymmetricCryptography.createMessageSignature(messageHash, senderPrivateKey);
        return new Package(content, networkServiceTypeSource, packageType, signature);
    }
}
