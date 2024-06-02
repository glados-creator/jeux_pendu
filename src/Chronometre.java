import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
// import javafx.event.ActionEvent;

/**
 * Permet de gérer un Text associé à une Timeline pour afficher un temps écoulé
 */
public class Chronometre extends Text {
    /**
     * timeline qui va gérer le temps
     */
    private Timeline timeline;
    /**
     * la fenêtre de temps
     */
    private KeyFrame keyFrame;
    /**
     * le contrôleur associé au chronomètre
     */
    private ControleurChronometre actionTemps;

    /**
     * Constructeur permettant de créer le chronomètre
     * avec un label initialisé à "0:0:0"
     * Ce constructeur créer la Timeline, la KeyFrame et le contrôleur
     */
    public Chronometre() {
        this.setText("0:0");
        this.setFont(new Font(20));
        this.setTextAlignment(TextAlignment.CENTER);

        actionTemps = new ControleurChronometre(this);

        keyFrame = new KeyFrame(Duration.seconds(1), actionTemps);
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Permet au controleur de mettre à jour le text
     * la durée est affichée sous la forme m:s
     * @param tempsMillisec la durée depuis à afficher
     */
    public void setTime(long tempsMillisec) {
        long totsec = tempsMillisec / 1000;
        long min = totsec / 60;
        long sec = totsec % 60;
        this.setText(min + " : " + sec);
    }

    /**
     * Permet de démarrer le chronomètre
     */
    public void start() {
        actionTemps.reset(); // reset the timer before starting
        this.timeline.play();
    }

    /**
     * Permet d'arrêter le chronomètre
     */
    public void stop() {
        this.timeline.stop();
    }

    /**
     * Permet de remettre le chronomètre à 0
     */
    public void resetTime() {
        stop();
        actionTemps.reset();
        this.setText("0:0");
    }
}
