package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.VaultNotConnectedToNetworkException;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.asset_issuing.exceptions.CantCreateDigitalAssetException;
import com.bitdubai.fermat_dap_api.exceptions.CantSetObjectException;
//import com.bitdubai.fermat_dap_api.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public class DigitalAssetCryptoTransactionFactory implements DealsWithErrors{

    CryptoVaultManager cryptoVaultManager;
    ErrorManager errorManager;

    public DigitalAssetCryptoTransactionFactory(CryptoVaultManager cryptoVaultManager) throws CantSetObjectException {

        setCryptoVaultManager(cryptoVaultManager);

    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {

        this.errorManager=errorManager;

    }

    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) throws CantSetObjectException{

        if(cryptoVaultManager==null){

            throw new CantSetObjectException("CryptoVaultManager is null");

        }
        this.cryptoVaultManager=cryptoVaultManager;

    }

    public void createDigitalAssetCryptoTransaction(DigitalAsset digitalAsset) throws CantCreateDigitalAssetException{

        /**
         * TODO:
         * Se solicita a la Crypto Vault la creación de una transaction bitcoin a través del Outoing Intra user. Este hash se define como la GenesisTransaction
         en el Digital Asset.
         * Se solicita a la Asset Vault una dirección bitcoin que será la GenesisAddress. Esta dirección solicitada queda registrada en el Address Book como solicitada por el Issue Actor,
         la plataforma DAP y la vault de donde sale la dirección.
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

        try{

            //We need to get a new GenesisAddress:
            this.cryptoVaultManager.connectToBitcoin();
            CryptoAddress genesisAddress=this.cryptoVaultManager.getAddress();
            digitalAsset.setGenesisAddress(genesisAddress);
            //TODO: hacer una prueba para la solicitud de direcciones
            //We need to complete the transaction

            this.cryptoVaultManager.disconnectFromBitcoin();

        } catch (VaultNotConnectedToNetworkException exception) {
            throw new CantCreateDigitalAssetException(exception, "Creating a new Digital Asset Transaction - Connecting to Network", "Vault is not connected");
        }

    }

}
