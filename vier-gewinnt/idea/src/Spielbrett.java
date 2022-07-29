import processing.core.PApplet;

import java.util.function.Consumer;

public class Spielbrett {
    private float posX,posY; //start pos of Spalte
    private float w,h;
    private PApplet parent;
    private Spalte[] spalten;
    private int zeilen;

    /**
     * @param x gibt die x Koordinate der oberen linken Ecke des Spielbretts an
     * @param y gibt die y Koordinate der oberen linken Ecke des Spielbretts an
     * @param w gibt die Breite des Spielbretts an
     * @param h gibt die Höhe des Spielbretts an
     * @param anzahlSpalten
     * @param anzahlZeilen
     * @param parent
     */
    public Spielbrett(float x, float y, float w, float h, int anzahlSpalten, int anzahlZeilen, PApplet parent) {
        this.posX = x;
        this.posY = y;
        this.w = w;
        this.h = h;
        spalten = new Spalte[anzahlSpalten];
        this.zeilen=anzahlZeilen;
        this.parent = parent;
        erzeugeSpalten();
    }

    private void erzeugeSpalten() {
        float breite = w/ spalten.length;
        for (int i = 0; i < spalten.length; i++) {
            spalten[i]=new Spalte( (float) (posX+i*breite),posY, breite, h,zeilen,i,parent);
        }
    }

    /**
     * Rendert das Spielfeld. Alle Spalten und die Felder werden passend zum aktuellen Spielstand angezeigt.
     */
    public void render() {
        for (Spalte s : spalten) {
            s.render();
        }
    }

    public void setOnClickSpalten(Consumer onClick)
    {
        for (Spalte s: spalten) {
            s.setOnClick(onClick);
        }
    }

    /**
     * Behandelt einen Klick auf eine Spalte entsprechend zu der dort implementierten Routine.
     */
    public void mouseClicked() {
        for (Spalte s:spalten
             ) {
            s.mouseClicked();
        }
    }

    /**
     * @param spaltenNr
     * @param zeile
     * @param spielerAmZug
     * Färbt das angegebene Feld in der passenden Farbe ein
     */
    public void spielzug(int spaltenNr, int zeile, int spielerAmZug) {
        spalten[spaltenNr].spielzug(spielerAmZug,zeile);
    }

    public void setSpielVorbei(boolean b) {
        for (Spalte s:spalten
             ) {
            s.setSpielVorbei(b);
        }
    }
}