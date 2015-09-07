package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.Vaults;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.VaultNotConnectedToNetworkException;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantCreateDigitalAssetException;
import com.bitdubai.fermat_dap_api.exceptions.CantSetObjectException;
//import com.bitdubai.fermat_dap_api.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_dap_api.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public class DigitalAssetCryptoTransactionFactory implements DealsWithErrors{

    CryptoAddressBookManager cryptoAddressBookManager;
    CryptoVaultManager cryptoVaultManager;
    DigitalAsset digitalAsset;
    ErrorManager errorManager;
    String deliveredByActorPublicKey;
    Actors deliveredByType;
    String deliveredToActorPublicKey;
    Actors deliveredToType;
    String walletPublicKey;
    ReferenceWallet walletType;

    public DigitalAssetCryptoTransactionFactory(CryptoVaultManager cryptoVaultManager, CryptoAddressBookManager cryptoAddressBookManager) throws CantSetObjectException {

        setCryptoAddressBookManagerManager(cryptoAddressBookManager);
        setCryptoVaultManager(cryptoVaultManager);

    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {

        this.errorManager=errorManager;

    }

    public void setCryptoAddressBookManagerManager(CryptoAddressBookManager cryptoAddressBookManager) throws CantSetObjectException{

        if(cryptoAddressBookManager==null){

            throw new CantSetObjectException("CryptoAddressBookManager is null");

        }
        this.cryptoAddressBookManager=cryptoAddressBookManager;

    }

    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) throws CantSetObjectException{

        if(cryptoVaultManager==null){

            throw new CantSetObjectException("CryptoVaultManager is null");

        }
        this.cryptoVaultManager=cryptoVaultManager;

    }

    private void setDigitalAssetGenesisAmount() throws CantSetObjectException{

        try{
            CryptoAddress genesisAddress=this.cryptoVaultManager.getAddress();
            digitalAsset.setGenesisAddress(genesisAddress);
        }catch(Exception exception){
            throw new CantSetObjectException(exception, "Setting GenesisAddress to DigitalAsset","Unexpected exception");
        }

    }

    public void setActors(String deliveredByActorPublicKey, Actors deliveredByType, String deliveredToActorPublicKey, Actors deliveredToType){

        this.deliveredByActorPublicKey=deliveredByActorPublicKey;
        this.deliveredByType=deliveredByType;
        this.deliveredToActorPublicKey=deliveredToActorPublicKey;
        this.deliveredToType=deliveredToType;

    }

    public void setWallet(String walletPublicKey, ReferenceWallet walletType){
        this.walletPublicKey=walletPublicKey;
        this.walletType=walletType;
    }

    private void areObjectsSettled() throws ObjectNotSetException{

        if(deliveredByActorPublicKey==null){
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
        }

    }

    public void createDigitalAssetCryptoTransaction(DigitalAsset digitalAsset) throws CantCreateDigitalAssetException{

        /**
         * TODO:
         * Se completa la crypto transacción especificando el GenesisAmount.
         * Se ejecuta un hash del Digital Asset y el mismo se coloca en el OP_RETURN de la crypto transacción.
         * Se commitea la transacción y la misma es publicada a la red de bitcoin a través de la Crypto Network.
         * Se asocia la metadata a la genesis transaction.
         * El Digital Asset es considerado creado, en este paso el estado (State) del Asset se fija en _final_. Una vez que el Digital Asset entra en estado _final_ se considera creado,
         en este estado, sus propiedades no deben ser modificadas por ningún actor de la plataforma, ya que esto modificaría la relación entre la metadata propia del Digital Asset y
         su respectiva GenesisTransaction.
         * El Digital Asset se considera _available_ cuando la transacción bitcoin es detectada como entrante por el Incoming Crypto.
         * Todos los Digital Assets generados deben ser transferidos al plugin AssetDistribution, este plugin se encargará de realizar la transferencia, de acuerdo a los
         criterios establecidos por AssetIssuer.
         * */
//TODO: crear un monitot/agente que coordine la construccion de un asset y retome en caso que exista una interrupción del proceso
        try{

            this.digitalAsset=digitalAsset;
            //Check if the Actors are set
            areObjectsSettled();
            //We need to get a new GenesisAddress:
            this.cryptoVaultManager.connectToBitcoin();
            setDigitalAssetGenesisAmount();
            //TODO no usar addressBook
            this.cryptoAddressBookManager.registerCryptoAddress(
                    this.digitalAsset.getGenesisAddress(),
                    this.deliveredByActorPublicKey,
                    this.deliveredByType,
                    this.deliveredToActorPublicKey,
                    this.deliveredToType,
                    Platforms.DIGITAL_ASSET_PLATFORM,
                    Vaults.BITCOIN_VAULT,
                    this.walletPublicKey,
                    this.walletType);
            //TODO: hacer una prueba para la solicitud de direcciones
            //We need to complete the transaction

            this.cryptoVaultManager.disconnectFromBitcoin();

        } catch(VaultNotConnectedToNetworkException exception) {
            throw new CantCreateDigitalAssetException(exception, "Creating a new Digital Asset Transaction - Connecting to Network", "Vault is not connected");
        } catch(CantSetObjectException exception){
            throw new CantCreateDigitalAssetException(exception, "Creating a new Digital Asset Transaction - Setting GenesisAddress", "Unexpected Exception");
        } catch(ObjectNotSetException exception){
            throw new CantCreateDigitalAssetException(exception, "Creating a new Digital Asset Transaction - Checking if actors are set", "Some actor is not set");
        } catch(CantRegisterCryptoAddressBookRecordException exception){
            throw new CantCreateDigitalAssetException(exception, "Creating a new Digital Asset Transaction - Registring cryptoAddres in AddressBook", "Please, Cceck the cause");
        }

    }

}
