package com.b2la.hnb.util;

import com.google.gson.Gson;

import java.util.prefs.Preferences;

public class Stockage {
    private static final Preferences prefs = Preferences.userNodeForPackage(Stockage.class);
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_ID = "id";

    private String username;
    private String fonction;
    private String id;

    public Stockage() {
        this.username = prefs.get(KEY_USERNAME, username);
        this.fonction = prefs.get(KEY_ROLE, fonction);
        this.id= prefs.get(KEY_ID, id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        prefs.put(KEY_USERNAME, username);
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
        prefs.put(KEY_ROLE, fonction);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        prefs.put(KEY_ID, id);
    }

    public static void reset() {
        prefs.remove(KEY_USERNAME);
        prefs.remove(KEY_ROLE);
        prefs.remove(KEY_ID);
    }

}
