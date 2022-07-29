import processing.core.PApplet;

public class Feld {
    private float posX,posY;
    private float durchmesser;
    private int r,g,b;

    private PApplet parent;

    /**
     * @param x Gibt die x Koordinate des Mittelpunkts des Feldes an.
     * @param y Gibt die y Koordinate des Mittelpunkts des Feldes an.
     * @param Durchmesser Gibt den durchmesser des Feldes an
     * @param parent
     */

    public Feld(float x, float y, float durchmesser, PApplet parent) {
        this.posX = x;
        this.posY = y;
        r=Farben.WEIS[0];
        b=Farben.WEIS[0];
        g=Farben.WEIS[0];
        this.durchmesser=durchmesser;
        this.parent = parent;
    }

    /**
     * Rendert die Spalte auf dem Parent-Applet
     */
    public void render() {
        parent.fill(r,g, b);
        parent.circle(posX, posY, durchmesser);
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

}
