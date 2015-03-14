package com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._7_world.CantCreateCryptoWalletException;
import com.bitdubai.fermat_api.layer._7_world.CryptoWallet;
import com.bitdubai.fermat_api.layer._7_world.CryptoWalletManager;
import com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.createwallet.CreateWallet;
import com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.createwallet.CreateWalletResponse;
import com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.*;
import com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.*;
import java.io.IOException;
/**
 * Created by toshiba on 13/03/2015.
 */
public class BlockchainInfoBitcoinWallet implements CryptoWallet, CryptoWalletManager {

    private String password = "nattyco2013";
    private String apiCode = "2MyHVQT1KybvVBT5wzWzgAW6if1VMc1Trqw";
    private String walletAddress ="";
    private String walletGuid ="25c34cee-1129-4e53-bf49-726530b0cc58";

    public long getWalletBalance(CryptoCurrency cryptoCurrency){
        long balance = 0;
        try{
            Wallet wallet = new Wallet(walletGuid, password);
            wallet.setApiCode(apiCode);
            balance = wallet.getBalance();


        } catch (APIException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}
        return balance;
    }

    public long getAddressBalance(CryptoAddress cryptoAddress){
        long addressBalance = 0;
        try{
        Wallet wallet = new Wallet(walletGuid, password);
        wallet.setApiCode(apiCode);
        // get an address from your wallet and include only transactions with up to 3
        // confirmations in the balance
        Address addr = wallet.getAddress(walletAddress, 3);
            addressBalance =  addr.getBalance();

        } catch (APIException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}
        return addressBalance;
    }

    public void sendCrypto (CryptoCurrency cryptoCurrency, long amount, CryptoAddress cryptoAddress){
        // send 0.2 bitcoins with a custom fee of 0.01 BTC and a note
        // public notes require a minimum transaction size of 0.005 BTC
        try{
            Wallet wallet = new Wallet(walletGuid, password);
            PaymentResponse payment = wallet.send("1dice6YgEVBf88erBFra9BHf6ZMoyvG88", amount, null,null, "");

        } catch (APIException e) {
        e.printStackTrace();}
        catch (IOException e) {
        e.printStackTrace();}

    }

    //TODO : NATALIA: Este metodo no va aca, y no es esta la clase que implementa esta interface, sino el plugin Root. sino, como desde el Fragmento accederias a una referencia a esta clase?
    // TODO: NATALIA: Lo que actaulmente tenes implementado aca deberia ir en el constructor de esta clase.
    public void createWallet (CryptoCurrency cryptoCurrency ) throws CantCreateCryptoWalletException{
        //save wallet guid, address and link in a binary file on disk
        try{
            CreateWalletResponse response =  CreateWallet.create(apiCode, password);
            String  walletAddress = response.getAddress();
            String  walletGuid = response.getIdentifier();
            String walletLink = response.getLink();
        } catch (APIException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}

    }
}
