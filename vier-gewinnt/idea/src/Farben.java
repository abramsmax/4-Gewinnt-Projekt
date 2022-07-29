public class Farben {
    public static final int [] SPIELER1 = {255,0,0};
    public static final int [] SPIELER2 = {0,0,255};
    public static final int [] WEIS = {255,255,255};
    public static final int [] GOLD = {212,175,55};
    public static final int [] UNENTSCHIEDEN = {100,100,100};
    public static final int [] AKZENTFARBE = {100,100,100};
    public static final int [] BUTTON = {100,100,100};
    public static final int [] MOUSEOVER_SPALTEN = {0,0,0};
    public static int [] SPIELERFARBE;

    public static void setSpielerfarbe(int i)
    {
        if(i==1)
        {
            SPIELERFARBE = SPIELER1;
            MOUSEOVER_SPALTEN [0] = (int) (SPIELER1[0]*0.3);
            MOUSEOVER_SPALTEN [1] = (int) (SPIELER1[1]*0.3);
            MOUSEOVER_SPALTEN [2] = (int) (SPIELER1[2]*0.3);
        }
        else
        {
            SPIELERFARBE = SPIELER2;
            MOUSEOVER_SPALTEN [0] = (int) (SPIELER2[0]*0.3);
            MOUSEOVER_SPALTEN [1] = (int) (SPIELER2[1]*0.3);
            MOUSEOVER_SPALTEN [2] = (int) (SPIELER2[2]*0.3);
        }
    }
}
