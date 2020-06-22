package com.appstone.maybatchtasksample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RelativeLayout mRlAddItem;
    private LinearLayout mLlDynamicLayout;
    private EditText mEtListTitle;
    private ArrayList<Items> items;
    private ArrayList<Items> retrievedItems;
    private int row = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtListTitle = findViewById(R.id.et_title);
        mLlDynamicLayout = findViewById(R.id.ll_dynamic_holder);
        mRlAddItem = findViewById(R.id.rl_add_item);


        mRlAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewListItem();
            }
        });

        items = new ArrayList<>();
        retrievedItems = new ArrayList<>();
    }


    private void addNewListItem() {
        mRlAddItem.setEnabled(false);
        row++;
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.cell_item_edit, null);

        CheckBox checkBox = view.findViewById(R.id.chk_edit_item);
        final EditText mEtListItem = view.findViewById(R.id.et_edit_item);
        final ImageView mIvAddItem = view.findViewById(R.id.iv_add_item);

        mEtListItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 1) {
                    mIvAddItem.setVisibility(View.VISIBLE);
                } else {
                    mIvAddItem.setVisibility(View.INVISIBLE);
                }
            }
        });


        mIvAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEtListItem.setEnabled(false);
                mRlAddItem.setEnabled(true);
                mIvAddItem.setVisibility(View.INVISIBLE);

                Items item = new Items();
                item.itemID = row;
                item.itemName = mEtListItem.getText().toString();
                item.isChecked = false;

                items.add(item);

                Toast.makeText(MainActivity.this, "Items Size " + items.size() + "Retrieved Item Size " + retrievedItems.size(), Toast.LENGTH_LONG).show();
            }
        });


        mLlDynamicLayout.addView(view);
    }

    public void onDoneClicked(View view) {


        if (items.size() > 0) {

            //Todo for adding an arraylist to JSON

            //for(Object/ClassName variableName : arraylistName)
            JSONArray itemsArray = new JSONArray();
            for (Items item : items) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("itemid", item.itemID);
                    jsonObject.put("itemname", item.itemName);
                    jsonObject.put("ischecked", item.isChecked);
                    itemsArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            String itemsArrayValue = itemsArray.toString();

            Log.i("JSON Array", itemsArray.toString());


            //Todo for retrieving as a string from database and convert it to JSONarray and conver that JSONARray to Arraylist.
            try {
                JSONArray retrieveItemArray = new JSONArray(itemsArrayValue);
                for (int i = 0; i < retrieveItemArray.length(); i++) {
                    JSONObject currentObject = retrieveItemArray.getJSONObject(i);

                    Items retrievedItem = new Items();
                    retrievedItem.itemID = currentObject.optInt("itemid");
                    retrievedItem.itemName = currentObject.optString("itemname");
                    retrievedItem.isChecked = currentObject.optBoolean("ischecked");

                    retrievedItems.add(retrievedItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(MainActivity.this, "Retrieved Item Size" + retrievedItems.size(), Toast.LENGTH_LONG).show();

        }


    }
}
