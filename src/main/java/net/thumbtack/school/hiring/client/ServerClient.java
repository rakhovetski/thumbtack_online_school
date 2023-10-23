package net.thumbtack.school.hiring.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.thumbtack.school.hiring.error.ErrorCode;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerClient {
    private static final Gson GSON = new GsonBuilder().create();

    private static Client createClient() {
        return ClientBuilder.newClient().register(JacksonFeature.class).register(new LoggingFeature(
                Logger.getLogger(ServerClient.class.getName()), Level.INFO, null, null
        ));
    }

    public Object get(String url, Class<?> classResponse, String token) {
        Client client = createClient();
        WebTarget myResource = client.target(url);
        Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
        Response response = builder.header("token", token).get();
        String body = response.readEntity(String.class);
        int httpCode = response.getStatus();
        Object obj;
        if(httpCode == Response.Status.OK.getStatusCode())
            obj = GSON.fromJson(body, classResponse);
        else {
            obj = GSON.fromJson(body, ErrorCode.class);
        }
        Response.ResponseBuilder responseBuilder = Response.status(httpCode)
                .entity(body)
                .header("token", token);
        responseBuilder.build();
        client.close();
        return obj;
    }

    public Object get(String url, Class<?> classResponse) {
        Client client = createClient();
        WebTarget myResource = client.target(url);
        Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
        Response response = builder.get();
        String body = response.readEntity(String.class);
        int httpCode = response.getStatus();
        Object obj;
        if(httpCode == Response.Status.OK.getStatusCode())
            obj = GSON.fromJson(body, classResponse);
        else {
            obj = GSON.fromJson(body, ErrorCode.class);
        }
        client.close();
        return obj;
    }

    public Object post(String url, Object object, Class<?> classResponse, String token) {
        Client client = createClient();
        WebTarget myResource = client.target(url);
        Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
        builder.header("token", token);
        Response response = builder.post(Entity.json(object));

        String body = response.readEntity(String.class);
        int httpCode = response.getStatus();
        Object obj;
        if (httpCode == Response.Status.OK.getStatusCode()) {
            obj = GSON.fromJson(body, classResponse);
        } else {
            obj = GSON.fromJson(body, ErrorCode.class);
        }

        Response.ResponseBuilder responseBuilder = Response.status(httpCode)
                .entity(body)
                .header("token", token);
        responseBuilder.build();

        client.close();
        return obj;
    }

    public Object delete(String url, Class<?> classResponse, String token) {
        Client client = createClient();
        WebTarget myResource = client.target(url);
        Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
        Response response = builder.header("token", token).delete();
        String body = response.readEntity(String.class);
        int httpCode = response.getStatus();
        Object obj;
        if(httpCode == Response.Status.OK.getStatusCode())
            obj = GSON.fromJson(body, classResponse);
        else {
            obj = GSON.fromJson(body, ErrorCode.class);
        }

        Response.ResponseBuilder responseBuilder = Response.status(httpCode)
                .entity(body)
                .header("token", token);
        responseBuilder.build();

        client.close();
        return obj;
    }

}

