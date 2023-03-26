package com.example.flappyghost;

//Programme réalisé par Yohann Manseau-Glémot 20217138
//et Alex Chevrier 20216495
//Nous ne sommes pas capables de retirer les lignes
//package dans notre code sinon il ne fonctionne pas
//Aussi pour se servir des images nus avons utilisé le path d'un de nos ordinateur
//alors pour tester le code il faudra les changer
//Nous utilisons les images aux endroits suivant:
//Classe Fantome ligne 180, Classe Fruits ligne 27, Classe FlappyGhost ligne 159-160-187

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.scene.canvas.Canvas;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;


/**
 * La classe FlappyGhost qui est l'application JavaFx du projet
 */
public class FlappyGhost extends Application {
    int width = 640;//largeur de la fenetre
    int height = 440;//hauteur de la fenetre
    int height2 = 400;//hauteur de la fenetre de jeu
    AnimationTimer timer;//timer pour l'animation

    //Pane servant de fenêtre ou se déroulera le jeu
    Pane window;
    //ctxt et canvas pour afficher les images sur la fenetre
    GraphicsContext ctxt;
    Canvas canvas;

    boolean pauserJeu;//boolean pour reconnaitre si le jeu est en pause ou non
    Text points;//Zone de texte pour afficher le pointage du joueur
    private Controleur ctlr;//Instanciation du controleur

    /**
     * Fonction main pour exécuter le code
     * @param args
     */
    public static void main(String[] args) {

        launch(args);
    }
    /**
     * Méthode defilerAp qui s'occupe de faire defiler l'arrière-plan vers l'arrière
     * pour donner l'impression que le fantome se déplace vers l'avant
     * @param ap1 image de l'arrière-plan
     * @param ap2  copie de l'image de l'arrière-plan
     * @param v vitesse de defilement
     *
     */
    public void defilerAp(ImageView ap1, ImageView ap2, double v){
        //Position en x des deux arrière plan
        double ap1X =   ap1.getX();
        double ap2X =   ap2.getX();

        //On modifie leurs positions selon la vitesse fournie
        ap1.setX(ap1X - v);
        ap2.setX(ap2X - v);

        //Si la première image sort de la fenetre de jeu
        if(ap1.getX()<0){
            //On place la seconde au début de la fenetre
            ap2.setX(640+ ap1.getX());
        }
        //Quand la seconde image depasse la fenetre de jeu
        if(ap2.getX() <0){
            //On place la premiere au debut
            ap1.setX(640+ap2.getX());
        }

    }

    /**
     * Méthode affichage qui permet d'afficher les images du context
     * dépendemment si le mode debug est activé ou non
     * @param modele
     * @param modeDebug
     */
    public void affichage(Modele modele, boolean modeDebug){

        modele.affichage(this.ctxt,modeDebug);
    }


    /**
     * Méthode actualiserPointage qui affiche le pointage adéquat
     * sur l'écran
     * @param pointage
     */
    public void actualiserPointage(int pointage){

        points.setText("Score: "+ pointage);
    }

    /**
     * Méthode nouvellePartie qui recommence le jeu
     * en déclarant une nouvelle instance du controleur
     */
    public void nouvellePartie(){

        ctlr = new Controleur(this);
    }

    /**
     * Méthode pause qui s'occupe de mettre le jeu en pause
     * et recueil la position en y du fantome pour que lorsqu'on
     * clique sur resume le fantome continue à la même position
     */
    public void pause(){
        ctlr.positionY = ctlr.fantome.getPosY();
        this.pauserJeu=true;

    }

    /**
     * Méthode reinitialiser qui set simplement le boolean pauserJeu à false
     */
    public void reinitialiser() {

        this.pauserJeu=false;
    }

    /**
     * Méthode start pour la configuration du gui
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //On se servira d'un borderPane pour l'affichage des composantes
        BorderPane root = new BorderPane();
        //On créer la scene avec les dimensions adéquates
        Scene scene = new Scene(root, width, height);
        //On instancie le controleur le canvas, le contexte ainsi que
        //le pointage
        this.ctlr = new Controleur(this);
        this.canvas = new Canvas(width, height2);
        this.ctxt = canvas.getGraphicsContext2D();
        this.points = new Text("Score: 0");

        //On créer deux images de l'arrière-plan pour pouvoir les faire défiler plus tard
        Image b1 = new Image("C:/Users/Bahsti/Desktop/fichiersFH/fichiersFH/bg.png");
        Image b2 = new Image("C:/Users/Bahsti/Desktop/fichiersFH/fichiersFH/bg.png");

        ImageView bg1 =new ImageView(b1);
        ImageView bg2 =new ImageView(b2);
        //On créer un bouton pause ainsi qu'une checkbox pour le mode debug
        Button pause= new Button("Pause");
        CheckBox modeDebug= new CheckBox("Mode debug");
        //On créer un Pane ou on y ajoute les arrière-plans ainsi que le canvas
        window = new Pane();
        window.getChildren().add(bg1);
        window.getChildren().add(bg2);
        window.getChildren().add(canvas);
        //On positionne le pane au milieu du borderPane
        root.setCenter(window);

        //Deux separator pour séparer le bouton, la checkbox et le pointage
        Separator s1 = new Separator();
        Separator s2 = new Separator();
        //HBox pour le menu du bouton, checkbox et pointage
        HBox menu=new HBox();
        menu.getChildren().addAll(pause,s1, modeDebug, s2, points);
        menu.setAlignment(Pos.CENTER);//Alignement au centre
        root.setBottom(menu);//On place la Hbox à la région bottom du borderPane

        primaryStage.setTitle("FlappyGhost");//Titre de la fenêtre
        primaryStage.setResizable(false);//On ne peut pas rezise la fenetre de jeu

        Image icone = new Image("C:/Users/Bahsti/Desktop/fichiersFH/fichiersFH/ghost.png");
        primaryStage.getIcons().add(icone);//Petit icone du fantome en haut à gauche de la fenetre
        //Les deux lignes obligatoires pour le fonctionnement
        primaryStage.setScene(scene);
        primaryStage.show();


        /* Après l’exécution de la fonction, le
            focus va automatiquement au canvas */
        Platform.runLater(() -> canvas.requestFocus());
        /* Lorsqu’on clique ailleurs sur la scène,
            le focus retourne sur le canvas */
        scene.setOnMouseClicked((event) -> {
            canvas.requestFocus();
        });

        /**
         * Procédure permettant l'animation et la physique (collision) des objets (obstacles et arrière-plan)
         * Cette procédure permet aussi d'afficher un obstacle (fruit) à chaque 3 secondes
         */
        timer = new AnimationTimer() {

            private double repere =0;//Variable repere qui permettra d'afficher les obstacles au 3 secondes
            private long lastTime = System.nanoTime();


            /**
             * Méthode handle qui permet de gérer les actions selon le temps
             * @param now
             */
            @Override
            public void handle(long now) {

                if(lastTime == 0){
                    lastTime = now;
                    return;
                }
                //variable dT qui correspond au temps en secondes
                double dT = (now - lastTime) * 1e-9;
                //Si le jeu n'est pas sur pause
                if(pauserJeu == false){
                    repere += dT;//On incrémente le temps à la variable repere
                    //On fait défiler l'arrière-plan avec les images de l'arrière-plan
                    //ainsi que la vitesse multiplié par le temps
                    defilerAp(bg1, bg2, ctlr.getV()*dT);
                }
                //On clear le canvas pour empêcher les images de rester sur le l'écran
                //au fur et à mesure de leur déplacement
                FlappyGhost.this.ctxt.clearRect(0, 0, 640, 400);

                //On se sert du modulo 4 pour afficher un obstacle au hasard à chaque 3 secondes environ
                //Si on utilise modulo 3, les obstacles ne s'affichent pas à exactement 3 secondes
                //Donc le modulo 4 est celui qui est le plus près de 3 secondes
                double compte = Math.floor(repere);
                if((compte % 4) == 0){
                    repere =1;
                    ctlr.ajouter();

                }

                //Avec les trois lignes ci-desous, on se sert des méthodes du controleur
                //pour afficher le score adéquats, gérer les collisions entre le fantome et les
                //fruits (obstacles) et afficher ceux-ci à tout moment.
                ctlr.actualiserPoints();
                ctlr.collision();
                ctlr.afficherObstacle(dT, pauserJeu);


                lastTime = now;
                reinitialiser();//On réinitialise le boolean pauseJeu
            }

        };

        timer.start();
        //Permet de gérer le jeu lorsqu'on clique sur le bouton pause
        pause.setOnAction((event) -> {
            //Si on clique et que le texte sur le bouton est Resume(donc en pause)
            if (pause.getText().equals("Resume")){
                //On change le texte pour pause
                pause.setText("Pause");
                //Et on start l'animation
                timer.start();

            } else {
                //Sinon on met le texte à Resume
                pause.setText("Resume");
                //On arrete l'animation
                timer.stop();
                //Et on pause le jeu
                pause();
            }
        });
        //permet de gérer le jeu lorsqu'on coche la checkbox mode debug
        modeDebug.setOnAction((event) -> {
            //Si la case est cochée on utilise la méthode du controleur setModeDebug
            //pour modifier le boolean modeDebug du controleur à true
            if(modeDebug.isSelected()){
                ctlr.setModeDebug(true);

            } else{
                //Si elle est décochée on le met à false
                ctlr.setModeDebug(false);
            }
        });

        //Gere les actions lorsqu'on clique sur certaines touches du clavier
        scene.setOnKeyPressed((event) -> {
            //Si on clique sur la barre d'espace on utilise la
            //méthode du controleur pour faire saut le fantome
            if (event.getCode() == KeyCode.SPACE) {
                ctlr.saut();
            }
            //Ajout facultatif: si on clique sur la touche P on pause le jeu
            //C'est la même procédure vu plus haut
            else if(event.getCode() == KeyCode.P){

                if (pause.getText().equals("Resume")){
                    pause.setText("Pause");
                    timer.start();

                } else {
                    pause.setText("Resume");

                    pause();
                    timer.stop();
                }

            }
            //Si on clique sur la touche escape la fenetre de jeu se ferme
            else if(event.getCode() == KeyCode.ESCAPE){
                Platform.exit();
            }
        });
    }
}

