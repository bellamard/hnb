package com.b2la.hnb.connexion;

import com.b2la.hnb.models.Bilan;
import com.b2la.hnb.models.Depense;
import com.b2la.hnb.models.Facturation;
import com.b2la.hnb.models.Produit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class Api {
    public static List<Produit> listProduit(){
        try {
            URL url= new URL("https://hnb.b2la.online/Produits");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            int responseCode=con.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response= new StringBuilder();
                String inputLine;
                while ((inputLine= in.readLine())!= null){
                    response.append(inputLine);
                }
                in.close();
                Gson json= new GsonBuilder().create();
                Type listeType= new TypeToken<List<Produit>>(){}.getType();
                return json.fromJson(response.toString(), listeType);
            }
            con.disconnect();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public static List<Produit> addList(List<Produit> produit){
        try {
            URL url= new URL("https://hnb.b2la.online/produits");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            Gson json= new Gson();
            String jsInput= json.toJson(produit);
            try(OutputStream os= con.getOutputStream()){
                byte[] input=jsInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
                os.flush();
            }

            int responseCode=con.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response= new StringBuilder();
                String inputLine;
                while ((inputLine= in.readLine())!= null){
                    response.append(inputLine);
                }
                in.close();
                return Collections.singletonList(json.fromJson(response.toString(), Produit.class));
            }
            con.disconnect();
            return null;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Facturation> listFacturation(){
        try {
            URL url= new URL("https://hnb.b2la.online/Produits");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            int responseCode=con.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response= new StringBuilder();
                String inputLine;
                while ((inputLine= in.readLine())!= null){
                    response.append(inputLine);
                }
                in.close();
                Gson json= new GsonBuilder().create();
                Type listeType= new TypeToken<List<Produit>>(){}.getType();
                return json.fromJson(response.toString(), listeType);
            }
            con.disconnect();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public static List<Facturation> addFacturation(List<Facturation> facturation){
        try {
            URL url= new URL("https://hnb.b2la.online/facturation");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            Gson json= new Gson();
            String jsInput= json.toJson(facturation);
            try(OutputStream os= con.getOutputStream()){
                byte[] input=jsInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
                os.flush();
            }

            int responseCode=con.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response= new StringBuilder();
                String inputLine;
                while ((inputLine= in.readLine())!= null){
                    response.append(inputLine);
                }
                in.close();
                return Collections.singletonList(json.fromJson(response.toString(), Facturation.class));
            }
            con.disconnect();
            return null;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Depense> listDepense(){
        try {
            URL url= new URL("https://hnb.b2la.online/depenses");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            int responseCode=con.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response= new StringBuilder();
                String inputLine;
                while ((inputLine= in.readLine())!= null){
                    response.append(inputLine);
                }
                in.close();
                Gson json= new GsonBuilder().create();
                Type listeType= new TypeToken<List<Depense>>(){}.getType();
                return json.fromJson(response.toString(), listeType);
            }
            con.disconnect();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public static List<Depense> addDepenses(List<Depense> depenses){
        try {
            URL url= new URL("https://hnb.b2la.online/depense");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            Gson json= new Gson();
            String jsInput= json.toJson(depenses);
            try(OutputStream os= con.getOutputStream()){
                byte[] input=jsInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
                os.flush();
            }

            int responseCode=con.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response= new StringBuilder();
                String inputLine;
                while ((inputLine= in.readLine())!= null){
                    response.append(inputLine);
                }
                in.close();
                return Collections.singletonList(json.fromJson(response.toString(), Depense.class));
            }
            con.disconnect();
            return null;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Bilan> listBilan(){
        try {
            URL url= new URL("https://hnb.b2la.online/bilan");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            int responseCode=con.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response= new StringBuilder();
                String inputLine;
                while ((inputLine= in.readLine())!= null){
                    response.append(inputLine);
                }
                in.close();
                Gson json= new GsonBuilder().create();
                Type listeType= new TypeToken<List<Bilan>>(){}.getType();
                return json.fromJson(response.toString(), listeType);
            }
            con.disconnect();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public static List<Bilan> addBilan(List<Bilan> Bilan){
        try {
            URL url= new URL("https://hnb.b2la.online/bilan");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            Gson json= new Gson();
            String jsInput= json.toJson(depenses);
            try(OutputStream os= con.getOutputStream()){
                byte[] input=jsInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
                os.flush();
            }

            int responseCode=con.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response= new StringBuilder();
                String inputLine;
                while ((inputLine= in.readLine())!= null){
                    response.append(inputLine);
                }
                in.close();
                return Collections.singletonList(json.fromJson(response.toString(), Bilan.class));
            }
            con.disconnect();
            return null;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
