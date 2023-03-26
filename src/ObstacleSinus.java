package com.example.flappyghost;

import java.lang.Math;

//Classe ObstacleSinus qui s'occupe de la logique et du comportement
//des obstacles de type sinus dans le jeu
public class ObstacleSinus extends Fruits {

    private double pointOrigine; //Point d'origine du sinus

    public ObstacleSinus() {
        super(650, Math.random() * (350 - 45));//y entre 0 et 350 moins le rayonMax
        pointOrigine = this.posY; //Point ou le sinus commence
        this.rayon = 45; //Rayon du sinus
    }

    /**
     * Meme methode que dans obstacle simple
     * @param fantome
     */
    public void depasserObstacle(Fantome fantome){
        if (this.rayon + this.posX < fantome.getPosX() && ajoutPoints == false) {
            ajoutPoints = true;
            fantome.augmenterPoints();
        }
    }


    /**
     * //Permet de faire faire un trajet sinusoidal en ajoutant un mouvement vertial
     *
     * @param dT
     * @param fantome
     */
    @Override
    public void actualiser(double dT, Fantome fantome) {
        //Actualisation de la position en x de l'obstacle
        this.posX += (dT * fantome.getvX())*-1;
        //50 pixels d'amplitude pour le mouvement sinusoidale
        this.posY = Math.sin(0.015*this.posX) * 50+pointOrigine;

        //Augmente les points de la partie si le joueur passe l'obstacle
        depasserObstacle(fantome);

    }
}

