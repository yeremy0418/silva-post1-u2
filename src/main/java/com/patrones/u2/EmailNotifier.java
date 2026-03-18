package com.patrones.u2;

public class EmailNotifier implements Notifier {
    @Override
    public String channel() {
        return "EMAIL";
    }

    @Override
    public void send(String recipient, String message) {
        NotificationLogger.INSTANCE.log(channel(), recipient, message);
    }
}
