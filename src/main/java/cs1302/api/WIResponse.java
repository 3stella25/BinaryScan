package cs1302.api;

import cs1302.api.Timezone;
import cs1302.api.Security;
import cs1302.api.Connection;

/**
 * Outermost json object that represents json response from IPWho.is api.
 */
public class WIResponse {
    String ip = "";
    boolean success = false;
    String type = "";
    String continent = "";
    String capital;
    String country = "";
    String region = "";
    String city = "";
    double latitude = 0.0;
    double longitude = 0.0;
    int postal = 0;
    Flag flag;
    Timezone timezone;
    Security security;
    Connection connection;
}
