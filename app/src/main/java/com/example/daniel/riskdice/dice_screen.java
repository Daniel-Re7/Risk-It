package com.example.daniel.riskdice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class dice_screen extends AppCompatActivity {
    private ImageView attack1;
    private ImageView attack2;
    private ImageView attack3;
    private ImageView defend1;
    private ImageView defend2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        attack1 = (ImageView) findViewById(R.id.attack1);
        attack2 = (ImageView) findViewById(R.id.attack2);
        attack3 = (ImageView) findViewById(R.id.attack3);
        defend1 = (ImageView) findViewById(R.id.defend1);
        defend2 = (ImageView) findViewById(R.id.defend2);
        int attackNum = Integer.parseInt(intent.getStringExtra("attackNum"));
        int defendNum = Integer.parseInt(intent.getStringExtra("defendNum"));

        if(attackNum > 3)
        {
            attack1.setVisibility(View.VISIBLE);
            attack2.setVisibility(View.VISIBLE);
            attack3.setVisibility(View.VISIBLE);
        }

        else if(attackNum > 2)
        {
            attack1.setVisibility(View.VISIBLE);
            attack2.setVisibility(View.VISIBLE);
            attack3.setVisibility(View.INVISIBLE);
        }

        else if(attackNum > 1 )
        {
            attack1.setVisibility(View.VISIBLE);
            attack2.setVisibility(View.INVISIBLE);
            attack3.setVisibility(View.INVISIBLE);
        }



    }
}
