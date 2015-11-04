//package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;
//
//import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
//import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
//<<<<<<< HEAD
//import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
//import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSignExtraUserMessageException;
//import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
//import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.TransactionType;
//import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
//=======
//import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
//import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantSignExtraUserMessageException;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
//import com.bitdubai.fermat_ccp_api.layer.crypto_wallet.interfaces.CryptoWalletTransaction;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2
//
//import java.util.GregorianCalendar;
//import java.util.Locale;
//import java.util.UUID;
//
///**
// * Created by mati on 2015.09.29..
// */
//public class CryptoWalletTransactionsTest implements CryptoWalletTransaction{
//
//    public CryptoWalletTransactionsTest() {
//    }
//
//            @Override
//            public UUID getTransactionId() {
//                return UUID.randomUUID();
//            }
//
//            @Override
//            public String getTransactionHash() {
//                return null;
//            }
//
//            @Override
//            public CryptoAddress getAddressFrom() {
//                return null;
//            }
//
//            @Override
//            public CryptoAddress getAddressTo() {
//                return null;
//            }
//
//            @Override
//            public String getActorToPublicKey() {
//                return null;
//            }
//
//            @Override
//            public String getActorFromPublicKey() {
//                return null;
//            }
//
//            @Override
//            public Actors getActorToType() {
//                return null;
//            }
//
//            @Override
//            public Actors getActorFromType() {
//                return null;
//            }
//
//            @Override
//            public BalanceType getBalanceType() {
//                return BalanceType.AVAILABLE;
//            }
//
//            @Override
//            public TransactionType getTransactionType() {
//                return TransactionType.CREDIT;
//            }
//
//            @Override
//            public long getTimestamp() {
//                GregorianCalendar gregorianCalendar = new GregorianCalendar(Locale.getDefault());
//                return gregorianCalendar.getTime().getTime();
//            }
//
//            @Override
//            public long getAmount() {
//                return 5000000;
//            }
//
//            @Override
//            public long getRunningBookBalance() {
//                return 0;
//            }
//
//            @Override
//            public long getRunningAvailableBalance() {
//                return 0;
//            }
//
//            @Override
//            public String getMemo() {
//                return "compra de carteras";
//            }
//
//    @Override
//    public Actor getInvolvedActor() {
//        return new Actor() {
//            @Override
//            public String getActorPublicKey() {
//                return null;
//            }
//
//            @Override
//            public String getName() {
//                return "Mati perez";
//            }
//
//            @Override
//            public Actors getType() {
//                return Actors.EXTRA_USER;
//            }
//
//            @Override
//            public byte[] getPhoto() {
//                return new byte[0];
//            }
//
//            @Override
//            public String createMessageSignature(String message) throws CantSignExtraUserMessageException {
//                return null;
//            }
//        };
//    }
//
//    @Override
//    public UUID getContactId() {
//        return null;
//    }
//}
