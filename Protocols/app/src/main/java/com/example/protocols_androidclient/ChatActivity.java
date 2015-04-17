package com.example.protocols_androidclient;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity implements Runnable, OnClickListener {
	protected TextView chatPartnerName;
	protected TextView chatRead;
	protected EditText chatWrite;
	protected Button btnSend;
	protected String chatPartner;
	protected String message;
	protected String source;
	protected String msg;
	protected Thread thread;
	protected Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		thread = new Thread(this);
		thread.start();
		handler = new Handler();
		chatPartnerName = (TextView) findViewById(R.id.textView1);
		chatRead = (TextView) findViewById(R.id.textView2);
		chatWrite = (EditText) findViewById(R.id.editText1);
		btnSend = (Button) findViewById(R.id.button1);
		btnSend.setOnClickListener(this);
		chatPartner = getIntent().getExtras().getString("chatPartnerName");
		handler.post(new UpdateChatPartner());
	}

	private class UpdateChatPartner implements Runnable {
		public void run() {
			chatPartnerName.setText("Chatting with " + chatPartner);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			String incomingMessage;
			try {
				incomingMessage = GlobalAccess.inFromServer.readLine();
				if (incomingMessage != null) {
					if (incomingMessage.split(",")[0].equals("chat")) {
						source = incomingMessage.split(",")[1];
						msg = incomingMessage.split(",")[2];
						handler.post(new UpdateChatWrite());
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View btn) {
		// TODO Auto-generated method stub
		message = chatWrite.getText().toString().trim();
		if (message.length() == 0) {
			handler.post(new ShowToastMissingMessage());
		} else {
			GlobalAccess.outToServer.println("chat," + chatPartner + "," 
					+ message);
			handler.post(new UpdateChatRead());
			
		}
	}
	
	private class ShowToastMissingMessage implements Runnable {
		public void run() {
			Toast.makeText(ChatActivity.this, "Please enter a message first!"
					, Toast.LENGTH_LONG).show();
		}
	}
	
	private class UpdateChatRead implements Runnable {
		public void run() {
			chatWrite.setText("");
			chatRead.append("\nYou : " + message);
			chatRead.refreshDrawableState();
		}
	}
	
	private class UpdateChatWrite implements Runnable {
		public void run() {
			chatRead.append("\n" + source + ": " + msg);
			chatRead.refreshDrawableState();
		}
	}
}