package main;

import java.io.*;
import java.net.*;

public class StreamManager {

    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;

    public StreamManager(Socket clientSocket) throws IOException {
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        ois = new ObjectInputStream(inputStream);
        oos = new ObjectOutputStream(outputStream);
    }
    
    public String receiveString() {
        String message = "error";
        try {
            message = ByteStream.toString(inputStream);
            System.out.println("Received: " + message);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void sendString(String message) {
        try {
            ByteStream.toStream(outputStream, message);
            System.out.println("Sent: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Object receiveObject() {
        try {           
            Object object = ois.readObject();
            System.out.println("Object " + object.getClass().getName() + " succesfully received! ");
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void sendObject(Object object) {
        try {           
            oos.flush();
            oos.writeObject(object);
            System.out.println(object.getClass().getName() + " sent! ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void closeAll() {
        try {
            this.inputStream.close();
            this.outputStream.close();
        } catch (IOException e) {
            System.err.println("Error closing the streams");
            e.printStackTrace();
        }
    }
}