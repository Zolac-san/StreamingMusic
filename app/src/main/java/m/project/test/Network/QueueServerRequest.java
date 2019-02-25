package m.project.test.Network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import m.project.test.MyApp;

public class QueueServerRequest {
    private static QueueServerRequest instance = null;

    private RequestQueue queue;

    private QueueServerRequest(){
        queue = Volley.newRequestQueue(MyApp.getAppContext());
    }

    public static QueueServerRequest getInstance(){
        if (instance == null)
            instance = new QueueServerRequest();
        return instance;
    }

    public <T> void addRequest(Request<T> request){
        queue.add(request);
    }
}
