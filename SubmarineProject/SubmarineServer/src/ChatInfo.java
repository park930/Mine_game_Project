public class ChatInfo {
    private String content;
    private String writerName;
    private long writerId;
    private long roomId;
    private String colorString;

    public ChatInfo(String content, String writerName, long writerId, long roomId, String color) {
        this.content = content;
        this.writerName = writerName;
        this.writerId = writerId;
        this.roomId = roomId;
        this.colorString = color;
    }

    public void setColor(String color) {
        this.colorString = color;
    }

    public String getColor() {
        return colorString;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public void setWriterId(long writerId) {
        this.writerId = writerId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getWriterName() {
        return writerName;
    }

    public long getWriterId() {
        return writerId;
    }

    public String getContent() {
        return content;
    }


    public long getRoomId() {
        return roomId;
    }
}
