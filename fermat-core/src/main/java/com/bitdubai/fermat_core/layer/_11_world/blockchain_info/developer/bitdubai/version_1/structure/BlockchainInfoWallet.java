package com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_world.CryptoWallet;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.DealsWithBlockchainInfoApi;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Address;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.PaymentResponse;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Wallet;

import java.io.IOException;
import java.util.UUID;
import com.bitdubai.fermat_api.layer._2_os.database_system.*;

/**
 * Created by ciencias on 3/19/15.
 */
public class BlockchainInfoWallet implements CryptoWallet ,DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, DealsWithBlockchainInfoApi {


    private UUID pluginId;

    public BlockchainInfoWallet( UUID pluginId,UUID walletId){

        this.pluginId = pluginId;
        this.walletId = walletId;

    }

    /**
     * UsesFileSystem Interface member variable
     */
    PluginFileSystem pluginFileSystem;

    /**
     * UsesFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void start() {
        /**
         * open database connection,read transaction tables
         * if not exist create it
         */
        try{
            // NATALIA TODO: como nombre de la base de datos vamos a usar un hash del id del plugin, asi nadie puede saber cual es el id, aunque tenga acceso a listar las bases de datos existentes. Esto es valido para cualquier base de datos que creemos y el hasheo lo maneja internamente el DatabaseSystem
            Database walletDatabase = pluginDatabaseSystem.openDatabase(this.pluginId,this.walletId.toString());
            //NATALIA TODO: En general cuando un metodo no puede hacer lo que se supone que tiene que hacer, debe disparar una excepcion. En el caso de openDatabase si no la puede abrir debe disparar DatabaseNotFoundException . No vamos a usar la tecnica de devolver null nunca en este sistema.
            if(walletDatabase == null){

                walletDatabase = pluginDatabaseSystem.createDatabase(this.pluginId,this.walletId.toString());

                DatabaseTable walletTable = walletDatabase.newTable();
                DatabaseTableColumn walletTableColum = walletTable.newColumn(); //Id, hash,tx_index, time,relayed_by, address_input, value_input
                //walletDatabase.createTable("INCOMING_CRYPTO");
            }

        }
        catch (CantOpenDatabaseException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void stop() {

    }

    /**
     * CryptoWallet Interface member variable
     */

    private String password = "";
    private String apiKey = "";
    private String walletAddress ="";
    private String walletGuid ="";
    private UUID walletId;

    /**
     * CryptoWallet Interface implementation.
     */

    @Override
    public long getWalletBalance(CryptoCurrency cryptoCurrency){
        long balance = 0;
        try{
            //get wallet property files, address and guid, and get actual balance
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, "wallets_data", this.walletId + ".txt", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            String[] walletData = layoutFile.getContent().split(";");

            this.walletGuid = walletData[1].toString();
            this.password = walletData[4].toString();

            Wallet wallet = new Wallet(this.walletGuid, this.password);
            wallet.setApiCode(this.apiKey);
            balance = wallet.getBalance();


        } catch (APIException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}
        catch (FileNotFoundException e) {
            e.printStackTrace();}
        return balance;
    }

    @Override
    public long getAddressBalance(CryptoAddress cryptoAddress){
        long addressBalance = 0;
        try{

            this.walletAddress = cryptoAddress.getAddress();
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, "wallets_data", this.walletId + ".txt", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String[] walletData = layoutFile.getContent().split(";");

            this.password = walletData[4].toString();
            Wallet wallet = new Wallet(this.walletGuid, this.password);
            wallet.setApiCode(this.apiKey);
            // get an address from your wallet and include only transactions with up to 3
            // confirmations in the balance
            Address addr = wallet.getAddress(this.walletAddress, 3);
            addressBalance =  addr.getBalance();

        } catch (APIException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}
        catch (FileNotFoundException e) {
            e.printStackTrace();}

        return addressBalance;
    }

    @Override
    public void sendCrypto (CryptoCurrency cryptoCurrency, long amount, CryptoAddress cryptoAddress){
        // send 0.2 bitcoins with a custom fee of 0.01 BTC and a note
        // public notes require a minimum transaction size of 0.005 BTC
        try{
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, "wallets_data", walletId + ".txt", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            String[] walletData = layoutFile.getContent().split(";");
            walletAddress = walletData[0].toString();
            walletGuid = walletData[1].toString();

            Wallet wallet = new Wallet(walletGuid, password);
            PaymentResponse payment = wallet.send(cryptoAddress.getAddress(), amount, null,null, "");

        } catch (APIException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}
        catch (FileNotFoundException e) {
            e.printStackTrace();}


    }

    /**
     * DealsWithPluginDatabaseSystem Interface member variable
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
