package BasicClasses.Requests;

import java.sql.Date;
import java.util.Calendar;

public abstract class Request {
    private String description;
    private String requesterId;
    private String handlerId;
    private String requestType;
    private Date requestedDate;
    private Date handledDate;

    public Request(String requestType,String requesterId){
        this.requestType = requestType;
        this.requesterId = requesterId;
        this.requestedDate = getCurrentDate();
    }

    public String getDescription() {
        return description;
    }

    public Date getHandledDate() {
        return handledDate;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public String getHandlerId() {
        return handlerId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHandledDate(Date handledDate) {
        this.handledDate = handledDate;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    public static Date getCurrentDate(){
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        return date;
    }
}
