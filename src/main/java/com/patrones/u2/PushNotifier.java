package com.patrones.u2;

public class PushNotifier implements Notifier {
    @Override
    public String channel() {
        return "PUSH";
    }

    @Override
    public void send(String recipient, String message) {
        NotificationLogger.INSTANCE.log(channel(), recipient, message);
    }
}
