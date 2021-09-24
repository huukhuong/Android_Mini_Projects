package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chatbot.Adapter.ChatAdapter;
import com.example.chatbot.Model.Chat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatView;
    private ChatAdapter chatAdapter;
    private ArrayList chatList;
    private TextView txtMessage;
    private ImageButton btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        chatList = new ArrayList();

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(MainActivity.this,
                        RecyclerView.VERTICAL,
                        false
                );
        chatView = findViewById(R.id.chatView);
        chatView.setLayoutManager(linearLayoutManager);

        chatAdapter = new ChatAdapter(MainActivity.this);
        chatAdapter.setData(chatList);
        chatView.setAdapter(chatAdapter);

        txtMessage = findViewById(R.id.txtMessage);
        btnSend = findViewById(R.id.btnSend);
    }

    private void addEvents() {
        btnSend.setOnClickListener(view -> processSend());

        txtMessage.setOnEditorActionListener(
                (view, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                            actionId == EditorInfo.IME_ACTION_DONE ||
                            event != null &&
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (event == null || !event.isShiftPressed()) {
                            processSend();
                            return true;
                        }
                    }
                    return false;
                }
        );
    }

    private void processSend() {
        if (txtMessage.getText().toString().trim().length() == 0) {
            return;
        }
        chatList.add(new Chat(txtMessage.getText().toString(), Chat.CLIENT_TYPE));
        chatAdapter.notifyDataSetChanged();

        ChatTask task = new ChatTask();
        task.execute();

        txtMessage.setText("");

        scrollBotoomChatView();
    }

    private void scrollBotoomChatView() {
        chatView.scrollToPosition(chatList.size() - 1);
    }

    class ChatTask extends AsyncTask<Void, Void, String> {

        private Chat chat = new Chat("...", Chat.BOT_TYPE);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            chatList.add(chat);
            chatAdapter.notifyDataSetChanged();
            scrollBotoomChatView();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            chatList.remove(chat);
            chatAdapter.notifyDataSetChanged();

            try {
                JSONObject object = new JSONObject(s);
                Chat chat = new Chat();

                if (object.has("success")) {
                    String message = object.getString("success");
                    if (message.length() == 0) {
                        message = "Xin lỗi, tôi không hiểu bạn nói gì";
                    }
                    chat.setMessage(message);
                    chat.setType(Chat.BOT_TYPE);
                }

                chatList.add(chat);
                chatAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }
            scrollBotoomChatView();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String text = txtMessage.getText().toString();
            String link = "https://simsumi.herokuapp.com/api?text=" + text + "&lang=vi";
            try {
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();

                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }

                return stringBuilder.toString();
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }
            return null;
        }
    }

}