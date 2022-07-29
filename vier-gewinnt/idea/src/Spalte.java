import processing.core.PApplet;

import java.util.function.Consumer;

public class Spalte {
    private float posX,posY; //start pos of Spalte
    private float w,h;
    private int r,g,b;
    private int anzahlFelder;
    private PApplet parent;
    private Consumer onClick;
    private Feld[] felder;
    private boolean farbe;
    private int spaltenNr;
    private boolean isSpielVorbei;

    /**
     * @param x gibt die x Koordinate der oberen linken Ecke der Spalte an
     * @param y gibt die y Koordinate der oberen linken Ecke der Spalte an
     * @param w gibt die Breite der Spalte an
     * @param h gibt die Höhe der Spalte an
     * @param anzahlFelder gibt die Anzahl der Felder pro Spalte an
     * @param spaltenNr gibt an, welche an welcher Stelle die Spalte steht
     * @param parent
     */
    public Spalte(float x, float y, float w, float h, int anzahlFelder, int spaltenNr, PApplet parent) {
        this.posX = x;
        this.posY = y;
        this.w = w;
        this.h = h;
        this.anzahlFelder= anzahlFelder;
        felder = new Feld[anzahlFelder];
        this.spaltenNr=spaltenNr;
        this.parent = parent;
        erzeugeFelder();
    }

    /*
        Erzeugt ein Array mit allen Feldern der Saplte und initialisiert diese. Das unterste feld erhält den Index 0.
     */
    private void erzeugeFelder() {
        float durchmesser= (h/anzahlFelder > w) ? w : (h/anzahlFelder);
        float startY = posY+h;
        for(int i= 0; i<anzahlFelder; i++)
        {
            felder[i]=new Feld((float) (posX+0.5*w),(float) (startY-(i+0.5)*durchmesser), (float) ( durchmesser*0.9),parent);
        }
    }

    /**
     * Rendert die Spalte auf dem Parent-Applet. Die einzelnen Felder auf der Spalte werden individuell gerendert.
     * Befindet sich die Maus über der Spalte,so wird diese entsprechend eingefärbt, solange das Spiel nicht vorbei ist.
     *
     */
    public void render() {
        if(farbe)
        {
            parent.fill(Farben.MOUSEOVER_SPALTEN[0],Farben.MOUSEOVER_SPALTEN[1],Farben.MOUSEOVER_SPALTEN[2]);
            parent.stroke(Farben.SPIELERFARBE[0],Farben.SPIELERFARBE[1],Farben.SPIELERFARBE[2]);
        }
        else
        {
            parent.noFill();
        }
        parent.rect(posX, posY, w, h);
        parent.stroke(Farben.AKZENTFARBE[0],Farben.AKZENTFARBE[1],Farben.AKZENTFARBE[2]);
        for (Feld f : felder) {
            f.render();
        }
        parent.noStroke();
        //mouseOverHandeling
        if(!isSpielVorbei && isPosInRect(parent.mouseX,parent.mouseY))
        {
            farbe = true;
        }
        else
        {
            farbe = false;
        }
    }


    /*
        Überprüft ob sich die Position der Maus über dem Rechteck befindet
     */
    private boolean isPosInRect(int mouseX, int mouseY) {
        //ist der Mauszeiger über der Spalte? (-1 um u verhindern, dass bei 2 benachbarten Spalten gleichzeitig true rauskommt
        boolean isInWidth = mouseX >= posX && mouseX <= posX+w-1;
        boolean isInHeight = mouseY >= posY && mouseY <= posY+h;
        return isInWidth && isInHeight;
    }


    /**
     * @param r
     * @param g
     * @param b
     * Setzt die Farbe des Feldes auf die angegebenen Werte.
     */
    public void setColor(int r, int g, int b)
    {
        this.r=r;
        this.g=g;
        this.b=b;
    }

    public void setOnClick(Consumer onClick) {
        this.onClick = onClick;
    }

    public int getSpaltenNr() {
        return spaltenNr;
    }

    /**
     * Löst ein Event aus, wenn auf die Spalte geklicked wurde, solange das Spiel noch nicht vorbei ist.
     */
    public void mouseClicked() {
        //mouseDown handling
        if(!isSpielVorbei && isPosInRect(parent.mouseX,parent.mouseY))
        {
            if (onClick != null) {
                onClick.accept(this);
            }
        }
    }

    /**
     * @param spielerNr
     * @param zeile
     * faerbt das entsprechende Feld in der passenden Farbe
     */
    public void spielzug(int spielerNr, int zeile) {
        if(spielerNr==1)
        {faerbeFeld(zeile,Farben.SPIELER1[0],Farben.SPIELER1[1],Farben.SPIELER1[2]);}
        else
        {
            faerbeFeld(zeile,Farben.SPIELER2[0],Farben.SPIELER2[1],Farben.SPIELER2[2]);
        }
    }
    private void faerbeFeld(int i,int r, int g, int b) {
        felder[i].setColor(r,g,b);
    }

    public void setSpielVorbei(boolean b) {
        this.isSpielVorbei = b;
    }
}
