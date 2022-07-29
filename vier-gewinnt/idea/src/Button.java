import processing.core.PApplet;
import processing.core.PConstants;

import java.util.function.Consumer;

public class Button {
    private float posX,posY;
    private float breite, hoehe;
    private float textSize;
    private PApplet parent;
    private String text;
    private boolean textSizeBerechnet;
    private Consumer onClick;
    private boolean mousePressed;

    /**
     * Erzeugt einen Button, der einen Text haben kann
     */
    public Button(float posX, float posY, String text, float breite, float hoehe, PApplet parent) {
        this.posX = posX;
        this.posY = posY;
        this.breite=breite;
        this.hoehe=hoehe;
        this.parent = parent;
        this.text=text;
        this.berechneTextSize();
    }

    /*
    Berechnet die Textgroeße so, dass der text auf den Button passt
     */
    private void berechneTextSize() {
        textSize = 0.5f;
        parent.textSize(textSize);
        while(parent.textWidth(text) < breite && (parent.textAscent() + parent.textDescent()) < hoehe)
        {
            textSize+=0.5f;
            parent.textSize(textSize);
        }
        textSize-=0.5;

    }


    public void render() {
            //mouseOver und mousePressed Handeling
            float verdunkeln = 1;
            if(isPosInRect(parent.mouseX,parent.mouseY))
            {
                verdunkeln= mousePressed ? 0.25f : 0.5f;
            }
            else
            {
                verdunkeln=1;
            }
            parent.fill(Farben.BUTTON[0]*verdunkeln,Farben.BUTTON[1]*verdunkeln,Farben.BUTTON[2]*verdunkeln);
            parent.rect(posX, posY , breite , hoehe , 50, 50, 50, 50);
            parent.textAlign(parent.CENTER, parent.CENTER);
            parent.fill(Farben.WEIS[0],Farben.WEIS[1],Farben.WEIS[2]);
            if(!textSizeBerechnet)
            {
                berechneTextSize();
            }
             parent.textSize(textSize);
            parent.text(text, posX+0.5f*breite, posY+0.4f*hoehe);
        }

    public void setOnClick(Consumer onClick) {
        this.onClick = onClick;
    }


    /**
     *
     * Löst ein Event aus, wenn auf die Spalte geklicked wurde, solange das Spiel noch nicht vorbei ist.
     */
    public void mouseClicked() {
        //mouseDown handling
        if(isPosInRect(parent.mouseX,parent.mouseY))
        {
             mousePressed = false;
            if (onClick != null) {
                onClick.accept(this);
            }
        }
    }

    /*
        Überprüft ob sich die Position der Maus über dem Rechteck befindet
     */
    private boolean isPosInRect(int mouseX, int mouseY) {
        //is point x,y in the button rectangle?
        boolean isInWidth = mouseX >= posX && mouseX <= posX+breite;
        boolean isInHeight = mouseY >= posY && mouseY <= posY+hoehe;
        return isInWidth && isInHeight;
    }

    /*
    Wenn die Maus gedrückt wurde, während sich der Cursor auf dem Button befindet soll eine Klick-Animation zu sehen sein
     */
    public void mousePressed() {
        if(isPosInRect(parent.mouseX,parent.mouseY))
        {
            mousePressed = true;
        }
    }
}
