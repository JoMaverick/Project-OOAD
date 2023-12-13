package controller;

import java.util.List;

import model.Order;
import model.Receipt;

public class ReceiptController {
    public static String createReceipt(Order receiptOrder, String receiptPaymentType, String receiptPaymentAmount,
            String receiptPaymentDate) {
        if (!receiptPaymentType.equals("Cash") && !receiptPaymentType.equals("Debit")
                && !receiptPaymentType.equals("Credit")) {
            return "Error: Payment type must be either Cash, Debit, or Credit.";
        } else if (receiptPaymentAmount.isBlank()) {
            return "Error: Payment amount must be filled.";
        } else if (Double.parseDouble(receiptPaymentAmount) < receiptOrder.getOrderTotal()) {
            return "Error: Payment amount must be greater or equal than total order price.";
        } else {
            Receipt.createReceipt(receiptOrder, receiptPaymentType, Double.parseDouble(receiptPaymentAmount),
                    receiptPaymentDate);
            return "Create receipt sucess.";
        }
    }

    public static void updateReceipt(int orderId, String receiptPaymentType, double receiptPaymentAmount,
            String receiptPaymentDate) {
        Receipt.updateReceipt(orderId, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate);
    }

    public static void deleteReceipt(int orderId) {
        Receipt.deleteReceipt(orderId);
    }

    public static Receipt getReceiptById(int receiptId) {
        return Receipt.getReceiptById(receiptId);
    }

    public static List<Receipt> getAllReceipts() {
        return Receipt.getAllReceipts();
    }
}
