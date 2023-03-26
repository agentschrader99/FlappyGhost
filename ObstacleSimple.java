package com.example.flappyghost;

import java.lang.Math;

//Classe ObstacleSimple qui s'occupe de la logique et du comportement
//des obstacles de type simple dans le jeu
public class ObstacleSimple extends Fruits {

    public ObstacleSimple(){
        //Définition de la position de l'obstacle, sa position en x commence à l'extérieur de
        //la fenetre de jeu et sa position en y est aléatoire entre 0 et 350
        //(et non 400 car sinon des obstacles peuvent se trouver presque en totalité
        // à l'extérieur de la fenetre)
        super(650,Math.random()*350);
    }
    public void depasserObstacle(Fantome fantome){
        //Si la region qu'occupe l'obstacle est plus petite que la position du fantome
        //et qu'on a pas encore ajouté de points alors on augmente le pointage du joueur
        if( this.posX+this.rayon < fantome.getPosX() && ajoutPoints==false){
            //Avoir un boolean pour gérer les points est nécéssaire car sinon lorsqu'on
            //dépasse un obstacle les points s'ajoute à l'infinie
            ajoutPoints=true;
            fantome.augmenterPoints();//Augmente le pointage et augmente la vitesse ainsi que la gravité
        }
    }
    /**
     * //Permet d'actualiser la position de l'obstacle et de vérifier si
     * le fantome dépasse l'obstacle en question
     * @param dT
     * @param fantome
     */
    @Override
    public void actualiser(double dT, Fantome fantome){

        //On actualise la position en x de l'obstacle selon la vitesse du fantome
        //(Plus le fantome va vite plus les obstacles vont vite)
        this.posX+= (dT *fantome.getvX())*-1;//La position doit etre négative car les
        //obstacles se dirigent vers la gauche de l'écran

        depasserObstacle(fantome);
    }



}

