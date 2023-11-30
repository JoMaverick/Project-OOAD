package model;

public class Receipt {
    private int receiptId;
    private int receiptOrderId;
    private int receiptPaymentAmount;
    private String receiptPaymentDate;
    private String receiptPaymentType;

    public int getReceiptId() {
        return receiptId;
    }

    public int getReceiptOrderId() {
        return receiptOrderId;
    }

    public int getReceiptPaymentAmount() {
        return receiptPaymentAmount;
    }

    public String getReceiptPaymentDate() {
        return receiptPaymentDate;
    }

    public String getReceiptPaymentType() {
        return receiptPaymentType;
    }

    public void setReceiptOrderId(int receiptOrderId) {
        this.receiptOrderId = receiptOrderId;
    }

    public void setReceiptPaymentAmount(int receiptPaymentAmount) {
        this.receiptPaymentAmount = receiptPaymentAmount;
    }

    public void setReceiptPaymentDate(String receiptPaymentDate) {
        this.receiptPaymentDate = receiptPaymentDate;
    }

    public void setReceiptPaymentType(String receiptPaymentType) {
        this.receiptPaymentType = receiptPaymentType;
    }
}
