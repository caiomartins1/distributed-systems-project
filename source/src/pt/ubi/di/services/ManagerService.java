package pt.ubi.di.services;

import pt.ubi.di.model.AdvanceReceipt;
import pt.ubi.di.model.Part;
import pt.ubi.di.utils.FileUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ManagerService {

    public void registerPart(ArrayList<Part> parts, Part p) {
        parts.add(p);
    }

    public void deletePart(ArrayList<Part> parts, int index) {
        parts.remove(index);
    }

    public String listByTypeAlpha(ArrayList<Part> p) {
        String s = "";

        if (p.isEmpty()) {
            s = "No parts registered yet!";
        } else {
            Collections.sort(p, new Comparator<Part>() {
                @Override
                public int compare(Part s1, Part s2) {
                    return s1.getType().compareToIgnoreCase(s2.getType());
                }
            });

            for (Part part : p) {
                s += part.beautifyOutput() + "\n";
            }
        }

        return s;
    }

    public String listByBuyPrice(ArrayList<Part> p) {
        String s = "";

        if (p.isEmpty()) {
            s = "No parts registered yet!";
        } else {
            Collections.sort(p, new Comparator<Part>() {
                @Override
                public int compare(Part s1, Part s2) {
                    return s1.getBuyPrice() - s2.getBuyPrice() < 1 ? -1 : 1;
                }
            });

            for (Part part : p) {
                s += part.beautifyOutput() + "\n";
            }
        }

        return s;
    }

    public String listBySellPrice(ArrayList<Part> p) {
        String s = "";

        if (p.isEmpty()) {
            s = "No parts registered yet!";
        } else {
            Collections.sort(p, new Comparator<Part>() {
                @Override
                public int compare(Part s1, Part s2) {
                    return s1.getSellPrice() - s2.getSellPrice() < 1 ? -1 : 1;
                }
            });

            for (Part part : p) {
                s += part.beautifyOutput() + "\n";
            }
        }

        return s;
    }

    public String listByStockItems(ArrayList<Part> p) {
        String s = "";

        if (p.isEmpty()) {
            s = "No parts registered yet!";
        } else {
            Collections.sort(p, new Comparator<Part>() {
                @Override
                public int compare(Part s1, Part s2) {
                    return s2.getItems().size() - s1.getItems().size() < 1 ? -1 : 1;
                }
            });

            for (Part part : p) {
                s += part.beautifyOutput() + "\n";
            }
        }

        return s;
    }

    public String listByDateAdded(ArrayList<Part> p) {
        String s = "";

        if (p.isEmpty()) {
            s = "No parts registered yet!";
        } else {
            Collections.sort(p, new Comparator<Part>() {
                @Override
                public int compare(Part s1, Part s2) {
                    return s1.getCreatedAt().after(s2.getCreatedAt()) ? -1 : 1;
                }
            });

            for (Part part : p) {
                s += part.beautifyOutput() + "\n";
            }
        }

        return s;
    }

    public String listByDateHistory(ArrayList<AdvanceReceipt> receiptHistory) {
        String s = "";

        if (receiptHistory.isEmpty()) {
            s = "No history!";
        } else {
            Collections.sort(receiptHistory, new Comparator<AdvanceReceipt>() {
                @Override
                public int compare(AdvanceReceipt s1, AdvanceReceipt s2) {
                    return s1.getCreatedAt().isAfter(s2.getCreatedAt()) ? -1 : 1;
                }
            });

            for (AdvanceReceipt advSlip : receiptHistory) {
                s += receiptHistory.toString();
            }
        }

        return s;
    }
}
