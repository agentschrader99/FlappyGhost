package com.example.flappyghost;

import javafx.scene.canvas.GraphicsContext;

//Classe Modele ayant comme attributs la position en x,y et le rayon
//Cette classe est la classe mère de celles du fantome et des fruits(obstacles)
public abstract class Modele {
    public double rayon;
    public double posX;
    public double posY;

    //Constructeur de la classe Modele qui utilise les positions et le rayon des objets
    //qui hériteront de cette classe
    public Modele(double posX,double posY,double rayon){

        this.rayon=rayon;
        this.posX=posX;
        this.posY=posY;

    }
    //Classes qui serviront à actualiser les positions et
    // vitesses des objets qui héritent de la classe modèle
    //ainsi que l'affichage de ceux-ci dans la fenêtre du jeu
    public abstract void actualiser(double dT, Fantome fantome); //
    public abstract void affichage(GraphicsContext contexte, boolean modeDebug);
    /**
     * Retourne le rayon de l'objet
     * @return double
     */
    public double getRayon(){

        return  rayon;
    }
    /**
     * Retourne la position en x de l'objet
     * @return double
     */
    public double getPosX(){

        return posX;
    }

    /**
     * Retourne la postion en y de l'objet
     * @return double
     */
    public double getPosY(){

        return posY;
    }

    /**
     * Actualise la valeur en y de l'objet
     * @param posY
     */
    public void setPosY(double posY){

        this.posY=posY;
    }

}

