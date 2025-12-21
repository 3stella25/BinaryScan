package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the LastAnalysisResult object in Json response.
 * This is all the responses for the security vendors/engines
 */
public class LastAnalysisResult {
    String category;

    @SerializedName("engine_name")
    String engineName;

    String method;
    String result;

    /**
     * For some reason cellValueFactory can't access package private fields.
     * @return String category for tableColumns in the CustomTableView class.
     */
    public String getCategory() {
        return category;
    }

    /**
     * returns engineName/security vendor.
     * @return String
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * returns the method used by the engine to analyze the given resource.
     * @return String method
     */
    public String getMethod() {
        return method;
    }

    /**
     * returns the result/verdict by one security vendor.
     * @return String result
     */
    public String getResult() {
        return result;
    }
}
