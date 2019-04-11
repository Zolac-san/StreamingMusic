package m.project.test.Streaming;



import android.net.Uri;
import android.util.Log;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;


import m.project.test.Music.Music;
import m.project.test.Music.Playlist;
import m.project.test.MyApp;
import m.project.test.Network.Ice.ListenerMusicUrl;
import m.project.test.Network.Ice.MusicPlayerManager;

public class PlayerVlc implements ListenerMusicUrl {
    private static PlayerVlc instance = null;

    private MediaPlayer vlc;
    private LibVLC libVlc;
    private Playlist playlist;

    private PlayerVlc(){
        this.libVlc = new LibVLC(MyApp.getAppContext());
        this.vlc = new MediaPlayer(libVlc);
        this.playlist = null;
        /*this.vlc.setEventListener(new MediaPlayer.EventListener() {
            @Override
            public void onEvent(MediaPlayer.Event event) {
                if(event.type == MediaPlayer.Event.EndReached){
                    if(PlayerVlc.getInstance().getPlaylist() != null){
                        PlayerVlc.getInstance().getPlaylist().getNext();
                    }
                }
            }
        });*/
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

    public static PlayerVlc getInstance(){
        if(instance == null)
            instance = new PlayerVlc();
        return instance;
    }

    public void play(){
        vlc.play();
    }

    public void stop(){
        vlc.stop();
    }

    public void pause(){
        vlc.pause();
    }

    public void replay(){
        vlc.setTime(0);
        vlc.play();
    }

    public void load(String url){
        Log.i("PlayerVlc",url);
        this.playlist = null;
        try{
            vlc.stop();
            Media media = new Media(libVlc,Uri.parse(url));// Need to parse to uri?
            vlc.setMedia(media);
        }catch(Exception e){

        }
    }

    public Playlist getPlaylist(){
        return this.playlist;
    }

    public void playPlaylist(Playlist playlist){
        this.playlist = playlist;
        Music music = this.playlist.getNext();
        MusicPlayerManager.getInstance().play(music.getTittle(),music.authorsToString(),music.getAlbum(),this);

    }

    public void loadNextMusicPlaylist(){
        if(this.playlist == null) return;
        Music music = this.playlist.getNext();
        MusicPlayerManager.getInstance().play(music.getTittle(),music.authorsToString(),music.getAlbum(),this);
    }


    public void loadPreviousMusicPlaylist(){
        if(this.playlist == null) return;
        Music music = this.playlist.getPrevious();
        MusicPlayerManager.getInstance().play(music.getTittle(),music.authorsToString(),music.getAlbum(),this);
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
}
