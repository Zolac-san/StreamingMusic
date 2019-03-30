package m.project.test.Streaming;



import android.net.Uri;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;



import m.project.test.MyApp;

public class PlayerVlc {
    private static PlayerVlc instance = null;

    MediaPlayer vlc;
    LibVLC libVlc;
    private PlayerVlc(){
        libVlc = new LibVLC(MyApp.getAppContext());
        vlc = new MediaPlayer(libVlc);

    }

    public static PlayerVlc getInstance(){
        if(instance == null)
            instance = new PlayerVlc();
        return instance;
    }

    public void play(String url){
        try{

            Media media = new Media(libVlc,Uri.parse(url));// Need to parse to uri?
            vlc.setMedia(media);
            vlc.play();
        }catch(Exception e){

        }

    }
}
