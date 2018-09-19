package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot order less than one cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Code to input name for order
        EditText nameInput = findViewById(R.id.nameInputEditText);
        String name = nameInput.getText().toString();

        // Code for whether or not the customer wants which topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whippedCreamCheckBox);
        CheckBox chocolateCheckBox = findViewById(R.id.chocolateCheckBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int totalPrice = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, totalPrice, hasWhippedCream, hasChocolate);
        displayQuantity(quantity);
        displayMessage(priceMessage);

//        // Create intent to send order to email
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto: rreay724@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Coffee Order Summary for " + name);
        emailIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     **/
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int pricePerCup = 5;

        if (addWhippedCream) {
            pricePerCup = pricePerCup + 1;
        }

        if (addChocolate) {
            pricePerCup = pricePerCup + 2;
        }

        return quantity * pricePerCup;
    }

    /**
     * Create summary of the order
     *
     * @param price        of the order
     * @param whippedCream is whether or not the user wants whipped cream topping
     * @param chocolate    is whether or not the user wants chocolate topping
     * @return text of summary
     **/
    private String createOrderSummary(String name, int price, boolean whippedCream, boolean chocolate) {
        String priceMessage = "\nName: " + name;
        priceMessage = priceMessage + "\nAdd whipped cream? " + whippedCream;
        priceMessage = priceMessage + "\nAdd chocolate? " + chocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + price + ".00";
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called to display message under Price
     **/
    public void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.orderSummaryTextView);
        orderSummaryTextView.setText(message);
    }


    /**
     * Method to reset values in price and quantity selected
     **/
    public void resetButton(View view) {
        quantity = 1;
        displayQuantity(1);
        displayMessage("$0");

        CheckBox whippedCreamCheckBox = findViewById(R.id.whippedCreamCheckBox);
        CheckBox chocolateCheckBox = findViewById(R.id.chocolateCheckBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();

        if (hasWhippedCream) {
            whippedCreamCheckBox.setChecked(false);
        }
        if (hasChocolate) {
            chocolateCheckBox.setChecked(false);
        }

        EditText nameInput = findViewById(R.id.nameInputEditText);
        nameInput.setText("");


    }
}
