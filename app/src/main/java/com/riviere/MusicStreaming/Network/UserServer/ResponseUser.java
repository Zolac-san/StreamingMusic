package com.riviere.MusicStreaming.Network.UserServer;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.riviere.MusicStreaming.Music.Music;

/**
 * Classe représentant la reponse du serveur utilisateur
 */
public class ResponseUser {


    private String typeRequest;
    private boolean error;
    private String errorType;
    private String username;
    private int id;
    private List<Music> listMusic;
    private String playListName;
    private String TAG = "ReponseUserObject";

    public ResponseUser() {
        this.typeRequest = "";
        this.error = true;
        this.errorType = "";
        this.username = "";
        this.id = 0;
        this.listMusic = new ArrayList<Music>();
        this.playListName = "";
    }

    public ResponseUser(String typeRequest, boolean error) {
        this();
        this.typeRequest = typeRequest;
        this.error = error;
    }

    public ResponseUser(String typeRequest, boolean error, String errorType) {
        this(typeRequest,error);
        this.errorType = errorType;
    }

    public ResponseUser(String typeRequest, boolean error, String errorType, String username, int id) {
        this(typeRequest,error,errorType);
        this.username = username;
        this.id = id;
    }

    public ResponseUser(JSONObject obj){
        this();
        try {
            if (obj.has("typeRequest"))
                this.typeRequest = (String) obj.get("typeRequest");
            if (obj.has("error"))
                this.error = (boolean) obj.get("error");
            if (obj.has("errorType"))
                this.errorType = (String) obj.get("errorType");
            if (obj.has("username"))
                this.username = (String) obj.get("username");
            if (obj.has("id"))
                this.id = (int) obj.get("id");
            if (obj.has("musics")) {
                JSONArray allMusics = obj.getJSONArray("musics");
                for (int i = 0; i < allMusics.length() ; i++) {
                    JSONObject oneMusic = allMusics.getJSONObject(i);
                    listMusic.add(new Music(oneMusic.getString("title"), Arrays.asList(oneMusic.getString("authors").split(",")),oneMusic.getString("album")));
                }

            }
            if(obj.has("playlistName")){
                this.playListName = obj.getString("playlistName");
            }
        }catch(Exception e){
            Log.i(TAG, "Error create via object");
            System.err.println("Error on create object : "+ e.getMessage());
        }
    }

    public String getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(String typeRequest) {
        this.typeRequest = typeRequest;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Music> getListMusic() {
        return listMusic;
    }

    public void setListMusic(List<Music> listMusic) {
        this.listMusic = listMusic;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    @Override
    public String toString() {
        return "ResponseUser{" +
                "typeRequest='" + typeRequest + '\'' +
                ", error=" + error +
                ", errorType='" + errorType + '\'' +
                ", username='" + username + '\'' +
                ", id=" + id +
                ", listMusic=" + listMusic +
                '}';
    }
}
