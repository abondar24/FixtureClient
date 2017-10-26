package org.abondar.experimental.fixtureclient;



import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

public class XmlId {


    private String value;

    @XmlAttribute(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "XmlId{" +
                "value='" + value + '\'' +
                '}';
    }
}
