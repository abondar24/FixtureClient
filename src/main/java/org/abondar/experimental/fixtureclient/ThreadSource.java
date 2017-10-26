package org.abondar.experimental.fixtureclient;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.logging.FaultListener;
import org.apache.cxf.message.Message;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ThreadSource<T extends org.abondar.experimental.fixtureclient.Response> implements Runnable, FaultListener {
    private Boolean done = true;

    private String serverUri;

    private ConcurrentHashMap<String, String> values;

    private final Class<T> type;

    private WebClient client;

    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<>();


    public ThreadSource(String serverUri, ConcurrentHashMap<String, String> values, Class<T> type) {
        this.serverUri = serverUri;
        this.values = values;
        this.type = type;
        client = getWebClient();
    }


    public final void addListener(final ThreadCompleteListener listener) {
        listeners.add(listener);
    }

    public final void removeListener(final ThreadCompleteListener listener) {
        listeners.remove(listener);
    }


    public final void notifyListeners() {
        for (ThreadCompleteListener listener : listeners) {
            listener.notifyOfThreadComplete(this);
        }

    }


    @Override
    public void run() {
        WebClient cl = WebClient.fromClient(client);
        while (done) {

            try {

                Response response = cl.get();
                T resp;

                if (response.getStatus() == 406) {
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                         e.printStackTrace();
                    }


                } else {

                    resp = response.readEntity(type);

                    if ((type == JsonResponse.class && "done".equals(resp.getStatus())) ||
                            (type == XmlResponse.class && !resp.getStatus().isEmpty())) {

                        notifyListeners();
                        done = false;
                    } else if (!"".equals(resp.getId()) && resp.getId() != null) {

                        if (!values.containsKey(resp.getId())) {
                            values.put(resp.getId(), "orphaned");
                        } else {
                            values.remove(resp.getId());
                            sendSink("joined", resp.getId());
                        }

                    }
                }
            } catch (ResponseProcessingException ex) {
                if (ex.getCause() instanceof ConnectException) {
                    notifyListeners();
                    done = false;
                }

            }


        }

    }

    private WebClient getWebClient() {
        WebClient client = null;
        if (type == JsonResponse.class) {
            client = WebClient.create(serverUri, Arrays.asList(new JacksonJsonProvider(), new ResponseExMapper()));
            client.path("/source/a");
            client.accept(MediaType.APPLICATION_JSON);


        } else if (type == XmlResponse.class) {
            client = WebClient.create(serverUri, Arrays.asList(new JacksonJsonProvider(), new ResponseExMapper()));
            client.accept(MediaType.APPLICATION_XML);
            client.path("/source/b");
        }


        return client;
    }


    public ConcurrentHashMap<String, String> getValues() {
        return values;
    }


    @Override
    public boolean faultOccurred(Exception e, String s, Message message) {
        if (e instanceof ConnectException) {
            System.out.println("Message");
            notifyListeners();
        }


        return false;
    }

    class ResponseExMapper implements ResponseExceptionMapper {

        @Override
        public Throwable fromResponse(Response response) {
            return new RuntimeException("shit");
        }
    }

    public void sendSink(String kind, String id) {
        WebClient cli = WebClient.create(serverUri, Arrays.asList(new JacksonJsonProvider(), new ResponseExMapper()));

        cli.type(MediaType.APPLICATION_JSON);
        cli.path("/sink/a");


        SinkBody body = new SinkBody(kind, id);
        javax.ws.rs.core.Response response = cli.post(body);

        if (response.getStatus() == 406) {

            //cli.post(body);
            while (true) {
                try {
                    Thread.sleep(500l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = cli.post(body);
                if (response.getStatus() == 200) {
                    break;
                }
            }
        }
    }
}
