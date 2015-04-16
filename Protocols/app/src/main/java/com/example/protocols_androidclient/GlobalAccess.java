package com.example.protocols_androidclient;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class GlobalAccess {
    protected static Socket clientSocket;
    protected static BufferedReader inFromServer;
    protected static PrintWriter outToServer;
}
