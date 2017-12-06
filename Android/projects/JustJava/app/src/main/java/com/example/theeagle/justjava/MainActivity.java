package com.example.theeagle.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 0;
    private TextView priceTextView;
    private TextView quantityTextView;
    private CheckBox checkBox;
    private CheckBox checkBox2;
    private int price;
    private EditText editText;
    private String name;
    private String message;
    boolean hasWhippedCream;
    boolean hasChocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        priceTextView = findViewById(R.id.price_text_view);
        quantityTextView = findViewById(R.id.quantity_text_view);
        checkBox = findViewById(R.id.checkbox);
        checkBox2 = findViewById(R.id.checkbox2);
        editText = findViewById(R.id.edit_text);
    }

    public void increment(View view) {
        if (quantity < 100)
            quantity++;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1) {
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
        hasWhippedCream = checkBox.isChecked();
        hasChocolate = checkBox2.isChecked();
        name = editText.getText().toString();

        displayMessage();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java "+name);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        priceTextView.setText("");

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
    private void displayMessage() {

        message = "\n"+getString(R.string.hello) + name +"\n"+ getString(R.string.thank_you) + "\n"+getString(R.string.whipped_cream_has) +
                hasWhippedCream + "\n"+getString(R.string.chocolate_has)
                + hasChocolate + "\n"+getString(R.string.price_is) + price;
        priceTextView.setText(message);
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private void calculatePrice(int quantity) {
        if (checkBox.isChecked() && checkBox2.isChecked()) {
            price = (quantity * 5) + (quantity * 3);

        } else if (checkBox.isChecked()) {
            price = (quantity * 5) + (quantity);
        } else if (checkBox2.isChecked()) {
            price = (quantity * 5) + (quantity * 2);
        } else {
            price = quantity * 5;

        }
    }
}