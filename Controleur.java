package com.example.flappyghost;

import java.util.ArrayList;
import java.lang.Math;

//Controleur du projet qui relie la vue et le modele ensemble
public class Controleur {

    public Fantome fantome;//Instanciation du joueur
    private final ArrayList<Fruits> fruits;//ArrayList permettant de stocker les obstacles
    private boolean modeDebug;//Boolean pour différencier le mode debug du mode normal
    private FlappyGhost vue;//Instanciation de la vue
    public double positionY;//position du fantome lorsqu'on met le jeu sur pause
    private double deltaTime;
    private boolean pause;

    //Constructeur de la classe qui prend la vue en paramètre
    Controleur(FlappyGhost vue) {
        fruits=new ArrayList<>();//Nouvel ArrayList d'obstacles
        modeDebug=false;//Le mode debug commence toujours false
        this.vue = vue;//Instanciation de la vue
        this.fantome = new Fantome();//On créer un nouveau joueur
    }
    /**
     *Méthode ajouter qui ajoute un type d'obstacle au hasard dans l'ArrayList Fruits
     */
    public void ajouter() {
        //Génère un chiffre entre 0 et 2 stocké dans la variable typeObstacle
        int typeObstacle = (int) (Math.random() * 3);
        //Selon le chiffre obtenu on génère soit un obstacle
        //simple, sinus ou quantique en l'ajoutant
        //à l'ArrayList mentionné plus haut
        if(typeObstacle==0){
            ObstacleSinus sinus = new ObstacleSinus();
            fruits.add(sinus);
        }
        else if(typeObstacle==1){
            ObstacleSimple oS = new ObstacleSimple();
            fruits.add(oS);
        }
        else if(typeObstacle == 2){
            ObstacleQuantique oQ = new ObstacleQuantique();
            fruits.add(oQ);
        }
    }
    //Méthode saut qui utilise la méthode saut de la classe fantome
    //afin de le faire saut
    public void saut(){

        fantome.saut();
    }
    //Méthode actualiserPoints qui utilise la méthode actualiserPointage
    //de la vue afin d'afficher le pointage actuel du joueur
    public void actualiserPoints(){
        int p = fantome.getPoints();
        vue.actualiserPointage(p);
    }

    /**
     * Setter setModeDebug qui modifie la valeur du boolean modeDebug
     * @param debug
     */
    public void setModeDebug(boolean debug){

        modeDebug=debug;
    }

    /**
     * Méthode collision qui gère les collisions entre le fantome et les fruits
     */
    public void collision() {
        //Boucle for qui itère à travers tous les obstacles
        //présent dans le jeu
        for (Fruits fruit : fruits) {
            //Si le fantome entre en contact avec un obstacle
            //on set la collision à true pour l'obstacle en question
            if (fantome.collision(fruit) == true) {

                fruit.setCollision(true);
                //Sinon on set la collision à false
            }else{

                fruit.setCollision(false);
            }
        }
        //Si le mode debug est désactivé et que le fantome entre en collision
        //c'est à dire que si le joueur perd la partie
        if (fantome.getCollision() == true &&modeDebug == false ) {
            vue.nouvellePartie();//On recommence une partie
        }
        //Et on set la collision du joueur à false
        fantome.setCollision(false);
    }

    /**
     * Méthode getV qui retourne la vitesse courant du fantome
     * @return double
     */
    public double getV(){
        return fantome.getvX();
    }



    /**
     * Méthode afficherObstacle qui s'occupe de l'animation des obstacles
     * ainsi que du fantome
     * @param deltaTime temps qui permet l'actualisation des objets
     */
    public void afficherObstacle(double deltaTime, boolean pause) {
        this.deltaTime = deltaTime;
        this.pause = pause;

        //Si le jeu n'est pas sur pause
        if (!pause) {
            //On itère à travers tous les obstacles présent dans le jeu
            for (Fruits fruit : fruits) {
                //Si la position en x d'un obstacle se trouve
                //en dehors de la window
                if (fruit.getPosX() < -320) {
                    fruits.remove(fruit);//On retire l'obstacle de la liste
                    break;
                } else {
                    //Sinon on affiche les obstacle selon si le mode debug est activé ou non
                    vue.affichage(fruit, modeDebug);
                    //et on actualise leurs vitesses
                    fruit.actualiser(deltaTime, fantome);
                }
            }

            //On affiche le fantome et on actualise ses vitesses
            vue.affichage(fantome, modeDebug);
            fantome.actualiser(deltaTime, null);
        }
        //Si le jeu est sur pause on set la position en y du fantome
        //à celle de positionY
        else {
            fantome.setPosY(positionY);
        }
    }

}

