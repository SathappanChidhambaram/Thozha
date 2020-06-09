package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void ngof(View V)
    {
       // Toast.makeText(MainActivity.this,"NGO",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,ngo.class);
        startActivity(intent);
        intent.putExtra("title","ngo");
    }
    public void healf(View V)
    {
       // Toast.makeText(MainActivity.this,"Health",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,BoxActivity.class);
        intent.putExtra("title","health");
        startActivity(intent);

    }
    public void eduf(View V)
    {
      //  Toast.makeText(MainActivity.this,"Education",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,BoxActivity.class);
        intent.putExtra("title","education");
        startActivity(intent);

    }
    public void awaf(View V)
    {
        Toast.makeText(MainActivity.this,"Awareness",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,BoxActivity.class);
        intent.putExtra("title","awareness");
        startActivity(intent);

    }
    public void agrif(View V)
    {
        //Toast.makeText(MainActivity.this,"Agriculture",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,BoxActivity.class);
        intent.putExtra("title","agriculture");
        startActivity(intent);

    }
}
