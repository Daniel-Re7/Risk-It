package com.example.daniel.riskdice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private EditText e1, e2;
    private TextView t1, t2, fight;
    private Button Lock_In_Button, nextButton;

    String attackNum, defendNum;        //Store the amount of attacking and defending units

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views
        e1 = (EditText) findViewById(R.id.attackText);
        e2 = (EditText) findViewById(R.id.defendText);
        fight = (TextView) findViewById(R.id.battleView);
        Lock_In_Button = (Button) findViewById(R.id.lockInButton);
        nextButton = (Button) findViewById(R.id.prepareButton);

        Lock_In_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                attackNum = e1.getText().toString();
                defendNum = e2.getText().toString();
                fight.setText("Attackers: " + attackNum + "\n" + "\nDefenders: " + defendNum);

            }
        });

        //TODO: Prevent using nextButton until user locks in
        //TODO: Will do this by making button invisible until lockin pressed
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent changeScreen = new Intent(MainActivity.this, dice_screen.class);
                changeScreen.putExtra("attackNum",attackNum.toString());
                changeScreen.putExtra("defendNum",defendNum.toString());
                startActivity(changeScreen);
            }
            });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
