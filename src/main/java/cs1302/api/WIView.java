package cs1302.api;

import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Custom component class that represents the gathered information from
 * the IPWhoIs API.
 */
public class WIView extends VBox {

    Label a;
    Label b;
    Label c;
    Label d;
    Label e;
    Label f;
    Label g;
    Label h;
    VBox labels;

    TitledPane countryPane;
    TitledPane connectionPane;
    TitledPane timezonePane;


    /**
     * Constructs instance of WIView with default settings before api is called.
     */
    public WIView() {
        a = new Label("IP: - ");
        c = new Label("Type: - ");
        e = new Label("Country: - ");
        f = new Label("Region: - ");
        g = new Label("City: - ");
        h = new Label("Coordinates: - ");

        countryPane = new TitledPane("Country Details", new VBox());
        connectionPane = new TitledPane("Connection Details", new VBox());
        timezonePane = new TitledPane("Timezone Details", new VBox());

        labels = new VBox();
        labels.setAlignment(Pos.BOTTOM_LEFT);
        labels.getChildren().addAll(a, c, e, f, g, h);
        labels.setSpacing(8);

        getChildren().addAll(countryPane, timezonePane, connectionPane);
    }

    /**
     * Returns the VBox with the labels so they are separate from the Panes.
     * Contains more general information
     * @return labels
     */
    public VBox getLabels() {
        return labels;
    }


    /**
     * Helper method called by ApiApp which updates the fields with the json response.
     * @param res the WI response returned after calling the api
     */
    public void update(WIResponse res) {
        a.setText("IP: " + res.ip);
        c.setText("Type: " + res.type);
        e.setText("Country: " + res.country);
        f.setText("Region: " + res.region);
        g.setText("City: " + res.city);
        h.setText("Coordinates: " + res.latitude + ", " + res.longitude);
        h.setWrapText(true);

        VBox countryBox = new VBox(
            new Label("Continent: " + res.continent),
            new Label("Postal Code: " + res.postal),
            new Label("Capital: " + res.capital));
        countryPane.setContent(countryBox);

        if (res.timezone != null) {
            VBox time = new VBox(
                new Label("ID: " + res.timezone.id),
                new Label("Abbr: " + res.timezone.abbr),
                new Label("UTC: " + res.timezone.utc),
                new Label("Current Time: " + res.timezone.currentTime)
            );
            timezonePane.setContent(time);
        } else {
            timezonePane.setContent(new Label("No timezone data fetched"));
        }

        if (res.connection != null) {
            VBox connect = new VBox(
                new Label("ASN: " + res.connection.asn),
                new Label("Org: " + res.connection.org),
                new Label("ISP: " + res.connection.isp),
                new Label("Domain: " + res.connection.domain)
            );
            connectionPane.setContent(connect);
        } else {
            connectionPane.setContent(new Label("No connection datta returned"));
        }

    }

}
