package com.riviere.MusicStreaming.Network.UserServer;


/**
 * Interface signifiant que la classe qui en herite accepte les reponses du serveur utilisateur
 */
public interface ListenerRequestUser {
    /**
     * Fonction qui est lancée quand une reponse utilisateur est recu
     * @param response : reponse du serveur
     */
    void getResultUser(ResponseUser response);
}
