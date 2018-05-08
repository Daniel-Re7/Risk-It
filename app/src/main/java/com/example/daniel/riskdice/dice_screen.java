package com.example.daniel.riskdice;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

public class dice_screen extends AppCompatActivity {
    private ImageView attack1;
    private ImageView attack2;
    private ImageView attack3;
    private ImageView defend1;
    private ImageView defend2;

    private TextView resultText;
    private TextView attack_units;
    private TextView defend_units;

    private Button attackButton;
    private Button defendButton;
    private Button defend_Roll1;
    private Button defend_Roll2;
    private Button attack_again;
    private Button retreat_button;

    private ArrayList<Integer> attackResults = new ArrayList<>();
    private ArrayList<Integer> defendResults = new ArrayList<>();

    private int totalAttackDie;
    private int defend_die_num;
    private int attackNum;
    private int defendNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        //Initialize TextView's
        attack_units = (TextView) findViewById(R.id.attack_units);
        defend_units = (TextView) findViewById(R.id.defend_units);
        resultText = (TextView) findViewById(R.id.resultText);

        //Initialize ImageView's
        attack1 = (ImageView) findViewById(R.id.attack1);
        attack2 = (ImageView) findViewById(R.id.attack2);
        attack3 = (ImageView) findViewById(R.id.attack3);
        defend1 = (ImageView) findViewById(R.id.defend1);
        defend2 = (ImageView) findViewById(R.id.defend2);

        //Initialize Button's
        attackButton = (Button) findViewById((R.id.attackButton));
        defendButton = (Button) findViewById(R.id.defendButton);
        defend_Roll1 = (Button) findViewById(R.id.defendButton_Roll1);
        defend_Roll2 = (Button) findViewById(R.id.defendButton_Roll2);
        attack_again = (Button) findViewById(R.id.attack_again_button);
        retreat_button = (Button) findViewById(R.id.retreat_button);

        //Get # of attack unit and defend units
        defendNum = Integer.parseInt(intent.getStringExtra("defendNum"));
        attackNum = Integer.parseInt(intent.getStringExtra("attackNum"));

        //Set attacker / defender amounts for TextView
        attack_units.setText(String.valueOf(attackNum));
        defend_units.setText(String.valueOf(defendNum));

        //Set number of diced based off of attacking amount in accordance to RISK rules
        setDice(attackNum, "attacker");
        setDice(defendNum, "defender");

        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attack button pressed
                //attackRoll() rolls the attackers dice
                attackRoll();
                //Update dice images accordingly
                updateAttackDice();

                if(defendNum > 1)
                {
                    defend_Roll1.setVisibility(View.VISIBLE);
                    defend_Roll2.setVisibility(View.VISIBLE);
                }

                else
                {
                    defend_Roll1.setVisibility(View.VISIBLE);
                    defend_Roll2.setVisibility(View.INVISIBLE);
                }
            }
        });

        defend_Roll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Defender chose to roll 1 die
                defend_Roll1.setVisibility(View.GONE);
                defend_Roll2.setVisibility(View.GONE);
                defendButton.setVisibility(View.VISIBLE);
                defend2.setVisibility(View.INVISIBLE);
                defend_die_num = 1;
            }
        });

        defend_Roll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Defender chose to roll 2 dice
                defend_Roll1.setVisibility(View.GONE);
                defend_Roll2.setVisibility(View.GONE);
                defendButton.setVisibility(View.VISIBLE);
                defend_die_num = 2;
            }
        });

        defendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Defender rolled so roll the dice
                defendRoll();
                //Update the images accordingly
                updateDefendDice();
                defendButton.setVisibility(View.INVISIBLE);
                //Compute the winner of battle
                decideWinner();
                checkArmyConditions();
            }
        });

        attack_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attacker chose to attack again
                //Get the intent and add updated attack and defend amounts to it
                Intent intent = getIntent();
                intent.putExtra("attackNum", String.valueOf(attackNum));
                intent.putExtra("defendNum", String.valueOf(defendNum));

                //finish the activity and start new with updated values
                finish();
                startActivity(intent);
            }
        });

        retreat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeScreen = new Intent(dice_screen.this, MainActivity.class);
                startActivity(changeScreen);
            }
        });


    }

    public void setDice(int attackNum, String army)
    {
        //Sets the number of diced based off of attacking amount in accordance to RISK rules

        switch(army) {
            case "attacker":
            {
                if (attackNum > 3) {
                    totalAttackDie = 3;
                    attack1.setVisibility(View.VISIBLE);
                    attack2.setVisibility(View.VISIBLE);
                    attack3.setVisibility(View.VISIBLE);
                } else if (attackNum > 2) {
                    totalAttackDie = 2;
                    attack1.setVisibility(View.VISIBLE);
                    attack2.setVisibility(View.VISIBLE);
                    attack3.setVisibility(View.INVISIBLE);
                } else {
                    totalAttackDie = 1;
                    attack1.setVisibility(View.VISIBLE);
                    attack2.setVisibility(View.INVISIBLE);
                    attack3.setVisibility(View.INVISIBLE);
                }
            }

            case "defender":
            {
                if(defendNum > 1)
                {
                    defend1.setVisibility(View.VISIBLE);
                    defend2.setVisibility(View.VISIBLE);
                }

                else
                {
                    defend1.setVisibility(View.VISIBLE);
                    defend2.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public void attackRoll ()
    {
        //Rolls random numbers between 1-6 for however many dice there are
        attackResults.clear();
        Random random = new Random();
        for(int x = 0; x < totalAttackDie; x++)
        {
            attackResults.add(random.nextInt(6) + 1);
        }
        Collections.sort(attackResults, Collections.<Integer>reverseOrder());
        attackButton.setVisibility(View.GONE);
    }

    public void updateAttackDice()
    {
        //Updates the dice images based on results from attackRoll()
        for(int x = 0; x < attackResults.size(); x++)
        {
            int number = attackResults.get(x);
            switch(number)
            {
                case 6:
                {
                    if(x == 0)
                        attack1.setImageResource(R.drawable.reddice6);
                    else if(x == 1)
                        attack2.setImageResource(R.drawable.reddice6);
                    else
                        attack3.setImageResource(R.drawable.reddice6);
                    break;
                }

                case 5:
                {
                    if(x == 0)
                        attack1.setImageResource(R.drawable.reddice5);
                    else if(x == 1)
                        attack2.setImageResource(R.drawable.reddice5);
                    else
                        attack3.setImageResource(R.drawable.reddice5);
                    break;
                }

                case 4:
                {
                    if(x == 0)
                        attack1.setImageResource(R.drawable.reddice4);
                    else if(x == 1)
                        attack2.setImageResource(R.drawable.reddice4);
                    else
                        attack3.setImageResource(R.drawable.reddice4);
                    break;
                }

                case 3:
                {
                    if(x == 0)
                        attack1.setImageResource(R.drawable.reddice3);
                    else if(x == 1)
                        attack2.setImageResource(R.drawable.reddice3);
                    else
                        attack3.setImageResource(R.drawable.reddice3);
                    break;
                }

                case 2:
                {
                    if(x == 0)
                        attack1.setImageResource(R.drawable.reddice2);
                    else if(x == 1)
                        attack2.setImageResource(R.drawable.reddice2);
                    else
                        attack3.setImageResource(R.drawable.reddice2);
                    break;
                }

                case 1:
                {
                    if(x == 0)
                        attack1.setImageResource(R.drawable.reddice1);
                    else if(x == 1)
                        attack2.setImageResource(R.drawable.reddice1);
                    else
                        attack3.setImageResource(R.drawable.reddice1);
                    break;
                }

            }
        }
    }

    public void defendRoll ()
    {
        //Clear out defendResults and randomly generate dice rolls for defender
        defendResults.clear();
        Random random = new Random();
        for(int x = 0; x < defend_die_num; x++)
        {
            defendResults.add(random.nextInt(6) + 1);
        }
        Collections.sort(defendResults, Collections.<Integer>reverseOrder());
        defendButton.setVisibility(View.GONE);
    }

    public void updateDefendDice()
    {
        //Set the appropriate images based on the values rolled for defender
        for(int x = 0; x < defendResults.size(); x++)
        {
            int number = defendResults.get(x);
            switch(number)
            {
                case 6:
                {
                    if(x == 0)
                        defend1.setImageResource(R.drawable.blackdice6);
                    else
                        defend2.setImageResource(R.drawable.blackdice6);
                    break;
                }

                case 5:
                {
                    if(x == 0)
                        defend1.setImageResource(R.drawable.blackdice5);
                    else
                        defend2.setImageResource(R.drawable.blackdice5);
                    break;
                }

                case 4:
                {
                    if(x == 0)
                        defend1.setImageResource(R.drawable.blackdice4);
                    else
                        defend2.setImageResource(R.drawable.blackdice4);
                    break;
                }

                case 3:
                {
                    if(x == 0)
                        defend1.setImageResource(R.drawable.blackdice3);
                    else
                        defend2.setImageResource(R.drawable.blackdice3);
                    break;
                }

                case 2:
                {
                    if(x == 0)
                        defend1.setImageResource(R.drawable.blackdice2);
                    else
                        defend2.setImageResource(R.drawable.blackdice2);
                    break;
                }

                case 1:
                {
                    if(x == 0)
                        defend1.setImageResource(R.drawable.blackdice1);
                    else
                        defend2.setImageResource(R.drawable.blackdice1);
                    break;
                }

            }
        }
    }

    public void decideWinner()
    {

        int attack_dice_won = 0, defend_dice_won = 0;

        //See how many times attacker / defender won
        //This is done by ordering Arrays from Highest -> Lowest and comparing die rolls
        //If defender is >= then they get defenders advantage and win
        if(totalAttackDie == 1)
        {
            if(defendResults.get(0) >= attackResults.get(0))
                defend_dice_won++;

            else
                attack_dice_won++;
        }

        else
        {
            for (int x = 0; x < defend_die_num; x++)
            {
                // Defenders advantage, if both are the same defender wins
                if (defendResults.get(x) >= attackResults.get(x))
                    defend_dice_won++;


                else
                    attack_dice_won++;

            }
        }

        String winnerText = "";

        //adjust the unit values
        attackNum = attackNum - defend_dice_won;
        defendNum = defendNum - attack_dice_won;

        //Set post battle text and unit lost report
        if(attack_dice_won > defend_dice_won)
        {
            winnerText+="Attackers Won!";
        }

        else if(defend_dice_won > attack_dice_won)
        {
            winnerText+="Defenders Won!";
        }

        else
        {
            winnerText+="Battle was a Draw!";
        }

        resultText.setText(winnerText + "\n\n" +
                           "Attackers Lost: " + defend_dice_won +"\n" +
                           "Defenders Lost: " + attack_dice_won);

        // Update the visual unit counter and make attack_again button visible
        attack_units.setText(String.valueOf(attackNum));
        defend_units.setText(String.valueOf(defendNum));
        attack_again.setVisibility(View.VISIBLE);
        retreat_button.setVisibility(View.VISIBLE);

    }

    public void checkArmyConditions()
    {
        if(attackNum <= 1 && defendNum > 0)
        {
            attack_again.setVisibility(View.INVISIBLE);
            retreat_button.setVisibility(View.INVISIBLE);

            // Delays the activity change
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Go to post battle screen
                    Intent changeScreen = new Intent(dice_screen.this, postBattleScreen.class);
                    changeScreen.putExtra("battleSynopsis","The Defender has held off the invading army. \n\n" +
                            "They live another day!");
                    finish();
                    startActivity(changeScreen);
                }
            }, 3000);

        }

        else if (attackNum > 1 && defendNum <= 0)
        {
            attack_again.setVisibility(View.INVISIBLE);
            retreat_button.setVisibility(View.INVISIBLE);

            // Delays the activity change
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Go to post battle screen
                    Intent changeScreen = new Intent(dice_screen.this, postBattleScreen.class);
                    changeScreen.putExtra("battleSynopsis","The Attacker has successfully destroyed the defending army. \n\n" +
                            "Their territory now belongs to the Attacker!");
                    finish();
                    startActivity(changeScreen);
                }
            }, 3000);

        }
    }

}
