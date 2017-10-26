package org.abondar.experimental.fixtureclient;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    private static String serverURI = "http://localhost:7299";

    private static ConcurrentHashMap<String, String> values;


    public static void main(String[] args) throws Exception {
        values = new ConcurrentHashMap<>();
        ThreadSource<JsonResponse> t1 = new ThreadSource<>(serverURI, values, JsonResponse.class);
        ThreadSource<XmlResponse> t2 = new ThreadSource<>(serverURI, values, XmlResponse.class);

        ThreadCompleteListenerImpl tl = new ThreadCompleteListenerImpl();

        List<ThreadSource> tsList = new ArrayList<>(Arrays.asList(t1,t2));
        t1.addListener(tl);
        t2.addListener(tl);
        tl.setTsList(tsList);

        new Thread(t2).start();
        new Thread(t1).start();

    }


}
