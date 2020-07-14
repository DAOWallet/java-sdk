import ru.daowallet.sdk.*;
import ru.daowallet.sdk.dto.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
//        DaoWallet wallet = new DaoWallet("7NiME3JTuJUCWI75dnpkHUcM9zeuvNjG", "XGIPHgLXkoy8VSrYhQpMccS5GdRFMfPXCJaOzvoR", "https://b2b.test.daowallet.com/api/v2");
        DaoWallet wallet = new DaoWallet("unm87I0qD8n4sPPjd9RTnUPmj0fXAYKh", "IHKjW4GgLUT79E8wzk5kUiPXslTWEGiAJmaU1Xot", "http://0.0.0.0:4500/v2");
        Double amount = 20.0;

        try {
            /* Invoice */
            InvoiceResponse invoiceResult = (InvoiceResponse) wallet.createInvoice(amount, "EUR");
            System.out.println("invoice result: " + invoiceResult.getForeign_id());

            InvoiceResponse invoiceStatusResult = (InvoiceResponse) wallet.getInvoiceStatus(invoiceResult.getForeign_id());
            System.out.println("invoice status: " + invoiceStatusResult.getStatus());

            /* Get address */
            double random = Math.random() * 10000;
            ResponseOK addressTakeResult = (ResponseOK) wallet.addressesTake("yourUserId-" + String.valueOf(random), "ETH");
            AddressTakeResponse addressTakeResponse = (AddressTakeResponse) addressTakeResult.getData();
            System.out.println("address: " + addressTakeResponse.getAddress());

            /* Withdrawal crypto */
            ResponseOK withdrawalCryptoResult
                    = (ResponseOK) wallet.withdrawalCrypto("yourUserId-" + String.valueOf(random), "ETH", new BigDecimal(0.02), "0x9b0d5e8e4125353a0ad30f6a16ece43ac0a5ee9f");
            WithdrawalResponse withdrawalCryptoResponse = (WithdrawalResponse) withdrawalCryptoResult.getData();
                    System.out.println("withdrawal: " + withdrawalCryptoResponse.getAmount());

            /* Get balance */
            BalanceResponse balanceResponse = (BalanceResponse) wallet.getBalance();
            System.out.println("balance: " + balanceResponse.getBalance().get(0).getValue());

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
