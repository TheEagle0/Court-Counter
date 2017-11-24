package com.example.theeagle.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 0;
    private TextView priceTextView;
    private TextView quantityTextView;
    private  int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        priceTextView = findViewById(R.id.price_text_view);
        quantityTextView = findViewById(R.id.quantity_text_view);

    }

    public void increment(View view) {
        quantity++;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity--;

            displayQuantity(quantity);
        } else
            Toast.makeText(this, "the number couldn't be less than 1", Toast.LENGTH_SHORT).show();


    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
//        displayPrice(quantity * 5);
        calculatePrice(quantity);
        displayMessage("Price is $ " + price + "\nThank you");

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {

        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {

        priceTextView.setText(message);
    }
    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private void calculatePrice(int quantity) {
         price = quantity * 5;
    }
}