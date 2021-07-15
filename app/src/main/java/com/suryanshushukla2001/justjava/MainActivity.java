package com.suryanshushukla2001.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee
 */

public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
    * This Method is called Order button Clicked
     */
    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view) {
        //  figure out if the user want whipped cream topping

        CheckBox whippedCreamCheckBox =  findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCreamCheckBox = whippedCreamCheckBox.isChecked();
        EditText nameField =  findViewById(R.id.name_field);
        String value = nameField.getText().toString();
        //    figure out if the user want whipped cream topping

        CheckBox chocolateCheckBox =  findViewById(R.id.chocolate_checkbox);
        boolean hasChocolateCheckBox = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCreamCheckBox, hasChocolateCheckBox);
        String priceMessage = createOrderSummary( value , price , hasWhippedCreamCheckBox , hasChocolateCheckBox) ;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just java order for :" + value);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        intent.setData(Uri.parse("mailto:"));
        startActivity(Intent.createChooser(intent,"choose an email client"));
    }
    /**
     * Calculates the price of the order.
     *
     * @return total is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean addWhippedCream , boolean addChocolate) {
        int basePrice = 5;
//        Add $1 if user wants whipped cream coffee
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }
//        Add $2 if user Chocolate coffee
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }
    public void submitDecrement(View view){
        if (quantity == 1){
            Toast.makeText(MainActivity.this,"You cannot have less than 1 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    public void submitIncrement(View view){
        if (quantity == 100){
            Toast.makeText(MainActivity.this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    @SuppressLint("SetTextI18n")
    private void displayQuantity(int number){
        TextView quantityTextView = findViewById( R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }
    /**
    * create summary of the order
    * @param price of the order
     * @param addWhippedCream is whether or not user wants whipped cream topping
     * @param addChocolate is whether or not user wants chocolate topping
     *
    * @return text of the summary
     */
    private String createOrderSummary( String addName,int price, boolean addWhippedCream , boolean addChocolate){
        String priceMessage = "Name: " + addName;
        priceMessage += "\nAdd Whipped cream: " + addWhippedCream;
        priceMessage += "\nAdd Chocolate: " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price ;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

}