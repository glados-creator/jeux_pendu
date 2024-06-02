import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur du chronomètre
 */
public class ControleurChronometre implements EventHandler<ActionEvent> {
    /**
     * temps enregistré lors du dernier événement
     */
    private long tempsPrec;
    /**
     * temps écoulé depuis le début de la mesure
     */
    private long tempsEcoule;
    /**
     * Vue du chronomètre
     */
    private Chronometre chrono;

    /**
     * Constructeur du contrôleur du chronomètre
     * noter que le modèle du chronomètre est tellement simple
     * qu'il est inclus dans le contrôleur
     * @param chrono Vue du chronomètre
     */
    public ControleurChronometre (Chronometre chrono){
        this.chrono = chrono;
        this.tempsEcoule = 0;
        this.tempsPrec = 0;
    }

    /**
     * Actions à effectuer tous les pas de temps
     * essentiellement mesurer le temps écoulé depuis la dernière mesure
     * et mise à jour de la durée totale
     * @param actionEvent événement Action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // Get the current time in milliseconds
        long currentTime = System.currentTimeMillis();

        // If this is the first event, initialize tempsPrec
        if (tempsPrec == 0) {
            tempsPrec = currentTime;
        }

        // Calculate the time elapsed since the last event
        long elapsedTime = currentTime - tempsPrec;

        // Update the total elapsed time
        tempsEcoule += elapsedTime;

        // Update the view (chrono) with the new elapsed time
        chrono.setTime(tempsEcoule);

        // Record the current time as the last recorded time
        tempsPrec = currentTime;
    }

    /**
     * Remet la durée à 0
     */
    public void reset(){
        this.tempsEcoule = 0;
        this.tempsPrec = 0;
    }
}
