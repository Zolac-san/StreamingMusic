package m.project.test.Streaming;



import android.net.Uri;
import android.util.Log;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;


import java.util.ArrayList;
import java.util.List;

import m.project.test.Music.Music;
import m.project.test.Music.Playlist;
import m.project.test.MyApp;
import m.project.test.Network.Ice.ListenerMusicUrl;
import m.project.test.Network.Ice.MusicPlayerManager;


/**
 * Classe qui joue la musique
 */
public class PlayerVlc implements ListenerMusicUrl {
    private static PlayerVlc instance = null;

    private MediaPlayer vlc;
    private LibVLC libVlc;
    private Playlist playlist;

    private List<ListenerMusic> listeners;
    Music currentMusic;

    /**
     * Constructeur
     */
    private PlayerVlc(){
        this.libVlc = new LibVLC(MyApp.getAppContext());
        this.vlc = new MediaPlayer(libVlc);
        this.playlist = null;
        this.listeners = new ArrayList<>();
        this.currentMusic = new Music();
        this.vlc.setEventListener(new MediaPlayer.EventListener() {
            @Override
            public void onEvent(MediaPlayer.Event event) {
                if(event.type == MediaPlayer.Event.EndReached){
                    if(PlayerVlc.getInstance().getPlaylist() != null){
                        PlayerVlc.getInstance().loadNextMusicPlaylist();
                    }
                }
            }
        });
    }

    /**
     * Retourne l'instance de la classe
     * @return instance
     */
    public static PlayerVlc getInstance(){
        if(instance == null)
            instance = new PlayerVlc();
        return instance;
    }

    /**
     * Joue la musique chargée
     */
    public void play(){
        vlc.play();
    }

    /**
     * Arrete la musique
     */
    public void stop(){
        vlc.stop();
    }

    /**
     * Met en pause la musique
     */
    public void pause(){
        vlc.pause();
    }

    /**
     * Rejoue la musique
     */
    public void replay(){
        vlc.setTime(0);
        vlc.play();
    }

    /**
     * Chrage une musique
     * Enleve la playlist ( si une playlist est jouée )
     * @param url : stream audio
     */
    public void load(String url){

        this.playlist = null;
        try{
            vlc.stop();
            Media media = new Media(libVlc,Uri.parse(url));// Need to parse to uri?
            vlc.setMedia(media);
        }catch(Exception e){

        }
    }

    /**
     * Retourne la playlist
     * @return playlist
     */
    public Playlist getPlaylist(){
        return this.playlist;
    }

    /**
     * Joue une playlist
     * @param playlist à jouer
     */
    public void playPlaylist(Playlist playlist){
        this.playlist = playlist;
        Music music = this.playlist.getNext();
        this.currentMusic = music;
        notifyMusicChange();
        MusicPlayerManager.getInstance().play(music.getTitle(),music.authorsToString(),music.getAlbum(),this);

    }

    /**
     * Charge la prochaine musique dans la playlist
     */
    public void loadNextMusicPlaylist(){
        if(this.playlist == null) return;
        Music music = this.playlist.getNext();
        this.currentMusic = music;
        notifyMusicChange();
        MusicPlayerManager.getInstance().play(music.getTitle(),music.authorsToString(),music.getAlbum(),this);
    }

    /**
     * Charge la musique précédente de la playlist
     */
    public void loadPreviousMusicPlaylist(){
        if(this.playlist == null) return;
        Music music = this.playlist.getPrevious();
        this.currentMusic = music;
        notifyMusicChange();
        MusicPlayerManager.getInstance().play(music.getTitle(),music.authorsToString(),music.getAlbum(),this);
    }

    @Override
    public void onResultMusicUrl(String url) {
        if(this.playlist != null){
            vlc.stop();
            Media media = new Media(libVlc,Uri.parse(url));// Need to parse to uri?
            vlc.setMedia(media);
            vlc.play();
        }
    }

    /**
     * Permet d'enregistrer un objet pour qu'ilsoit notifier lors d'une action sur une musique
     * @param listener : objet a enregister
     */
    public void addListener(ListenerMusic listener){
        this.listeners.add(listener);
    }

    /**
     * Permet de notify tous les objets enregistré que la musique à changer
     */
    public void notifyMusicChange(){
        for (ListenerMusic listener : this.listeners){
            listener.onMusicChange(this.currentMusic);
        }
    }

    /**
     * Get current music
     * @return music
     */
    public Music getCurrentMusic() {
        return currentMusic;
    }

    /**
     * Set current music
     * @param currentMusic
     */
    public void setCurrentMusic(Music currentMusic) {
        this.currentMusic = currentMusic;
    }
}
