package m.project.test.Network.TranslateServer;


/**
 * Interface signifiant que la classe qui en herite accepte les resultats des command oral
 */
public interface ListenerRequestTranslate {

    /**
     * Fonction qui est appellée lors dela recuperation d'une commande vocal.
     * @param response : reponse obtenu
     */
    void getResultCommand(ResponseTranslate response);
}
