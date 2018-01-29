package com.example.tanapone.smartcashier;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Button scanBtn;
    private LinearLayout listItem;
    ArrayList<String> barcode = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Main");
        setContentView(R.layout.activity_main);
        scanBtn = (Button) findViewById(R.id.scanBtn);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listItem = (LinearLayout) findViewById(R.id.listItem);
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
                barcode.add(result.getContents());
                putBarcodeText();
            }
        }
    }
    public void putBarcodeText(){
        listItem.removeAllViews();
        for(int i = 0; i<barcode.size(); i++){
            final TextView textView = new TextView(this);
            textView.setText(barcode.get(i).toString());
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barcode.remove(finalI);
                    putBarcodeText();
                }
            });
            listItem.addView(textView);
        }
    }
}
