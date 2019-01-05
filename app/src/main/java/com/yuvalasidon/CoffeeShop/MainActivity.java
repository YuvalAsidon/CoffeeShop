package com.yuvalasidon.CoffeeShop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    int quantity=0;
    final int Price_Of_Cup=5;
    String Message;
    String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        public void increment (View v){
            quantity++;
            if(quantity<=100){
                displayQuantity(quantity);
            }
            else{
                Toast.makeText(this, "Can't have more then 100, try again!", Toast.LENGTH_LONG).show();
            }
        }
        public void decrement (View v){
            quantity--;
            if(quantity>=0){
                displayQuantity(quantity);
            }
            else{
                Toast.makeText(this, "Can't have less then 1, try again!", Toast.LENGTH_LONG).show();
            }
        }

        public void submitOrder (View v){
            CheckBox WhippedCreamCheck= (CheckBox) findViewById(R.id.whippedcream);
            boolean hasWhippedCream=WhippedCreamCheck.isChecked();
            CheckBox ChocolateCheck=(CheckBox)findViewById(R.id.chocolate);
            boolean hasChocolate=ChocolateCheck.isChecked();
            createOrderSummery(hasWhippedCream, hasChocolate);
            Uri uri = Uri.parse("mailto:")
                    .buildUpon()
                    .appendQueryParameter("subject", "order for "+Name)
                    .appendQueryParameter("body", Message)
                    .build();

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(emailIntent, "Send email"));
            }
        }
        private void displayQuantity ( int numberOfCoffees){
            TextView quantityT = (TextView) findViewById(R.id.quantityText);
            quantityT.setText("" + numberOfCoffees);
        }

        private int calculatePrice (boolean hasChocolate, boolean hasWhippedCream) {
            if(hasChocolate==true){
                if(hasWhippedCream==true)
                    return (Price_Of_Cup+2+1)*quantity;
                else
                    return (Price_Of_Cup+2)*quantity;
            }
            else{
                if(hasWhippedCream==true)
                    return (Price_Of_Cup+1)*quantity;
                else
                    return Price_Of_Cup*quantity;
            }
        }
        private void createOrderSummery (boolean hasWhippedCream, boolean hasChocolate) {
            TextView name = (TextView) findViewById(R.id.nameOfClient);
            Name=name.getText().toString();
            Message = getString(R.string.clientName) + Name + "\n"+getString(R.string.whippedcream)
                    + hasWhippedCream +"\n"+getString(R.string.haschocolate)+ hasChocolate+ "\n"+getString(R.string.Quantity)
                    + quantity + "\n"+getString(R.string.total)+ calculatePrice(hasChocolate, hasWhippedCream)
                    + "\n"+getString(R.string.thank_you);

        }
}
