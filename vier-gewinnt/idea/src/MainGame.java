import processing.core.PApplet;
import processing.core.PImage;
import java.awt.*;
import java.sql.SQLOutput;
import javax.swing.JOptionPane;

public class MainGame extends PApplet {
    private static int ANZAHLSPALTEN;
    private static int ANZAHLZEILEN;
    private static Spielbrett spielbrett;
    private static Anzeigetafel anzeigetafel;
    private GraphicsDevice defaultScreenDevice;
    private double bildschirmBreite;
    private double bildschirmHoehe;
    private PImage bg;
    private Toast toast;
    private Button restart;


    public void settings() {
        // setzt die Größe des Bildschirms auf FullScreen. Alternative fullScreen() von Processing -> mit esc beenden
        defaultScreenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        bildschirmBreite = defaultScreenDevice.getDefaultConfiguration().getBounds().getWidth();
        bildschirmHoehe = defaultScreenDevice.getDefaultConfiguration().getBounds().getHeight();
        size((int) bildschirmBreite, (int) bildschirmHoehe);
    }

    public void setup() {
        bg = loadImage(".idea/pictures/img.png");
        bg.resize((int) bildschirmBreite, (int) bildschirmHoehe);

        //startet das Spiel
        starteSpiel();

        //erzeugt ein Objekt der Klasse Toast um Nachrichten anzuzeigen. Der Text wird dynamisch gesetzt.
        toast= new Toast((float) (bildschirmBreite * 0.5), (float) (bildschirmHoehe *0.8),(float) (bildschirmBreite * 0.02),this);

        //erzeugt einen Button um das Spiel neu zu starten
        restart = new Button((float) (bildschirmBreite*0.76), (float)bildschirmHoehe*0.25f, "Restart",(float)bildschirmBreite*0.1f,(float)bildschirmHoehe*0.05f,this);
        restart.setOnClick((btn) -> onRestart(btn));
    }


    public void draw() {
        background(bg);
        spielbrett.render();
        anzeigetafel.render();
        //zeigt eine Nachricht, um dem Spieler Informationen über die Steuerung und Regeln mitzuteilen, die Logik, wann sie gezeigt wird, befindet sich in der Klasse Toast
        toast.render();
        restart.render();
    }

    public void mouseClicked()
    {
        spielbrett.mouseClicked();
        restart.mouseClicked();
    }

    public void mousePressed()
    {
        restart.mousePressed();
    }

    //Spiellogik
    private static Spielfeld spielfeld;
    private static int spaltenNr;
    private static int spielerAmZug;
    private static int gewinner;

    public static void starteSpielFenster() {
        PApplet.main("MainGame");
    }

    private void onRestart(Object btn) {
        toast.showToast("Spiel neu gestartet",70);
        starteSpiel();
    }

    private void starteSpiel()
    {

        ANZAHLSPALTEN = MainMenu.getSpalten();
        ANZAHLZEILEN = MainMenu.getZeilen();
        //erzeugt ein neues Spielbrett in der Mitte des Bildschirms
        spielbrett = new Spielbrett((float) bildschirmBreite / 4, (float) bildschirmHoehe / 4, (float) bildschirmBreite / 2, (float) bildschirmHoehe / 2, ANZAHLSPALTEN, ANZAHLZEILEN, this);
        spielbrett.setOnClickSpalten((spalte) -> onSpielzug(spalte));

        spielfeld = new Spielfeld(ANZAHLZEILEN, ANZAHLSPALTEN);
        //startspieler wird festgelegt
        spielerAmZug = (int) (Math.random() * 2) + 1;
        Farben.setSpielerfarbe(spielerAmZug);

        //erzeugt eine Anzeigetafel, die Informationen über da Spiel anzeigen kann
        anzeigetafel = new Anzeigetafel((float) (bildschirmBreite * 0.5), (float) (bildschirmHoehe *0.2),(float) (bildschirmBreite * 0.05),this);
        anzeigetafel.setSpielerAmZug(spielerAmZug);
        gewinner=0;
    }

    /**
     * @param spalte
     * wird bei einem Klick auf eine Spalte aufgerufen. Kümmert sich um sämtliche Logik die durch den Klick ausgelöst wird.
     */
    public void onSpielzug(Object spalte)
    {
        if(gewinner ==0) {
            Spalte sp = (Spalte) spalte;
            spaltenNr = sp.getSpaltenNr();
            // Ausführung des Spielzugs, wenn die Spalte noch nicht voll ist
            try {
                int zeile = spielfeld.setSpielzug(spaltenNr, spielerAmZug);
                spielbrett.spielzug(spaltenNr, zeile, spielerAmZug);
                // Überprüfung ob der Zug das Spiel beendet hat
                gewinner = spielfeld.getSieger();
                if(gewinner!=0)
                {
                    if(gewinner == 1){
                        anzeigetafel.setText(MainMenu.getSpieler1Name() +  " gewinnt!");
                    }
                    if(gewinner == 2){
                        anzeigetafel.setText(MainMenu.getSpieler2Name() +  " gewinnt!");
                    }

                    anzeigetafel.setAnimation(true);
                    anzeigetafel.setColor(Farben.GOLD[0],Farben.GOLD[1],Farben.GOLD[2]);
                    spielbrett.setSpielVorbei(true);
                }
                else if(spielfeld.isUnentschieden())
                {
                    anzeigetafel.setText("Unentschieden");
                    anzeigetafel.setColor(Farben.UNENTSCHIEDEN[0],Farben.UNENTSCHIEDEN[1],Farben.UNENTSCHIEDEN[2]);
                    spielbrett.setSpielVorbei(true);
                }
                else
                {
                    //Wechseld zwischen Spieler 1 und Spieler 2
                    spielerAmZug = (spielerAmZug % 2) + 1;
                    Farben.setSpielerfarbe(spielerAmZug);
                    anzeigetafel.setSpielerAmZug(spielerAmZug);
                }
            }
            //abfangen der Exeption, falls die Spalte bereits voll ist, eine entsprechende Fehlermeldung wird auf dem bildschirm angezeigt
            catch (ZugNichtMoeglichExeption e) {
                toast.showToast("Spalte "+ (e.getSpalte()+1)+" ist voll. Wirf in eine andere Spalte!" , 70);
            }
        }
    }
}



