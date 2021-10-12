package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class editActivity extends AppCompatActivity {
    EditText etItem;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etItem = findViewById(R.id.etTextBox);
        btn = findViewById(R.id.btnSave);
        getSupportActionBar().setTitle("Edit Item");
        String word = getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT);
        etItem.setText(word);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String todoItem = etItem.getText().toString().trim();
                if(todoItem.length()!=0) {
                    //create intent that contains results
                    Intent intent = new Intent();
                    //pass data
                    intent.putExtra(MainActivity.KEY_ITEM_TEXT, etItem.getText().toString());
                    intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                    //set result of the intent
                    setResult(RESULT_OK, intent);
                    //finish activity, close screen and go back
                    finish();
                }
            }
        });
    }

}