package m.project.test.Music;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Music {
    String tittle;
    ArrayList<String> authors;
    String album;

    public Music() {
        this.tittle = "";
        this.authors = new ArrayList<String>();
        this.album = "";
    }

    public Music(String tittle, ArrayList<String> authors, String album) {
        this.tittle = tittle;
        this.authors = authors;
        this.album = album;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String toJsonString(){
        JSONObject json = new JSONObject ();
        try {
            json.put("title",this.tittle);
            json.put("authors",this.authors);
            json.put("album",this.album);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    return json.toString();
    }

    public String authorToString(){
        StringBuilder builder = new StringBuilder();
        for ( String oneAuthor : this.authors){
            builder.append("|");
            builder.append(oneAuthor);

        }
        return builder.toString().substring(1);
    }
}
