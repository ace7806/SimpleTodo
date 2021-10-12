package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;
    Button btn;
    EditText text;
    RecyclerView view;
    ItemsAdapter itemsAdapter;
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION  = "item_position";
    public static int EDIT_TEXT_CORE  = 20;
    //handle result of edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode==EDIT_TEXT_CORE){
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            int pos = data.getExtras().getInt(KEY_ITEM_POSITION);
            //update model at the right pos
            items.set(pos,itemText);
            itemsAdapter.notifyItemChanged(pos);
            saveItems();
            Toast.makeText(getApplicationContext(),"Item updated Successfully!",Toast.LENGTH_SHORT).show();
        }else{
            Log.d("MainActivity","Unknown Call to onActivityResult");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        btn = findViewById(R.id.addButton);
        text = findViewById(R.id.etItem);
        view = findViewById(R.id.todoRecyclerView);


        loadItems();
        //remove item
        ItemsAdapter.onLongClickListener onLongClickListener = new ItemsAdapter.onLongClickListener(){
            @Override
            public void onItemLongClicked(int pos) {
                //delete item form the adapter
                items.remove(pos);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(pos);
                Toast.makeText(getApplicationContext(),"item was removed",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        //edit
        ItemsAdapter.onClickListener onClickListener = new ItemsAdapter.onClickListener() {
            @Override
            public void onItemClicked(int pos) {
                Log.d("MainActivity","singleClickAtItem"+items.get(pos));

                //create the new activity
                Intent i = new Intent(MainActivity.this,editActivity.class);
                //pass the data being edited
                i.putExtra(KEY_ITEM_TEXT,items.get(pos));
                i.putExtra(KEY_ITEM_POSITION,pos);
                //display the activity
                startActivityForResult(i,EDIT_TEXT_CORE);
            }
        };
        itemsAdapter = new ItemsAdapter(items,onLongClickListener,onClickListener);
        view.setAdapter(itemsAdapter);
        view.setLayoutManager(new LinearLayoutManager(this));


        //add item
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = text.getText().toString().trim();
                if(todoItem.length() !=0) {
                    // add item to model
                    items.add(todoItem);
                    //notify adapter an item was inserted
                    itemsAdapter.notifyItemInserted(items.size() - 1);
                    text.setText("");
                    Toast.makeText(getApplicationContext(), "item was added", Toast.LENGTH_SHORT).show();
                    saveItems();
                }
            }
        });

    }
    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
    }

    //this function will read the items by by reading the lines fo the file
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error reading items",e);
            items = new ArrayList<>();
        }
    }
    //saves items in a file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity","Error writing items",e);
        }
    }
}