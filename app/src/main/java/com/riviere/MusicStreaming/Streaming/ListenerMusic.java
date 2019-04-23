package com.riviere.MusicStreaming.Streaming;

import com.riviere.MusicStreaming.Music.Music;

/**
 * Interface permettant au classe ehritant de s'enregistrer au pres du player vlc
 */
public interface ListenerMusic {
    /**
     * Trigger quand la musique change
     * @param music : nouvelle musique jouer
     */
    void onMusicChange(Music music);
}
