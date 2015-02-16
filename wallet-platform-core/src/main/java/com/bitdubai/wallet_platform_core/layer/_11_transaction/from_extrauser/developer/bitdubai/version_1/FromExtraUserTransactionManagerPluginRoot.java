package com.bitdubai.wallet_platform_core.layer._11_transaction.from_extrauser.developer.bitdubai.version_1;

/**
 * Created by ciencias on 2/16/15.
 */

/**
 * The From Extra User Transaction Manager Plugin is in charge of coordinating the transactions coming from outside the
 * system, meaning from people not a user of the platform. Usually this situation is detected by the crypto network plugin
 * monitoring the crypto network and seeing an incoming payment.
 * 
 * This plugin knows which wallet to store the funds.
 * 
 * Usually a crypto address is generated from a particular wallet, and that payment should go there, but there is nothing
 * preventing a user to uninstall a wallet and discard the underlying structure in which the user interface was relaying.
 * 
 * For that reason it is necessary this middle man, to get sure any incoming payment for any wallet that ever existed is
 * not lost.
 * 
 * It can send the funds to a default wallet is some is defined or stored itself until the user manually release them.
 * 
 * It is also a centralized place where to query all of the incoming transaction from outside the system.
 *
 * 
 * * * * * * * 
 * * * 
 */

public class FromExtraUserTransactionManagerPluginRoot {
}



// Loui TODO; Conecta este nuevo plugin para inicializarlo desde la plataforma sin olvidarte del PluginsIdentityManager, y todo el resto.


