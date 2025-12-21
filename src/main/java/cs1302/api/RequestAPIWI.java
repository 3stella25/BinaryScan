package cs1302.api;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.lang.InterruptedException;


/**
 * Deals with creating the http request, client, and handling the response for the IPWHOIS api.
 */
public class RequestAPIWI {

    private static final Gson GSON = new Gson();

    /**
     * Method called using the resolved url returned by the VirusTotal api.
     * This method takes the resolved url and converts it into an ip address
     * using the InetAddress class in the java api documentations\
     * @return String new request with api address + ip address
     * @param s the resolved url
     */
    public static String searchWIAPI(String s) throws URISyntaxException, UnknownHostException {
        System.out.println(s);
        URI domain = new URI(s);
        String host = domain.getHost();
        System.out.println("host: " + host);
        InetAddress name = InetAddress.getByName(host);
        String addy = name.getHostAddress();
        String uri = "http://ipwho.is/" + addy;
        System.out.print(uri);
        return uri;
    }

    /**
     * Creates the http request, clinet, and checks the response for any errors which are propogated
     * to be dealt in the main method (alert popup).
     * @return String json response
     * @param uri the properly formatted uri
     */
    public static String readFromURI(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .GET()
            .build();

        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

        HttpResponse<String> response;
        response = client.<String> send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();

        ensureGoodResponse(response);
        return json.stripTrailing();
    }

    /**
     * Gets the WIResponse object from the json string.
     * @param json
     * @return WIResponse
     */
    public static WIResponse fetch(String json) {
        return GSON.fromJson(json, WIResponse.class);
    }

    /**
     * Throws any errors to be handled in the main class.
     * @throws IOException
     * @param <T>
     * @param response
     */
    private static <T> void ensureGoodResponse(HttpResponse<T> response) throws IOException {
        if (response.statusCode() != 200) {
            throw new IOException(response.toString());
        }
    }
}
