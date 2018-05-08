package com.example.daniel.riskdice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class postBattleScreen extends AppCompatActivity {

    TextView postBattleSynopsis;
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_battle_screen);

        postBattleSynopsis = (TextView) findViewById(R.id.battleSynopsis);
        returnButton = (Button) findViewById(R.id.mainMenu);

        // Get the battleSynopsis from Intent and display on the TextView
        Intent intent = getIntent();
        postBattleSynopsis.setText(intent.getStringExtra("battleSynopsis"));

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeScreens = new Intent(postBattleScreen.this, MainActivity.class);
                finish();
                startActivity(changeScreens);
            }
        });
    }
}
