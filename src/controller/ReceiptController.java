package controller;

import java.util.List;

import model.Order;
import model.Receipt;

public class ReceiptController {
    public static void createReceipt(Order receiptOrder, String receiptPaymentType, double receiptPaymentAmount,
            String receiptPaymentDate) {
        Receipt.createReceipt(receiptOrder, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate);
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
