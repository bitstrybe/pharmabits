package lxe.pharmabitretail.utils;

// **********************************************************************
//   ShoppingCart.java
//
//   Represents a shopping cart as an array of items
// **********************************************************************
import lxe.pharmabitretail.tablemodel.SelectItemSaleTableModel;

public class ShoppingCarts {

    private SelectItemSaleTableModel[] cart;
    private int itemCount;      // total number of items in the cart
    private double totalPrice;  // total price of items in the cart
    private int capacity;       // current cart capacity

    // -----------------------------------------------------------
    //  Creates an empty shopping cart with a capacity of 5 items.
    // -----------------------------------------------------------
    public ShoppingCarts() {

        capacity = 5;
        cart = new SelectItemSaleTableModel[capacity];
        itemCount = 0;
        totalPrice = 0.0;
    }

    // -------------------------------------------------------
    //  Adds an item to the shopping cart.
    // -------------------------------------------------------
    public void addToCart(String batchno, String itemname, int quantity, double cost, double price, double total, double nhisvalue, String nhis, double discount) {
        SelectItemSaleTableModel temp = new SelectItemSaleTableModel(batchno, itemname, quantity, cost, price, total, nhisvalue, nhis, discount);
        totalPrice += (price * quantity);
        cart[itemCount] = temp;
        itemCount += 1;
    }

    // -------------------------------------------------------
    //  Returns the contents of the cart together with
    //  summary information.
    // -------------------------------------------------------
    public String toString() {
        String contents = "\nShopping Cart\n";
        contents += "\nItem\t\tUnit Price\tQuantity\tTotal\n";
       for (int i = 0; i < itemCount; i++) {
            contents += cart[i].getBatchCode() + "\n";
            contents += cart[i].getItemName() + "\n";
        }
        return contents;
    }

    // ---------------------------------------------------------
    //  Increases the capacity of the shopping cart by 3
    // ---------------------------------------------------------
    private void increaseSize() {
        SelectItemSaleTableModel[] temp = new SelectItemSaleTableModel[capacity + 3];
        for (int i = 0; i < capacity; i++) {
            temp[i] = cart[i];
        }
        cart = temp;
        temp = null;
        capacity = cart.length;
    }
}
