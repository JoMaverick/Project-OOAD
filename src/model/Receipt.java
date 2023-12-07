package model;

public class Receipt {
    private int receiptId;
    private Order receiptOrder;
    private double receiptPaymentAmount;
    private String receiptPaymentDate;
    private String receiptPaymentType;

    public int getReceiptId() {
        return receiptId;
    }

    public Order getReceiptOrder() {
        return receiptOrder;
    }

    public double getReceiptPaymentAmount() {
        return receiptPaymentAmount;
    }

    public String getReceiptPaymentDate() {
        return receiptPaymentDate;
    }

    public String getReceiptPaymentType() {
        return receiptPaymentType;
    }

    public void setReceiptOrder(Order receiptOrder) {
        this.receiptOrder = receiptOrder;
    }

    public void setReceiptPaymentAmount(double receiptPaymentAmount) {
        this.receiptPaymentAmount = receiptPaymentAmount;
    }

    public void setReceiptPaymentDate(String receiptPaymentDate) {
        this.receiptPaymentDate = receiptPaymentDate;
    }

    public void setReceiptPaymentType(String receiptPaymentType) {
        this.receiptPaymentType = receiptPaymentType;
    }
}
