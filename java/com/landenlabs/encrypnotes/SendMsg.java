package com.landenlabs.encrypnotes;

public interface SendMsg {
    public static final int MSG_FAIL = 0;
    public static final int MSG_OKAY = 1;
    
    void send(int msgNum);
}
