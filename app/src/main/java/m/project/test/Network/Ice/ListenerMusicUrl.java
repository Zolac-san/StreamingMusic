package m.project.test.Network.Ice;

/**
 * Interface signifiant que la classe qui en herite accepte le renvoie d'url de stream
 */
public interface ListenerMusicUrl {
    /**
     * Fonction lance lors de la recuperation de l'url d'un stream de musique
     * @param url : url du stream
     */
    void onResultMusicUrl(String url);
}
