package com.riviere.MusicStreaming.SpeechAudio;

/**
 * Interface permettant aux classes
 */
public interface ListenerLiveAudioResult {
    /**
     * Fonction trigger qaund une reponse audio est obtenu
     * @param liveSpeechResult : parole de l'utilisateur
     */
    void getLiveAudioResult(String liveSpeechResult);
}
