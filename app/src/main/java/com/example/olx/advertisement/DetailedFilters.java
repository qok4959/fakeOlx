package com.example.olx.advertisement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.olx.R;
import com.example.olx.fragments.FragmentNavigation;
import com.example.olx.usefulClasses.AdvertisementData;
import com.example.olx.usefulClasses.ObjArrConversion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DetailedFilters extends AppCompatActivity {

    Button btnCheap, btnExpensive, btnShow;
    String sortPrice="none";
    EditText priceFrom, priceTo;
    String strPriceFrom, strPriceTo, strLocation;
    Spinner dropdownLocations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_filters);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentNavigation.class, null)
                    .commit();
        }

        btnCheap = findViewById(R.id.buttonSortCheapest);
        btnExpensive = findViewById(R.id.buttonSortExpensive);
        btnShow = findViewById(R.id.buttonFilter);

        priceFrom = findViewById(R.id.editTxtPriceFrom);
        priceTo = findViewById(R.id.editTxtPriceTo);

        dropdownLocations = findViewById(R.id.spinnerLocationFilters);


        String[] locations = new String[]{"none", "dolnośląskie","kujawsko-pomorskie","lubelskie","lubuskie","łódzkie","małopolskie","mazowieckie","opolskie","podkarpackie","podlaskie","pomorskie","śląskie","świętokrzyskie","warmińsko-mazurskie","wielkopolskie","zachodniopomorskie"};


        ArrayAdapter<String> adapterLocations = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        dropdownLocations.setAdapter(adapterLocations);

        Bundle bundle = getIntent().getExtras();
        String objAsJson = bundle.getString("my_obj");
        ObjArrConversion androidPacket = ObjArrConversion.fromJson(objAsJson);


        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (priceFrom.getText().length()==0)
                    strPriceFrom="0";
                else
                    strPriceFrom = priceFrom.getText().toString().toLowerCase().trim();

                if (priceTo.getText().length()==0)
                    strPriceTo="99999999";
                else
                    strPriceTo = priceTo.getText().toString().toLowerCase().trim();

                if (dropdownLocations.getSelectedItem().toString().equals("none"))
                    strLocation="none";
                else
                    strLocation = dropdownLocations.getSelectedItem().toString();

                if (Integer.valueOf(strPriceFrom)>Integer.valueOf(strPriceTo)) {
                    priceTo.setError("max price have to be greater than min price");
                    priceTo.requestFocus();
                    return;
                }


                ArrayList<AdvertisementData> tempList = androidPacket.data;

                if (strLocation!="none"){
                    tempList = (ArrayList<AdvertisementData>) tempList.stream()
                            .filter(x -> x.getLocation().equals(strLocation))
                            .collect(Collectors.toList());

                    Log.d("afterFiltering", String.valueOf(tempList.size()));
                }

                tempList = (ArrayList<AdvertisementData>) tempList.stream()
                                .filter(x-> (Integer.valueOf(x.getPrice()) >= Integer.valueOf(strPriceFrom) && Integer.valueOf(x.getPrice())<=Integer.valueOf(strPriceTo))
                                )
                               .collect(Collectors.toList());
                Log.d("afterFiltering2", String.valueOf(tempList.size()));


                //sorting
                if (!sortPrice.equals("none")){
                    Log.d("checkPrice", "test");
                    if (sortPrice.equals("cheap")) {
                        tempList = (ArrayList<AdvertisementData>) tempList.stream()
                                .sorted(Comparator.comparingDouble(AdvertisementData::getPriceDouble)).collect(Collectors.toList());
                    }
                    else{
                        tempList = (ArrayList<AdvertisementData>) tempList.stream()
                                .sorted(Comparator.comparingDouble(AdvertisementData::getPriceDouble)
                                .reversed())
                                .collect(Collectors.toList());
                    }
                }
                Log.d("testInputs", strPriceFrom+" "+strPriceTo + " "+strLocation+" "+sortPrice);


                Intent i = new Intent(DetailedFilters.this, Filtered.class);
                ObjArrConversion androidPacket2 = new ObjArrConversion(tempList);
                String objAsJson = androidPacket2.toJson();
                i.putExtra("my_obj", objAsJson);
                startActivity(i);
            }
        });

        btnCheap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortPrice.equals("cheap")){
                    sortPrice="none";
                    btnCheap.setTextColor(Color.WHITE);
                }
                else{
                    sortPrice="cheap";
                    btnCheap.setTextColor(Color.RED);
                    btnExpensive.setTextColor(Color.WHITE);
                }
            }
        });

        btnExpensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortPrice.equals("expensive")){
                    sortPrice = "none";
                btnExpensive.setTextColor(Color.WHITE);
            }
                    else{
                    sortPrice="expensive";
                    btnCheap.setTextColor(Color.WHITE);
                    btnExpensive.setTextColor(Color.RED);
                }

            }
        });



    }
}