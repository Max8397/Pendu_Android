package froissant.pendu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewDebug;
import android.widget.TextView;


public class rules extends Activity {

    TextView t1;
    TextView t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rules);


        t1 = (TextView) findViewById(R.id.T1);
        t1.setText(Html.fromHtml("Règles du jeu"));
        t1.setTextColor(Color.RED);
        t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        t1.setPaintFlags(t1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        t1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        t2 = (TextView) findViewById(R.id.T2);
        t2.setText(Html.fromHtml("* Un mot va être tiré au hasard. La 1er lettre va s'afficher, ainsi que les endroits avec la même lettre.<br /><br />*  Vous devez proposé une lettre puis valider.<br /><br />Si vous avez juste : la lettre s'affiche et le pendu n'évolue pas <br />Si vous avez faux : le pendu évolue d'une étape <br /><br />* Vous avez le droit à 10 essais<br /><br />* La partie s'arrête lorsque vous avez trouver le mot ou lorsque le pendu est complet."));


    }

    public void onBackPressed(){
        Intent intent = new Intent(rules.this, home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish(); // Call once you redirect to another activity

    }
}

