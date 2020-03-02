package nl.tudelft.oopp.demo.communication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AdminManageServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    public static void createRoom(String name, int building, boolean teacher_only, int capacity, String photos, String description, String type) {
        String params = "?name="+name+"&building="+building+"&teacher_only="+teacher_only+"&capacity="+capacity+"&photos="+photos+"&description="+description+"&type="+type;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createRoom"+params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
        }
    }

    public static void updateRoom(int id, String name, int building, boolean teacher_only, int capacity, String photos, String description, String type){
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

    public static String getAllBuildings(){
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getAllBuildings")).build();
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

    public static String getBuilding(int id){
        String params = "?id="+id;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getBuilding"+params)).build();
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

    public static void createBuilding(String name, int room_count, String address){
        String params = "?name="+name+"&room_count="+room_count+"&address="+address;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createBuilding"+params)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode() + response.body());
        }
    }

    public static void deleteBuilding(int id){
        String params = "?id="+id;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/deleteBuilding"+params)).build();
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

    public static void updateBuilding(int id, String name, int room_count, String address){
        String params = "?id="+id+"&name="+name+"&room_count="+room_count+"&address="+address;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/updateBuilding"+params)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
        if (response.statusCode() != 200){
            System.out.println("Status: " + response.statusCode() + response.body());
        }
    }

    public static String getAllUsers(){
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getAllUsers")).build();
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

    public static String getUser(String username){
        String params = "?username="+username;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getUser"+params)).build();
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

    public static void deleteUser(String username){
        String params = "?username="+username;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/deleteUser"+params)).build();
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

    public static void updateUser(String username, String password, int type){
        String params = "?username="+username+"&password="+password+"&type="+type;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/updateUser"+params)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
        if (response.statusCode() != 200){
            System.out.println("Status: " + response.statusCode() + response.body());
        }
    }

    public static void createUser(String username, String password, int type){
        String params = "?username="+username+"&password="+password+"&type="+type;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createUser"+params)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
        if (response.statusCode() != 200){
            System.out.println("Status: " + response.statusCode() + response.body());
        }
    }

    public static void createReservation(String username, int room, String date, String starting_time, String ending_time){
        String params = "?username="+username+"&room="+room+"&date="+date+"&starting_time="+starting_time+"ending_time="+ending_time;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/createReservation"+params)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
        if (response.statusCode() != 200){
            System.out.println("Status: " + response.statusCode() + response.body());
        }
    }

    public static void updateReservation(int id, int room, String date, String starting_time, String ending_time){
        String params = "?id="+id+"&room="+room+"&date="+date+"&starting_time="+starting_time+"ending_time="+ending_time;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/updateReservation"+params)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
        if (response.statusCode() != 200){
            System.out.println("Status: " + response.statusCode() + response.body());
        }
    }

    public static void deleteReservation(int id){
        String params = "?id="+id;
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).uri(URI.create("http://localhost:8080/deleteReservation"+params)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
        if (response.statusCode() != 200){
            System.out.println("Status: " + response.statusCode() + response.body());
        }
    }

    public static String getReservation(int id){
        String params = "?id="+id;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getReservation"+params)).build();
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

    public static String getAllReservations(){
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/getAllReservations")).build();
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
