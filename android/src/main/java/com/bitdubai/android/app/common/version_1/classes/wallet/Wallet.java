package com.bitdubai.android.app.common.version_1.classes.wallet;

/**
 * Created by Natalia on 12/03/2015.
 */
import com.bitdubai.android.app.common.version_1.classes.APIException;
import com.bitdubai.android.app.common.version_1.classes.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class reflects the functionality documented
 * at https://blockchain.info/api/blockchain_wallet_api. It allows users to interact
 * with their Blockchain.info wallet. If you have an API code, please set it via the
 * `setApiCode` method.
 *
 */
public class Wallet
{
   // private JsonParser jsonParser;

    private String identifier;
    private String password;
    private String secondPassword;
    private String apiCode;

    /**
     *
     * @param identifier Wallet identifier (GUID)
     * @param password Decryption password
     */
    public Wallet(String identifier, String password)
    {
        this(identifier, password, null);
    }

    /**
     *
     * @param identifier Wallet identifier (GUID)
     * @param password Decryption password
     * @param secondPassword Second password
     */
    public Wallet(String identifier, String password, String secondPassword)
    {
        this.identifier = identifier;
        this.password = password;
        this.secondPassword = secondPassword;
        //this.jsonParser = new JsonParser();
    }

    /**
     * Sends bitcoin from your wallet to a single address.
     * @param toAddress Recipient bitcoin address
     * @param amount Amount to send (in satoshi)
     * @param fromAddress Specific address to send from (optional, nullable)
     * @param fee Transaction fee in satoshi. Must be greater than the default fee (optional, nullable).
     * @param note Public note to include with the transaction (optional, nullable)
     * @return An instance of the PaymentResponse class
     * @throws APIException If the server returns an error
     * @throws IOException
     */
    public PaymentResponse send(String toAddress, long amount, String fromAddress,
                                Long fee, String note) throws APIException, IOException,JSONException
    {
        Map<String, Long> recipient = new HashMap<String, Long>();
        recipient.put(toAddress, amount);

        return sendMany(recipient, fromAddress, fee, note);
    }

    /**
     * Sends bitcoin from your wallet to multiple addresses.
     * @param recipients Map with the structure of 'address':amount in satoshi (String:long)
     * @param fromAddress Specific address to send from (optional, nullable)
     * @param fee Transaction fee in satoshi. Must be greater than the default fee (optional, nullable).
     * @param note Public note to include with the transaction (optional, nullable)
     * @return An instance of the PaymentResponse class
     * @throws APIException If the server returns an error
     * @throws IOException
     */
    public PaymentResponse sendMany(Map<String, Long> recipients, String fromAddress,
                                    Long fee, String note) throws APIException, IOException,JSONException
    {
        Map<String, String> params = buildBasicRequest();
        String method = null;

        if (recipients.size() == 1)
        {
            method = "payment";
            Entry<String, Long> e = recipients.entrySet().iterator().next();
            params.put("to", e.getKey());
            params.put("amount", e.getValue().toString());
        }
        else
        {
            method = "sendmany";
            params.put("recipients", new JSONObject(recipients).toString());
        }

        if (fromAddress != null)
            params.put("from", fromAddress);
        if (fee != null)
            params.put("fee", fee.toString());
        if (note != null)
            params.put("note", note);

        String response = HttpClient.post(String.format("merchant/%s/%s",
                identifier, method), params);
        JSONObject topElem = parseResponse(response);

        return new PaymentResponse(
                topElem.get("message").toString(),
                topElem.get("tx_hash").toString(),
                topElem.has("notice") ? topElem.get("notice").toString() : null);
    }

    /**
     *  Fetches the wallet balance. Includes unconfirmed transactions and
     *  possibly double spends.
     * @return Wallet balance in satoshi
     * @throws IOException
     * @throws APIException If the server returns an error
     */
    public long getBalance() throws APIException, IOException,JSONException
    {
        String response = HttpClient.get(String.format("merchant/%s/balance",
                identifier), buildBasicRequest());
        JSONObject topElem = parseResponse(response);

        return Long.valueOf(topElem.get("balance").toString());
    }

    /**
     * Lists all active addresses in the wallet.
     * @param confirmations Minimum number of confirmations transactions
     * must have before being included in the balance of addresses (can be 0)
     * @return A list of Address objects
     * @throws APIException If the server returns an error
     * @throws IOException
     */
    public List<Address> listAddresses(int confirmations)
            throws APIException, IOException, JSONException
    {
        Map<String, String> params = buildBasicRequest();
        params.put("confirmations", String.valueOf(confirmations));

        String response = HttpClient.get(
                String.format("merchant/%s/list",identifier), params);
        JSONObject topElem = parseResponse(response);

        List<Address> addresses = new ArrayList<Address>();

        JSONArray addressesArr = topElem.getJSONArray("addresses");
          for (int i=0; i< addressesArr.length(); i++){
                JSONObject a = addressesArr.getJSONObject(i);

            Address address = new Address(
                   Long.parseLong(a.get("balance").toString()) ,
                    a.get("address").toString(),
                    a.has("label") && a.get("label") != null ?
                            a.get("label").toString() : null,
                    Long.parseLong(a.get("total_received").toString()));

            addresses.add(address);
        }

        return addresses;
    }

    /**
     * Retrieves an address from the wallet.
     * @param address Address in the wallet to look up
     * @param confirmations Minimum number of confirmations transactions
     * must have before being included in the balance of an addresses (can be 0)
     * @return An instance of the Address class
     * @throws APIException If the server returns an error
     * @throws IOException
     */
    public Address getAddress(String address, int confirmations)
            throws APIException, IOException,JSONException
    {
        Map<String, String> params = buildBasicRequest();
        params.put("address", address);
        params.put("confirmations", String.valueOf(confirmations));

        String response = HttpClient.get(
                String.format("merchant/%s/address_balance",identifier), params);
        JSONObject topElem = parseResponse(response);

        return new Address(
                Long.parseLong(topElem.get("balance").toString()),
                topElem.get("address").toString(),
                topElem.has("label") && topElem.get("label") != null ?
                        topElem.get("label").toString() : null,
                Long.parseLong(topElem.get("total_received").toString()));
    }

    /**
     * Generates a new address and adds it to the wallet.
     * @param label Label to attach to this address (optional, nullable)
     * @return An instance of the Address class
     * @throws APIException If the server returns an error
     * @throws IOException
     */
    public Address newAddress(String label) throws APIException, IOException,JSONException
    {
        Map<String, String> params = buildBasicRequest();
        if (label != null)
            params.put("label", label);

        String response = HttpClient.post(
                String.format("merchant/%s/new_address",identifier), params);
        JSONObject topElem = parseResponse(response);

        return new Address(
                0L,
                topElem.get("address").toString(),
                topElem.has("label") && topElem.get("label") != null ?
                        topElem.get("label").toString() : null,
                0L);
    }

    /**
     * Archives an address.
     * @param address Address to archive
     * @return String representation of the archived address
     * @throws APIException If the server returns an error
     * @throws IOException
     */
    public String archiveAddress(String address) throws APIException, IOException,JSONException
    {
        Map<String, String> params = buildBasicRequest();
        params.put("address", address);

        String response = HttpClient.post(
                String.format("merchant/%s/archive_address",identifier), params);
        JSONObject topElem = parseResponse(response);

        return topElem.get("archived").toString();
    }

    /**
     * Unarchives an address.
     * @param address Address to unarchive
     * @return String representation of the unarchived address
     * @throws APIException If the server returns an error
     * @throws IOException
     */
    public String unarchiveAddress(String address) throws APIException, IOException,JSONException
    {
        Map<String, String> params = buildBasicRequest();
        params.put("address", address);

        String response = HttpClient.post(
                String.format("merchant/%s/unarchive_address",identifier), params);
        JSONObject topElem = parseResponse(response);

        return topElem.get("active").toString();
    }

    /**
     * Consolidates the wallet addresses.
     * @param days Addresses which have not received any
     * transactions in at least this many days will be consolidated.
     * @return A list of consolidated addresses in the string format
     * @throws APIException If the server returns an error
     * @throws IOException
     */
    public List<String> consolidate(int days) throws APIException, IOException,JSONException
    {
        Map<String, String> params = buildBasicRequest();
        params.put("days", String.valueOf(days));

        String response = HttpClient.post(
                String.format("merchant/%s/auto_consolidate", identifier), params);
        JSONObject topElem = parseResponse(response);

        List<String> addresses = new ArrayList<String>();
        JSONArray addressesArr = topElem.getJSONArray("consolidated");
        for (int i=0; i< addressesArr.length(); i++){
            JSONObject a = addressesArr.getJSONObject(i);
            addresses.add(a.toString());
        }

        return addresses;
    }

    private Map<String, String> buildBasicRequest()
    {
        Map<String,String> params = new HashMap<String, String>();

        params.put("password", password);
        if (secondPassword != null)
            params.put("second_password", secondPassword);
        if (apiCode != null)
            params.put("api_code", apiCode);

        return params;
    }

    private JSONObject parseResponse(String response) throws APIException, JSONException
    {
        JSONObject topElem = new JSONObject(response);
        if (topElem.has("error"))
            throw new APIException(topElem.get("error").toString());

        return topElem;
    }

    /**
     * Sets the Blockchain.info API code
     * @param apiCode
     */
    public void setApiCode(String apiCode)
    {
        this.apiCode = apiCode;
    }
}
