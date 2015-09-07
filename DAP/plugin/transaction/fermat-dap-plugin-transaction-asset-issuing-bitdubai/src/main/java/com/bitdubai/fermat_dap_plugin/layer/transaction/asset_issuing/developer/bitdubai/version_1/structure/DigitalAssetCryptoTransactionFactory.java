package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.VaultNotConnectedToNetworkException;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CryptoWalletBalanceInsufficientException;
import com.bitdubai.fermat_dap_api.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public class DigitalAssetCryptoTransactionFactory implements DealsWithErrors{

    //CryptoAddressBookManager cryptoAddressBookManager;
    CryptoVaultManager cryptoVaultManager;
    CryptoWallet cryptoWallet;
    DigitalAsset digitalAsset;
    ErrorManager errorManager;
    /*String deliveredByActorPublicKey;
    Actors deliveredByType;
    String deliveredToActorPublicKey;
    Actors deliveredToType;
    String walletPublicKey;
    ReferenceWallet walletType;*/
    final long MINIMAL_TRANSACTION_FEE=300;
    /**
     * Minimal Asset quiantity to send
     */
    final int MINIMAL_QUANTITY=1;
    /**
     * Assuming that the DigitalAsset.UnitValue=0.
     * */
    //final long MINIMAL_GENESIS_AMOUNT=MINIMAL_TRANSACTION_FEE*MINIMAL_QUANTITY;

    public DigitalAssetCryptoTransactionFactory(CryptoVaultManager cryptoVaultManager, CryptoWallet cryptoWallet/*, CryptoAddressBookManager cryptoAddressBookManager*/) throws CantSetObjectException {

        //setCryptoAddressBookManagerManager(cryptoAddressBookManager);
        setCryptoVaultManager(cryptoVaultManager);
        setCryptoWallet(cryptoWallet);

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

    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) throws CantSetObjectException{

        if(cryptoVaultManager==null){

            throw new CantSetObjectException("CryptoVaultManager is null");

        }
        this.cryptoVaultManager=cryptoVaultManager;

    }

    private void setDigitalAssetGenesisAmount() throws CantSetObjectException{

        try{
            //CryptoAddress genesisAddress=this.cryptoVaultManager.getAddress();
            //digitalAsset.setGenesisAddress(genesisAddress);
        }catch(Exception exception){
            throw new CantSetObjectException(exception, "Setting GenesisAddress to DigitalAsset","Unexpected exception");
        }

    }

    /*public void setActors(String deliveredByActorPublicKey, Actors deliveredByType, String deliveredToActorPublicKey, Actors deliveredToType){

        this.deliveredByActorPublicKey=deliveredByActorPublicKey;
        this.deliveredByType=deliveredByType;
        this.deliveredToActorPublicKey=deliveredToActorPublicKey;
        this.deliveredToType=deliveredToType;

    }*/

    /*public void setWallet(String walletPublicKey, ReferenceWallet walletType){
        this.walletPublicKey=walletPublicKey;
        this.walletType=walletType;
    }*/

    private void areObjectsSettled() throws ObjectNotSetException{

        /*if(deliveredByActorPublicKey==null){
            throw new ObjectNotSetException("deliveredByActorPublicKey is not set");
        }
        if(deliveredByType==null){
            throw new ObjectNotSetException("deliveredByType is not set");
        }
        if(deliveredToActorPublicKey==null){
            throw new ObjectNotSetException("deliveredToActorPublicKey is not set");
        }
        if(deliveredToType==null){
            throw new ObjectNotSetException("deliveredToType is not set");
        }
        if(walletPublicKey==null){
            throw new ObjectNotSetException("deliveredToActorPublicKey is not set");
        }
        if(walletType==null){
            throw new ObjectNotSetException("deliveredToType is not set");
        }*/
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

    //This method can change in the future, I prefer design an monitor to create Digital Asset.
    public void createDigitalAssetCryptoTransaction(DigitalAsset digitalAsset) throws CantCreateDigitalAssetTransactionException {

        /**
         * TODO:
         * 3) Al estar todo completado para emitir el Asset, se persiste el DA (en DB o archivo, creo que sería mejor archivo) y TranscationStatus para a estar en estado FormingGenesis.

         4) Se llama al método generateEmptyTransactionHash() de la CryptoVault que devolverá un String con el Hash de la genesis Transaction. El especialista de la transacción
         para a ser en este momento la CryptoVault hasta que devuelve el valor esperado. Se persiste este valor.

         5) Se llama al método requestGenesisAddress de la AssetWallet y se persistirá este valor en la GenesisAddress.

         6) Se creará el objeto DigitalAssetMetadata y se generará el hash del DA con el método getDigitalAssetHash. La transacción pasaría a estar en estado PendingSubmitCryptoNetwork.

         7) Se enviará la transacción a través de la cryptoVault utilizando el metodo send de la CryptoWallet. La transacción pasa a estado PendingRecieveCryptoNetwork

         8) Al momento de ingresar la transacción bitcoin a través de la crypto Network, la transacción pasa a estado PendingConfirmCryptoNetwork y ejecutamos un crédito en el book
         balance de la Asset Wallet. En este momento, el DigitalAssetMetadata queda persistido en la Asset Wallet.

         9) Al confirmarse la transacción en la cryptoNetwork la transacción pasa a estado PendingCreditIssuerWallet y se genera el crédito  en el Available balance en la
         Issuer Wallet llamando a un método a crear por Franklin.

         10) La issuerWallet genera un crédito en el available balance de la wallet.

         11) La transacción finaliza.
         * */
//TODO: crear un monitot/agente que coordine la construccion de un asset y retome en caso que exista una interrupción del proceso
        try{

            this.digitalAsset=digitalAsset;
            //Check if the Actors are set
            areObjectsSettled();
            //Check if the CryptoWallet has the needed amount
            checkCryptoWalletBalance();
            //We need to get a new GenesisAddress:
            //this.cryptoVaultManager.connectToBitcoin();
            setDigitalAssetGenesisAmount();

            /*this.cryptoAddressBookManager.registerCryptoAddress(
                    this.digitalAsset.getGenesisAddress(),
                    this.deliveredByActorPublicKey,
                    this.deliveredByType,
                    this.deliveredToActorPublicKey,
                    this.deliveredToType,
                    Platforms.DIGITAL_ASSET_PLATFORM,
                    Vaults.BITCOIN_VAULT,
                    this.walletPublicKey,
                    this.walletType);*/
            //TODO: hacer una prueba para la solicitud de direcciones
            //We need to complete the transaction

            this.cryptoVaultManager.disconnectFromBitcoin();

        } /*catch(VaultNotConnectedToNetworkException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Connecting to Network", "Vault is not connected");
        }*/ catch(CantSetObjectException exception){
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Setting GenesisAddress", "Unexpected Exception");
        } catch(ObjectNotSetException exception){
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Checking if actors are set", "Some actor is not set");
        } /*catch(CantRegisterCryptoAddressBookRecordException exception){
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Registring cryptoAddres in AddressBook", "Please, Cceck the cause");
        }*/ catch (CantGetBalanceException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Checking if the balance is sufficient", "Can't get the Crypto Wallet balance");
        } catch (CryptoWalletBalanceInsufficientException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception, "Creating a new Digital Asset Transaction - Checking if the balance is sufficient", "The balance is insufficient");
        }

    }

}
