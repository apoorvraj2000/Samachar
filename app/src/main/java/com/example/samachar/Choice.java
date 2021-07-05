package com.example.samachar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Choice extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    String choice="";
    @SuppressLint("SourceLockedOrientationActivity")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
    }

    public void radioCheck(View view) {
    int checkedButtonid=radioGroup.getCheckedRadioButtonId();
    radioButton=(RadioButton)findViewById(checkedButtonid);
    choice=radioButton.getText().toString();

        Toast.makeText(this,choice+" Checked",Toast.LENGTH_SHORT).show();
    }

    public void search(View view) {
        if(choice.isEmpty()){
            Toast.makeText(this,"Please Select The Category",Toast.LENGTH_LONG).show();
        }else {
            String finalChoice=choice.toLowerCase();
            Intent intent = new Intent(Choice.this, MainActivity.class);
            intent.putExtra("category", finalChoice);
            startActivity(intent);
        }
    }
}