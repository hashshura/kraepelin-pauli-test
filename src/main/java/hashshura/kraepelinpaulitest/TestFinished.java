package hashshura.kraepelinpaulitest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TestFinished extends AppCompatActivity {

    private TextView finalName, finalScore, spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_finished);

        finalName = findViewById(R.id.finalName);
        finalScore = findViewById(R.id.finalScore);
        spm = findViewById(R.id.spm);

        Intent getIntent = getIntent();

        String nameFinal = getIntent.getStringExtra("finalName");
        String scoreFinal = getIntent.getStringExtra("finalScore");
        String timeLimit = getIntent.getStringExtra("testTime");

        finalName.setText(nameFinal);
        finalScore.setText(scoreFinal);

        float scorePerMinute = Float.parseFloat(scoreFinal);
        float testMinute = Float.parseFloat(timeLimit);

        scorePerMinute = scorePerMinute * 60 / testMinute;

        spm.setText(String.format("%.2f", scorePerMinute));

        if (timeLimit.matches("30")){
            setHighScore(scoreFinal, nameFinal);
        }

    }

    private void setHighScore(String score, String name){

        SharedPreferences prefs = this.getSharedPreferences("kraepelin-pauli-test-highscore", Context.MODE_PRIVATE);
        int scoreHs = prefs.getInt("highscore", 0);

        int currentScore = Integer.parseInt(score);

        if (currentScore > scoreHs){

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", currentScore);
            editor.putString("name", name);
            editor.commit();

        }

    }

    public void toMain(View view){
        finish();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }
}
