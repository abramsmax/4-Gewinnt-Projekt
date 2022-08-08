import time
from imp import reload
from logging import raiseExceptions
import numpy
import pygame
import sys
import math


#Definition der Farben für GUI
BLUE = (0,0,255)
BLACK = (0,0,0)    
RED = (255,0,0)
WHITE = (255, 255, 255)
PINK = (255, 192, 203)

QUADRAT = 80

def Game(Spieler1, Spieler2, ANZAHL_SPALTEN_ZEILEN): #Game Funktion Anfang
        def Spielzug(spielfeld,spieler,spalte):

            if spielfeld[0][spalte] != 0: #falls Spalte bereits gefüllt ist
                e = BaseException("Spalte voll")
                raise e

            else:
                for i in range(0, len(spielfeld)):
                    if i == len(spielfeld) - 1: #Überprüfung, ob unterste Zeile
                        spielfeld[i][spalte] = spieler
                        print(spielfeld)
                        if CheckGewinnervert(spieler, spielfeld, spalte) or CheckGewinnerhorizontal(spieler,spielfeld,i) or CheckGewinnerDiagonal(spieler,spielfeld,i,spalte): #Überprüfung, ob Gewinnbedingung erfüllt
                            return True
                        break
                    elif spielfeld[i+1][spalte] != 0: #Spalte wird nach unten durchiteriert, Überprüfung ob nächstes Feld besetzt
                        spielfeld[i][spalte] = spieler
                        print(spielfeld)
                        if CheckGewinnervert(spieler, spielfeld, spalte) or CheckGewinnerhorizontal(spieler,spielfeld,i) or CheckGewinnerDiagonal(spieler,spielfeld,i,spalte):
                            return True
                        break


        def CheckGewinnervert(spieler, spielfeld, spalte):

            zaehler = 0

            for s in range(0,len(spielfeld)): #Spalte wird nach unten durchiteriert, Überprüfung, ob 4 mal nacheinander selber Spieler
                if spielfeld[s][spalte] == spieler:
                    zaehler += 1
                else:
                    zaehler = 0
                if zaehler == 4:
                    return True
            return False


        def CheckGewinnerhorizontal(spieler, spielfeld, zeile):

            zaehler = 0

            for s in range(0, len(spielfeld[zeile])): #Zeile wird nach rechts durchiteriert, Überprüfung, ob 4 mal nacheinander selber Spieler

                if spielfeld[zeile][s] == spieler:
                    zaehler += 1
                else:
                    zaehler = 0
                if zaehler == 4:
                    return True
            return False


        def CheckGewinnerDiagonal(spieler,spielfeld,zeile,spalte):

             zaehler = 0
             startZ = zeile
             startS = spalte

             while(startS > 0 and startZ > 0): #Zeilen und/oder Spaltenwert werden an Rand des Speielfelds gesetzt, um ganze Diagonale zu überprüfen -> Verschiebung nach oben links
                 startZ -= 1
                 startS -= 1

             while(startS < len(spielfeld[zeile]) and startZ < len(spielfeld)): #Durchiterieren der jeweiligen Diagonale von oben links nach unten rechts. Überprüfung, ob 4 mal nacheinander selber Spieler
                 if spielfeld[startZ][startS] == spieler:
                     zaehler += 1
                 else:
                     zaehler = 0
                 if zaehler == 4:
                    return True
                 startZ += 1
                 startS += 1

             zaehler = 0
             startZ = zeile
             startS = spalte

             while (startS > 0 and startZ < len(spielfeld) - 1 ): #Zeilen und/oder Spaltenwert werden an Rand des Speielfelds gesetzt, um ganze Diagonale zu überprüfen -> Verschiebung nach unten links
                 startZ += 1
                 startS -= 1

             while (startS < len(spielfeld[zeile]) and startZ  > 0 ): #Durchiterieren der jeweiligen Diagonale von unten links nach oben rechts. Überprüfung, ob 4 mal nacheinander selber Spieler
                 if spielfeld[startZ][startS] == spieler:
                     zaehler += 1
                 else:
                     zaehler = 0
                 if zaehler == 4:
                     return True
                 startZ -= 1
                 startS += 1

             return False

        def draw_spielfeld(spielfeld): #Aufbau des Spielfelds
            for s in range(len(spielfeld)):
                for z in range(len(spielfeld[0])):
                    pygame.draw.rect(screen, PINK, (s*QUADRAT, z*QUADRAT+QUADRAT, QUADRAT, QUADRAT)) #Rechtecke, oben eine Zeile frei
                    pygame.draw.circle(screen, BLACK, (int(s*QUADRAT+QUADRAT/2), int(z*QUADRAT+QUADRAT+QUADRAT/2)), radius) #Kreise in den Rechtecken

            for s in range(len(spielfeld)): #Chips der 2 Spieler
                for z in range(len(spielfeld[0])):
                    if spielfeld[z][s] == 1:
                        pygame.draw.circle(screen, RED, (int(s*QUADRAT+QUADRAT/2), int((z+1)*QUADRAT+QUADRAT/2)), radius) #Spieler 1: Roter Chip
                    elif spielfeld[z][s] == 2:
                        pygame.draw.circle(screen, BLUE, (int(s*QUADRAT+QUADRAT/2), int((z+1)*QUADRAT+QUADRAT/2)), radius) #Spieler 2: Gelber Chip
            pygame.display.update()


        spielfeld = numpy.zeros((ANZAHL_SPALTEN_ZEILEN,ANZAHL_SPALTEN_ZEILEN))  #Spielfeld[zeile][spalte]

        pygame.init()

        print(spielfeld)

        #Berechnung der Maße für GUI
        radius = int(QUADRAT/2 - 4)

        breite = len(spielfeld) * QUADRAT
        hoehe = (len(spielfeld[0]) + 1) * QUADRAT
        size = (breite, hoehe)

        fonttype = pygame.font.SysFont("Bauhaus 93", int (QUADRAT*0.2)) #Schriftart der Textfelder
        currspieler = 1
        ende = False
        ZugZähler = 0

        #Anzeigen des Spielfelds
        screen = pygame.display.set_mode(size)
        draw_spielfeld(spielfeld)
        pygame.display.update()

        while True:

            for event in pygame.event.get():
                if event.type == pygame.QUIT: #Zum Schließen des Programms
                    sys.exit()

                if event.type == pygame.KEYDOWN:
                        if event.key == pygame.K_SPACE: #Neustart des Spiels bei Taste SPACE
                            ZugZähler = 0
                            spielfeld = numpy.zeros((ANZAHL_SPALTEN_ZEILEN,ANZAHL_SPALTEN_ZEILEN))

                if event.type == pygame.MOUSEMOTION: #Wenn Maus sich bewegt
                    pygame.draw.rect(screen, BLACK, (0,0, breite, QUADRAT)) #Schwarzer Block in oberster Zeile, damit Spur nicht bleibt
                    xpos = event.pos[0] #Scannen der X-Achsen-Position des Mauszeigers (Zwischen 0 und 799)
                    if currspieler == 1:
                        pygame.draw.circle(screen, RED, (xpos, int(QUADRAT/2)), radius) #Anzeige des Chips in oberster Spalte
                    else:
                        pygame.draw.circle(screen, BLUE, (xpos, int(QUADRAT/2)), radius)
                    pygame.display.update()

                if event.type == pygame.MOUSEBUTTONDOWN: #Wenn Maus geklickt
                    pygame.draw.rect(screen, BLACK, (0,0, breite, QUADRAT))

                    if currspieler == 1:
                        xpos = event.pos[0]
                        spalte = int(math.floor(xpos/QUADRAT)) #Teilen der X-Achsen-Position durch die Breite eines Quadrats (x/100) -> Erkennen welche Spalte

                    if currspieler == 2:
                        xpos = event.pos[0]
                        spalte = int(math.floor(xpos/QUADRAT))

                    try:
                        if Spielzug(spielfeld,currspieler,spalte): #Wenn Spielzug = True, steht ein Gewinner fest, sonst läuft Spielzug Methode durch

                            if currspieler == 1:
                                draw_spielfeld(spielfeld)
                                label = fonttype.render(Spieler1 + " HAT GEWONNEN!!!", 1, RED) #Ausgabe der Gewinn-Message
                                screen.blit(label, ((QUADRAT*1.7),QUADRAT/4))
                                pygame.display.update()
                                pygame.time.wait(3000)

                            elif currspieler == 2:
                                draw_spielfeld(spielfeld)
                                label = fonttype.render( Spieler2 +" HAT GEWONNEN!!!", 1, BLUE)
                                screen.blit(label, (170,25))
                                pygame.display.update()
                                pygame.time.wait(3000)

                            ende = True

                        else: #Wechsel der Spieler
                            ZugZähler +=1
                            if ZugZähler == ANZAHL_SPALTEN_ZEILEN * ANZAHL_SPALTEN_ZEILEN:  # Falls alle Felder belegt sind ohne Sieger
                                label = fonttype.render("Unentschieden, Nochmal? -> SPACE", 1, RED)
                                screen.blit(label, (20, 25))
                                pygame.display.update()
                                pygame.time.wait(3000)
                            print(ZugZähler)
                            if currspieler == 1:
                                currspieler = 2
                            else:
                                currspieler = 1
                    except:
                         print("zug nicht möglich")


                draw_spielfeld(spielfeld)

                if ende == True:
                    pygame.draw.rect(screen, BLACK, (0,0, breite, QUADRAT))
                    label = fonttype.render("NOCHMAL? -> SPACE   ENDE? -> X", 1, WHITE)
                    screen.blit(label, (QUADRAT*0.5,QUADRAT/4))
                    pygame.display.update()
                    pygame.event.wait()

                    if event.type == pygame.KEYDOWN: #Neustart bei Taste SPACE
                        if event.key == pygame.K_SPACE:
                            spielfeld = numpy.zeros((ANZAHL_SPALTEN_ZEILEN,ANZAHL_SPALTEN_ZEILEN))
                            ende = False

                        if event.key == pygame.K_x: #Schließen bei Taste x
                            sys.exit()
                         


def Menu(): #Menu Funktion Anfang

    pygame.init()
    displayInfo = pygame.display.Info()
    screen = pygame.display.set_mode((displayInfo.current_w /2, displayInfo.current_h / 2))
    pygame.display.set_caption("4 gewinnt")
    fonttype = pygame.font.SysFont("Bauhaus 93", int(QUADRAT * 0.4))

    def draw_text(text, font, color, surface, x, y):
        textobj = font.render(text, 1, color)
        textrect = textobj.get_rect()
        textrect.topleft = (x, y)
        surface.blit(textobj, textrect)
        return textrect



    def main_menu():
            buttonPressed = False
            Menuactive = True
            Spieler1Text = "Klicken zum ändern"
            Spieler2Text = "Klicken zum ändern"
            SpaltenZeilenText   = "Klicken zum ändern"
            aktuelleAuswahl = ""
            while Menuactive:

                screen.fill((0, 0, 50))

                StartRec = draw_text("4 GEWINNT", fonttype, (255, 255, 255), screen, displayInfo.current_w * 0.2,   displayInfo.current_h * 0.04)

                draw_text('Spieler 1 Name:', fonttype, (255, 255, 255), screen, displayInfo.current_w * 0.05, displayInfo.current_h * 0.12)
                Spieler1Rec  = draw_text(Spieler1Text, fonttype, (255, 255, 255), screen, displayInfo.current_w * 0.3, displayInfo.current_h * 0.12)

                draw_text('Spieler 2 Name:', fonttype, (255, 255, 255), screen, displayInfo.current_w * 0.05, displayInfo.current_h * 0.19)
                Spieler2Rec = draw_text(Spieler2Text, fonttype, (255, 255, 255), screen, displayInfo.current_w * 0.3, displayInfo.current_h * 0.19)

                draw_text('Anzahl Spalten / Zeilen:', fonttype, (255, 255, 255), screen,displayInfo.current_w * 0.05, displayInfo.current_h * 0.26)
                SpaltenZeilenRec = draw_text(SpaltenZeilenText, fonttype, (255, 255, 255), screen, displayInfo.current_w * 0.3, displayInfo.current_h * 0.26)


                StartRec = draw_text("Spiel starten", fonttype, (255, 255, 255), screen,  displayInfo.current_w * 0.2, displayInfo.current_h * 0.40)
                # Rechteck um Spiel Starten Button passend zu Größe und Postion des Start Buttons
                pygame.draw.rect(screen,WHITE, pygame.Rect(StartRec.topleft[0]-10,StartRec.topleft[1]-10 , StartRec.topright[0] - StartRec.topleft[0] + 20, StartRec.bottomright[1] - StartRec.topright[1] + 20), 2)


                #blinkender Cursor bei ausgewähltem Text
                if time.time() % 1 > 0.5 and aktuelleAuswahl == "Spieler1":
                    cursor = pygame.Rect(Spieler1Rec.topright, (3, Spieler1Rec.height))
                    cursor.topleft = Spieler1Rec.topright
                    pygame.draw.rect(screen,WHITE,cursor)

                if time.time() % 1 > 0.5 and aktuelleAuswahl == "Spieler2":
                    cursor = pygame.Rect(Spieler2Rec.topright, (3, Spieler2Rec.height))
                    cursor.topleft = Spieler2Rec.topright
                    pygame.draw.rect(screen, WHITE, cursor)

                if time.time() % 1 > 0.5 and aktuelleAuswahl == "Spalten/Zeilen":
                    cursor = pygame.Rect(SpaltenZeilenRec.topright, (3, SpaltenZeilenRec.height))
                    cursor.topleft = SpaltenZeilenRec.topright
                    pygame.draw.rect(screen, WHITE, cursor)

                mx, my = pygame.mouse.get_pos()

                if Spieler1Rec.collidepoint(mx,my):
                    if buttonPressed:
                        Spieler1Text = ""
                        aktuelleAuswahl = "Spieler1"

                if Spieler2Rec.collidepoint(mx,my):
                    if buttonPressed:
                        Spieler2Text = ""
                        aktuelleAuswahl = "Spieler2"

                if SpaltenZeilenRec.collidepoint(mx,my):
                    if buttonPressed:
                        SpaltenZeilenText = ""
                        aktuelleAuswahl = "Spalten/Zeilen"


                if StartRec.collidepoint(mx,my):
                    if buttonPressed:
                        if SpaltenZeilenText.isnumeric(): #Überprüfung ob Eingabe korrekt ist
                            Game(Spieler1Text,Spieler2Text,int(SpaltenZeilenText))
                        else:
                            print("Eingabe ist ungültig")


                buttonPressed = False
                for event in pygame.event.get():
                    if event.type == pygame.QUIT:
                        pygame.quit()
                    if event.type == pygame.KEYDOWN:
                        if event.key == pygame.K_ESCAPE:
                            pygame.quit()
                        if event.key == pygame.K_BACKSPACE: #eingegebenen Text in ausgewählten Fenster löschen
                            if aktuelleAuswahl =="Spieler1" and len(Spieler1Text) > 0:
                                Spieler1Text = Spieler1Text[:-1]
                            if aktuelleAuswahl =="Spieler2" and len(Spieler2Text) > 0:
                                Spieler2Text =Spieler2Text[:-1]
                            if aktuelleAuswahl =="Spalten/Zeilen" and len(SpaltenZeilenText) > 0:
                                SpaltenZeilenText = SpaltenZeilenText[:-1]
                        else: # Eingabe der Namen und Anzahl der Spalten/Zeilen
                            if aktuelleAuswahl == "Spieler1":
                                 Spieler1Text += event.unicode
                            if aktuelleAuswahl == "Spieler2":
                                Spieler2Text += event.unicode
                            if aktuelleAuswahl == "Spalten/Zeilen":
                                SpaltenZeilenText += event.unicode
                    if event.type == pygame.MOUSEBUTTONDOWN:
                        if event.button == 1:
                            buttonPressed = True

                pygame.display.update()

    main_menu()

Menu()