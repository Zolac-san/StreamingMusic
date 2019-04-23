package com.riviere.MusicStreaming.SpeechAudio;

/**
 * interface permettant au classe en heritant d'obtenir le volume audio quand l'utilisateur parle
 */
public interface ListenerLiveAudioChangeRms {
    /**
     * Fonction trigger quand un changement audio est effectue.
     * @param rms : decibel de la parole de l'utilisateur sur 10
     */
    void onChangeAudioRms(float rms);
}
