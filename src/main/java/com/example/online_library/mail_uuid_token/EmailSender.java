package com.example.online_library.mail_uuid_token;

public interface EmailSender {
    void send(String to, String email);
}
