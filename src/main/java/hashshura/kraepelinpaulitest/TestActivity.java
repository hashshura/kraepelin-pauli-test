package hashshura.kraepelinpaulitest;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    private String name, timeLimit;
    private int scoreInt, timerLimit;
    private TextView hoursLeft, minutesLeft, secondsLeft, score, head, tail;
    private String headString, tailString;
    private boolean isEnded;
    private CountDownTimer timer;
    private boolean isStarted;

    @Override
    protected void onStop(){
        super.onStop();
        isEnded = true;
        if (isStarted == true){
            timer.cancel();
        }
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        isEnded = false;
        isStarted = false;

        hoursLeft = findViewById(R.id.hoursLeft);
        minutesLeft = findViewById(R.id.minutesLeft);
        secondsLeft = findViewById(R.id.secondsLeft);

        score = findViewById(R.id.score);
        head = findViewById(R.id.head);
        tail = findViewById(R.id.tail);

        Intent getIntent = getIntent();
        name = getIntent.getStringExtra("name");
        timeLimit = getIntent.getStringExtra("timeLimit");

        if (timeLimit.matches("")){timeLimit = "30";}
        if (name.matches("")){name = "John Smith";}

        timerLimit = Integer.parseInt(timeLimit);

        scoreInt = 0;

        headString = String.format("%02d", (int) (Math.random() * 100));
        head.setText(headString);

        hoursLeft.setText(String.format("%02d", timerLimit/3600));
        minutesLeft.setText(String.format("%02d", (timerLimit%3600)/60));
        secondsLeft.setText(String.format("%02d", (timerLimit)%60));

    }

    private void startTest(){

        TextView beforeStart = findViewById(R.id.beforeStart);
        beforeStart.setText("");

        tailString = String.format("%09d", (int) (Math.random() * 1000000000));
        tail.setText(tailString);

        timer = new CountDownTimer((timerLimit)*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long timeRemaining = millisUntilFinished/1000;

                hoursLeft.setText(String.format("%02d", timeRemaining/3600));
                minutesLeft.setText(String.format("%02d", (timeRemaining%3600)/60));
                secondsLeft.setText(String.format("%02d", (timeRemaining)%60));

            }

            @Override
            public void onFinish() {

                if (isEnded == false){
                    endTest();
                }

            }
        };
        timer.start();

    }

    private void push(int num){

        if (isStarted == false){
            isStarted = true;
            startTest();
        }

        int firstNumber = Character.getNumericValue(headString.charAt(0));
        int secondNumber = Character.getNumericValue(headString.charAt(1));

        int correctAnswer = (firstNumber + secondNumber)%10;

        if (num == correctAnswer){

            addScore(1);

            headString = "" + headString.charAt(1) + tailString.charAt(0);

            String newRandom = String.format("%d", (int) (Math.random() * 10));
            tailString = tailString.substring(1) + newRandom;

            head.setText(headString);
            tail.setText(tailString);

            head.setTextColor(getResources().getColor(R.color.defaultHead));
            score.setTextColor(getResources().getColor(R.color.defaultText));

        } else {

            addScore(-1);
            head.setTextColor(getResources().getColor(R.color.wrongAnswer));
            score.setTextColor(getResources().getColor(R.color.wrongAnswer));

        }

    }

    private void addScore(int val){
        scoreInt += val;
        score.setText(Integer.toString(scoreInt));
    }

    private void endTest(){
        String finalScore = Integer.toString(scoreInt);

        Intent toFinal = new Intent(this, TestFinished.class);
        toFinal.putExtra("finalName", name);
        toFinal.putExtra("finalScore", finalScore);
        toFinal.putExtra("testTime", timeLimit);

        startActivity(toFinal);
        finish();
    }

    public void push0(View view){push(0);}
    public void push1(View view){push(1);}
    public void push2(View view){push(2);}
    public void push3(View view){push(3);}
    public void push4(View view){push(4);}
    public void push5(View view){push(5);}
    public void push6(View view){push(6);}
    public void push7(View view){push(7);}
    public void push8(View view){push(8);}
    public void push9(View view){push(9);}

}
