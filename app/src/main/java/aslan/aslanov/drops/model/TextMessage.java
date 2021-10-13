package aslan.aslanov.drops.model;

import java.util.Date;

public class TextMessage {
    private String messageTitle;
    private Date date;
    private int writerType;
    private String writerName;
    private String writerEmail;

    public TextMessage() {

    }
    public TextMessage(String messageTitle, Date date, int writerType, String writerName, String writerEmail) {
        this.messageTitle = messageTitle;
        this.date = date;
        this.writerType = writerType;
        this.writerName = writerName;
        this.writerEmail = writerEmail;
    }
    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getWriterType() {
        return writerType;
    }

    public void setWriterType(Integer writerType) {
        this.writerType = writerType;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

}
