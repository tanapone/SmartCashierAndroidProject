package com.example.tanapone.smartcashier;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ImageView scanBtn;
    private LinearLayout listItem;
    private String qtyValue = "";
    ArrayList<String> barcode = new ArrayList<String>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<Integer> quantity = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Main");
        setContentView(R.layout.activity_main);

        scanBtn = (ImageView) findViewById(R.id.scanBtn);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listItem = (TableLayout) findViewById(R.id.listItem);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      scanBtn.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View view) {
              IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
              integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
              integrator.setPrompt("Scan");
              integrator.setCameraId(0);
              integrator.setBeepEnabled(false);
              integrator.setBarcodeImageEnabled(false);
              integrator.initiateScan();
          }
      });
    }

   @Override
   public void onBackPressed() {
      return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                setQuantity();
                barcode.add(result.getContents());
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public void putBarcodeText(){
        listItem.removeAllViews();
        TextView headerBarcode = new TextView(this);
        TextView headerName = new TextView(this);
        TextView headerQty = new TextView(this);
//
        headerBarcode.setPadding(10,10,10,10);
        headerBarcode.setBackgroundColor(Color.parseColor("#4DA2DE"));
        headerBarcode.setText(R.string.item_header_barcode);
        headerBarcode.setTextColor(Color.WHITE);

        headerName.setPadding(10,10,10,10);
        headerName.setBackgroundColor(Color.parseColor("#4DA2DE"));
        headerName.setText(R.string.item_header_name);
        headerName.setTextColor(Color.WHITE);

        headerQty.setPadding(10,10,10,10);
        headerQty.setBackgroundColor(Color.parseColor("#4DA2DE"));
        headerQty.setText(R.string.item_header_qty);
        headerQty.setTextColor(Color.WHITE);

//
        TableRow tableRow = new TableRow(this);
        tableRow.addView(headerBarcode);
        tableRow.addView(headerName);
        tableRow.addView(headerQty);
        listItem.addView(tableRow);

        for(int i = 0; i<barcode.size(); i++){
            TableRow tableRowData = new TableRow(this);
            final TextView itemID = new TextView(this);
            itemID.setText(barcode.get(i).toString());

            final TextView itemName = new TextView(this);
            itemName.setText(name.get(i).toString());

            final TextView itemQty = new TextView(this);
            itemQty.setText(quantity.get(i).toString());
            final int finalI = i;
            itemID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barcode.remove(finalI);
                    quantity.remove(finalI);
                    name.remove(finalI);
                    putBarcodeText();
                }
            });
            tableRowData.addView(itemID);
            tableRowData.addView(itemName);
            tableRowData.addView(itemQty);
            listItem.addView(tableRowData);
        }

    }
    public void setQuantity() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.set_quantity_dialog, null);
        builder.setView(view);
        final EditText quantityEditText = (EditText) view.findViewById(R.id.qtyEditText);
        builder.setPositiveButton(R.string.set_quantity_dialog_submitBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                qtyValue = quantityEditText.getText().toString().trim();
                if (qtyValue == null || qtyValue.equals("") || qtyValue.charAt(0) == '0') {
                    qtyValue = String.valueOf(1);
                }
                quantity.add(Integer.parseInt(qtyValue));
                System.out.println("Size of quantity : "+quantity.size());
                name.add("ทดสอบ");
                putBarcodeText();
            }
        });
    builder.show();
    }
}
