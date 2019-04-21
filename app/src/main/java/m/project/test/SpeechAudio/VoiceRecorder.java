package m.project.test.SpeechAudio;


/**
 * Interface decrivant chaue fonction necessaire a l'utilisation d'un voice recorder ( reconnaisance vocale)
 */
public interface VoiceRecorder {
    /**
     * Permet de demarrer le voice recorder
     */
    void launch();

    /**
     * Permet d'arreter le voice recorder
     */
    void close();

    /**
     * Permet de commencer l'enregistrement de la voix
     */
    void startRecord();

    /**
     * Permet d'arreter l'enregistrement
     */
    void stopRecord();

    /**
     * Permet de savoir si la reconnaissance vocal ets continue
     * @return boolean
     */
    boolean isContinuous();

    /**
     * Permet de savoir si la reconnaissance vocal est en train d'enregistrer
     * @return
     */
    boolean isRecording();
}
