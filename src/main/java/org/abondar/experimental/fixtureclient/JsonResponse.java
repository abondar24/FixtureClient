package org.abondar.experimental.fixtureclient;

public class JsonResponse implements Response {

    private String status;

    private String id;

    public JsonResponse(){}

    public JsonResponse(String status, String value) {
        this.status = status;
        this.id = value;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String statusObject) {
        this.status = statusObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "status='" + status + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
