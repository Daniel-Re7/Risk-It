package com.example.daniel.riskdice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private EditText attackUnitsField, defendUnitsField;
    private TextView fight;
    private Button Lock_In_Button, nextButton;

    private int attackNum, defendNum;        //Store the amount of attacking and defending units

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views
        attackUnitsField = (EditText) findViewById(R.id.attackText);
        defendUnitsField = (EditText) findViewById(R.id.defendText);
        fight = (TextView) findViewById(R.id.battleView);
        Lock_In_Button = (Button) findViewById(R.id.lockInButton);
        nextButton = (Button) findViewById(R.id.prepareButton);

        Lock_In_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {
                    attackNum = Integer.parseInt(attackUnitsField.getText().toString());
                    defendNum = Integer.parseInt(defendUnitsField.getText().toString());

                    // Checks to make sure the attacker is entering battle with at least 2 units. If they do not
                    // then they can not attack with this army
                    if (attackNum < 2)
                    {
                        new AlertDialog.Builder(view.getContext())
                                .setMessage("Attacker must enter battle with at least 2 units. If the attacker does not have at least 2 units, then they can not attack with this army")
                                .setPositiveButton("Okay", null)
                                .show();
                    }

                    // Checks if defender has at least 1 defending unit. If they do not, then the attacker automatically wins.
                    else if (defendNum <= 0)
                    {
                        new AlertDialog.Builder(view.getContext())
                                .setMessage("Defender must defend their territory with at least 1 unit. If the defender has no units, the attacker wins the territory")
                                .setPositiveButton("Okay", null)
                                .show();
                    }

                    else
                    {
                        fight.setText("Attackers: " + attackNum + "\n" + "\nDefenders: " + defendNum);
                        nextButton.setVisibility(View.VISIBLE);
                    }

                }

                catch (NumberFormatException e)
                {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Please enter a value for all fields.\n\n" +
                            "Attacker must enter battle with at least 2 units, otherwise they can not attack with this army.\n\n" +
                            "Defender must have at least 1 unit defending, otherwise the attacker wins.")
                            .setPositiveButton("Okay", null)
                            .show();
                }

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent changeScreen = new Intent(MainActivity.this, dice_screen.class);
                changeScreen.putExtra("attackNum",String.valueOf(attackNum));
                changeScreen.putExtra("defendNum",String.valueOf(defendNum));
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
