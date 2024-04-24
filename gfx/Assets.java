package gfx;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

import sokoban.Level;
public class Assets {
    public static Image playerLeft, playerBack , playerRight, playerFront;
    public static Image floor,floor2, wall, boxOn, boxOff, spot, outline, outline2,outline3, boxrecur, box_return,floor_rec,floor2_rec, wall_rec, boxOn_rec, boxOff_rec, spot_rec, outline_rec, outline2_rec, boxrecur_rec, box_return_rec;
    public static Font font35;
    public static Color colorfond;
    public static Font font25;
    public static Font font45;
    public static TextArea txtabout;
    


    public static void init(){
        playerLeft = loadImage("res/player/player_left.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        playerBack = loadImage("res/player/player_back.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        playerFront = loadImage("res/player/player_front.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        playerRight = loadImage("res/player/player_right.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);

        floor = loadImage("res/blocks/ground_1.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        floor2 = loadImage("res/blocks/surfer4.png").getScaledInstance(1700,980 , BufferedImage.SCALE_DEFAULT);
        wall = loadImage("res/blocks/grey_Brick.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        boxOn = loadImage("res/blocks/box_On.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        boxOff = loadImage("res/blocks/box_Off.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        spot = loadImage("res/blocks/spot.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        outline = loadImage("res/blocks/outline_1.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        outline2 = loadImage("res/blocks/outline_2.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        outline3 = loadImage("res/blocks/outline_3.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        boxrecur = loadImage("res/blocks/crate_08.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);
        box_return = loadImage("res/blocks/box_return.png").getScaledInstance(Level.TILESIZE,Level.TILESIZE,BufferedImage.SCALE_DEFAULT);


        floor_rec = loadImage("res/blocks/ground_1.png").getScaledInstance(12,12,BufferedImage.SCALE_DEFAULT);
        floor2_rec = loadImage("res/blocks/ground_2.png").getScaledInstance(12,12,BufferedImage.SCALE_DEFAULT);
        wall_rec = loadImage("res/blocks/grey_Brick.png").getScaledInstance(12,12,BufferedImage.SCALE_DEFAULT);
        boxOn_rec = loadImage("res/blocks/box_On.png").getScaledInstance(12,12,BufferedImage.SCALE_DEFAULT);
        boxOff_rec = loadImage("res/blocks/box_Off.png").getScaledInstance(12,12,BufferedImage.SCALE_DEFAULT);
        spot_rec = loadImage("res/blocks/spot.png").getScaledInstance(12,12,BufferedImage.SCALE_DEFAULT);
        outline_rec = loadImage("res/blocks/outline_1.png").getScaledInstance(48,48,BufferedImage.SCALE_DEFAULT);
        outline2_rec = loadImage("res/blocks/outline_2.png").getScaledInstance(48,48,BufferedImage.SCALE_DEFAULT);
        boxrecur_rec = loadImage("res/blocks/crate_08.png").getScaledInstance(12,12,BufferedImage.SCALE_DEFAULT);
        box_return_rec = loadImage("res/blocks/box_return.png").getScaledInstance(12,12,BufferedImage.SCALE_DEFAULT);
        
        font35 = new Font("Serif",Font.BOLD,35);
        font25 = new Font("Serif",Font.BOLD,25);
        font45 = new Font("Serif",Font.BOLD,45);
        
        txtabout = new TextArea("Jouez à WAVERIDERS SOKOBAN (BoxWorld) ! \n @author GHODBANI Achraf, DJEROUA Mstapha Hacène,BOULFRAD Imam,ESSAOUDI Mohammed \n Email : achrafghod.pro@gmail.com \n Règles du Jeu : \n Vous etes un Luigi Waverider enfermé dans un entrepot de surf(divisé en cases carrées), vous vous déplacez de case en case (vers le haut, le bas, la gauche ou la droite; avec les flèches du clavier). \n Une case peut comporter un mur, une caisse, ou un monde récursif. \n Un Déplacement n'est possible que si la case de destination est vide(c’est-à-dire qu'il ne s'agit ni d'un mur, ni d'une caisse; en revanche, il peut s'agir d'une zone de rangement). \n si cette case contient une caisse et que la suivante (dans la même direction) est vide (ou une zone de rangement), le mouvement est aussi autorisé : vous poussez alors la caisse dans la case vide et venez prendre sa place.\n Un niveau est résolu dès que toutes les caisses sont rangées dans le niveau de base et le niveau récursif si celui ci existe.\n Réussir est un vrai casse-tête  (les deux premiers niveaux sont hyper simples pour vous familiariser, à partir du niveau 3, c'est moins évident...) L'idéal est d'essayer de réussir avec le moins de déplacements et de poussées ! \n ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n Historique : \n  Le Sokoban original a été écrit par le japonais Hiroyuki Imabayashi en 1982 pour l'ordinateur Nec PC-8801 et comportait 50 niveaux.\n En 1988, il sera adapté pour PC. \n En 1991, Jeng Long Jiang propose Boxworld, une autre version pour PC, avec 100 niveaux différents de Sokoban, et qui a été très populaire. \n Le code source n'est pas disponible, mais on trouve la description des niveaux ici (remarque : il manque les niveaux 61 et 62, apparemment impossibles) : \n http://games.knet.ca/modules/pnFlashGames/games/boxesLevels \n J'ai commencé à écrire cette version JAVASCRIPT en Janvier 1998. \n Elle a été à jour en Janvier 2005 (utilisation du clavier, écriture de cette page d'aide, 30 niveaux au lieu de 5...) puis mai 2006 (98 niveaux). \n Licence : Waveriders (Sokoban) - Puzzle Game \n Programmation Copyright (C) Université Sorbonne Paris Nord (licence Informatique) \n Graphismes et Niveaux Copyright (C) Waveriders Group University Sorbonne Paris Nord");
    }

    
    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(Assets.class.getResourceAsStream(path));
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
