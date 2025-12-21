package cs1302.api;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
//New Components
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.Node;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import java.util.Map;
//Alert stuff
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//Events + Event Handlers
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
//GSON imports
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.Runnable;
//Custom Component Imports
import cs1302.api.ViewSummary;
import cs1302.api.CustomTableView;
import cs1302.api.WIResponse;
import cs1302.api.WIView;

/**
 * My ApiApp combines the responses of the APIs IPWhoIs and VirusTotal
 * In the javafx application, the user will search for a resource on the web.
 * That request will then be sent to urlscan.io which will return more information about it. That
 * information (in JSON format) will then be used to prompt the virustotal API, which will give the
 * user feedback about the safety of the domain, URL, and detect malware/breaches associated
 * witht the entered url
 */
public class ApiApp extends Application {
    Stage stage;
    Scene scene;
    VBox root;

    HBox input;
    Label enterUrl;
    TextField search;
    Button scan;
    ProgressBar progress;
    String uri;
    HBox resView;

    //GridPane stuff
    ViewSummary summary;
    CustomTableView res;
    WIView view;
    VBox labelsSummary;

    WIResponse response2;


    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        root = new VBox();
        root.setPadding(new Insets(10));
        enterUrl = new Label("Enter URL:");
        enterUrl.setId("enter-Url");
        summary = new ViewSummary();
        view = new WIView();

        progress = new ProgressBar(0);
        progress.setPrefSize(400, 40);
        res = new CustomTableView(progress);
        res.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        view.setPrefHeight(300);
        search = new TextField();
        search.setPrefWidth(300);


    } // ApiApp

    /**
     * Helper method to create an {@code Label} object.
     * This is to save space haha
     * @param text the stuff in the label
     * @param width
     * @param height
     * @param style calls the local label object's setStyle
     * @param align user passes in an alignment for the text in the label
     * @return Label
     */
    private Label labelCreator(String text, int width, int height, String style, Pos align) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setPrefHeight(height);
        label.setStyle(style);
        label.setAlignment(align);
        return label;
    }

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;

        scan = new Button("Analyze");
        EventHandler<ActionEvent> scanHandler = event -> {
            scan.setText("Analyzing...");
            scan.setDisable(true);
            myTaskMethod();
            scan.setDisable(false);
            scan.setText("Analyze");
        };
        scan.setOnAction(scanHandler);

        input = new HBox(enterUrl, search, scan);
        //        input.setAlignment(Pos.LEFT);
        input.setSpacing(25);
        VBox gL = view.getLabels();
        gL.setPrefWidth(200);
        resView = new HBox(summary,gL);
        resView.setSpacing(10);
        resView.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(gL, Priority.ALWAYS);
        view.setPrefWidth(200);
        HBox resi = new HBox(res, view);


        // HBox.setHgrow(view, Priority.SOMETIMES);
        view.setMaxWidth(200);
        HBox.setHgrow(summary, Priority.ALWAYS);
        HBox.setHgrow(res, Priority.ALWAYS);
        view.setMaxWidth(Double.MAX_VALUE);
        root.getChildren().addAll(input, progress, resView, resi);

        // setup stage
        this.scene = new Scene(this.root, 850, 600);
        String stylesheet = "file:resources/theme-apiApp.css"; //access the css sheet
        scene.getStylesheets().add(stylesheet);
        stage.setTitle("BinaryScan- A GreyNoise + VirusTotal url report generator");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
    } // start

    /**
     * Helper method to shorten code.
     * @param title to set the title of the alert
     * @param response to set the content
     */
    private void showError(String title, String response) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(response);
        a.setHeight(400);
        a.setWidth(600);
        a.show();
    }

    /**
     * Helper method that handles calling the methods for calling the api, updating the progress.
     * bar, alert/error handling, custom classes, etc.
     */
    private void myTaskMethod() {
        Thread load = new Thread(() -> {
            response2 = null;
            try {

                Platform.runLater(() -> res.updateProgress(0.0));
                String query = search.getText();
                String uri = RequestAPIVT.searchVTAPI(query);
                Platform.runLater(() -> res.updateProgress(0.10));
                String json = RequestAPIVT.readFromURI(uri);
                Platform.runLater(() -> res.updateProgress(0.25));
                VTResponse response = RequestAPIVT.fetch(json);
                Platform.runLater(() -> res.updateProgress(0.4));
                Map<String, LastAnalysisResult> responses = response.data.attributes.res;
                ArrayList<LastAnalysisResult> engines = new ArrayList<>(responses.values());
                int total = engines.size();
                String resolvedURL = response.data.attributes.url;
                int reputed = response.data.attributes.reputation;
                Map<String, String> cats = response.data.attributes.categories;
                ArrayList<String> conglom = new ArrayList<>(cats.values());
                int subs = response.data.attributes.submissions;
                int bad = response.data.attributes.stats.malicious;
                int sus = response.data.attributes.stats.suspicious;
                String malic = bad + "/" + total;
                String sussy = sus + "/" + total;
                try {
                    String uri2 = RequestAPIWI.searchWIAPI(resolvedURL);
                    Platform.runLater(() -> res.updateProgress(0.55));
                    String json2 = RequestAPIWI.readFromURI(uri2);
                    Platform.runLater(() -> res.updateProgress(0.7));
                    response2 = RequestAPIWI.fetch(json2);
                    Platform.runLater(() -> res.updateProgress(0.8));

                } catch (Exception exc) {
                    Platform.runLater(() -> {
                        showError("IPWhoIs error", "/n Error Stack: " + exc);
                        res.updateProgress(1.0);
                    });
                    return;
                }
                Platform.runLater(() -> {
                    summary.updateGrid(resolvedURL, reputed, conglom, subs, malic, sussy);
                    res.updateTable(engines);
                    res.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    if (response2 != null) {
                        view.update(response2);
                    }
                    res.updateProgress(1.0);
                });

            } catch (Exception exc) {
                Platform.runLater(() -> {
                    showError("Error: URL: " + search.getText(), "\n Exception:" + exc.toString());
                    res.updateProgress(0.0);
                });
            }
        });
        load.start();

    }
} // ApiApp
