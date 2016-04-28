package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.Plugins</code>
 * Contains all the type of Plugins in Fermat.
 * Created by ciencias on 2/13/15.
 * Modified by Manuel Perez on 03/08/2015
 * Updated by lnacosta (laion.cj91@gmail.com) on 18/11/2015.
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 01/12/2015. *
 */
public enum Plugins implements FermatPluginsEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BITDUBAI_BITCOIN_CRYPTO_NETWORK             ("BBTCCNET"     ),

    BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL    ("BWSCCLIENTCH" ),
    BITDUBAI_WS_COMMUNICATION_CLOUD_SERVER      ("BWSCCLSERVER" ),

    NETWORK_NODE("BCNNODE"),
    NETWORK_CLIENT("BCNCLIENT"),

    BITDUBAI_CLOUD_SERVER_COMMUNICATION                             ("BCLOUSC"      ),
    BITDUBAI_USER_NETWORK_SERVICE                                   ("BUSERNETS"    ),
    BITDUBAI_TEMPLATE_NETWORK_SERVICE                               ("BTEMNETS"     ),
    BITDUBAI_INTRAUSER_NETWORK_SERVICE                              ("BINUSERNS"    ),
    BITDUBAI_APP_RUNTIME_MIDDLEWARE                                 ("BAPPRUNM"     ),
    BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET                           ("BDWALLBW"     ),
    BITDUBAI_WALLET_RUNTIME_MODULE                                  ("BWALLRUNM"    ),
    BITDUBAI_BITCOIN_CRYPTO_VAULT                                   ("BBTCCRYV"     ),
    BITDUBAI_ASSETS_CRYPTO_VAULT                                    ("BASSTCRYV"    ),
    BITDUBAI_INTRA_USER_FACTORY_MODULE                              ("BINUSFACM"    ),
    BITDUBAI_BANK_NOTES_WALLET_WALLET_MODULE                        ("BBNWWM"       ),
    BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE             ("BCLPWWM"      ),
    BITDUBAI_CRYPTO_WALLET_WALLET_MODULE                            ("BCWWM"        ),
    BITDUBAI_DISCOUNT_WALLET_WALLET_MODULE                          ("BDWWM"        ),
    BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE   ("BFOCLPWWM"    ),
    BITDUBAI_FIAT_OVER_CRYPTO_WALLET_WALLET_MODULE                  ("BFOCWWM"      ),
    BITDUBAI_MULTI_ACCOUNT_WALLET_WALLET_MODULE                     ("BMAWWM"       ),
    BITDUBAI_INCOMING_INTRA_USER_TRANSACTION                        ("BININUST"     ),
    BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION                       ("BINDEVUT"     ),
    BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION                       ("BODEVUST"     ),
    BITDUBAI_INTER_WALLET_TRANSACTION                               ("BINWALLT"     ),
    BITDUBAI_BANK_NOTES_MIDDLEWARE                                  ("BBNMIDD"      ),
    BITDUBAI_BANK_NOTES_NETWORK_SERVICE                             ("BBNNETSER"    ),
    BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE                       ("BWRNETSER"    ),
    BITDUBAI_WALLET_STORE_NETWORK_SERVICE                           ("BWSTONETSER"  ),
    BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE                       ("BWCNETSER"    ),
    BITDUBAI_CRYPTO_ADDRESS_BOOK                                    ("BCADDB"       ),
    BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION                        ("BOUEXUT"      ),
    BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION                        ("BINEXUT"      ),
    BITDUBAI_INCOMING_CRYPTO_TRANSACTION                            ("BINCRYT"      ),
    BITDUBAI_USER_DEVICE_USER                                       ("BUDEVU"       ),
    BITDUBAI_ACTOR_EXTRA_USER                                       ("BAEXU"        ),
    BITDUBAI_USER_INTRA_USER                                        ("BUINU"        ),
    BITDUBAI_COINBASE_WORLD                                         ("BCOINW"       ),
    BITDUBAI_BITCOIN_WALLET_BASIC_WALLET                            ("BBTCWBW"      ),
    BITDUBAI_DEVICE_CONNECTIVITY                                    ("BBTCDEVC"     ),
    BITDUBAI_LOCATION_WORLD                                         ("BLOCW"        ),
    BITDUBAI_ACTOR_DEVELOPER                                        ("BACTORD"      ),
    BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE                             ("BWLMIDD"      ),
    BITDUBAI_WALLET_SKIN_MIDDLEWARE                                 ("BWSMIDD"      ),
    BITDUBAI_WALLET_NAVIGATION_STRUCTURE_MIDDLEWARE                 ("BWNSMIDD"     ),
    BITDUBAI_SUB_APP_SETTINGS_MIDDLEWARE                            ("BSASEMIDD"    ),
    BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE                      ("BWSTANETSER"  ),
    BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE                       ("BSRNETSER"    ),
    BITDUBAI_CCP_CRYPTO_TRANSMISSION_NETWORK_SERVICE                ("BCTNSER"      ),
    BITDUBAI_REQUEST_MONEY_REQUEST                                  ("BRMR"         ),
    BITDUBAI_DEVELOPER_IDENTITY                                     ("BDEVID"       ),
    BITDUBAI_TRANSLATOR_IDENTITY                                    ("BDTRAID"      ),
    BITDUBAI_IDENTITY_MANAGER                                       ("BIDMAN"       ),
    BITDUBAI_DESIGNER_IDENTITY                                      ("BDDESID"      ),
    BITDUBAI_DEVELOPER_MODULE                                       ("BDEVMOD"      ),
    BITDUBAI_MIDDLEWARE_NOTIFICATION                                ("BDNOTMID"     ),
    BITDUBAI_DESKTOP_RUNTIME                                        ("BDR"          ),

    // Init CCP Plugins
    BITDUBAI_CCP_INTRA_USER_ACTOR                           ("BCCPIUA"  ),
    BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE           ("BCCPCANS" ),
    BITDUBAI_CCP_CRYPTO_CRYPTO_TRANSMISSION_NETWORK_SERVICE ("BCCPCTNS" ),
    BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE     ("BCCPCPRNS"),
    BITDUBAI_CCP_INTRA_USER_IDENTITY                        ("BCCPIUI"  ),
    BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE                 ("BCCPWCM"  ),
    BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST                     ("BCCPCPR"  ),
    BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION           ("BCCPOIAT" ),


    BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE    ("BCTNS"),


    // End  CCP Plugins

    //CCM Plugins

    BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR            ("BCCMIWUA"),

    // Init DAP Plugins
    BITDUBAI_DAP_ASSET_ISSUER_ACTOR                       ("BDAPAIA"   ),
    BITDUBAI_DAP_ASSET_USER_ACTOR                         ("BDAPAUA"   ),
    BITDUBAI_DAP_REDEEM_POINT_ACTOR                       ("BDAPRPA"   ),
    BITDUBAI_DAP_ASSET_ISSUER_IDENTITY                    ("BDAPAII"   ),
    BITDUBAI_DAP_ASSET_USER_IDENTITY                      ("BDAPAUI"   ),
    BITDUBAI_DAP_REDEEM_POINT_IDENTITY                    ("BDAPRPI"   ),
    BITDUBAI_ASSET_ISSUING_TRANSACTION                    ("BAIT"      ),
    BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION               ("BADT"      ),
    BITDUBAI_ASSET_RECEPTION_TRANSACTION                  ("BADR"      ),
    BITDUBAI_ISSUER_REDEMPTION_TRANSACTION                ("BIRT"      ),
    BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION          ("BRPRT"     ),
    BITDUBAI_USER_REDEMPTION_TRANSACTION                  ("BURT"      ),
    BITDUBAI_ASSET_APPROPRIATION_TRANSACTION              ("BAAT"      ),
    BITDUBAI_ASSET_WALLET_ISSUER                          ("BASWI"     ),
    BITDUBAI_ASSET_FACTORY                                ("BASF"      ),
    BITDUBAI_ASSET_FACTORY_MODULE                         ("BASFM"     ),
    BITDUBAI_DAP_ASSET_USER_WALLET_MODULE                 ("BDAUWMO"   ),
    BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE               ("BDAIWMO"   ),
    BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE         ("BDARWMO"   ),
    BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE    ("BDAPAICSAM"),
    BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE      ("BDAPAUCSAM"),
    BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE    ("BDAPRPCSAM"),
    BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET                ("BDAPAWRD"  ),
    BITDUBAI_DAP_ASSET_USER_WALLET                        ("BDAPAWU"   ),
    BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE         ("BDAPAUANS" ),
    BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE       ("BDAPAIANS" ),
    BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE ("BDAPARANS" ),
    BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE       ("BDAPATNS"  ),

    // End  DAP Plugins

    // Init WPD Plugins
    BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE              ("BWPDWMDM" ),
    BITDUBAI_WPD_WALLET_FACTORY_MIDDLEWARE                  ("BWPDWFM"  ),
    BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE                  ("BWPDWMM"  ),
    BITDUBAI_WPD_WALLET_PUBLISHER_MIDDLEWARE                ("BWPDWPM"  ),
    BITDUBAI_WPD_WALLET_SETTINGS_MIDDLEWARE                 ("BWPDWSEM" ),
    BITDUBAI_WPD_WALLET_STORE_MIDDLEWARE                    ("BWPDWSTM" ),
    BITDUBAI_WPD_PUBLISHER_IDENTITY                         ("BWPDPI"   ),
    BITDUBAI_WPD_WALLET_COMMUNITY_NETWORK_SERVICE           ("BWPDWCNS" ),
    BITDUBAI_WPD_WALLET_RESOURCES_NETWORK_SERVICE           ("BWPDWRNS" ),
    BITDUBAI_WPD_WALLET_STATISTICS_NETWORK_SERVICE          ("BWPDWSNS" ),
    BITDUBAI_WPD_WALLET_STORE_NETWORK_SERVICE               ("BWPDSNS"  ),
    BITDUBAI_WPD_WALLET_FACTORY_SUB_APP_MODULE              ("BWPDWFSAM"),
    BITDUBAI_WPD_WALLET_PUBLISHER_SUB_APP_MODULE            ("BWPDWPSAM"),
    BITDUBAI_WPD_WALLET_STORE_SUB_APP_MODULE                ("BWPDWSSAM"),
    // End  WPD Plugins

    //Init BNK Plugins
    BITDUBAI_BNK_HOLD_MONEY_TRANSACTION         ("BBNKHMT"  ),
    BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION       ("BBNKUMT"  ),
    BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION      ("BBNKDMT"  ),
    BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION     ("BBNKWMT"  ),
    BITDUBAI_BNK_BANK_MONEY_WALLET_MODULE       ("BBNKBMWM" ),
    BITDUBAI_BNK_BANK_MONEY_WALLET              ("BBNKBMW"  ),

    // End BNK Plugins

    //Init CSH Plugins
    BITDUBAI_CSH_MONEY_TRANSACTION_HOLD         ("BCSHMTH"  ),
    BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD       ("BCSHMTU"  ),
    BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT      ("BCSHMTD"  ),
    BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL   ("BCSHMTW"  ),
    BITDUBAI_CSH_MONEY_WALLET_MODULE            ("BCSHMWM"),
    BITDUBAI_CSH_WALLET_CASH_MONEY              ("BCSHWCM"  ),
    // End CSH Plugins

    //Init CER Plugins
    FILTER("BCERPF")                    ,
    BITCOINVENEZUELA("BCERPBV")         ,
    BITFINEX("BCERPBF")                 ,
    BTCE("BCERPBC")                     ,
    BTER("BCERPBT")                     ,
    CCEX("BCERPCC")                     ,
    DOLARTODAY("BCERPDT")               ,
    ELCRONISTA("BCERPEC")               ,
    EUROPEAN_CENTRAL_BANK("BCERPECB")   ,
    LANACION("BCERPLN")                 ,
    YAHOO("BCERPYH")                    ,
    // End CER Plugins

    // Init new Plugins

    ASSET_APPROPRIATION         ("ASAP"),
    ASSET_BUYER                 ("ASBU"),
    ASSET_DIRECT_SELL           ("ASDS"),
    ASSET_DISTRIBUTION          ("ASD"),
    ASSET_FACTORY               ("ASF"),
    ASSET_ISSUER                ("ASI"),
    ASSET_ISSUER_COMMUNITY      ("ASIC"),
    ASSET_ISSUING               ("ASIS"),
    ASSET_RECEPTION             ("ASR"),
    ASSET_SELLER                ("ASSE"),
    ASSET_TRANSFER              ("ASTT"),
    ASSET_TRANSMISSION          ("AST"),
    ASSET_USER                  ("ASU"),
    ASSET_USER_COMMUNITY        ("ASUC"),
    BITCOIN_ASSET_VAULT         ("BAV"),
    BITCOIN_NETWORK             ("BN"),
    BITCOIN_VAULT               ("BV"),
    BITCOIN_WALLET              ("BW"),
    LOSS_PROTECTED_WALLET       ("LPW"),
    BITCOIN_WATCH_ONLY_VAULT    ("BWOV"),
    BITCOIN_HOLD                ("BHOLD"),
    BITCOIN_UNHOLD              ("BUNHOLD"),
    CHAT_ACTOR                  ("CHTA"),
    CRYPTO_ADDRESSES            ("CA"),
    CRYPTO_ADDRESS_BOOK         ("CAB"),
    CRYPTO_BROKER               ("CB"),
    CRYPTO_BROKER_COMMUNITY     ("CBC"),
    CRYPTO_BROKER_IDENTITY      ("CBI"),
    CRYPTO_BROKER_PURCHASE      ("CBP"),
    CRYPTO_BROKER_SALE          ("CBS"),
    CRYPTO_CUSTOMER             ("CC"),
    CRYPTO_CUSTOMER_IDENTITY    ("CCI"),
    CRYPTO_CUSTOMER_COMMUNITY   ("CCC"),
    CRYPTO_PAYMENT_REQUEST      ("CPR"),
    CRYPTO_TRANSMISSION         ("CT"),
    CRYPTO_WALLET               ("CW"),
    CRYPTO_LOSS_PROTECTED_WALLET("CLPW"),
    DESKTOP_RUNTIME             ("DER"),
    DEVELOPER                   ("DEV"),
    DEVICE_USER                 ("DU"),
    EXTRA_WALLET_USER           ("EWU"),
    INCOMING_CRYPTO             ("IC"),
    INCOMING_EXTRA_USER         ("IEU"),
    INCOMING_INTRA_USER         ("IIU"),
    INTRA_IDENTITY_USER         ("IIA"),
    INTRA_WALLET_USER           ("IWU"),
    ISSUER_REDEMPTION           ("IR"),
    ISSUER_APPROPRIATION        ("ISAP"),
    NOTIFICATION                ("NOT"),
    NEGOTIATION_DIRECT_SELL     ("NDS"),
    ANDROID_CORE                ("AND"),
    OUTGOING_EXTRA_USER         ("OEU"),
    OUTGOING_INTRA_ACTOR        ("OIA"),
    TRANSFER_INTRA_WALLET        ("TIW"),
    TIMEOUT_NOTIFIER            ("TON"),
    PUBLISHER                   ("PBL"),
    REDEEM_POINT                ("RP"),
    REDEEM_POINT_COMMUNITY      ("RPC"),
    REDEEM_POINT_REDEMPTION     ("RPR"),
    SUB_APP_RESOURCES           ("SAR"),
    SUB_APP_FERMAT_MONITOR      ("FERM"),
    SUB_APP_RUNTIME             ("SPR"),
    USER_REDEMPTION             ("UR"),
    WALLET_COMMUNITY            ("WCOM"),
    WALLET_CONTACTS             ("WC"),
    WALLET_FACTORY              ("WF"),
    WALLET_MANAGER              ("WM"),
    SUB_APP_MANAGER             ("SAM"),
    WALLET_PUBLISHER            ("WPU"),
    WALLET_RESOURCES            ("WRE"),
    WALLET_RUNTIME              ("WRU"),
    WALLET_SETTINGS             ("WSE"),
    WALLET_STATISTICS           ("WSTA"),
    WALLET_STORE                ("WST"),
    WS_CLOUD_CLIENT             ("WCL"),

    //CBP
    BANK_MONEY_RESTOCK                  ("BMRE"),
    BANK_MONEY_DESTOCK                  ("BMDE"),
    CASH_MONEY_RESTOCK                  ("CMRE"),
    CASH_MONEY_DESTOCK                  ("CMDE"),
    CONTRACT_PURCHASE                   ("CONTP"),
    CONTRACT_SALE                       ("CONTS"),
    CRYPTO_BROKER_WALLET                ("CBWA"),
    CRYPTO_BROKER_ACTOR                 ("CBAC"),
    CRYPTO_CUSTOMER_ACTOR               ("CCAC"),
    CRYPTO_MONEY_DESTOCK                ("CRDE"),
    CRYPTO_MONEY_RESTOCK                ("CRRE"),
    CUSTOMER_BROKER_CLOSE               ("CBCL"),
    CUSTOMER_BROKER_NEW                 ("CBNE"),
    CUSTOMER_BROKER_UPDATE              ("CBUP"),
    FIAT_INDEX                          ("FI"),
    NEGOTIATION_PURCHASE                ("NGP"),
    NEGOTIATION_SALE                    ("NGS"),
    NEGOTIATION_TRANSMISSION            ("NGTR"),
    OPEN_CONTRACT                       ("OPC"),
    TRANSACTION_TRANSMISSION            ("TRTX"),
    MATCHING_ENGINE                     ("MAEN"),
    CLOSE_CONTRACT                      ("CLC"),
    CUSTOMER_ONLINE_PAYMENT             ("CONP"),
    CUSTOMER_OFFLINE_PAYMENT            ("COFP"),
    BROKER_ACK_OFFLINE_PAYMENT          ("BAFP"),
    BROKER_ACK_ONLINE_PAYMENT           ("BAOP"),
    CUSTOMER_ACK_OFFLINE_MERCHANDISE    ("CAFM"),
    CUSTOMER_ACK_ONLINE_MERCHANDISE     ("CAOM"),
    BROKER_SUBMIT_ONLINE_MERCHANDISE    ("BSOM"),
    BROKER_SUBMIT_OFFLINE_MERCHANDISE   ("BSFM"),

    CUSTOMER_BROKER_PURCHASE            ("CBPU"),
    CUSTOMER_BROKER_SALE                ("CBSA"),

    //CHT
    CHAT_COMMUNITY_SUP_APP_MODULE       ("CATCH"),
    CHAT_MIDDLEWARE                     ("CHMID"),
    CHAT_NETWORK_SERVICE                ("CHTNS"),
    CHAT_SUP_APP_MODULE                 ("CHTSAM"),
    CHAT_IDENTITY_SUP_APP_MODULE        ("CHTISAM"),
    CHAT_IDENTITY                       ("CHTIDE"),
    CCP_OUTGOING_DRAFT_TRANSACTION      ("CCPODT"),
    CHAT_ACTOR_CONNECTION               ("CHTAC"),
    CHAT_ACTOR_NETWORK_SERVICE          ("CHTANS"),

    // ART
    ARTIST_ACTOR_CONNECTION             ("ARTAAC"),
    FAN_ACTOR_CONNECTION                ("ARTFAC"),
    ARTIST                              ("ANSART"),
    FAN                                 ("FANS"),
    ARTIST_IDENTITY                     ("ARTIDNTY"),
    FANATIC_IDENTITY                    ("FANIDNTY"),
    ART_ARTIST_SUB_APP_MODULE           ("AASAM"),
    ART_FAN_SUB_APP_MODULE              ("AFSAM"),
    MUSIC_PLAYER_SUB_APP_MODULE         ("ARTMPM"),
    ARTIST_COMMUNITY_SUB_APP_MODULE     ("ARTACM"),
    FAN_COMMUNITY_SUB_APP_MODULE        ("ARTFCM"),

    //TKY
    TOKENLY_API                         ("TOKAP"),
    TOKENLY_ARTIST                      ("TARTIST"),
    TOKENLY_FAN                         ("TFAN"),
    TOKENLY_ARTIST_SUB_APP_MODULE       ("TASAM"),
    TOKENLY_FAN_SUB_APP_MODULE          ("TFSAM"),
    TOKENLY_FAN_WALLET_MODULE           ("TFWM"),
    TOKENLY_WALLET                      ("TWALLET"),

    //Init PIP
    PIP_FERMAT_MONITOR                  ("PFM");
    //End PIP

    // End  new Plugins

    private final String code;

    Plugins(final String code) {
        this.code = code;
    }

    public static Plugins getByCode(final String code) throws InvalidParameterException {

        for (Plugins plugin : Plugins.values()) {
            if(plugin.getCode().equals(code))
                return plugin;
        }

        throw new InvalidParameterException(
                "Code Received: " + code,
                "This code is not valid for the Plugins enum."
        );
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
