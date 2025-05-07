package com.edsoncasta.principal;

import com.edsoncasta.screenmatch.modelos.Titulo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalConBusqueda {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Escribe el nombre de la pelicula: ");
        var busqueda = scanner.nextLine();

        String claveApi = "2d783b4f";
        String url = "http://www.omdbapi.com/?t=" + busqueda + "&apikey=" + claveApi;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            TituloOmdb miTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            System.out.println(miTituloOmdb);
            Titulo miTitulo =  new Titulo(miTituloOmdb);
            System.out.println("Titulo ya convertido: " + miTitulo);
        } catch (NumberFormatException error){
            System.out.println("Ocurrio un error: ");
            System.out.println(error.getMessage());
        } catch (IllegalArgumentException error){
            System.out.println("Error en la URI, Verifique la dirección.");
        }
        System.out.println("Finalizo la ejecucion del programa!");
    }
}
