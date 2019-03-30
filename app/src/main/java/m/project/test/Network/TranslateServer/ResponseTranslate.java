package m.project.test.Network.TranslateServer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseTranslate {
    private boolean error;
    private String command;
    private boolean foundMusic;
    private String title;
    private ArrayList<String> authors;
    private String album;
    private String namePlaylist;
    private String text;
    private String service;
    private ArrayList<String> listCommand;


    public ResponseTranslate() {
        this.error = true;
        this.text = "";
        this.service = "";
        this.command = "";
        this.foundMusic = false;
        this.title = "";
        this.authors = new ArrayList<String>();
        this.album = "";
        this.namePlaylist = "";
        this.listCommand = new ArrayList<String>();
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
        Log.i("Object ", "create via object");
        try {
            if (obj.has("text")) this.text = (String) obj.get("text");
            if (obj.has("service")) this.service = (String) obj.get("service");
            if (obj.has("listCommand")){
                JSONArray listCommandJSON = obj.getJSONArray("listCommand");
                for(int i=0; i< listCommandJSON.length();i++ ){
                    this.listCommand.add( listCommandJSON.getString(i));
                }
            }
            if (obj.has("error")) {
                this.error = (boolean) obj.get("error");
                if (!this.error && obj.has("command")) {
                    this.command = (String) obj.get("command");
                    Log.i("finish : ",this.command);
                    Log.i("finish : ",(obj.has("foundMusic")?"true":"false"));
                    if (this.command.equals("jouer")  && obj.has("foundMusic")){
                        this.foundMusic = (boolean) obj.get("foundMusic");
                        Log.i("found Music : ",( this.foundMusic ?"true":"false"));
                        if(this.foundMusic){
                            this.title = (String) obj.get("title");

                            JSONArray listAuthors =obj.getJSONArray("authors");
                            for(int i=0; i<listAuthors.length();i++ ){
                                this.addAuthor( listAuthors.getString(i));
                            }

                            this.album = (String) obj.get("album");
                        }
                    }
                }
            }
            if (obj.has("namePlaylist")) this.namePlaylist = (String) obj.get("namePlaylist");
            Log.i("Object finish : ",this.toString());
        }catch(Exception e){
            Log.i("Object ", "Error create via object");
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


    public String getNamePlaylist() {
        return namePlaylist;
    }

    public void setNamePlaylist(String namePlaylist) {
        this.namePlaylist = namePlaylist;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public ArrayList<String> getListCommand() {
        return listCommand;
    }

    public void setListCommand(ArrayList<String> listCommand) {
        this.listCommand = listCommand;
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
                ", namePlaylist='" + namePlaylist + '\'' +
                ", text='" + text + '\'' +
                ", service='" + service + '\'' +
                ", listCommand=" + listCommand +
                '}';
    }
}
