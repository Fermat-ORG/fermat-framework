package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 * Updated by lnacosta (laion.cj91@gmail.com) on 18/11/2015.
 */
public enum Plugins implements FermatEnum {

    //Modified by Manuel Perez on 03/08/2015
    BITDUBAI_LICENSE_MANAGER("BLICM"),
    BITDUBAI_BLOCKCHAIN_INFO_WORLD("BBLOCKIW"),
    BITDUBAI_SHAPE_SHIFT_WORLD("BSHAPESW"),
    BITDUBAI_COINAPULT_WORLD("BCOINAW"),
    BITDUBAI_CRYPTO_INDEX("BCRYPTOINW"),
    BITDUBAI_BITCOIN_CRYPTO_NETWORK("BBTCCNET"),
    BITDUBAI_BITCOIN_CRYPTO_NETWORK2("BBTCCNET2"),
    BITDUBAI_CLOUD_CHANNEL("BCLOUDC"),

    BITDUBAI_WS_COMMUNICATION_CLOUD_SERVER("BWSCCLSERVER"),
    BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL("BWSCCLIENTCH"),

    BITDUBAI_CLOUD_SERVER_COMMUNICATION("BCLOUSC"),
    BITDUBAI_USER_NETWORK_SERVICE("BUSERNETS"),
    BITDUBAI_TEMPLATE_NETWORK_SERVICE("BTEMNETS"),
    BITDUBAI_INTRAUSER_NETWORK_SERVICE("BINUSERNS"),
    BITDUBAI_APP_RUNTIME_MIDDLEWARE("BAPPRUNM"),
    BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET("BDWALLBW"),
    BITDUBAI_WALLET_RUNTIME_MODULE("BWALLRUNM"),
    BITDUBAI_BITCOIN_CRYPTO_VAULT("BBTCCRYV"),
    BITDUBAI_ASSETS_CRYPTO_VAULT("BASSTCRYV"),
    BITDUBAI_INTRA_USER_FACTORY_MODULE("BINUSFACM"),
    BITDUBAI_BANK_NOTES_WALLET_WALLET_MODULE("BBNWWM"),
    BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE("BCLPWWM"),
    BITDUBAI_CRYPTO_WALLET_WALLET_MODULE("BCWWM"),
    BITDUBAI_DISCOUNT_WALLET_WALLET_MODULE("BDWWM"),
    BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE("BFOCLPWWM"),
    BITDUBAI_FIAT_OVER_CRYPTO_WALLET_WALLET_MODULE("BFOCWWM"),
    BITDUBAI_MULTI_ACCOUNT_WALLET_WALLET_MODULE("BMAWWM"),
    BITDUBAI_INCOMING_INTRA_USER_TRANSACTION("BININUST"),
    BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION("BINDEVUT"),
    BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION("BODEVUST"),
    BITDUBAI_INTER_WALLET_TRANSACTION("BINWALLT"),
    BITDUBAI_BANK_NOTES_MIDDLEWARE("BBNMIDD"),
    BITDUBAI_BANK_NOTES_NETWORK_SERVICE("BBNNETSER"),
    BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE("BWRNETSER"),
    BITDUBAI_WALLET_STORE_NETWORK_SERVICE("BWSTONETSER"),
    BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE("BWCNETSER"),
    BITDUBAI_CRYPTO_ADDRESS_BOOK("BCADDB"),
    BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION("BOUEXUT"),
    BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION("BINEXUT"),
    BITDUBAI_INCOMING_CRYPTO_TRANSACTION("BINCRYT"),
    BITDUBAI_USER_DEVICE_USER("BUDEVU"),
    BITDUBAI_ACTOR_EXTRA_USER("BAEXU"),
    BITDUBAI_USER_INTRA_USER("BUINU"),
    BITDUBAI_COINBASE_WORLD("BCOINW"),
    BITDUBAI_BITCOIN_WALLET_BASIC_WALLET("BBTCWBW"),
    BITDUBAI_DEVICE_CONNECTIVITY("BBTCDEVC"),
    BITDUBAI_LOCATION_WORLD("BLOCW"),
    BITDUBAI_ACTOR_DEVELOPER("BACTORD"),
    BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE("BWLMIDD"),
    BITDUBAI_WALLET_SKIN_MIDDLEWARE("BWSMIDD"),
    BITDUBAI_WALLET_NAVIGATION_STRUCTURE_MIDDLEWARE("BWNSMIDD"),
    BITDUBAI_SUB_APP_SETTINGS_MIDDLEWARE("BSASEMIDD"),
    BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE("BWSTANETSER"),
    BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE("BSRNETSER"),
    BITDUBAI_CCP_CRYPTO_TRANSMISSION_NETWORK_SERVICE("BCTNSER"),
    BITDUBAI_REQUEST_MONEY_REQUEST("BRMR"),
    BITDUBAI_DEVELOPER_IDENTITY("BDEVID"),
    BITDUBAI_TRANSLATOR_IDENTITY("BDTRAID"),
    BITDUBAI_IDENTITY_MANAGER("BIDMAN"),
    BITDUBAI_DESIGNER_IDENTITY("BDDESID"),
    BITDUBAI_DEVELOPER_MODULE("BDEVMOD"),
    BITDUBAI_MIDDLEWARE_NOTIFICATION("BDNOTMID"),
    BITDUBAI_DESKTOP_RUNTIME("BDR"),

    // Init CCP Plugins
    BITDUBAI_CCP_INTRA_USER_ACTOR                           ("BCCPIUA"  ),
    BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE           ("BCCPCANS" ),
    BITDUBAI_CCP_CRYPTO_CRYPTO_TRANSMISSION_NETWORK_SERVICE ("BCCPCTNS" ),
    BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE     ("BCCPCPRNS"),
    BITDUBAI_CCP_INTRA_USER_IDENTITY                        ("BCCPIUI"  ),
    BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE                 ("BCCPWCM"  ),
    BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST                     ("BCCPCPR"  ),
    BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION           ("BCCPOIAT" ),


    BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE("BCTNS"),


    // End  CCP Plugins

    //CCM Plugins

    BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR("BCCMIWUA"),

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
    BITDUBAI_ASSET_APPROPRIATION_STATS_TRANSACTION        ("BAAST"     ),
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
    BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE("BWPDWMDM"),
    BITDUBAI_WPD_WALLET_FACTORY_MIDDLEWARE("BWPDWFM"),
    BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE("BWPDWMM"),
    BITDUBAI_WPD_WALLET_PUBLISHER_MIDDLEWARE("BWPDWPM"),
    BITDUBAI_WPD_WALLET_SETTINGS_MIDDLEWARE("BWPDWSEM"),
    BITDUBAI_WPD_WALLET_STORE_MIDDLEWARE("BWPDWSTM"),
    BITDUBAI_WPD_PUBLISHER_IDENTITY("BWPDPI"),
    BITDUBAI_WPD_WALLET_COMMUNITY_NETWORK_SERVICE("BWPDWCNS"),
    BITDUBAI_WPD_WALLET_RESOURCES_NETWORK_SERVICE("BWPDWRNS"),
    BITDUBAI_WPD_WALLET_STATISTICS_NETWORK_SERVICE("BWPDWSNS"),
    BITDUBAI_WPD_WALLET_STORE_NETWORK_SERVICE("BWPDSNS"),
    BITDUBAI_WPD_WALLET_FACTORY_SUB_APP_MODULE("BWPDWFSAM"),
    BITDUBAI_WPD_WALLET_PUBLISHER_SUB_APP_MODULE("BWPDWPSAM"),
    BITDUBAI_WPD_WALLET_STORE_SUB_APP_MODULE("BWPDWSSAM"),
    // End  WPD Plugins

    //Init CSH Plugins
    BITDUBAI_CSH_MONEY_TRANSACTION_HOLD("BCSHMTH"),
    BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD("BCSHMTU"),
    BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT("BCSHMTD"),
    BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAW("BCSHMTW"),
    // End CSH Plugins

    // Init new Plugins

    ASSET_APPROPRIATION_STATS   ("ASAPS"),
    ASSET_APPROPRIATION         ("ASAP"),
    ASSET_DISTRIBUTION          ("ASD"),
    ASSET_FACTORY               ("ASF"),
    ASSET_ISSUER                ("ASI"),
    ASSET_ISSUER_COMMUNITY      ("ASIC"),
    ASSET_ISSUING               ("ASIS"),
    ASSET_RECEPTION             ("ASR"),
    ASSET_USER                  ("ASU"),
    ASSET_USER_COMMUNITY        ("ASUC"),
    ASSET_TRANSMISSION          ("AST"),
    BITCOIN_ASSET_VAULT         ("BAV"),
    BITCOIN_NETWORK             ("BN"),
    BITCOIN_VAULT               ("BV"),
    BITCOIN_WALLET              ("BW"),
    BITCOIN_WATCH_ONLY_VAULT    ("BWOV"),
    BITCOIN_HOLD                ("BHOLD"),
    BITCOIN_UNHOLD              ("BUNHOLD"),
    CRYPTO_ADDRESSES            ("CA"),
    CRYPTO_ADDRESS_BOOK         ("CAB"),
    CRYPTO_BROKER               ("CB"),
    CRYPTO_BROKER_IDENTITY      ("CBI"),
    CRYPTO_BROKER_PURCHASE      ("CBP"),
    CRYPTO_BROKER_SALE          ("CBS"),
    CRYPTO_CUSTOMER             ("CC"),
    CRYPTO_CUSTOMER_IDENTITY    ("CCI"),
    CRYPTO_PAYMENT_REQUEST      ("CPR"),
    CRYPTO_TRANSMISSION         ("CT"),
    CRYPTO_WALLET               ("CW"),
    DESKTOP_RUNTIME             ("DER"),
    DEVELOPER                   ("DEV"),
    DEVICE_USER                 ("DU"),
    EXTRA_WALLET_USER           ("EWU"),
    INCOMING_CRYPTO             ("IC"),
    INCOMING_EXTRA_USER         ("IEU"),
    INCOMING_INTRA_USER         ("IIU"),
    INTRA_WALLET_USER           ("IWU"),
    ISSUER_REDEMPTION           ("IR"),
    NOTIFICATION                ("NOT"),
    OUTGOING_EXTRA_USER         ("OEU"),
    OUTGOING_INTRA_ACTOR        ("OIA"),
    PUBLISHER                   ("PBL"),
    REDEEM_POINT                ("RP"),
    REDEEM_POINT_COMMUNITY      ("RPC"),
    REDEEM_POINT_REDEMPTION     ("RPR"),
    SUB_APP_RESOURCES           ("SAR"),
    SUB_APP_RUNTIME             ("SPR"),
    USER_REDEMPTION             ("UR"),
    WALLET_COMMUNITY            ("WCOM"),
    WALLET_CONTACTS             ("WC"),
    WALLET_FACTORY              ("WF"),
    WALLET_MANAGER              ("WM"),
    WALLET_PUBLISHER            ("WPU"),
    WALLET_RESOURCES            ("WRE"),
    WALLET_RUNTIME              ("WRU"),
    WALLET_SETTINGS             ("WSE"),
    WALLET_STATISTICS           ("WSTA"),
    WALLET_STORE                ("WST"),
    WS_CLOUD_CLIENT             ("WCL"),

    //CBP
    BANK_MONEY_RESTOCK          ("BMRE"),
    BANK_MONEY_DESTOCK          ("BMDE"),
    CASH_MONEY_RESTOCK          ("CMDE"),
    CASH_MONEY_DESTOCK          ("CMRE"),
    CRYPTO_MONEY_RESTOCK        ("CRDE"),
    CRYPTO_MONEY_DESTOCK        ("CRRE"),
    BITDUBAI_CBP_STOCK_TRANSACTIONS_BANK_MONEY_RESTOCK ("BCBPSTBMR"),
    TRANSACTION_TRANSMISSION    ("TRTX")

    // End  new Plugins

    ;

    private final String code;

    Plugins(final String code) {
        this.code = code;
    }

    public static Plugins getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "APR"  :   return SUB_APP_RUNTIME          ;
            case "ASAPS":   return ASSET_APPROPRIATION_STATS;
            case "ASAP" :   return ASSET_APPROPRIATION      ;
            case "ASD"  :   return ASSET_DISTRIBUTION       ;
            case "ASF"  :   return ASSET_FACTORY            ;
            case "ASI"  :   return ASSET_ISSUER             ;
            case "ASIC" :   return ASSET_ISSUER_COMMUNITY   ;
            case "ASIS" :   return ASSET_ISSUING            ;
            case "ASR"  :   return ASSET_RECEPTION          ;
            case "ASU"  :   return ASSET_USER               ;
            case "ASUC" :   return ASSET_USER_COMMUNITY     ;
            case "AST"  :   return ASSET_TRANSMISSION       ;
            case "BAV"  :   return BITCOIN_ASSET_VAULT      ;
            case "BN"   :   return BITCOIN_NETWORK          ;
            case "BV"   :   return BITCOIN_VAULT            ;
            case "BW"   :   return BITCOIN_WALLET           ;
            case "BWOV" :   return BITCOIN_WATCH_ONLY_VAULT ;
            case "BHOLD":   return BITCOIN_HOLD             ;
            case "BUNHOLD": return BITCOIN_UNHOLD           ;
            case "CA"   :   return CRYPTO_ADDRESSES         ;
            case "CAB"  :   return CRYPTO_ADDRESS_BOOK      ;
            case "CB"   :   return CRYPTO_BROKER            ;
            case "CBI"  :   return CRYPTO_BROKER_IDENTITY   ;
            case "CC"   :   return CRYPTO_CUSTOMER          ;
            case "CCI"  :   return CRYPTO_CUSTOMER_IDENTITY ;
            case "CPR"  :   return CRYPTO_PAYMENT_REQUEST   ;
            case "CT"   :   return CRYPTO_TRANSMISSION      ;
            case "CW"   :   return CRYPTO_WALLET            ;
            case "DER"  :   return DESKTOP_RUNTIME          ;
            case "DEV"  :   return DEVELOPER                ;
            case "DU"   :   return DEVICE_USER              ;
            case "EWU"  :   return EXTRA_WALLET_USER        ;
            case "IC"   :   return INCOMING_CRYPTO          ;
            case "IEU"  :   return INCOMING_EXTRA_USER      ;
            case "IIU"  :   return INCOMING_INTRA_USER      ;
            case "IWU"  :   return INTRA_WALLET_USER        ;
            case "IR"   :   return ISSUER_REDEMPTION        ;
            case "NOT"  :   return NOTIFICATION             ;
            case "OEU"  :   return OUTGOING_EXTRA_USER      ;
            case "OIA"  :   return OUTGOING_INTRA_ACTOR     ;
            case "PBL"  :   return PUBLISHER                ;
            case "RP"   :   return REDEEM_POINT             ;
            case "RPC"  :   return REDEEM_POINT_COMMUNITY   ;
            case "RPR"  :   return REDEEM_POINT_REDEMPTION  ;
            case "SAR"  :   return SUB_APP_RESOURCES        ;
            case "SPR"  :   return SUB_APP_RUNTIME          ;
            case "UR"   :   return USER_REDEMPTION          ;
            case "WCOM" :   return WALLET_COMMUNITY         ;
            case "WC"   :   return WALLET_CONTACTS          ;
            case "WF"   :   return WALLET_FACTORY           ;
            case "WM"   :   return WALLET_MANAGER           ;
            case "WPU"  :   return WALLET_PUBLISHER         ;
            case "WRE"  :   return WALLET_RESOURCES         ;
            case "WRU"  :   return WALLET_RUNTIME           ;
            case "WSE"  :   return WALLET_SETTINGS          ;
            case "WSTA" :   return WALLET_STATISTICS        ;
            case "WST"  :   return WALLET_STORE             ;
            case "WCL"  :   return WS_CLOUD_CLIENT          ;
            case ("BMRE"):  return BANK_MONEY_RESTOCK       ;
            case ("BMDE"):  return BANK_MONEY_DESTOCK       ;
            case ("CMRE"):  return CASH_MONEY_RESTOCK       ;
            case ("CMDE"):  return CASH_MONEY_DESTOCK       ;
            case ("CRRE"):  return CRYPTO_MONEY_RESTOCK     ;
            case ("CRDE"):  return CRYPTO_MONEY_DESTOCK     ;
            case ("TRTX"):  return TRANSACTION_TRANSMISSION ;
            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This Code Is Not Valid for the Plugins enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
