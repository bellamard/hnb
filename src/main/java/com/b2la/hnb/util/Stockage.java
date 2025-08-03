package com.b2la.hnb.util;

import com.google.gson.Gson;

import java.util.prefs.Preferences;

public class Stockage {
    private static final Preferences prefs = Preferences.userNodeForPackage(Stockage.class);
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";

    private String username;
    private String fonction;

    public Stockage() {
        this.username = prefs.get(KEY_USERNAME, username);
        this.fonction = prefs.get(KEY_ROLE, fonction);
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

    public void reset() {
        prefs.remove(KEY_USERNAME);
        prefs.remove(KEY_ROLE);
    }

}
