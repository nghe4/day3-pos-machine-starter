package pos.machine;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class PosMachine {
    private static int totalPrice = 0;

    private static boolean isBarcodeValid(List<String> barcodes) {
        for (String barcode : barcodes) {
            if (barcode == null && barcode.trim().isEmpty())
                return false;
        }
        return true;
    }

    private static int calculateItemQuantity(List<String> barcodes, String barcode) {
        int quantity = 0;
        for (String code : barcodes) {
            if (code.equals(barcode)) {
                quantity++;
            }
        }
        return quantity;
    }

    private static Map<Item, Integer> mapItemsToQuantities(List<String> barcodes, List<Item> items) {
        Map<Item, Integer> itemQuantityMap = new LinkedHashMap<>();
        for (Item item : items) {
            int quantity = calculateItemQuantity(barcodes, item.getBarcode());
            if (quantity > 0) {
                itemQuantityMap.put(item, quantity);
            }
        }
        return itemQuantityMap;
    }

    private static Map<Item, Integer> loadItemsByBarcodes(List<String> barcodes) {
        List<Item> items = ItemsLoader.loadAllItems();
        return mapItemsToQuantities(barcodes, items);
    }

    private static String generateItemReceipt(Item item, int quantity) {
        int subtotal = item.getPrice() * quantity;
        totalPrice += subtotal;

        return String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                item.getName(), quantity, item.getPrice(), subtotal);
    }

    private static String generateReceipt(Map<Item, Integer> itemQuantityMap) {
        StringBuilder receipt = new StringBuilder("***<store earning no money>Receipt***\n");

        for (Map.Entry<Item, Integer> entry : itemQuantityMap.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();

            receipt.append(generateItemReceipt(item, quantity));
        }

        receipt.append("----------------------\n");
        receipt.append(String.format("Total: %d (yuan)\n", totalPrice));
        receipt.append("**********************");

        return receipt.toString();
    }

    public String printReceipt(List<String> barcodes) {
        if (!isBarcodeValid(barcodes)) {
            System.out.println("Invalid barcodes input");
        }
        Map<Item, Integer> itemQuantityMap = loadItemsByBarcodes(barcodes);
        return generateReceipt(itemQuantityMap);
    }
}
