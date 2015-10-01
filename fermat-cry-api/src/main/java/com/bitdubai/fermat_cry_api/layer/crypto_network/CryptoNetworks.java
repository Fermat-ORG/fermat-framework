package com.bitdubai.fermat_cry_api.layer.crypto_network;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 20.01.15.
 */
public enum CryptoNetworks {
     BITCOIN("BTC"),
     BITCOIN2("BTC2"),
     LITECOIN("LTC"),
     DOGECOIN("DGC");

     private final String code;

     CryptoNetworks(final String code){
          this.code = code;
     }

     public String getCode(){
          return code;
     }

     private static CryptoNetworks getByCode(final String code) throws InvalidParameterException{
          switch (code){
               case "BTC": return BITCOIN;
               case "BTC2": return BITCOIN2;
               case "LTC": return LITECOIN;
               case "DGC": return DOGECOIN;
               default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code: " + code, null);
          }
     }
}
