package m.project.test.Network.TranslateServer;

import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseTranslate {
    private boolean error;
    private String command;
    private boolean foundMusic;
    private String title;
    private ArrayList<String> authors;
    private String album;

    public ResponseTranslate() {
        this.error = true;
        this.command = "";
        this.foundMusic = false;
        this.title = "";
        this.authors = new ArrayList<String>();
        this.album = "";
    }

    public ResponseTranslate(boolean error) {
        this();
        this.error = error;
    }

    public ResponseTranslate(boolean error, String command) {
        this(error);
        this.command = command;
    }

    public ResponseTranslate(boolean error, String command, boolean foundMusic) {
        this(error,command);
        this.foundMusic = foundMusic;
    }

    public ResponseTranslate(boolean error, String command, boolean foundMusic, String title, ArrayList<String> authors, String album) {
        this(error,command,foundMusic);
        this.title = title;
        this.authors = authors;
        this.album = album;
    }


    public ResponseTranslate(JSONObject obj){
        this();
        try {
            if (obj.has("error")) {
                this.error = (boolean) obj.get("error");
                if (!this.error && obj.has("command")) {
                    this.command = (String) obj.get("command");
                    if (this.command == "jouer" && obj.has("foundMusic")){
                        this.foundMusic = (boolean) obj.get("foundMusic");
                        if(this.foundMusic){
                            this.title = (String) obj.get("title");
                            this.album = (String) obj.get("album");
                            for(Object o : (Object[])obj.get("authors")){
                                this.addAuthor((String) o);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            System.err.println("Error on create object : "+ e.getMessage());
        }
    }


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean isFoundMusic() {
        return foundMusic;
    }

    public void setFoundMusic(boolean foundMusic) {
        this.foundMusic = foundMusic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public void addAuthor(String author){
        this.authors.add(author);
    }

    public void eraseAuthors(){
        this.authors.clear();
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "ResponseTranslate{" +
                "error=" + error +
                ", command='" + command + '\'' +
                ", foundMusic=" + foundMusic +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", album='" + album + '\'' +
                '}';
    }
}
