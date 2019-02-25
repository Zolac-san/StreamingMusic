package m.project.test.Network.UserServer;

import android.util.Log;


import org.json.JSONObject;

public class ResponseUser {


    private String typeRequest;
    private boolean error;
    private String errorType;
    private String username;
    private int id;

    public ResponseUser() {
        this.typeRequest = "";
        this.error = true;
        this.errorType = "";
        this.username = "";
        this.id = 0;
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

        }catch(Exception e){
            Log.i("Object User", "Error create via object");
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

    @Override
    public String toString() {
        return "ResponseUser{" +
                "typeRequest='" + typeRequest + '\'' +
                ", error=" + error +
                ", errorType='" + errorType + '\'' +
                ", username='" + username + '\'' +
                ", id=" + id +
                '}';
    }
}
