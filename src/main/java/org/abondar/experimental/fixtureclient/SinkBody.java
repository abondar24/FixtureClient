package org.abondar.experimental.fixtureclient;

import javax.xml.bind.annotation.XmlElement;


public class SinkBody {


    private String kind;

    private String id;

    public SinkBody(){

    }

    public SinkBody(String kind, String id) {
        this.kind = kind;
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SinkBody{" +
                "kind='" + kind + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
