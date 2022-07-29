import processing.core.PApplet;
import processing.core.PConstants;


public class Toast {
    private float posX,posY;
    private float textSize;
    private PApplet parent;
    private String text;
    private int zeit;

    /**
     * Erzeugt ein Textfeld, welches für eine bestimmte Zeit (Anzahl an Frames) angezeigt werden kann.
     */
    public Toast(float posX, float posY, float textSize, PApplet parent) {
        this.posX = posX;
        this.posY = posY;
        this.textSize=textSize;
        this.parent = parent;
    }

    /**
     * Falls die Zeit größer 0 ist, wird das Textfeld mit dem entsprechenden Text angezeigt.
     */
    public void render() {
        if(zeit>0) {
            //am Ende verblasst das Textfeld langsam
            float verblassen = 1;
            if(zeit<=10)
            {
                verblassen-=(0.1*(11-zeit));
            }
            parent.textSize(textSize);
            parent.textAlign(parent.CENTER,parent.CENTER);
            float breite = parent.textWidth(text);
            float hoehe = parent.textAscent() + parent.textDescent();
            //Rechteck zentral um den Text
            parent.fill(Farben.AKZENTFARBE[0]*verblassen,Farben.AKZENTFARBE[1]*verblassen,Farben.AKZENTFARBE[2]*verblassen);
            parent.rectMode(parent.CENTER);
            parent.rect(posX, posY+0.1f*hoehe, (float) (breite * 1.2), (float) (hoehe * 1.2), 50, 50, 50, 50);
            parent.rectMode(PConstants.CORNER);
            parent.fill(Farben.WEIS[0]*verblassen,Farben.WEIS[1]*verblassen,Farben.WEIS[2]*verblassen);
            parent.text(text, posX, posY);
            zeit--;
        }
    }

    /**
     * @param text spezifiert den Text, der angezeigt werden soll
     * @param duration spezifiziert, wie häufig der Text angezeigt werden soll.
     */
    public void showToast(String text, int duration) {
        this.text = text;
        zeit = duration;
    }

}

