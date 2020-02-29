package nl.tudelft.oopp.demo.communication;

import javax.print.DocFlavor;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AdminManageRoomServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    public static String createRoom(String name, int building, boolean teacher_only, int capacity, String photos, String description, String type) {
        String params = "?name="+name+"&building="+building+"&teacher_only="+teacher_only+"&capacity="+capacity+"&photos="+photos+"&description="+description+"&type="+type;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createRoom"+params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
        }
        return response.body();
    }

    public static String updateRoom(int id, String name, int building, boolean teacher_only, int capacity, String photos, String description, String type){
        String params = "?id="+id+"&name="+name+"&building="+building+"&teacher_only="+teacher_only+"&capacity="+capacity+"&photos="+photos+"&description="+description+"&type="+type;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/updateRoom"+params)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
        if (response.statusCode() != 200){
            System.out.println("Status: " + response.statusCode() + response.body());
        }
        return response.body();
    }

    public static void deleteRoom(int id){
        String params = "?id="+id;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/deleteRoom"+params)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
        if(response.statusCode() != 200){
            System.out.println("Status: "+ response.statusCode() + response.body());
        }
    }

    public static String getRoom(int id){
        String params = "?id="+id;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getRoom"+params)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
        if(response.statusCode() != 200){
            System.out.println("Status: "+ response.statusCode() + response.body());
        }
        return response.body();
    }

    public static String getAllRooms(){
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getAllRooms")).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
        if(response.statusCode() != 200){
            System.out.println("Status: "+ response.statusCode() + response.body());
        }
        return response.body();
    }
}
