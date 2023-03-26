package com.example.flappyghost;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

import java.lang.Math;

//Classe Fantome qui est une extension du modele.
//Cette classe contient les attributs ainsi que les méthodes
//propres à la physique du fantome
public class Fantome extends Modele {

    private double aY; //Accélération en y vers le bas du fantome
    private double vX, vY, gravite;//vitesse en x et en y ainsi que la gravité subit par le fantome

    private boolean collision;//Boolean pour reconnaitre si il y a une collision entre le fantome et un fruit
    private int points;//Stock le nombre de points du joueur

    //Constructeur de la classe qui utilise entre autre le constructeur de la superclasse Modele pour
    //construire le fantome selon les dimensions données
    public Fantome(){
        //On positionne le fantome à 275 en x qui est environ au centre de la fenetre
        super(275,220,30);
        vX= 120;//Vitesse initiale en x
        vY=0;//Vitesse initiale en y

        aY=0;//Accélération initiale en y
        gravite=500;//Gravité initiale

        collision=false;
        points=0;

    }
    //Méthode saut qui modifie la vitesse du ghost de -300 en y
    //afin de la faire bondir vers le haut sur l'écran
    public void saut(){

        vY=-300;
    }
    //Méthode accelerer qui augmente la gravité subit par le fantome de 15
    //et la vitesse en x de 15 aussi
    public void accelerer(){
        gravite+=15;
        vX+=15;
        //Si la vitesse en y du fantome surpasse 300 on la force à
        //revenir à 300
        if(vY>300) {
            vY = 300;
        }
    }

    /**
     * Getter getvX qui retourne la vitesse en x
     * @return double
     */
    public double getvX(){
        return vX;
    }

    /**
     * Getter getCollision qui retourne le boolean
     * collision
     * @return boolean
     */
    public boolean getCollision(){

        return this.collision;
    }

    /**
     * Setter setCollision qui permet de modifier
     * la valeur du boolean collision
     * @param collision
     */
    public void setCollision(boolean collision){

        this.collision=collision;
    }

    //Méthode augmenterPoints qui augmente le pointage du joueur de 5
    //et qui accélère le fantome

    public void augmenterPoints(){
        int temp = this.points % 2;

        this.points+=5;
        //Si le pointage est un multiple de 10 alors on accelere le fantome
        //Donc la fonction accelerer est appelé à chaque 2 obstacle dépassés
        if(temp==0)
            accelerer();
    }

    /** ¨
     * Getter getPoints qui retourne le pointage du joueur
     * @return int
     */
    public int getPoints(){
        return this.points;
    }


    /**
     * Méthode collision qui retourne un boolean qui indique
     * si il y a une collision entre le fantome et un fruits
     * *Tiré des notes de cours Infographie
     * @param obstacle
     * @return boolean
     */
    public boolean collision(Fruits obstacle){
        //On calcule les régions qu'occupent le fantome et l'obstacle
        double regionGhostX = this.posX + this.rayon;
        double regionGhostY = this.posY + this.rayon;
        double regionObstacleX = obstacle.getPosX() + obstacle.getRayon();
        double regionObstacleY = obstacle.getPosY() + obstacle.getRayon();
        //On calcul leur distance relative en x et y
        double distX = regionGhostX - regionObstacleX;
        double distY = regionGhostY - regionObstacleY;
        //Calcul de leur distance
        double distCarre = Math.pow(distX,2) + Math.pow(distY,2);
        //Si leur distance est plus petite que le carré de la somme des rayons du fantome et de l'obstacle
        boolean col = distCarre<Math.pow((this.rayon + obstacle.rayon),2);
        //Alors il y a une collison et on retourne le boolean collision qui sera true
        if(col == true){
            this.collision = true;
        }
        return this.collision;
    }

    /**
     *La méthode actualiser permet de mettre à jour la vitesse et la position
     * en y du fantome
     * selon le dT, la gravite et l'accélération en y
     * @param dT
     * @param fantome
     */
    @Override
    public void actualiser(double dT, Fantome fantome){

        //On met a jour la vitesse en y
        vY += dT * (gravite - aY);
        //On met a jour la position en y
        this.posY += dT * vY;
        //Si la position en y dépasse la fenetre vers le haut
        //on fait rebondir le fantome de 70 pixels
        if (this.posY < 0){
            vY = 70;
        }
        //Si il depasse la fenetre vers le bas on fait
        //rebondir le fantome de 250 pixels
        else if (this.posY > 345){
            vY = -250;
        }
        //Si la vitesse en y dépasse 300 vers le haut on la remet a 300
        else if (vY < -300){
            vY = -300;
        }
        //Si la vitesse en y du fantome depasse 300 vers le bas on la remet a 300
        else if (vY > 300){
            vY = 300;
        }
    }

    /**
     * Méthode affichage qui affiche les objets sur l'écran
     * dépendemment si on est en mode modeDebug ou non
     * @param context
     * @param modeDebug
     */
    @Override
    public void affichage(GraphicsContext context,boolean modeDebug){
        //Si on est en mode modeDebug alors on affiche le fantome
        //comme étant un cercle noir dans le canvas

        if(modeDebug == true){
            context.setFill(Color.BLACK);
            context.fillOval(this.posX, this.posY, 60,60);
            //Sinon on affiche l'image fourni pour le fantome
        }else {
            Image im = new Image("C:/Users/Bahsti/Desktop/fichiersFH/fichiersFH/ghost.png");
            context.drawImage(im, this.posX, this.posY);
        }
    }
}
