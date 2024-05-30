import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controleur du clavier
 */
public class ControleurLettres implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     */
    private Pendu vuePendu;
    /** private char lettre */
    private char lettre;
    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    ControleurLettres(MotMystere modelePendu, Pendu vuePendu,char lettre){
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
        this.lettre = lettre;
    }

    /**
     * Actions à effectuer lors du clic sur une touche du clavier
     * Il faut donc: Essayer la lettre, mettre à jour l'affichage et vérifier si la partie est finie
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        modelePendu.essaiLettre(lettre);
        // TODO : update vue to reckt letter
    }
}
