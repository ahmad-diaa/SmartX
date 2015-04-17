package com.example.protocols_androidclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements Runnable, OnClickListener {
	protected EditText username;
	protected Button btnRequestUsername;
	protected String otherClientNames;
	protected int clientID;
	protected Thread thread;
	protected Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		thread = new Thread(this);
		thread.start();
		handler = new Handler();
		username = (EditText) findViewById(R.id.editText1);
		btnRequestUsername = (Button) findViewById(R.id.button1);
		btnRequestUsername.setOnClickListener(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			GlobalAccess.clientSocket = new Socket("10.0.2.2", 4444);
			GlobalAccess.inFromServer = new BufferedReader(new InputStreamReader(
					GlobalAccess.clientSocket.getInputStream()));
			GlobalAccess.outToServer = new PrintWriter(GlobalAccess.clientSocket.getOutputStream(), true);
			while (true) {
				String incomingMessage = GlobalAccess.inFromServer.readLine();
				if (incomingMessage != null) {
					if (incomingMessage.split(",")[0].equals("requestedUsername")) {
						if (incomingMessage.split(",")[1].equals("invalid")) {
							handler.post(new ViewToastInvalidUsername());
						} else {
							handler.post(new ViewToastValidUsername());
							otherClientNames = incomingMessage.split(",")[2];
							Intent i = new Intent(MainActivity.this, SelectPlayer.class);
							Bundle b = new Bundle();
							b.putString("otherClientNames", otherClientNames);
                            b.putString("client1", username.getText().toString().trim());
							i.putExtras(b);
							finish();
							startActivity(i);
							break;
						}
					}
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View btn) {
		// TODO Auto-generated method stub
		if (username.getText().toString().trim().length() == 0) {
			handler.post(new ViewToastMissingField());
		}
		else {
			GlobalAccess.outToServer.println("requestedUsername," 
					+ username.getText().toString().trim());
		}
	}
	
	private class ViewToastMissingField implements Runnable {
		public void run() {
			Toast.makeText(MainActivity.this, "Please enter your requested username!"
					, Toast.LENGTH_LONG).show();
		}
	}
	
	private class ViewToastInvalidUsername implements Runnable {
		public void run() {
			Toast.makeText(MainActivity.this, "Invalid username. Please request another username!"
					, Toast.LENGTH_LONG).show();
		}
	}
	
	private class ViewToastValidUsername implements Runnable {
		public void run() {
			Toast.makeText(MainActivity.this, "Successful username!"
					, Toast.LENGTH_LONG).show();
		}
	}
}