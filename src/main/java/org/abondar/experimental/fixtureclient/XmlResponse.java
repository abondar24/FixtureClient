package org.abondar.experimental.fixtureclient;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "msg")
@XmlAccessorType(value = XmlAccessType.PROPERTY)
public class XmlResponse implements Response {


    private XmlId statusObject;


    private XmlId xmlId;


    public XmlResponse() {
    }

    public XmlResponse(String status, XmlId id) {
        this.statusObject = new XmlId();
        this.xmlId = id;
    }



    public XmlId getStatusObject() {
        return statusObject;
    }


    public void setStatusObject(XmlId statusObject) {
        this.statusObject = statusObject;
    }

    @Override
    @XmlElement(name="done",nillable = true,required = false)
    public String getStatus() {

        return getStatusObject() == null ? "": "done";
    }

    @Override
    public void setStatus(String status) {
           this.statusObject = new XmlId();
    }

    @Override
    @XmlTransient
    public String getId() {
        return xmlId.getValue();
    }

    @XmlElement(name = "id",nillable = true,required = false)
    public XmlId getXmlId() {
        return xmlId;
    }

    public void setXmlId(XmlId xmlId) {
        this.xmlId = xmlId;
    }

    @Override
    public void setId(String id) {
       XmlId xid =  new XmlId();
        xid.setValue(id);
        this.xmlId=xid;
    }

    @Override
    public String toString() {
        return "XmlResponse{" +
                "statusObject='" + statusObject + '\'' +
                ", xmlId=" + xmlId +
                '}';
    }
}
