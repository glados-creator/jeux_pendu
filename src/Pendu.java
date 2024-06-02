
// import javafx.animation.KeyFrame;
import javafx.application.Application;
// import javafx.beans.value.ChangeListener;
// import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
// import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
// import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
// import javafx.scene.text.TextAlignment;
// import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
// import java.util.Arrays;
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
    // private ProgressBar pg;
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
    /** private ToggleGroup diff_select */
    private ToggleGroup diff_select;
    // private String path = "/usr/share/dict/french";
    private String path = "/media/glados/PAVARDARTHU/dict/french";

    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono
     * ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere(this.path, 3, 10, MotMystere.Difficulter.FACILE.value(), 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.dessin = new ImageView(this.lesImages.get(0));
        this.leNiveau = new Text();
        this.chrono = new Chronometre();
        this.motCrypte = new Text("");
        this.clavier = new Clavier(this.alphabet, (String l) -> {
            return new ControleurLettres(this.modelePendu, this, l.charAt(0));
        }, 8);
        this.fenetreAccueil_v = fenetreAccueil();
        this.fenetreJeu_v = fenetreJeu();
    }

    /**
     * @return le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene() {
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        panelCentral = new BorderPane();
        fenetre.setCenter(this.panelCentral);
        Scene sc = new Scene(fenetre, 800, 100);
        var god = this;
        sc.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            String tmp = event.getText();
            System.out.println(tmp);
            if (god.alphabet.contains(tmp)) {
                god.modelePendu.essaiLettre(tmp.charAt(0));
                god.essaiLettre();
            }
        });
        return sc;
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre() {
        final int bar_size = 50;

        Pane banniere = new Pane();
        HBox root = new HBox();
        banniere.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, null, new Insets(1))));
        Label l = new Label("Jeu du Pendu");
        HBox holder = new HBox();
        var god = this;

        boutonMaison = new Button();
        File home_img_tmp = new File("./img/home.png");
        System.out.println(home_img_tmp.toURI().toString());
        var home_img = new ImageView(new Image(home_img_tmp.toURI().toString()));
        home_img.setFitHeight(bar_size);
        home_img.setFitWidth(bar_size);
        boutonMaison.setMaxSize(bar_size, bar_size);
        boutonMaison.setGraphic(home_img);
        boutonMaison.setOnAction(new RetourAccueil(modelePendu, this));

        boutonParametres = new Button();
        File param_img_tmp = new File("./img/parametres.png");
        System.out.println(param_img_tmp.toURI().toString());
        var param_img = new ImageView(new Image(param_img_tmp.toURI().toString()));
        param_img.setFitHeight(bar_size);
        param_img.setFitWidth(bar_size);
        boutonParametres.setMaxSize(bar_size, bar_size);
        boutonParametres.setGraphic(param_img);
        boutonParametres.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                god.modeParametres();
            }
        });

        Button info = new Button();
        File info_img_tmp = new File("./img/info.png");
        System.out.println(info_img_tmp.toURI().toString());
        var info_img = new ImageView(new Image(info_img_tmp.toURI().toString()));
        info_img.setFitHeight(bar_size);
        info_img.setFitWidth(bar_size);
        info.setMaxSize(bar_size, bar_size);
        info.setGraphic(info_img);
        info.setOnAction(new ControleurInfos(this));

        holder.getChildren().addAll(boutonMaison, boutonParametres, info);
        holder.setAlignment(Pos.CENTER_RIGHT);
        banniere.setMaxHeight(bar_size);
        root.setMaxHeight(bar_size);
        // setHgrow
        root.getChildren().addAll(l, holder);
        banniere.getChildren().add(root);
        return banniere;
    }

    /**
     * @return le panel du chronomètre
     */
    private TitledPane leChrono() {
        HBox node = new HBox();
        this.chrono = new Chronometre();
        this.chrono.start();
        node.getChildren().add(this.chrono);
        TitledPane res = new TitledPane("", node);
        return res;
    }

    /**
     * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     *         de progression et le clavier
     */
    private Pane fenetreJeu() {
        GridPane res = new GridPane();
        res.add(this.motCrypte, 0, 0);
        res.add(this.leNiveau, 1, 0);
        res.add(this.dessin, 0, 1);
        VBox tmp = new VBox();
        Button reset = new Button("changer de mot");
        var god = this;
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                god.lancePartie();
            }
        });
        tmp.getChildren().addAll(this.leNiveau, this.chrono, reset);
        res.add(tmp, 1, 1);
        res.add(this.clavier, 0, 2);
        return res;
    }

    /**
     * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de
     *         jeu
     */
    private Pane fenetreAccueil() {
        Pane res = new Pane();
        // res.setBackground(new Background(new BackgroundFill(Color.RED, null, new
        // Insets(1))));
        bJouer = new Button("Lancer une partie");
        var god = this;
        bJouer.setOnAction(new ControleurLancerPartie(modelePendu, this));

        VBox inner = new VBox();
        diff_select = new ToggleGroup();
        var tg = diff_select;
        god.niveaux = new ArrayList<>();
        for (MotMystere.Difficulter diff : MotMystere.Difficulter.values()) {
            RadioButton tmp = new RadioButton(diff.name());
            tmp.setToggleGroup(tg);
            tmp.setUserData(diff);
            god.niveaux.add(diff.name());
            inner.getChildren().add(tmp);
        }
        ((RadioButton) inner.getChildren().get(0)).fire();
        TitledPane holder = new TitledPane("Niveau de difficulter", inner);
        holder.setExpanded(true);
        holder.setCollapsible(false);

        VBox content = new VBox();
        content.getChildren().addAll(bJouer, holder);
        res.getChildren().addAll(content);
        return res;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * 
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire) {
        for (int i = 0; i < this.modelePendu.getNbErreursMax() + 1; i++) {
            File file = new File(repertoire + "/pendu" + i + ".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    /** void modeAccueil met l affichage Accueil */
    public void modeAccueil() {
        chrono.stop();
        panelCentral.setCenter(fenetreAccueil_v);
    }

    /** void modeJeu met l affichage Jeux */
    public void modeJeu() {
        chrono.start();
        panelCentral.setCenter(fenetreJeu_v);
    }

    /** void modeParametres */
    public void modeParametres() {
        popUpReglesDuJeu().showAndWait();
    }

    /** lance une partie */
    public void lancePartie() {
        MotMystere.Difficulter diff = (MotMystere.Difficulter) this.diff_select.getSelectedToggle().getUserData();
        this.leNiveau.setText("difficulter : " + diff.name());
        this.clavier.reset();
        this.chrono.resetTime();
        this.modelePendu.setNiveau(diff.value());
        this.modelePendu.setMotATrouver();
        System.out.println(this.modelePendu.getMotATrouve());
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        modeJeu();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage() {
        // A implementer
        // ?????
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * 
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono() {
        return this.chrono;
    }

    /**
     * popUpPartieEnCours
     * 
     * @return Alert
     */
    public Alert popUpPartieEnCours() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }

    /**
     * popUpReglesDuJeu
     * 
     * @return Alert
     */
    public Alert popUpReglesDuJeu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("regle du jeux");
        alert.setContentText("ici les regles du pendu ; google ");
        return alert;
    }

    /**
     * popUpMessageGagne
     * 
     * @return Alert
     */
    public Alert popUpMessageGagne() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("win");
        alert.setContentText("vous avez gagné");
        return alert;
    }

    /**
     * popUpMessagePerdu
     * 
     * @return Alert Alert
     */
    public Alert popUpMessagePerdu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("lose");
        alert.setContentText("vous avez perdu");
        return alert;
    }

    /** void essaiLettre */
    public void essaiLettre() {
        this.clavier.desactiveTouches(this.modelePendu.getLettresEssayees());
        if (this.modelePendu.gagne()) popUpMessageGagne();
        if (this.modelePendu.perdu()) popUpMessagePerdu();
        int index = (int) ((1 - (double) this.modelePendu.getNbErreursRestants() / this.modelePendu.getNbErreursMax()) * (this.lesImages.size() - 1));
        // System.out.println("size : "+this.lesImages.size()+" , index : "+index);
        this.dessin.setImage(this.lesImages.get(index));
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
    }

    /**
     * créer le graphe de scène et lance le jeu
     * 
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
     * 
     * @param args the terminal vargs unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
