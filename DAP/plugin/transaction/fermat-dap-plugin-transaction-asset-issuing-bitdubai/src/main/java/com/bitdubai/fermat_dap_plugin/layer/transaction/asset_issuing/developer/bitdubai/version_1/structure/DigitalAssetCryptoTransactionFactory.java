package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CryptoWalletBalanceInsufficientException;
import com.bitdubai.fermat_dap_api.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public class DigitalAssetCryptoTransactionFactory implements DealsWithErrors{

    CryptoVaultManager cryptoVaultManager;
    CryptoWallet cryptoWallet;
    DigitalAsset digitalAsset;
    ErrorManager errorManager;
    PluginFileSystem pluginFileSystem;
    UUID pluginId;

    final long MINIMAL_TRANSACTION_FEE=300;
    /**
     * Minimal Asset quiantity to send
     */
    final int MINIMAL_QUANTITY=1;
    /**
     * Assuming that the DigitalAsset.UnitValue=0.
     * */
    //final long MINIMAL_GENESIS_AMOUNT=MINIMAL_TRANSACTION_FEE*MINIMAL_QUANTITY;
    final String LOCAL_STORAGE_PATH="digital-asset/";
    String digitalAssetLocalFilePath;

    public DigitalAssetCryptoTransactionFactory(UUID pluginId, CryptoVaultManager cryptoVaultManager, CryptoWallet cryptoWallet, PluginFileSystem pluginFileSystem/*, CryptoAddressBookManager cryptoAddressBookManager*/) throws CantSetObjectException {

        setCryptoVaultManager(cryptoVaultManager);
        setCryptoWallet(cryptoWallet);
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);

    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {

        this.errorManager=errorManager;

    }

    public void setCryptoWallet(CryptoWallet cryptoWallet) throws CantSetObjectException{
        if(cryptoWallet==null){
            throw new CantSetObjectException("CryptoWallet is null");
        }
        this.cryptoWallet=cryptoWallet;
    }

    public void setPluginId(UUID pluginId) throws CantSetObjectException{
        if(pluginId==null){
            throw new CantSetObjectException("PluginId is null");
        }
        this.pluginId=pluginId;
    }

    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException{
        if(pluginFileSystem==null){
            throw new CantSetObjectException("pluginFileSystem is null");
        }
        this.pluginFileSystem=pluginFileSystem;
    }

    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) throws CantSetObjectException{

        if(cryptoVaultManager==null){

            throw new CantSetObjectException("CryptoVaultManager is null");

        }
        this.cryptoVaultManager=cryptoVaultManager;

    }

    private void setDigitalAssetGenesisAmount() throws CantSetObjectException{

        try{
            //TODO: Implement this method with new CryptoVault version
            //CryptoAddress genesisAddress=this.cryptoVaultManager.getAddress();
            //digitalAsset.setGenesisAddress(genesisAddress);
        }catch(Exception exception){
            throw new CantSetObjectException(exception, "Setting GenesisAddress to DigitalAsset","Unexpected exception");
        }

    }

    private void areObjectsSettled() throws ObjectNotSetException{

        if(this.digitalAsset.getContract()==null){
            throw new ObjectNotSetException("Digital Asset Contract is not set");
        }
        if(this.digitalAsset.getResources()==null){
            throw new ObjectNotSetException("Digital Asset Resources is not set");
        }
        if(this.digitalAsset.getDescription()==null){
            throw new ObjectNotSetException("Digital Asset Description is not set");
        }
        long digitalAssetTransactionFee=this.digitalAsset.getTransactionFee();
        if(digitalAssetTransactionFee<MINIMAL_TRANSACTION_FEE){
            throw new ObjectNotSetException("Digital Asset Genesis Transaction Fee is insufficient: "+digitalAssetTransactionFee);
        }
        int digitalAssetQuantity=this.digitalAsset.getQuantity();
        if(digitalAssetQuantity<MINIMAL_QUANTITY){
            throw new ObjectNotSetException("Digital Asset quantity is insufficient: "+digitalAssetQuantity);
        }
        if(this.digitalAsset.getName()==null){
            throw new ObjectNotSetException("Digital Asset Name is not set");
        }
        if(this.digitalAsset.getPublicKey()==null){
            throw new ObjectNotSetException("Digital Asset PublicKey is not set");
        }
        if(this.digitalAsset.getState()==null){
            //throw new ObjectNotSetException("Digital Asset State is not set");
            digitalAsset.setState(State.DRAFT);
        }
        checkGenesisAmount();

    }

    private void checkGenesisAmount() throws ObjectNotSetException{
        long digitalAssetGenesisAmount=this.digitalAsset.getGenesisAmount();
        long calculatedDigitalAssetGenesisAmount=calculateGenesisAmount();
        if(calculatedDigitalAssetGenesisAmount!=digitalAssetGenesisAmount){
            throw new ObjectNotSetException("The Genesis Amount set in Digital Asset is incorrect: '"+digitalAssetGenesisAmount+"' is set in object, Asset Issuing plugin calculates in '"+calculatedDigitalAssetGenesisAmount+"'");
        }
    }

    private long calculateGenesisAmount(){
        int digitalAssetQuantity=this.digitalAsset.getQuantity();
        long digitalAssetTransactionFee=this.digitalAsset.getTransactionFee();
        long digitalAssetUnitValue=this.digitalAsset.getUnitValue();
        long genesisAmount=((long)digitalAssetQuantity*digitalAssetUnitValue)+(digitalAssetQuantity*digitalAssetTransactionFee);
        return genesisAmount;
    }

    private void checkCryptoWalletBalance() throws CryptoWalletBalanceInsufficientException, CantGetBalanceException {

        String digitalAssetPublicKey=this.digitalAsset.getPublicKey();
        long digitalAssetGenesisAmount=this.digitalAsset.getGenesisAmount();
        long cryptoWalletBalance=this.cryptoWallet.getAvailableBalance(digitalAssetPublicKey);

        if(digitalAssetGenesisAmount>cryptoWalletBalance){

            throw new CryptoWalletBalanceInsufficientException("The current balance in Wallet "+digitalAssetPublicKey+" is "+cryptoWalletBalance+" the amount needed is "+digitalAssetGenesisAmount);

        }

    }

    private void persistInLocalStorage() throws CantCreateDigitalAssetFileException {
        //Path structure: digital-asset/walletPublicKey/name.xml
        try{

            String digitalAssetFileStoragePath=this.LOCAL_STORAGE_PATH+this.digitalAsset.getPublicKey();
            String digitalAssetFileName=this.digitalAsset.getName()+".xml";
            this.digitalAssetLocalFilePath=digitalAssetFileStoragePath+"/"+digitalAssetFileName;
            String digitalAssetInnerXML=digitalAsset.toString();
            PluginTextFile digitalAssetFile=this.pluginFileSystem.createTextFile(this.pluginId, digitalAssetFileStoragePath,digitalAssetFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            digitalAssetFile.setContent(digitalAssetInnerXML);

        }catch(CantCreateFileException exception){
            throw new CantCreateDigitalAssetFileException(exception,"Persisting Digital Asset in local storage","Can't create '"+this+digitalAssetLocalFilePath+"' file");
        }catch(Exception exception){
            throw new CantCreateDigitalAssetFileException(exception,"Persisting Digital Asset in local storage","Unexpected Exception");
        }

    }

    //This method can change in the future, I prefer design an monitor to create Digital Asset.
    public void createDigitalAssetCryptoTransaction(DigitalAsset digitalAsset) throws CantCreateDigitalAssetTransactionException {

        /**
         * TODO:
         1) La AssetIssuer subApp, a través de un wizard solicitará los datos básicos necesarios para la creación del asset. En el mismo se
         realizarán distintas especificaciones del asset y el contrato inicial.

         La Issuer Subapp debe mostrar al usuario el monto final de la transacción bitcoin (GenesisAmount) para su aprobación mediante
         el uso de los siguientes campos:
         * Cantidad de Assets que se están creando (DigitalAsset.quantity)
         * Valor de cada Asset. (DigitalAsset.unitValue)
         * Valor del Fee de cada transacción. (DigitalAsset.transactionFee).

         El valor del GenesisAmount se calculará de la siguiente forma:

         *DigitalAsset.quantity * DigitalAsset.unitValue + ( DigitalAsset.transactionFee * DigitalAsset.quantity)*

         El DigitalAsset.transactionFee es un valor que calculará la cryptoVault a través del método getEstimatedFeeValue.

         La Issuer subApp no permitirá iniciar el proceso de issuing del Asset si no dispone los fondos disponibles para cubrir el GenesisAmount.

         La transacción de Asset Issuing se dará inicio a través del mñetodo IssueAsset(DigitalAsset digitalAsset).

         El primer paso es asegurarse que el DigitalAsset (DA) está completo en todos sus campos y contrato. Las únicas propiedades que no deben estar completas son GenesisTransaction y
         GenesisAddress.


         2) Al estar todo completado para emitir el Asset, se persiste el DA (en archivo) y se actualiza el TransactionStatus a FormingGenesis.

         3) Se llama al método generateEmptyTransactionHash() de la CryptoVault que devolverá un String con el Hash de la genesis Transaction. El especialista de la transacción
         para a ser en este momento la CryptoVault hasta que devuelve el valor esperado. Se actualiza este valor en el DA y se persisten los cambios.

         4) Se llama al método requestGenesisAddress de la AssetWallet y se persistirá este valor en la GenesisAddress. Esta solicitud de dirección
         debe ser registrada en el CryptoAddressBook a través del método registerCryptoAddress.

         5) Se creará el objeto DigitalAssetMetadata y se generará el hash del DA con el método getDigitalAssetHash. La transacción pasaría a estar en estado PendingSubmitCryptoNetwork.

         6) Se enviará la transacción a través de la cryptoVault utilizando el metodo send de la CryptoWallet y se pasan los siguientes valores:

         * transactionId: el obtenido en el paso 3.
         * addressTo: la obtenida en el paso 4.
         * OP_RETURN: el hash del objeto DigitalAssetMetadata (DigitalAssetMetadata.getDigitalAssetHash())
         * Amount: el valor calculado en el punto 1.

         La transacción pasa a estado PendingReceiveCryptoNetwork

         7) Al momento de ingresar la transacción bitcoin a través de la crypto Network, la transacción pasa a estado PendingConfirmCryptoNetwork y ejecutamos un crédito en el book
         balance de la Asset Wallet. En este momento, el DigitalAssetMetadata queda persistido en la Asset Wallet. La transacción debe escuchar los eventos
         del incoming crypto.

         8) Al confirmarse la transacción en la cryptoNetwork la transacción pasa a estado PendingConfirmationIssuerWallet y se genera el crédito  en el Available balance en la
         Issuer Wallet.

         9) La issuerWallet genera un crédito en el available balance de la wallet y confirma que toma posesión del DA.

         10) La transacción finaliza y actualiza a Finalized.
         * */
//TODO: crear un monitot/agente que coordine la construccion de un asset y retome en caso que exista una interrupción del proceso
        try{

            this.digitalAsset=digitalAsset;
            //Check if the Actors are set
            areObjectsSettled();
            //Check if the CryptoWallet has the needed amount
            checkCryptoWalletBalance();
            //Persist Digital Asset to local storage
            persistInLocalStorage();
            //We need to get a new GenesisAddress:
            //this.cryptoVaultManager.connectToBitcoin();
            //setDigitalAssetGenesisAmount();

            //TODO: hacer una prueba para la solicitud de direcciones
            //We need to complete the transaction

            this.cryptoVaultManager.disconnectFromBitcoin();

        }  /*catch(CantSetObjectException exception){
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Setting GenesisAddress", "Unexpected Exception");
        }*/ catch(ObjectNotSetException exception){
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Checking if actors are set", "Some actor is not set");
        }  catch (CantGetBalanceException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Checking if the balance is sufficient", "Can't get the Crypto Wallet balance");
        } catch (CryptoWalletBalanceInsufficientException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Checking if the balance is sufficient", "The balance is insufficient");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Persisting Digital Asset in local Storage", "A local storage procedure exception");
        }

    }

}
