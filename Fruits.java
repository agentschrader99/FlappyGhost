package com.example.flappyghost;


import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.lang.Math;

//Déclaration de la classe Fruits qui contient les attributs et les méthodes
//pour la gestion des obstacles
public abstract class Fruits extends Modele{

    private boolean collision; //Pour vérifier les collisions
    public boolean ajoutPoints;//Permet de savoir si le +5 points a été ajouté au score
    public int nbrRandom = (int)(Math.random()*27);//Genere un nombre au hasard entre 0 et 26
    private String path; //Chemin vers les obstacles

    //Constructeur de la classe Fruits qui utilise le constructeur de la classe mère Modele
    //pour les positions et le rayon. Le constructeur contient le path des images et deux
    //boolean pour l'ajout des points et pour les collisions
    public Fruits(double posX,double posY){
        super(posX,posY,(int)(Math.random()*35)+10); //Appele le constructeur de la classe Modele pour
        // créer des objets fruits aves des rayon entre 10 et 45
        ajoutPoints=false;
        collision=false;
        //Genère un path qui correspond à une des 27 images au hasard mises à notre disposition
        path = "C:/Users/Bahsti/Desktop/fichiersFH/fichiersFH/obstacles/"+nbrRandom+".png";

    }

    /**
     * Actualise la valeur de la variable bool collision
     * @param collision
     */
    public void setCollision(boolean collision){
        this.collision=collision;
    }

    /**
     * Méthode actualiser qui permet de gerer les positions des obstacles
     * et du fantome
     * @param dT
     * @param fantome
     */

    @Override
    public abstract void actualiser(double dT, Fantome fantome);
    /**
     *Méthode affichage qui permet de dessiner les différents obstacles sur le canvas
     * selon si le mode debug est activé ou non
     * @param context
     * @param modeDebug
     */
    @Override
    public void affichage(GraphicsContext context, boolean modeDebug) {
        double diametre = rayon*2;
        if (modeDebug == true) { //Si on est en mode debug...
            if (this.collision) { //Si il y a une collision
                context.setFill(Color.RED); //Le fruit devient rouge
                context.fillOval(this.posX, this.posY, diametre, diametre);
            } else if(modeDebug == true) { //Sinon...
                context.setFill(Color.YELLOW); //le cercle est jaune
                context.fillOval(this.posX, this.posY, diametre, diametre);
            }
        }
        //Si on est pas en mode debug
        else {
            Image image = new Image(path);
            //On affiche la vrai image de l'obstacle choisi au hasard
            context.drawImage(image, this.posX, this.posY, diametre, diametre);
        }
    }

}
