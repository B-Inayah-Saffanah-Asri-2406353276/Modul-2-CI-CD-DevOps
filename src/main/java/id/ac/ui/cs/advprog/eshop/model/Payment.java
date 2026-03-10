package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData ){
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = determineStatus(method, paymentData);
    }

    public void setStatus(String status){
        if (!status.equals("SUCCESS") && !status.equals("REJECTED")) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }
        this.status = status;
    }

    private String determineStatus(String method,Map<String, String> paymentData){
        if(method.equals("VOUCHER")){
            return validateVoucher(paymentData);
        }
        else if(method.equals("BANK_TRANSFER")){
            return validateBankTransfer(paymentData);
        }
        return "REJECTED";
    }

    private String validateVoucher(Map<String, String> paymentData){
        String voucherCode = paymentData.get("voucherCode");

        if (voucherCode == null) return "REJECTED";
        if (voucherCode.length() != 16) return "REJECTED";
        if (!voucherCode.startsWith("ESHOP")) return "REJECTED";
        long digitCount = voucherCode.chars().filter(Character::isDigit).count();
        if (digitCount != 8) return "REJECTED";

        return "SUCCESS";
    }

    private String validateBankTransfer(Map<String,String> paymentData){
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        if (bankName == null || bankName.isEmpty()) return "REJECTED";
        if (referenceCode == null || referenceCode.isEmpty()) return "REJECTED";

        return "SUCCESS";
    }

}
