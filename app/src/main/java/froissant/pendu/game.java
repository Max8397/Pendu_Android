package froissant.pendu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class game extends Activity {


    private Button delete;
    private Button valid;
    private TextView center;
    private TextView letter;
    private ImageView image;

    private String mot;
    private String mot_a_deviner;
    private String affichage;

    private short k = 0;
    private short taille;
    private short marge = 1;
    private short erreur;
    private short reussite;


    private char[] lettre_deja_jouer = new char[26];
    private char[] MotCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        center = (TextView) findViewById(R.id.textView);
        letter = (EditText) findViewById(R.id.editText);
        delete = (Button) findViewById(R.id.bdelete);
        valid = (Button) findViewById(R.id.bvalid);
        image = (ImageView) findViewById(R.id.imageView);
        reussite = 0;
        erreur = 0;

        Generation_Mot_Cache();

        affichage = "Le mot :" + "\n" + mot.substring(0,1).toUpperCase() + mot.substring(1).toLowerCase();
        center.setText(affichage); // on affiche à l'écran le mot caché
        image.setImageResource(R.drawable.e0);
        Toast.makeText(getApplicationContext(), "Appuyez sur la case blanche pour faire apparaître le clavier", Toast.LENGTH_SHORT).show();

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                letter.setText("");
            }


        });

        letter.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return false;
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    action();
                    return true;
                }
                return false;
            }
        });

        valid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                action();
            }
        });
    }

    // ============================================================================================================

    private void action()
    {
        try
        {
            Verif_Mot();
            letter.setText(""); //on vide le champs de saisie

            affichage = mot.substring(0,1).toUpperCase() + mot.substring(1).toLowerCase() + "\n" + "Erreur(s) : " + erreur + " sur 10 ";
            center.setText(affichage); // on affiche le mot et le nombre d'erreur(s)

            if (reussite == taille - marge) {
                Gestion_victoire();
            } else {
                Gestion_Erreur();
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(), "Veuillez saisir une lettre !", Toast.LENGTH_SHORT).show();
        }

    }

    //===============================================================================================================

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(game.this);
        builder.setMessage("Voulez vous quitter le jeu ?");
        builder.setTitle("Avertissement");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent;
                intent = new Intent(game.this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Non", null);
        {
        }
        builder.create().show();
    }

    // =========================================================================================================


    private void Gestion_Erreur()
    {
        // On affiche les images du pendu en fonction du nombre d'erreur(s)

        switch (erreur)
        {
            case 0 :
                image.setImageResource(R.drawable.e0);
                break;
            case 1 :
                image.setImageResource(R.drawable.e1);
                break;
            case 2 :
                image.setImageResource(R.drawable.e2);
                break;
            case 3 :
                image.setImageResource(R.drawable.e3);
                break;
            case 4 :
                image.setImageResource(R.drawable.e4);
                break;
            case 5 :
                image.setImageResource(R.drawable.e5);
                break;
            case 6 :
                image.setImageResource(R.drawable.e6);
                break;
            case 7 :
                image.setImageResource(R.drawable.e7);
                break;
            case 8 :
                image.setImageResource(R.drawable.e8);
                break;
            case 9 :
                image.setImageResource(R.drawable.e9);
                break;
            case 10 : {
                image.setImageResource(R.drawable.e10);
                affichage = "Game Over :(" + "\n" + "\n" + "Le mot à deviner était :\n" + mot_a_deviner;
                center.setText(affichage); // on annonce la défaite ainsi que le mot qu'il fallait trouver
                valid.setEnabled(false); // on désactive le bouton pour "bloquer le jeu"
                delete.setEnabled(false);

                Fermeture_Fin();
            }
                break;

        }
    }

    // ====================================================================================================

    private void Generation_Mot_Cache()
    {
        ArrayList<String> tabMots = Creation_TabMots();

        Random rand = new Random(); //Déclaration de l'objet rand, qui servira a utiliser des nombres aléatoires.
        int nbAleatoire = rand.nextInt(tabMots.size()); //Cette variable contient un nombre compris entre 0 et la valeur de la taille du tableau des mots a chercher.
        mot_a_deviner = tabMots.get(nbAleatoire); //On insère dans la chaine de caractère mot_a_deviner le mots qui a été tiré au hasard
        taille = (short)mot_a_deviner.length(); //On définit une variable égale aux nombres de lettre dt mot choisit

        MotCache = new char[taille];

        for (int j = 0; j < taille; j++) // on masque le mot
        {

            if (j == 0)
            {
                MotCache[0] = mot_a_deviner.charAt(0); // on laisse apparaitre la 1er lettre
                lettre_deja_jouer[k] = mot_a_deviner.charAt(0);
                k++;
            }
            else if (MotCache[0] == mot_a_deviner.charAt(j))
            {
                MotCache[j] = mot_a_deviner.charAt(j); // on laisse les lettres identiques à la 1er;
                marge++;
            }
            else
                MotCache[j] = '*'; // on masque les autres
        }
        mot = String.valueOf(MotCache); // on récupère le contenu du tableau MotCache dans la chaine de caractère mot

        System.out.println("mot : " +mot_a_deviner+" taille : "+taille+" mot masqué : "+MotCache.toString());

    }

    //======================================================================================================================================================

    private void Fermeture_Fin()
    {
        new CountDownTimer(2000, 2000) { //on lance un délai de 2 secondes
            public void onTick(long tps) {
            }

            public void onFinish() {
                Intent intent;
                intent = new Intent(game.this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish(); // Call once you redirect to another activity
            }
        }.start();
    }

    //======================================================================================================

    private ArrayList<String> Creation_TabMots() /* Cette fonction va lire le fichier txt contenant les mots */
    {
        ArrayList<String> mylist = new ArrayList<String>();

        try{
            InputStream in = getResources().openRawResource(R.raw.listemots);
            if(in != null)
            {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader Lecture = new BufferedReader(tmp);
                String Mot_F;
                while((Mot_F = Lecture.readLine())!= null){
                    mylist.add(Mot_F);
                }
                in.close();
            }
        }
        catch (Throwable t)
        {
            System.out.println("Erreur lors du chargement des mots");
        }

        return mylist;
    }

    //=======================================================================================================================================================

    private void Verif_Mot()
    {
        boolean trouve;

        char[] rep1 = mot_a_deviner.toCharArray(); // on convertit la chaine de caratères mot_a_deviner en caractère dans le tableau rep1
        char[] rep2 = letter.getText().toString().toCharArray(); // on convertit la chaine de caratères issue de l'entrée texte en caractères dans le tableau rep2
        char[] rep3 = MotCache; // on met dans le tableau rep3, le mot caché

        trouve = remplacement_caractere(rep1, rep2, rep3);

        if (!trouve) { // on augmente le nombre d'échec si la lettre n'est pas dans le mot
            erreur += 1; // On augemnte le nombre d'erreur
        }

        letter.setText(""); //on nettoie l'entrée du texte
        mot = String.valueOf(rep3); //on récupère le contenu du tableau rep dans la chaine de caractère mot


    }

    //=========================================================================================================================================================


    private Boolean Deja_saisie(char[] rep2)
    {

        boolean lettre_deja_saisie = false;
        for(int l = 0; l < 26; l++)
        {
            if (rep2[0] == lettre_deja_jouer[l])
            {
                Toast.makeText(getApplicationContext(), "Cette lettre a déjà été saisie !", Toast.LENGTH_SHORT).show();
                lettre_deja_saisie = true;
            }

        }

        lettre_deja_jouer[k] = rep2[0]; // Ajout de la lettre dans les lettres déjà saisie
        k++;

        return lettre_deja_saisie;
    }

    //=========================================================================================================

    private boolean remplacement_caractere(char[] rep1, char[] rep2, char[] rep3)
    {
        boolean deja_saisie = Deja_saisie(rep2);
        boolean remplace = deja_saisie;

        if (!deja_saisie) // on vérifie si la vérification précédente (lettre déjà validée) est fausse
        {
            for (int j = 0; j < taille; j++) { //Pour chaque case...
                if (rep1[j] == rep2[0]) { //On vérifie si la lettre validée est dans le mot
                    rep3[j] = rep2[0]; // on remplace l'astérixe par la lettre validée
                    reussite += 1; // On augmente le nombre de lettre trouvé
                    remplace = true;
                }
            }

        }

        return remplace;
    }

    //==============================================================================================================

    private void Gestion_victoire()
    {
        image.setImageResource(R.drawable.win);
        affichage = "VICTOIRE :)" + "\n" + "\n" + "Vous avez trouvé le mot :\n" + mot_a_deviner.substring(0,1).toUpperCase() + mot_a_deviner.substring(1).toLowerCase();
        center.setText(affichage); //On annonce la victoire ainsi que le mot trouvé
        valid.setEnabled(false); // on désactive le bouton pour "bloquer le jeu"
        delete.setEnabled(false);
        Fermeture_Fin();
    }
}




