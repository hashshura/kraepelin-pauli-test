package hashshura.kraepelinpaulitest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    private EditText nameView, timeLimitView;
    private int scoreHs;
    private String nameHs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        nameView = findViewById(R.id.name);
        timeLimitView = findViewById(R.id.timeLimit);

    }

    public void highscore(View view){

        SharedPreferences prefs = this.getSharedPreferences("kraepelin-pauli-test-highscore", Context.MODE_PRIVATE);

        scoreHs = prefs.getInt("highscore", 0);
        nameHs = prefs.getString("name", "Nobody");

        Toast.makeText(this, "Name: " + nameHs + "\nScore: " + scoreHs, Toast.LENGTH_SHORT).show();

    }

    public void beginTest(View view){

        String name = nameView.getText().toString();
        String timeLimit = timeLimitView.getText().toString();

        if ((timeLimit.matches("-?\\d+")) || (timeLimit.matches(""))){

            Intent toTest = new Intent(this, TestActivity.class);
            toTest.putExtra("name", name);
            toTest.putExtra("timeLimit", timeLimit);

            startActivity(toTest);

        } else {

            Toast.makeText(this, "Please insert valid (integer) time limit.", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }

}
