package cs1302.api;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import cs1302.api.VTResponse;
import cs1302.api.Data;
import cs1302.api.Attributes;
import cs1302.api.LastAnalysisResult;
import cs1302.api.TotalVotes;
import cs1302.api.KeyProperties;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * Helper class that builds the HttpResponse, client, request, and Gson.
 */
public class RequestAPIVT {

    private static final Gson GSON = new Gson();

    /**
     * Base-64 Encode the url because VirusTotal can only take encoded uris.
     * @return uri Base64 encoded
     * @param search user inputted into the textField
     */
    public static String searchVTAPI(String search) {
        System.out.println(search);
        String cut = search.trim();

        System.out.println(cut);
        String sampleURL = "https://www.geeksforgeeks.org/";
        String encodedURL = Base64.getEncoder()
            .withoutPadding()
            .encodeToString(cut.getBytes());

        System.out.println("Base64 encoded url: " + encodedURL);
        String uri = "https://www.virustotal.com/api/v3/urls/" + encodedURL;
        System.out.println("Url: " + uri);
        //Lookie
        return uri;
    }

    /**
     * Builds the http client and request using my personal api key.
     * @return String json response to be processed later
     * @param combine the resultant URI after it was base64 encoded
     */
    public static String readFromURI(String combine) throws IOException, InterruptedException {

        KeyProperties.load();
        String apiKey = KeyProperties.getVTKey();
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("Missing key");
        }

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(combine))
            .header("x-apikey", apiKey) //stored in resources directory in .properties file
            .header("accept", "application/json")
            .GET()
            .build();

        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();


        HttpResponse<String> response;
        response = client.<String>send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();

        ensureGoodResponse(response);
        return json.stripTrailing();
    }

    //Finish this
    /**
     * Use the json string generated from user input + hard-coded url to convert into VTResponse.
     * @return VTResponse
     * @param json
     */
    public static VTResponse fetch (String json) {
        return GSON.fromJson(json, VTResponse.class);
    }

    /**
     * Responsible for throwing errors that happen and throwing them to be processed in main.
     * @param <T>
     * @param response the results of the HTTP Response returned
     */
    private static <T> void ensureGoodResponse(HttpResponse<T> response) throws IOException {
        if (response.statusCode() != 200) {
            throw new IOException(response.toString());
        }
    }

}
