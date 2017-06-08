package fr.ihm.polytech.com.ihmtboth.model.saves;

/**
 * Created by Antoine on 5/15/2017.
 */

public class NotificationPrefs {
    private final boolean enable;

    public NotificationPrefs(boolean b) {
        this.enable = b;
    }

    public boolean isEnable() {
        return enable;
    }
}
