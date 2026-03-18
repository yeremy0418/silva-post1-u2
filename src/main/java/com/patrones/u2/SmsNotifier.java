package com.patrones.u2;

public class SmsNotifier implements Notifier {
    @Override
    public String channel() {
        return "SMS";
    }

    @Override
    public void send(String recipient, String message) {
        NotificationLogger.INSTANCE.log(channel(), recipient, message);
    }
}
