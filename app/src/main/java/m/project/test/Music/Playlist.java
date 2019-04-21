package m.project.test.Music;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe représentant une playlist
 */
public class Playlist {
    private String namePlaylist;
    private List<Music> musics;

    /**
     * Constructeur
     * @param namePlaylist : nom de la playlist
     */
    public Playlist(String namePlaylist){
        this.namePlaylist = namePlaylist;
        this.musics = new ArrayList<>();
    }

    /**
     * Ajoute une musique
     * @param music
     */
    public void add(Music music){
        this.musics.add(music);
    }

    /**
     * Retourne la musique suivante a jouer
     * @return musique suivante
     */
    public Music getNext(){
        if(this.musics.size() == 0) return null;
        Music musicToReturn = this.musics.get(0);
        this.musics.remove(0);
        this.musics.add(musicToReturn);
        return musicToReturn;
    }


    /**
     * Retrourne la musique précédente
     * @return musique précédente
     */
    public Music getPrevious(){
        if(this.musics.size() == 0) return null;
        Music musicToPutAtTheFirstPlace = this.musics.get(this.musics.size()-1);
        this.musics.remove(this.musics.size()-1);
        this.musics.add(0,musicToPutAtTheFirstPlace);
        return this.musics.get(this.musics.size()-1);
    }
}
