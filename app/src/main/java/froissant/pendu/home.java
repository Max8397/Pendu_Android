package froissant.pendu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class home extends Activity {
    int backpress = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Button buttonrules = (Button) findViewById(R.id.btnrules);
        buttonrules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(home.this, rules.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish(); // Call once you redirect to another activity
            }
        });

        Button buttongame = (Button) findViewById(R.id.btngame);
        buttongame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(home.this, game.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish(); // Call once you redirect to another activity
            }
        });


        Button buttonquit = (Button) findViewById(R.id.btnquit);
        buttonquit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }



        });}


    public void onBackPressed() {
        backpress = (backpress + 1);
        Toast.makeText(getApplicationContext(), " Pressez à nouveau pour quitter ", Toast.LENGTH_SHORT).show();


        if (backpress > 1) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
