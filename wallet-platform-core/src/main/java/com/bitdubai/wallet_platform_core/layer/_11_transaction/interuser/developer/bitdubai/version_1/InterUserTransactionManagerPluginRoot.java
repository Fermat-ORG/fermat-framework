package com.bitdubai.wallet_platform_core.layer._11_transaction.interuser.developer.bitdubai.version_1;

/**
 * Created by ciencias on 2/16/15.
 */

/**
 * This plugin handles Inter User transactions, meaning transactions happening between users of the platform in both ends.
 * 
 * One of the reasons for this plugin to exist is that a user can send money to another without a payment request at all.
 * In this case when the transaction is received by the payed user, someone has to decide to which wallet to send it. 
 * 
 * As this plugin is  monitoring all User to User transactions, it is the one perfect for the job of deciding where to 
 * send the payment received.
 * 
 * It can also process queries of all such transactions that happened in the past. 
 * 
 * * * * * 
 */

public class InterUserTransactionManagerPluginRoot {
}

// Loui TODO; Conecta este nuevo plugin para inicializarlo desde la plataforma sin olvidarte del PluginsIdentityManager, y todo el resto.