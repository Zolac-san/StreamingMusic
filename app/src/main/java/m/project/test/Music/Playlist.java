package m.project.test.Music;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String namePlaylist;
    private List<Music> musics;

    public Playlist(String namePlaylist){
        this.namePlaylist = namePlaylist;
        this.musics = new ArrayList<>();
    }

    public void add(Music music){
        this.musics.add(music);
    }

    public Music getNext(){
        if(this.musics.size() == 0) return null;
        Music musicToReturn = this.musics.get(0);
        this.musics.remove(0);
        this.musics.add(musicToReturn);
        return musicToReturn;
    }

    public Music getPrevious(){
        if(this.musics.size() == 0) return null;



        Music musicToPutAtTheFirstPlace = this.musics.get(this.musics.size()-1);
        this.musics.remove(this.musics.size()-1);
        this.musics.add(0,musicToPutAtTheFirstPlace);
        return this.musics.get(this.musics.size()-1);
    }
}
