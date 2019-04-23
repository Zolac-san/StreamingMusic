package com.riviere.MusicStreaming.Music;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe representant une musique
 */
public class Music {
    String title;
    List<String> authors;
    String album;

    /**
     * Constructeur
     */
    public Music() {
        this.title = "";
        this.authors = new ArrayList<String>();
        this.album = "";
    }

    /**
     * Constructeur
     * @param title : titre
     * @param authors : autheurs
     * @param album : album
     */
    public Music(String title, List<String> authors, String album) {
        this.title = title;
        this.authors = authors;
        this.album = album;
    }

    /**
     * Get title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get authors
     * @return authors
     */
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * Set authors
     * @param authors
     */
    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    /**
     * Get album
     * @return album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Set album
     * @param album
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Create a json object of the music
     * @return json object of music
     */
    public String toJsonString(){
        JSONObject json = new JSONObject ();
        try {
            json.put("title",this.title);
            json.put("authors",this.authorsToString());
            json.put("album",this.album);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }


    /**
     * Transform list of authors to string
     * @return authors in string
     */
    public String authorsToString(){
        StringBuilder builder = new StringBuilder();
        for ( String oneAuthor : this.authors){
            builder.append(",");
            builder.append(oneAuthor);

        }
        return builder.toString().substring(1);
    }

    @Override
    public String toString() {
        return "Music{" +
                "tittle='" + title + '\'' +
                ", authors=" + authors +
                ", album='" + album + '\'' +
                '}';
    }
}
