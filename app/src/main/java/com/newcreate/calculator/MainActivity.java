package com.newcreate.calculator;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    TextView tf1;
    TextView tf2;

    boolean shortButtonfragment;     // will show SBF is visible or not

    Button bdel,b0,b1,b4;


    @Override
    public void onStart()
    {
        super.onStart();
        FragmentManager fg = getSupportFragmentManager();
        FragmentTransaction ft = fg.beginTransaction();
        ft.show(fg.findFragmentById(R.id.f1));       // Not needed
        ft.show(fg.findFragmentById(R.id.f2));       // Display MBF while rotation
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shortButtonfragment = true;

        tf1 = findViewById(R.id.tf1);
        tf2 = findViewById(R.id.tf2);

        bdel = findViewById(R.id.bdel);
        bdel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlphaAnimation buttonClick = new AlphaAnimation(1f, 0.8f);
                buttonClick.setDuration(100);
                view.startAnimation(buttonClick);
                tf2.setText("");
                return true;
            }
        }) ;
    }


    public void buttonClicked(View v)
    {
        AlphaAnimation buttonClick = new AlphaAnimation(1f, 0.8f);
        buttonClick.setDuration(100);
        v.startAnimation(buttonClick);

        if(tf2.getText().toString().equals("ERROR"))  tf2.setText("");

        String temp = tf2.getText().toString();

        switch (v.getId())
        {
            case R.id.b0:    tf2.append("0"); break;
            case R.id.b1:    tf2.append("1"); break;
            case R.id.b2:    tf2.append("2"); break;
            case R.id.b3:    tf2.append("3"); break;
            case R.id.b4:    tf2.append("4"); break;
            case R.id.b5:    tf2.append("5"); break;
            case R.id.b6:    tf2.append("6"); break;
            case R.id.b7:    tf2.append("7"); break;
            case R.id.b8:    tf2.append("8"); break;
            case R.id.b9:    tf2.append("9"); break;
            case R.id.bdot:  tf2.append("."); break;
            case R.id.bdel:  if(!temp.equals("")) tf2.setText(temp.substring(0,temp.length()-1));  break;
            case R.id.bdiv:  tf2.append("/"); break;
            case R.id.bmul:  tf2.append("x"); break;
            case R.id.bsub:  tf2.append("-"); break;
            case R.id.badd:  tf2.append("+"); break;
            case R.id.bpi:   tf2.append("PI"); break;
            case R.id.broot:   tf2.append("sqrt"); break;
            case R.id.bsin: tf2.append("sin"); break;
            case R.id.bcos: tf2.append("cos"); break;
            case R.id.btan: tf2.append("tan"); break;
            case R.id.bln: tf2.append("ln"); break;
            case R.id.blog: tf2.append("log"); break;
            case R.id.bopen: tf2.append("("); break;
            case R.id.bexp: tf2.append("e"); break;
            case R.id.bpow: tf2.append("^"); break;
            case R.id.bclose: tf2.append(")"); break;
            case R.id.bper:  tf2.append("%"); break;

            case R.id.bequal:
                            if(tf2.getText().toString().isEmpty())  break;
                           try {

                               String temp1 = tf2.getText().toString();

                               temp1 = temp1.replace("log","log10");
                               temp1 = temp1.replace("x","*");
                               temp1 = temp1.replace("ln","log");
                               temp1 = temp1.replace("PI",String.valueOf(Math.PI));
                               temp1 = temp1.replace("%","0.01");

                               Expression expression = new ExpressionBuilder(temp1).build();

                               double result = expression.evaluate();


                               long longResult = (long) result;

                               if(longResult == result)  tf2.setText(String.valueOf(longResult));
                               else                      tf2.setText(String.valueOf(result));


                               tf1.setText(temp);
                               break;
                           }catch (Exception e1)
                           {   tf1.setText(temp);
                               tf2.setText("ERROR");
                               break;
                           }
        }
    }


    public void fragmentChange(View v)
    {
        FragmentManager fg = getSupportFragmentManager();

        FragmentTransaction ft = fg.beginTransaction();

        if(shortButtonfragment)
        {
            ft.hide(fg.findFragmentById(R.id.f1));
            ft.show(fg.findFragmentById(R.id.f2));
            shortButtonfragment = false;
        }

        else
        {
            ft.hide(fg.findFragmentById(R.id.f2));
            ft.show(fg.findFragmentById(R.id.f1));
            shortButtonfragment = true;
        }

        ft.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("tf1Text",tf1.getText().toString());
        outState.putString("tf2Text",tf2.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        tf1.setText(savedInstanceState.getString("tf1Text"));
        tf2.setText(savedInstanceState.getString("tf2Text"));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
