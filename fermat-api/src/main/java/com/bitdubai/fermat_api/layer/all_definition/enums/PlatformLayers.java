/*
 * @#PlatformLayers.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.enums.PlatformLayers</code> define
 * the type of layers
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum PlatformLayers {

    /**
     *  Definitions types
     */
    BITDUBAI_COMMUNICATION_LAYER     ("Communication Layer",     Developers.BITDUBAI),
    BITDUBAI_DEFINITION_LAYER        ("Definition Layer",        Developers.BITDUBAI),
    BITDUBAI_PLATFORM_SERVICE_LAYER  ("Platform Service Layer",  Developers.BITDUBAI),
    BITDUBAI_OS_LAYER                ("Operating System Layer",  Developers.BITDUBAI),
    BITDUBAI_HARDWARE_LAYER          ("Hardware Layer",          Developers.BITDUBAI),
    BITDUBAI_USER_LAYER              ("User Layer",              Developers.BITDUBAI),
    BITDUBAI_LICENSE_LAYER           ("License Layer",           Developers.BITDUBAI),
    BITDUBAI_WORLD_LAYER             ("World Layer",             Developers.BITDUBAI),
    BITDUBAI_CRYPTO_LAYER            ("Crypto Layer",            Developers.BITDUBAI),
    BITDUBAI_CRYPTO_NETWORK_LAYER    ("Crypto Network Layer",    Developers.BITDUBAI),
    BITDUBAI_CRYPTO_VAULT_LAYER      ("Crypto Vault Layer",      Developers.BITDUBAI),
    BITDUBAI_CRYPTO_ROUTER_LAYER     ("Crypto Router Layer",     Developers.BITDUBAI),
    BITDUBAI_NETWORK_SERVICE_LAYER   ("Network Service Layer",   Developers.BITDUBAI),
    BITDUBAI_TRANSACTION_LAYER       ("Transaction Layer",       Developers.BITDUBAI),
    BITDUBAI_MIDDLEWARE_LAYER        ("Middleware Layer",        Developers.BITDUBAI),
    BITDUBAI_MODULE_LAYER            ("Module Layer",            Developers.BITDUBAI),
    BITDUBAI_AGENT_LAYER             ("Agent Layer",             Developers.BITDUBAI),
    BITDUBAI_BASIC_WALLET_LAYER      ("Basic Wallet Layer",      Developers.BITDUBAI),
    BITDUBAI_NICHE_WALLET_TYPE_LAYER ("Niche Wallet Type Layer", Developers.BITDUBAI),
    BITDUBAI_ACTOR_LAYER             ("Actor Layer",             Developers.BITDUBAI),
    BITDUBAI_IDENTITY_LAYER          ("Identity Layer",          Developers.BITDUBAI),
    BITDUBAI_PIP_MODULE_LAYER        ("PIP Module Layer",        Developers.BITDUBAI);

    /**
     * Represent the key
     */
    private final String key;

    /**
     * Represent the developer
     */
    private final Developers developer;

    /**
     * Constructor
     *
     * @param key
     * @param developer
     */
    PlatformLayers(String key, Developers developer) {
        this.key = key;
        this.developer = developer;
    }

    /**
     * Get the key representation
     *
     * @return String
     */
    public String getKey()   { return this.key; }

    /**
     * Get the developer
     *
     * @return Developers
     */
    public Developers getDeveloper()   { return this.developer; }

    /**
     * Get the plataform layer representation from key
     *
     * @param key
     * @return PlatformLayers
     * @throws InvalidParameterException
     */
    public static PlatformLayers getByKey(String key) throws InvalidParameterException {
        switch(key){

            case "Communication Layer":
                return PlatformLayers.BITDUBAI_COMMUNICATION_LAYER;

            case "Definition Layer":
                return PlatformLayers.BITDUBAI_DEFINITION_LAYER;

            case "Platform Service Layer":
                return PlatformLayers.BITDUBAI_PLATFORM_SERVICE_LAYER;

            case "Operating System Layer":
                return PlatformLayers.BITDUBAI_OS_LAYER;

            case "Hardware Layer":
                return PlatformLayers.BITDUBAI_HARDWARE_LAYER;

            case "User Layer":
                return PlatformLayers.BITDUBAI_USER_LAYER;

            case "License Layer":
                return PlatformLayers.BITDUBAI_LICENSE_LAYER;

            case "World Layer":
                return PlatformLayers.BITDUBAI_WORLD_LAYER;

            case "Crypto Layer":
                return PlatformLayers.BITDUBAI_CRYPTO_LAYER;

            case "Crypto Network Layer":
                return PlatformLayers.BITDUBAI_CRYPTO_NETWORK_LAYER;

            case "Crypto Vault Layer":
                return PlatformLayers.BITDUBAI_CRYPTO_VAULT_LAYER;

            case "Crypto Router Layer":
                return PlatformLayers.BITDUBAI_CRYPTO_ROUTER_LAYER;

            case "Network Service Layer":
                return PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER;

            case "Transaction Layer":
                return PlatformLayers.BITDUBAI_TRANSACTION_LAYER;

            case "Middleware Layer":
                return PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER;

            case "Module Layer":
                return PlatformLayers.BITDUBAI_MODULE_LAYER;

            case "Agent Layer":
                return PlatformLayers.BITDUBAI_AGENT_LAYER;

            case "Basic Wallet Layer":
                return PlatformLayers.BITDUBAI_BASIC_WALLET_LAYER;

            case "Niche Wallet Type Layer":
                return PlatformLayers.BITDUBAI_NICHE_WALLET_TYPE_LAYER;

            case "Actor Layer":
                return PlatformLayers.BITDUBAI_ACTOR_LAYER;

            case "Identity Layer":
                return PlatformLayers.BITDUBAI_IDENTITY_LAYER;

            case "PIP Module Layer":
                return PlatformLayers.BITDUBAI_PIP_MODULE_LAYER;
        }

        throw new InvalidParameterException(key);
    }

}