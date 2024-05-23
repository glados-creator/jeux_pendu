import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;
    /** private Pane fenetreJeu_v */
    private Pane fenetreJeu_v;
    /** private Pane fenetreAccueil_v */
    private Pane fenetreAccueil_v;

    private ToggleGroup diff_select;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.Difficulter.FACILE.value(), 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.fenetreAccueil_v = fenetreAccueil();
        this.fenetreJeu_v = fenetreJeu();
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        panelCentral = new BorderPane();
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 100);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){
        Pane banniere = new Pane();
        Label l = new Label("Jeu du Pendu");
        HBox holder = new HBox(); 

        
        boutonMaison = new Button();
        File home_img_tmp = new File("./img/home.png");
        System.out.println(home_img_tmp.toURI().toString());
        var home_img = new ImageView(new Image(home_img_tmp.toURI().toString()));
        boutonMaison.setGraphic(home_img);

        boutonParametres = new Button();
        File param_img_tmp = new File("./img/parametres.png");
        System.out.println(param_img_tmp.toURI().toString());
        var param_img = new ImageView(new Image(param_img_tmp.toURI().toString()));
        boutonMaison.setGraphic(param_img);

        Button info = new Button();
        File info_img_tmp = new File("./img/info.png");
        System.out.println(info_img_tmp.toURI().toString());
        var info_img = new ImageView(new Image(info_img_tmp.toURI().toString()));
        boutonMaison.setGraphic(info_img);

        holder.getChildren().addAll(boutonMaison,boutonParametres,info);
        holder.setAlignment(Pos.CENTER_RIGHT);
        holder.setPrefHeight(100);
        holder.setMaxWidth(Region.USE_PREF_SIZE);
        banniere.getChildren().addAll(l,holder);
        return banniere;
    }

    /**
     * @return le panel du chronomètre
     */
    private TitledPane leChrono(){
        // A implementer
        TitledPane res = new TitledPane();
        return res;
    }

    /**
     * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     *         de progression et le clavier
     */
    private Pane fenetreJeu(){
        // A implementer
        Pane res = new Pane();
        return res;
    }

    /**
     * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     */
    private Pane fenetreAccueil(){
        Pane res = new Pane();
        bJouer = new Button("Lancer une partie");
        var god = this;
        bJouer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                god.modeJeu();
            }
        });

        VBox inner = new VBox();
        diff_select = new ToggleGroup();
        var tg = diff_select;
        god.niveaux = new ArrayList<>();
        for (MotMystere.Difficulter diff : MotMystere.Difficulter.values()){
            RadioButton tmp = new RadioButton(diff.name());
            tmp.setToggleGroup(tg); 
            god.niveaux.add(diff.name()); /// JSP
            inner.getChildren().add(tmp);
        }
        ((RadioButton)inner.getChildren().get(0)).fire();
        TitledPane holder = new TitledPane("Niveau de difficulter",inner);
        holder.setExpanded(true);
        holder.setCollapsible(false);
        res.getChildren().addAll(bJouer,holder);
        return res;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    /** void modeAccueil met l affichage Accueil */
    public void modeAccueil(){
        panelCentral.setCenter(fenetreAccueil_v);
    }
    /** void modeJeu met l affichage Jeux */
    public void modeJeu(){
        panelCentral.setCenter(fenetreAccueil_v);
    }
    
    /** void modeParametres */
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    /**
     * popUpPartieEnCours
     * @return Alert
     */
    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    /**
     * popUpReglesDuJeu
     * @return Alert
     */
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }
    
    /**
     * popUpMessageGagne
     * @return Alert
     */
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);        
        return alert;
    }
    
    /**
     * popUpMessagePerdu
     * @return Alert Alert
     */
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args the terminal vargs unused
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
