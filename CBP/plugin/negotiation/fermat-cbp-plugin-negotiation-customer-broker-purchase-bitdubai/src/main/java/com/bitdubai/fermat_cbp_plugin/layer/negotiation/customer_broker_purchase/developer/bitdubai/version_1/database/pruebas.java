package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListBankAccountsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListLocationsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateLocationPurchaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.NegotiationBankAccountPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.NegotiationPurchaseLocations;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 13/1/16.
 */
public class pruebas {

    CustomerBrokerPurchaseNegotiationDao dao;

    public pruebas(CustomerBrokerPurchaseNegotiationDao dao){
        this.dao = dao;

        //init_test_locations();
        //init_test_bank();

    }

    public void init_test_locations(){

        System.out.println("vlz: ==============================================================");

        System.out.println("vlz: Creando las locacion");

        for(int i=0; i<5; i++){
            try {
                this.dao.createNewLocation("Locacion "+i, "Uri de la direccion "+i);
            } catch (CantCreateLocationPurchaseException e) {
                System.out.println("vlz: Error creando la locacion ["+i+"]");
            }
        }

        System.out.println("vlz: Mostrando las locaciones");

            NegotiationLocations aux = null;

            try {
                Collection<NegotiationLocations> locaciones = this.dao.getAllLocations();

                for(NegotiationLocations loc : locaciones){
                    if(aux == null){
                        aux = loc;
                    }
                    System.out.println("vlz: \tID: "+loc.getLocationId());
                    System.out.println("vlz: \tLocacion: "+loc.getLocation());
                    System.out.println("vlz: \tURI: "+loc.getURI());
                }

            } catch (CantGetListLocationsPurchaseException e) {
                System.out.println("vlz: Error obteniendo las locaciones");
            }

        System.out.println("vlz: Actualizando las locaciones");

            NegotiationLocations aux2 = new NegotiationPurchaseLocations(aux.getLocationId(), aux.getLocation()+" Modificada", aux.getURI());

            try {
                this.dao.updateLocation(aux2);
            } catch (CantUpdateLocationPurchaseException e) {
                System.out.println("vlz: Error actualizando la locacion");
            }

        System.out.println("vlz: Mostrando las locaciones");

            try {
                Collection<NegotiationLocations> locaciones = this.dao.getAllLocations();

                for(NegotiationLocations loc : locaciones){
                    System.out.println("vlz: \tID: "+loc.getLocationId());
                    System.out.println("vlz: \tLocacion: "+loc.getLocation());
                    System.out.println("vlz: \tURI: "+loc.getURI());
                }

            } catch (CantGetListLocationsPurchaseException e) {
                System.out.println("vlz: Error obteniendo las locaciones");
            }

        System.out.println("vlz: Eliminando las locaciones");

            try {
                this.dao.deleteLocation(aux2);
            } catch (CantDeleteLocationPurchaseException e) {
                System.out.println("vlz: Error eliminando la locacion");
            }

        System.out.println("vlz: Mostrando las locaciones");

            try {
                Collection<NegotiationLocations> locaciones = this.dao.getAllLocations();

                for(NegotiationLocations loc : locaciones){
                    System.out.println("vlz: \tID: "+loc.getLocationId());
                    System.out.println("vlz: \tLocacion: "+loc.getLocation());
                    System.out.println("vlz: \tURI: "+loc.getURI());
                }

            } catch (CantGetListLocationsPurchaseException e) {
                System.out.println("vlz: Error obteniendo las locaciones");
            }

        System.out.println("vlz: ==============================================================");

    }

    public void init_test_bank(){
        System.out.println("vlz: ==============================================================");

            System.out.println("vlz: Creando las cuentas");

                FiatCurrency[] monedas = {
                        FiatCurrency.ARGENTINE_PESO,
                        FiatCurrency.BRAZILIAN_REAL,
                        FiatCurrency.VENEZUELAN_BOLIVAR
                };

                for(int i=0; i<monedas.length; i++){
                    try {
                        this.dao.createNewBankAccount( new NegotiationBankAccountPurchase(
                                UUID.randomUUID(),
                                "Cuenta "+i,
                                monedas[i]
                            )
                        );
                    } catch (CantCreateBankAccountPurchaseException e) {
                        System.out.println("vlz: Error creando cuenta bancaria");
                    }
                }

            System.out.println("vlz: Mostrando las cuentas");

                NegotiationBankAccount aux = null;
                try {
                    Collection<NegotiationBankAccount> cuentas = this.dao.getAllBankAccount();
                    for(NegotiationBankAccount cuenta : cuentas){
                        if( aux == null ){
                            aux = cuenta;
                        }
                        System.out.println("vlz: \tID: "+cuenta.getBankAccountId());
                        System.out.println("vlz: \tBank: "+cuenta.getBankAccount());
                        System.out.println("vlz: \tCurrencyType: "+cuenta.getCurrencyType().getCode());
                    }

                } catch (CantGetListBankAccountsPurchaseException e) {
                    System.out.println("vlz: Error obteniendo las cuentas");
                }

            System.out.println("vlz: Actualizando las cuentas");

                NegotiationBankAccount aux2 = new NegotiationBankAccountPurchase(aux.getBankAccountId(), aux.getBankAccount()+" Modificada", aux.getCurrencyType());

                try {
                    this.dao.updateBankAccount(aux2);
                } catch (CantUpdateBankAccountPurchaseException e) {
                    System.out.println("vlz: Error actualizando la cuenta");
                }

            System.out.println("vlz: Mostrando las cuentas");

                try {
                    Collection<NegotiationBankAccount> cuentas = this.dao.getAllBankAccount();
                    for(NegotiationBankAccount cuenta : cuentas){
                        System.out.println("vlz: \tID: "+cuenta.getBankAccountId());
                        System.out.println("vlz: \tBank: "+cuenta.getBankAccount());
                        System.out.println("vlz: \tCurrencyType: "+cuenta.getCurrencyType().getCode());
                    }

                } catch (CantGetListBankAccountsPurchaseException e) {
                    System.out.println("vlz: Error obteniendo las locaciones");
                }

            System.out.println("vlz: Eliminando las locaciones");

                try {
                    this.dao.deleteBankAccount(aux2);
                } catch (CantDeleteBankAccountPurchaseException e) {
                    System.out.println("vlz: Error eliminando la cuenta");
                }

            System.out.println("vlz: Mostrando las cuentas");

                try {
                    Collection<NegotiationBankAccount> cuentas = this.dao.getAllBankAccount();
                    for(NegotiationBankAccount cuenta : cuentas){
                        System.out.println("vlz: \tID: "+cuenta.getBankAccountId());
                        System.out.println("vlz: \tBank: "+cuenta.getBankAccount());
                        System.out.println("vlz: \tCurrencyType: "+cuenta.getCurrencyType().getCode());
                    }

                } catch (CantGetListBankAccountsPurchaseException e) {
                    System.out.println("vlz: Error obteniendo las cuentas");
                }

            System.out.println("vlz: Mostrando las currency");

                FiatCurrency mon = null;
                try {
                    Collection<FiatCurrency> _monedas = this.dao.getCurrencyTypeAvailableBankAccount();
                    for(FiatCurrency moneda : _monedas){
                        mon = moneda;
                        System.out.println("vlz: \tMoneda: "+moneda.getCode());
                    }

                } catch (CantGetListBankAccountsPurchaseException e) {
                    System.out.println("vlz: Error obteniendo las cuentas");
                }

            System.out.println("vlz: Mostrando las currency");

                try {
                    Collection<NegotiationBankAccount> cuentas = this.dao.getBankAccountByCurrencyType(mon);
                    for(NegotiationBankAccount cuenta : cuentas){
                        System.out.println("vlz: \tID: "+cuenta.getBankAccountId());
                        System.out.println("vlz: \tBank: "+cuenta.getBankAccount());
                        System.out.println("vlz: \tCurrencyType: "+cuenta.getCurrencyType().getCode());
                    }

                } catch (CantGetListBankAccountsPurchaseException e) {
                    System.out.println("vlz: Error obteniendo las cuentas");
                }




        System.out.println("vlz: ==============================================================");
    }



}
