import org.junit.jupiter.params.ParameterizedTest;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import controlP5.*;     //library für Processing, die unteranderem Textfelder zu Verfügung stellt
import processing.core.PFont;
import processing.core.PImage;

import java.awt.*;

public class MainMenu extends PApplet {

    private GraphicsDevice defaultScreenDevice;
    private double bildschirmBreite;
    private double bildschirmHoehe;
    private Textfield Spieler1Feld;
    private Textfield Spieler2Feld;
    private Textfield ZeilenFeld;
    private Textfield SpaltenFeld;
    private Button startMainGame;
    private Toast toast;
    private static  int Zeilen;
    private static  int Spalten;
    private static String Spieler1Name;
    private static String Spieler2Name;
    private PImage bg;


    public void settings() {
        // setzt die Größe des Bildschirms auf FullScreen. Alternative fullScreen() von Processing -> mit esc beenden
        defaultScreenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        bildschirmBreite = defaultScreenDevice.getDefaultConfiguration().getBounds().getWidth();
        bildschirmHoehe = defaultScreenDevice.getDefaultConfiguration().getBounds().getHeight();
        size((int) bildschirmBreite/2, (int) bildschirmHoehe/2);
    }

    public void draw() {

        //System.out.println(Spieler1Name.getText());

        background(bg);
        startMainGame.render();
    }

    ControlP5 cp5;

    public void setup() {

        bg = loadImage(".idea/pictures/img.png");
        bg.resize((int) bildschirmBreite/2, (int) bildschirmHoehe/2);
        PFont font = createFont("arial",20);

        cp5 = new ControlP5(this);

        toast= new Toast((float) (bildschirmBreite * 0.5), (float) (bildschirmHoehe *0.8),(float) (bildschirmBreite * 0.02),this);

         Spieler1Feld =    cp5.addTextfield("Spieler 1 Name ")
                    .setPosition((float) (bildschirmBreite * 0.1),(float) (bildschirmHoehe * 0.1 ))
                    .setSize(100,40)
                    .setFont(font)
                    .setFocus(true)
                    .setColor(color(255,255,255))
            ;

        Spieler2Feld   =    cp5.addTextfield("Spieler 2 Name")
                .setPosition((float) (bildschirmBreite * 0.2),(float) (bildschirmHoehe * 0.1 ))
                .setSize(100,40)
                .setFont(font)
                .setFocus(true)
                .setColor(color(255,255,255))

        ;

        ZeilenFeld =    cp5.addTextfield("Anzahl Zeilen")
                .setPosition((float) (bildschirmBreite * 0.1),(float) (bildschirmHoehe * 0.3 ))
                .setSize(100,40)
                .setFont(font)
                .setFocus(true)
                .setColor(color(255,255,255))
        ;

        SpaltenFeld =    cp5.addTextfield("Anzahl Spalten")
                .setPosition((float) (bildschirmBreite * 0.2),(float) (bildschirmHoehe * 0.3 ))
                .setSize(100,40)
                .setFont(font)
                .setFocus(true)
                .setColor(color(255,255,255))
        ;


        startMainGame = new Button((float) (bildschirmBreite * 0.3),(float) (bildschirmHoehe * 0.2 ), "spielen",200,50,this);
        startMainGame.setOnClick((btn) -> startMainGame(btn));


        textFont(font);
    }

    public void mouseClicked()
    {
        startMainGame.mouseClicked();
    }


    public void startMainGame(Object btn){
        Spieler1Name = Spieler1Feld.getText();
        Spieler2Name = Spieler2Feld.getText();
        //Text aus Textfeldern speichern
       try {
           Spalten = Integer.parseInt(SpaltenFeld.getText());
           if(Spalten < 0) {
               Spalten = Spalten * -1;                 //Falls Eingabe negativ, Betrag benutzen um Fehler zu vermeiden
           }
           Zeilen = Integer.parseInt(ZeilenFeld.getText());
           if(Zeilen <0){
               Zeilen = Zeilen * -1;
           }
           Frame frame = ( (PSurfaceAWT.SmoothCanvas) ((PSurfaceAWT)surface).getNative()).getFrame();
           frame.dispose();                //Menü schließen
           MainGame.starteSpielFenster();          //4 gewinnt GUI starten

       }catch (NumberFormatException e){
           toast.showToast("bitte fülle alle Textfelder korrekt aus",3);
       }

    }



    public static void main(String[] args) {
        PApplet.main("MainMenu");
    }

    public static int getSpalten(){
        return Spalten;
    }

    public static int getZeilen(){
        return Zeilen;
    }

    public static String getSpieler1Name(){
        return Spieler1Name;
    }
    public static String getSpieler2Name(){
        return Spieler2Name;
    }




}
