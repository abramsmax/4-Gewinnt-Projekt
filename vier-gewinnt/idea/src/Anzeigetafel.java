import processing.core.PApplet;

public class Anzeigetafel {
    private float posX, posY;
    private float textSize;
    private PApplet parent;
    private String text;
    private int r, g, b;
    private boolean animation;
    private float posXAnimation, posYAnimation;

    /**
     * @param posX
     * @param posY
     * @param textSize
     * @param parent   Erzeugt eine Anzeigetafel, an der angegeben Position. Der Text kann später spezifiziert werden.
     */
    public Anzeigetafel(float posX, float posY, float textSize, PApplet parent) {
        this.posX = posX;
        this.posY = posY;
        posYAnimation= posY;
        posXAnimation = posX;
        this.textSize = textSize;
        this.parent = parent;
    }

    /**
     * @param r
     * @param g
     * @param b Setzt die Farbe des Textes auf die angegebenen Werte.
     */
    public void setColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Die Anzeigetafel wird auf dem parent PApplet gerendert. Wenn bestimmt, beginnt der Text zu "zittern"
     */
    public void render() {
        float x = posX;
        float y = posY;
        //Der Text beginnt sich dynamisch zu bewegen, es entsteht ein "zitter" effekt
        if(animation)
        {
            //Zufallszahl zwischen 0.1 und -0.1
            float verschiebeX = (float) (Math.random()*0.006f)-0.003f;
            float verschiebeY = (float) (Math.random()*0.006f)-0.003f;
            //breite und hoehe des Textfeldes
            float breite = parent.textWidth(text);
            float hoehe = parent.textAscent() + parent.textDescent();
            //berechnung der neuen Position
            float xNeu = posXAnimation+verschiebeX*breite;
            float yNeu = posYAnimation + verschiebeY*hoehe;
            if(xNeu>posX-breite*0.1 && xNeu<posX+breite*0.1)
            {
                posXAnimation =xNeu;
            }
            if(yNeu>posY-hoehe*0.1 && yNeu<posY+hoehe*0.1)
            {
                posYAnimation = yNeu;
            }
            x=posXAnimation;
            y=posYAnimation;

        }
        parent.fill(r, g, b);
        parent.textSize(textSize);
        parent.textAlign(parent.CENTER);
        parent.text(text, x, y);
    }

    public void setText(String s) {
        text = s;
    }

    public void setAnimation(boolean animation)
    {
        this.animation=animation;
    }

    /**
     * @param spielerAmZug stellt die Anzeigetafel für den angegebenen Spieler ein
     */
    public void setSpielerAmZug(int spielerAmZug) {
        if(spielerAmZug == 1){
            setText( MainMenu.getSpieler1Name() + " ist am Zug");
        }
        if(spielerAmZug == 2){
            setText( MainMenu.getSpieler2Name() + " ist am Zug");
        }

        setColor(Farben.SPIELERFARBE[0], Farben.SPIELERFARBE[1], Farben.SPIELERFARBE[2]);

    }
}
