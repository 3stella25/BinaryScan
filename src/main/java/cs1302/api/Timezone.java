package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the timezone object in the json response for IPWHOIS api.
 */
public class Timezone {
    String id;
    String abbr;
    String utc;
    @SerializedName("current_time")
    String currentTime;
}
