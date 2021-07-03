package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView nine = (TextView) findViewById(R.id.nine);
        TextView zero = (TextView) findViewById(R.id.zero);
        TextView eight = (TextView) findViewById(R.id.eight);
        TextView seven = (TextView) findViewById(R.id.seven);
        TextView six = (TextView) findViewById(R.id.six);
        TextView five = (TextView) findViewById(R.id.five);
        TextView four = (TextView) findViewById(R.id.four);
        TextView three = (TextView) findViewById(R.id.three);
        TextView two = (TextView) findViewById(R.id.two);
        TextView one = (TextView) findViewById(R.id.one);
        TextView dot = (TextView) findViewById(R.id.dot);
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("0");

            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("9");

            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("8");

            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("7");

            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("6");

            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("5");

            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("4");

            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("3");

            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("2");

            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("1");

            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen(".");

            }
        });
        TextView add = (TextView) findViewById(R.id.plus);
        TextView sub = (TextView) findViewById(R.id.minus);
        TextView mulp = (TextView) findViewById(R.id.mulp);
        TextView div = (TextView) findViewById(R.id.divide);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("+");

            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("-");

            }
        });
        mulp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("*");

            }
        });
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen("/");

            }
        });


    }
    String str = "";
    public void clear(View view){
        TextView screen = (TextView) findViewById(R.id.screen);
        TextView window = (TextView) findViewById(R.id.window);
        screen.setText("");
        window.setText("");
        str="";
    }

    public void screen(String txt) {
        if(str.length()!=0) {
            if ((str.charAt(str.length()-1) == '+' || str.charAt(str.length()-1) == '-' || str.charAt(str.length()-1) == '*' || str.charAt(str.length()-1) == '/' || str.charAt(str.length()-1) == '.') && (txt.charAt(0) == '+' || txt.charAt(0) == '-' || txt.charAt(0) == '*' || txt.charAt(0) == '/' || txt.charAt(0) == '.')) {
                str = str.substring(0, str.length() - 1);
                str = str + txt;
            } else
                str = str + txt;
        }
        else
            str = str + txt;
        TextView screen = (TextView) findViewById(R.id.screen);
        if(str.charAt(0)=='/'||str.charAt(0)=='*')
            screen.setText(str.substring(1));

        else
            screen.setText(str);
    }

    public void window(View view) {

        TextView window = (TextView) findViewById(R.id.window);
        TextView screen = (TextView) findViewById(R.id.screen);

        //settext cant display char. so convert to string
        try {
            if ((screen.getText().length() != 0)) {
                if ((eval(str) - (int) (eval(str))) == 0) {
                    window.setText(String.valueOf((int) eval(str)));
                    str = String.valueOf((int) eval(str));
                } else {
                    window.setText(String.valueOf(eval(str)));
                    str = String.valueOf(eval(str));
                }
            }
        }
        catch (Exception e) {
            window.setText("Error");
        }


    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public void undo(View view) {

        TextView screen = (TextView) findViewById(R.id.screen);
        if (screen.getText().length() != 0) {


            str = str.substring(0, str.length() - 1);
            screen.setText(str);
        }

    }
}

