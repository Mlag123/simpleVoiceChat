package ru.mlagsoft.VoiceEngine;

public class VoiceIO {
    private byte[] data = new byte[1024];
    public int numBytesRead = 0;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getNumBytesRead() {
        return numBytesRead;
    }

    public void setNumBytesRead(int numBytesRead) {
        this.numBytesRead = numBytesRead;
    }
}
