package com.bsoft.mob.pivas.service.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by huangy on 2015-05-06.
 */
public class SocketClient {

    public static final int DEFAULT_SO_TIMEOUT = 3000;

    public static final int DEFAULT_TIMEOUT = 3000;


    private Socket clientSoc; // Client Socket

    @SuppressWarnings("unused")
    private int port; // Server Port

    private String message; // Message For The Server

    @SuppressWarnings("unused")
    private String serverIP; // Server IP


    // Constructor That Creates The Client Socket
    public SocketClient(int port, String message, String serverIP)  {

        this.port = port;
        this.message = message;
        this.serverIP = serverIP;

    }


    // Send Message To Server
    public String writeToServer() throws UnknownHostException, IOException {


        // Initializing The Client Socket
        //InetAddress addr = InetAddress.getByName(serverIP);
        //this.clientSoc = new Socket();
        //clientSoc.setSoTimeout(DEFAULT_SO_TIMEOUT);
        //clientSoc.connect(new InetSocketAddress(addr, port), DEFAULT_TIMEOUT);

        this.clientSoc = new Socket(serverIP, port);


        PrintWriter request = new PrintWriter(clientSoc.getOutputStream(), true); // Create request

        request.println(this.message); // Send Message To Server

        String responseStr = this.readFromServer(); // Read Message From Server

        request.close(); // Closing ComplexRequest

        this.socketClose(); // Closing Client Socket

        return responseStr;

    }


    // Read From Server
    private String readFromServer() throws IOException {

        InputStreamReader inputRead = new InputStreamReader(this.clientSoc.getInputStream(),"GBK"); // Create inputRead

        BufferedReader request = new BufferedReader(inputRead); // Create request

        char[] inputChars = new char[100];

        request.read(inputChars);

        String line = new String(inputChars); // Buffer

        return line;

    }


    // Close the socket
    private void socketClose() throws IOException {

        this.clientSoc.close();

    }


    // Main
    public static void main(String[] args) throws UnknownHostException, IOException {

        int port = 2000; // Communication Port

        String message = "with ns"; // Message To Forward

        String serverIP = "172.16.108.20"; // Server Address

        // Client Object Instantiation
        try {
            SocketClient cl = new SocketClient(port, message, serverIP);
            cl.writeToServer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
