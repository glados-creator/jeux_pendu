// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'un faire un héritié d'un TilePane
 */
public class Clavier extends TilePane{
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     * @param touches une chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(String touches, Function<String,ControleurLettres> actionTouches,int tailleLigne) {
        this.clavier = new ArrayList<>();
        this.setPrefColumns(13);
        this.setMaxWidth(Region.USE_PREF_SIZE);
        for (int i = 0; i < touches.length(); i++) {
            Button tmp = new Button();
            tmp.setOnAction(actionTouches.apply(String.valueOf(touches.charAt(i))));
            this.clavier.add(tmp);
            this.getChildren().add(tmp);
        }
    }

    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * @param touchesDesactivees une chaine de caractères contenant la liste des touches désactivées
     */
    public void desactiveTouches(Set<String> touchesDesactivees){
        for (String current : touchesDesactivees){
            this.clavier.get((int)current.charAt(0)-'a').setDisable(true);
        }
    }

    /** void reset */
    public void reset(){
        for(Button b : this.clavier){
            b.setDisable(false); 
        }
    }
}