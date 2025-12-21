package cs1302.api;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import java.util.ArrayList;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

/**
 * Custom class that represents the results from VirusTotal API.
 * Contains information like it's resolved URL, reputation score, etc.
 */
public class ViewSummary extends GridPane {

    Label resolvedURL;
    Label reputation;
    Label categories;
    Label timesSubmitted;
    Label flags;

    /**
     * Organizes fields in a gridpane so it's neater.
     * Sets default values before api is called
     * {@code ViewSummary}
     */
    public ViewSummary() {
        super();
        setHgap(10);
        setVgap(10);
        this.setPadding(new Insets(10));

        ColumnConstraints c = new ColumnConstraints();
        c.setHgrow(Priority.NEVER);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        this.getColumnConstraints().addAll(c, c2);

        resolvedURL = new Label("null");
        reputation = new Label("null");
        categories = new Label("null");
        timesSubmitted = new Label("null");
        flags = new Label("null");


        this.add(new Label("Resolved URL: "), 0, 0);
        this.add(resolvedURL, 1, 0);
        this.add(new Label("Reputation Score: "), 0, 1);
        this.add(reputation, 1, 1);
        this.add(new Label("Categories: "), 0, 2);
        this.add(categories, 1, 2);
        this.add(new Label("Times submitted: "), 0, 3);
        this.add(timesSubmitted, 1, 3);
        this.add(new Label("Vendors flagged: "), 0, 4);
        this.add(flags, 1, 4);
    }

    /**
     * Updates the ViewSummary with info that is retrieved in ApiApp.
     * @param p is the resolvedURl
     * @param d is the reputation score
     * @param c is the categories determined for a url by various security vendors
     * @param subs the number of times a search was a submitted for a particular url
     * @param b is the string representation of how many vendors flagged the url out of all
     * @param s
     */
    public void updateGrid(
        String p, int d, ArrayList<String> c, int subs, String b, String s) {

        this.getChildren().clear();
        resolvedURL.setText(p);
        if (d >= 0) {
            reputation.setTextFill(Color.GREEN);
            reputation.setText("" + d + " (safe)");
        } else {
            reputation.setTextFill(Color.RED);
            reputation.setText("" + d + " (unsafe)");
        }

        categories.setText(c.get(0));
        categories.setWrapText(true);
        flags.setText(b + " (malicious), " + s + "(suspicious)");

        timesSubmitted.setText("" + subs);

        this.add(resolvedURL, 1, 0);
        this.add(reputation, 1, 1);
        this.add(categories, 1, 2);
        this.add(timesSubmitted, 1, 3);
        this.add(flags, 1, 4);
    }
}
