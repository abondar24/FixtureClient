package org.abondar.experimental.fixtureclient;

import java.util.*;

public class ThreadCompleteListenerImpl implements ThreadCompleteListener {

    private List<ThreadSource> tsList = new ArrayList<>();


    public ThreadCompleteListenerImpl() {
    }

    @Override
    public void notifyOfThreadComplete(ThreadSource thread) {
        tsList.remove(thread);
        if (tsList.isEmpty()) {
            thread.getValues().forEach((k, v) -> {
                thread.sendSink((String) v, (String) k);
            });

        }
    }

    public void setTsList(List<ThreadSource> tsList) {
        this.tsList = tsList;
    }


}
