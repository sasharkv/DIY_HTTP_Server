package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Works");
        // we want to be able to accept the incoming request from the browser

        // we make a ServerSocket and give it a port
       final ServerSocket serverSocket = new ServerSocket(8009);

       int i = 1; // for a counter
       while(true) {
           // when we accept() we are telling the OS that we want someone to connect on this port
           final Socket socket = serverSocket.accept();
           System.out.println("Connected to " + (i++) + "client: " + socket.getRemoteSocketAddress()); // this will show us who connected
           //this above sout will only print when there is a connection from the browser in the line above
           //to have the line 16, and then 17 execute go to the browser and type in http://localhost:8009/
           // but at this point we don't have a response for the client yet.

           // socket has .getInputStream() - for receiving data from the client
           //and the .getOutputStream() - for sending information to it

           final OutputStream outputStream = socket.getOutputStream();
           // the outputStream sends bytes, but we are trying to send something simpler, like a string
           // we can do it with the PrintWriter. We instantiate it with an outputStream
           final PrintWriter printWriter = new PrintWriter(outputStream);

           // a good source for examples of HTTP responses is here: https://www.jmarshall.com/easy/http/
           printWriter.println("HTTP/1.0 200 OK");
           int size = 12 + Integer.toBinaryString(i).length();
           printWriter.println("Content-Length: " + size); //the amount of bytes (characters in this case) sent
           printWriter.println(""); //need an empty line for syntax
           printWriter.println("Hello World!" + i);
           printWriter.flush(); // need this to wait for all the data to be sent

           System.out.println("Response sent.");
           // the connection finished and the program ends. A real server, however, always stays active and able to handle incoming requests.
           // to keep it open, just put everything in a while (true) loop
           // when in this loop you need to manually close the connection with socket.close()
           socket.close();
       }
    }
}
