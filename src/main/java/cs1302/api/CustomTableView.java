package cs1302.api;

import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import cs1302.api.LastAnalysisResult;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import javafx.scene.control.ProgressBar;

/**
 * Represents a custom class that holds all the LastAnalysisResults.
 * Table with all the fields that each security vendor should return.
 */
public class CustomTableView extends TableView<LastAnalysisResult> {

    TableColumn<LastAnalysisResult, String> engineC;
    TableColumn<LastAnalysisResult, String> categoryC;
    TableColumn<LastAnalysisResult, String> methodC;
    TableColumn<LastAnalysisResult, String> resultC;
    private ProgressBar progressBar;

    /**
     * Initializes the column names and results are default empty.
     * {@code customTableView}
     * @param progress progressBar passed in by api app so it can actually run
     */
    public CustomTableView(ProgressBar progress) {
        super();
        this.progressBar = progress;
        engineC = new TableColumn<>("Engine");
        engineC.setCellValueFactory(new PropertyValueFactory<>("engineName"));
        categoryC = new TableColumn<>("Category");
        categoryC.setCellValueFactory(new PropertyValueFactory<>("category"));
        methodC = new TableColumn<>("Method");
        methodC.setCellValueFactory(new PropertyValueFactory<>("method"));
        resultC = new TableColumn<>("Result");
        resultC.setCellValueFactory(new PropertyValueFactory<>("result"));

        this.getColumns().addAll(engineC, categoryC, methodC, resultC);
        setPlaceholder(new Label("Enter a URL to see resultss"));

    }

    /**
     * Add a row that represents each LastAnalysis result returned by the api.
     * @param bob ArrayList of lastanalysisresults returned
     */
    public void updateTable(ArrayList<LastAnalysisResult> bob) {
        for (LastAnalysisResult res: bob) {
            this.getItems().addAll(res);
        }
    }

    /**
     * update the progress bar.
     * @param progress mathematically calculated in loop in ApiApp
     */
    public void updateProgress(double progress) {
        progressBar.setProgress(progress);
    }



}
