package ru.daowallet.sdk;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.daowallet.sdk.dto.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class DaoWallet {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private String walletUrl;
    private String apiKey;
    private String secretKey;

    ObjectMapper objectMapper = new ObjectMapper();

    private static final String HMAC_SHA512 = "HmacSHA512";

    private final static String PROCESSING_KEY_HEADER = "X-Processing-Key";
    private final static String PROCESSING_SIGNATURE_HEADER = "X-Processing-Signature";

    private static final String URL_ADDRESSES_TAKE_PATH = "/addresses/take";
    private static final String URL_WITHDRAWAL_CRYPTO_PATH = "/withdrawal/crypto";
    private static final String URL_INVOICE_CREATE_PATH = "/invoice/new";
    private static final String URL_INVOICE_STATUS_PATH = "/invoice/status?id=";
    private static final String URL_BALANCE_PATH = "/balance";

    /**
     *
     * @param apiKey
     * @param secretKey
     */
    public DaoWallet(String apiKey, String secretKey){
        init(apiKey, secretKey, null);
    }

    /**
     *
     * @param apiKey
     * @param secretKey
     * @param walletUrl
     */
    public DaoWallet(String apiKey, String secretKey, String walletUrl){
        init(apiKey, secretKey, walletUrl);
    }

    private void init(String apiKey, String secretKey, String walletUrl){
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.walletUrl = walletUrl == null ? "https://b2b.daowallet.com/api/v2" : walletUrl;

        objectMapper = objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper = objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper = objectMapper.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
    }

    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public String calculateHMAC(String key, String data)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
        Mac mac = Mac.getInstance(HMAC_SHA512);
        mac.init(secretKeySpec);
        return toHexString(mac.doFinal(data.getBytes()));
    }

    private String postRequest(String pathStr, String jsonStr) throws IOException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        HttpPost request = new HttpPost(walletUrl + pathStr);
        String result = null;

        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.addHeader(PROCESSING_KEY_HEADER, apiKey);
        request.addHeader(PROCESSING_SIGNATURE_HEADER, calculateHMAC(secretKey, jsonStr));

        request.setEntity(new StringEntity(jsonStr));
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        }

        return result;
    }

    private String getRequest(String pathStr, Map<String, String> headers) throws IOException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        HttpGet request = new HttpGet(walletUrl + pathStr);
        String result = null;
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        if(headers != null && headers.size() > 0){
            for(Map.Entry<String, String> entry : headers.entrySet()){
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            System.out.println("Status: " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        }

        return result;
    }

    /**
     *
     * @param foreignId
     * @param currency
     * @return ResponseError on error or ResponseOk with data = AddressTakeResponse on success
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public Object addressesTake(String foreignId, String currency) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        AddressTake addressTakeDTO = new AddressTake(foreignId, currency);
        String ret = postRequest(URL_ADDRESSES_TAKE_PATH, objectMapper.writeValueAsString(addressTakeDTO));
        if(ret.contains("status")){
            return objectMapper.readValue(ret, ResponseError.class);
        }
        else {
            JsonNode jsonNode = objectMapper.readTree(ret);
            return new ResponseOK(objectMapper.convertValue(jsonNode.get("data"), AddressTakeResponse.class));
        }
    }

    /**
     *
     * @param foreignId
     * @param currency
     * @param amount ex.: new BigDecimal("0.001"), do not BigDecimal number constructor
     * @param address
     * @return ResponseError on error or ResponseOk with data = WithdrawalResponse on success
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public Object withdrawalCrypto(String foreignId, String currency, BigDecimal amount, String address) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        WithdrawalCrypto withdrawalCryptoDTO = new WithdrawalCrypto(foreignId, currency, amount, address);
        String ret = postRequest(URL_WITHDRAWAL_CRYPTO_PATH, objectMapper.writeValueAsString(withdrawalCryptoDTO));
        if(ret.contains("status")){
            return objectMapper.readValue(ret, ResponseError.class);
        }
        else {
            JsonNode jsonNode = objectMapper.readTree(ret);
            return new ResponseOK(objectMapper.convertValue(jsonNode.get("data"), WithdrawalResponse.class));
        }
    }

    /**
     *
     * @param amount
     * @param fiatCurrency
     * @return ResponseError on error or InvoiceResponse on success
     * @throws IOException
     * @throws SignatureException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public Object createInvoice(Double amount, String fiatCurrency) throws IOException, SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        Invoice invoice = new Invoice(amount, fiatCurrency);
        String ret = postRequest(URL_INVOICE_CREATE_PATH, objectMapper.writeValueAsString(invoice));
        if(!ret.contains("expired_at")){
            return objectMapper.readValue(ret, ResponseError.class);
        }
        else {
            return objectMapper.readValue(ret, InvoiceResponse.class);
        }
    }

    /**
     *
     * @param foreignId
     * @return ResponseError on error or InvoiceResponse on success
     * @throws SignatureException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IOException
     */
    public Object getInvoiceStatus(String foreignId) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        String ret = getRequest(URL_INVOICE_STATUS_PATH + foreignId, null);
        if(!ret.contains("expired_at")){
            return objectMapper.readValue(ret, ResponseError.class);
        }
        else {
            return objectMapper.readValue(ret, InvoiceResponse.class);
        }
    }

    /**
     *
     * @return ResponseError on error or InvoiceResponse on success
     * @throws SignatureException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IOException
     */
    public Object getBalance() throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(PROCESSING_KEY_HEADER, apiKey);
        headers.put(PROCESSING_SIGNATURE_HEADER, calculateHMAC(secretKey, "{}"));
        String ret = getRequest(URL_BALANCE_PATH, headers);
        if(!ret.contains("currency_name")){
            return objectMapper.readValue(ret, ResponseError.class);
        }
        else {
            return objectMapper.readValue(ret, BalanceResponse.class);
        }
    }

    public static void main(String[] args) {
        
    }
}
