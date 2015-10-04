package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigo on 10/4/15.
 */
class VaultKeyHierarchy extends DeterministicHierarchy {
    private Map<HierarchyAccount, DeterministicKey> accountsMasterKeys;

    /**
     * Constructor
     * @param rootKey (m) key
     */
    public VaultKeyHierarchy(DeterministicKey rootKey) {
        super(rootKey);
        accountsMasterKeys = new HashMap<>();
    }

    /**
     * Generates a master Deterministic Key for the given account on the account path
     * These are the m/n paths...for example m/0 , m/1, ... m m/n
     * @param account
     */
    public void addVaultAccount(HierarchyAccount account){
        DeterministicKey accountMasterKey = this.deriveChild(account.getAccountPath(), false, true, ChildNumber.ZERO);
        accountsMasterKeys.put(account, accountMasterKey);
    }

    /**
     * Returns a public Key only from the specified account used to generate bitcoin addresses     *
     * @param hierarchyAccount
     * @return the fist key of the path m/HierarchyAccount/0. Example: m/0/0
     */
    private DeterministicKey getAddressPublicKeyFromAccount(HierarchyAccount hierarchyAccount){
        /**
         * gets the masterKey for this account
         */
        DeterministicKey masterKey = accountsMasterKeys.get(hierarchyAccount);

        /**
         * Serialize the pubkey of the master key
         */
        byte[] pubKeyBytes = masterKey.getPubKey();
        byte[] chainCode = masterKey.getChainCode();

        return HDKeyDerivation.createMasterPubKeyFromBytes(pubKeyBytes, chainCode);
    }

    /**
     * Generates a new hierarchy on the path m/account/0 with only public keys
     * @param hierarchyAccount
     * @return a new hierarchy used to generate bitcoin addresses
     */
    public DeterministicHierarchy getAddressPublicHierarchyFromAccount(HierarchyAccount hierarchyAccount){
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(getAddressPublicKeyFromAccount(hierarchyAccount));
        return deterministicHierarchy;
    }

    /**
     * Generates a Bitcoin Address from the specified networkType and Account.
     * It wil use the next available publicKey from the hierarchy for that account.
     * @param blockchainNetworkType
     * @return the crypto address
     */
    public CryptoAddress getBitcoinAddress(BlockchainNetworkType blockchainNetworkType, HierarchyAccount hierarchyAccount){
        /**
         * The depth of the next available public key for this account
         */
        int pubKeyDepth = getNextAvailablePublicKeyDepth(hierarchyAccount);

        /**
         * I will derive a new public Key from this account
         */
        DeterministicHierarchy pubKeyHierarchy = getAddressPublicHierarchyFromAccount(hierarchyAccount);
        DeterministicKey pubKey = pubKeyHierarchy.deriveChild(pubKeyHierarchy.getRootKey().getPath(), true, true, new ChildNumber(pubKeyDepth, false));

        /**
         * I will create the CryptoAddress
         */
        String address = pubKey.toAddress(BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType)).toString();
        CryptoAddress cryptoAddress = new CryptoAddress(address, CryptoCurrency.BITCOIN);

        return cryptoAddress;
    }

    private int getNextAvailablePublicKeyDepth(HierarchyAccount hierarchyAccount) {
        //todo get current value
        //todo increase value
        return 3;
    }
}
