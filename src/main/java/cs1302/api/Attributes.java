package cs1302.api;

import java.util.Map;
import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

/**
 * Models the json object.
 * Nested class within VTResponse
 */
public class Attributes {
    String url;

    @SerializedName("last_analysis_results")
    Map<String, LastAnalysisResult> res;

    Map<String, String> categories;

    @SerializedName("last_analysis_stats")
    LastAnalysisStats stats;


    int reputation;

    @SerializedName("total_votes")
    TotalVotes total;

    @SerializedName("times_submitted")
    int submissions;

    @SerializedName("redirection_chain")
    ArrayList<String> redirects;
}
