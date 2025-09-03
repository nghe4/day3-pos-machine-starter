package pos.machine;

import java.util.List;

public class PosMachine {
    private static boolean isBarcodeValid(List<String> barcodes) {
        for (String barcode : barcodes) {
            if (barcode == null && barcode.trim().isEmpty())
                return false;
        }
        return true;
    }

    public String printReceipt(List<String> barcodes) {
        if (!isBarcodeValid(barcodes)) {
            System.out.println("Invalid barcodes input");
        }
        return null;
    }
}
