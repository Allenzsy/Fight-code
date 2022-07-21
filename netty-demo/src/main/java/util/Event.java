package util;

import java.nio.channels.SelectionKey;

/**
 * @Author Allenzsy
 * @Date 2022/7/15 19:36
 * @Description:
 */
public enum Event {
    OP_READ(1),
    OP_WRITE(4),
    OP_CONNECT(8),
    OP_ACCEPT(16);

    private String eventName;
    Event(int p) {
        switch (p){
            case 1: eventName = "OP_READ";break;
            case 4: eventName = "OP_WRITE";break;
            case 8: eventName = "OP_CONNECT";break;
            case 16: eventName = "OP_ACCEPT";break;
            default: eventName = "UNKNOWN";
        }
    }
    public String getEventName() {
        return eventName;
    }

    public static String eventName(SelectionKey key) {

        if(key.isAcceptable()) {
            return "OP_ACCEPT";
        } else if(key.isConnectable()){
            return "OP_CONNECT";
        } else if(key.isReadable()) {
            return "OP_READ";
        } else if(key.isWritable()) {
            return "OP_WRITE";
        } else {
            return "UNKNOWN";
        }
    }

}
