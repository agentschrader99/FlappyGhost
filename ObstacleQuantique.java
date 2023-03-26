package com.example.flappyghost;

import java.lang.Math;

//Classe ObstacleQuantique qui s'occupe de la logique et du comportement
//des obstacles de type quantique  dans le jeu
public class ObstacleQuantique extends Fruits{

    private double temps;//Variable qui permettra de stocker le dT

    public ObstacleQuantique(){
        super(650,Math.random()*320+50);//y entre 50 et 355
        this.temps=0;
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
     * @param dT
     * @param fantome
     */
    @Override
    public void actualiser(double dT, Fantome fantome){

        this.posX+= (dT *fantome.getvX())*-1;
        temps+= dT;
        //À chaque 0.2 secondes
        if(Math.floor(temps*10)/10%0.2==0){
            //On genère un nombre au hasard entre -30 et 30
            int random = (int) ((Math.random()*60)-30);
            //Si en additionnant le nombre à la position en y de l'obstacle celle-ci ne dépasse pas la fenetre de jeu
            if((this.posY + random <0)==false && (this.posY + random>400)==false){
                //Alors on modifie sa position en y
                this.posY += random;
            }
            //On génère un nouveau nombre au hasard entre -30 et 30
            int random2 = (int) ((Math.random()*60)-30);
            //et on modifie sa position en x
            this.posX += random2;

        }
        //Augmente les points de la partie si le joueur passe l'obstacle
        depasserObstacle(fantome);

    }
}

