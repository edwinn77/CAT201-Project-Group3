package com.example.busticketbookingmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {
//    implements Initializable

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button availableB_Btn;

    @FXML
    private Button availableB_addBtn;

    @FXML
    private TextField availableB_busID;

    @FXML
    private TableView<busData> availableB_tableView;

    @FXML
    private TableColumn<busData, String> availableB_col_busID;

    @FXML
    private TableColumn<busData, String> availableB_col_dlocation;
    @FXML
    private TableColumn<busData, String> availableB_col_alocation;

    @FXML
    private TableColumn<busData, String> availableB_col_price;

    @FXML
    private TableColumn<busData, String> availableB_col_status;

    @FXML
    private Button availableB_deleteBtn;

    @FXML
    private AnchorPane availableB_form;

    @FXML
    private TextField availableB_location_depart;

    @FXML
    private TextField availableB_location_arrive;

    @FXML
    private TextField availableB_price;


    @FXML
    private Button availableB_resetBtn;

    @FXML
    private TextField availableB_search;

    @FXML
    private ComboBox<?> availableB_status;

    @FXML
    private Button availableB_updateBtn;

    @FXML
    private Button bookingTicket_Btn;

    @FXML
    private ComboBox<?> bookingTicket_busID;

    @FXML
    private DatePicker bookingTicket_date;

    @FXML
    private TextField bookingTicket_firstName;

    @FXML
    private AnchorPane bookingTicket_form;

    @FXML
    private ComboBox<?> bookingTicket_gender;

    @FXML
    private TextField bookingTicket_lastName;

    @FXML
    private ComboBox<?> bookingTicket_location;

    @FXML
    private ComboBox<?> bookingTicket_dlocation;

    @FXML
    private ComboBox<String> bookingTicket_alocation;


    @FXML
    private TextField bookingTicket_phoneNum;

    @FXML
    private Button bookingTicket_resetBtn;

    @FXML
    private Label bookingTicket_sci_busID;

    @FXML
    private Label bookingTicket_sci_date;

    @FXML
    private Label bookingTicket_sci_firstName;

    @FXML
    private Label bookingTicket_sci_gender;

    @FXML
    private Label bookingTicket_sci_lastName;

    @FXML
    private Label bookingTicket_sci_dlocation;

    @FXML
    private Label bookingTicket_sci_alocation;

    @FXML
    private Button bookingTicket_sci_pay;

    @FXML
    private Label bookingTicket_sci_phoneNum;

    @FXML
    private Button bookingTicket_sci_receipt;

    @FXML
    private Label bookingTicket_sci_ticketNum;

    @FXML
    private Label bookingTicket_sci_total;

    @FXML
    private Label bookingTicket_sci_type;

    @FXML
    private Button bookingTicket_selectBtn;

    @FXML
    private ComboBox<?> bookingTicket_ticketNum;

    @FXML
    private ComboBox<?> bookingTicket_type;

    @FXML
    private Button close;

    @FXML
    private AnchorPane customer_form;

    @FXML
    private TableView<customerData> customer_tableView;

    @FXML
    private Button customers_Btn;

    @FXML
    private Button customers_deleteBtn;

    @FXML
    private TextField customer_ID;

    @FXML
    private TableColumn<?, ?> customers_busID;

    @FXML
    private TableColumn<?, ?> customers_customerNum;

    @FXML
    private TableColumn<?, ?> customers_date;

    @FXML
    private TableColumn<?, ?> customers_firstName;

    @FXML
    private TableColumn<?, ?> customers_gender;

    @FXML
    private TableColumn<?, ?> customers_lastName;

    @FXML
    private TableColumn<?, ?> customers_dlocation;

    @FXML
    private TableColumn<?, ?> customers_alocation;

    @FXML
    private TableColumn<?, ?> customers_phoneNum;

    @FXML
    private TextField customers_search;

    @FXML
    private TableColumn<?, ?> customers_ticketNum;

    @FXML
    private TableColumn<?, ?> customers_type;

    @FXML
    private Button dashboard_Btn;

    @FXML
    private Label dashboard_availableB;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_incomeToday;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Button logout;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    //database tools
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public void availableBusAdd() {
        // SQL query to insert a new bus record into the database
        String addData = "INSERT INTO bus (bus_id, depart_location, arrive_location, status, price) VALUES(?,?,?,?,?)";

        // Establish a connection to the database
        connect = database.connectDb();

        try {
            // Check if any of the required fields are empty
            if (availableB_busID.getText().isEmpty()
                    || availableB_location_depart.getText().isEmpty()
                    || availableB_location_arrive.getText().isEmpty()
                    || availableB_status.getSelectionModel().getSelectedItem() == null
                    || availableB_price.getText().isEmpty()) {

                // Display an error alert if any field is empty
                showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all blank fields");
            } else {
                // SQL query to check if the bus ID already exists in the database
                String check = "SELECT bus_id FROM bus WHERE bus_id = '" + availableB_busID.getText() + "'";

                // Execute the query to check for existing bus ID
                statement = connect.createStatement();
                result = statement.executeQuery(check);

                // Check if the bus ID already exists
                if (result.next()) {
                    // Display an error alert if the bus ID already exists
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Bus ID: " + availableB_busID.getText() + " already exists!");
                } else {
                    // Prepare the SQL statement for inserting a new bus record
                    prepare = connect.prepareStatement(addData);
                    prepare.setString(1, availableB_busID.getText());
                    prepare.setString(2, availableB_location_depart.getText());
                    prepare.setString(3, availableB_location_arrive.getText());
                    prepare.setString(4, (String) availableB_status.getSelectionModel().getSelectedItem());
                    prepare.setString(5, availableB_price.getText());

                    // Execute the insert operation
                    prepare.executeUpdate();

                    // Display a confirmation alert on successful addition
                    showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Added!");

                    // Refresh the table view to show the newly added bus data
                    availableBShowBusData();
                    // Reset the form fields to their default state
                    availableBusReset();

                    // Refresh the search to include the new data
                    availableSearch();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources to prevent memory leaks
            closeDatabaseResources();
        }
    }

    public void availableBusUpdate() {
        // SQL query to update a bus record in the database
        String updateData =
                "UPDATE bus SET depart_location = '" + availableB_location_depart.getText()
                        + "', arrive_location = '" + availableB_location_arrive.getText()
                        + "', status = '" + availableB_status.getSelectionModel().getSelectedItem()
                        + "', price = '" + availableB_price.getText()
                        + "' WHERE bus_id = '" + availableB_busID.getText() + "'";

        // Establish a connection to the database
        connect = database.connectDb();

        try {
            // Check if any of the required fields are empty
            if (availableB_busID.getText().isEmpty()
                    || availableB_location_depart.getText().isEmpty()
                    || availableB_location_arrive.getText().isEmpty()
                    || availableB_status.getSelectionModel().getSelectedItem() == null
                    || availableB_price.getText().isEmpty()) {

                // Display an error alert if any field is empty
                showAlert(Alert.AlertType.ERROR, "Error Message", "Please select the item first");
            } else {
                // Confirmation alert to confirm the update operation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Bus ID: " + availableB_busID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                // If the user confirms, proceed with the update
                if (option.get().equals(ButtonType.OK)) {
                    // Prepare and execute the update statement
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    // Display a confirmation alert on successful update
                    showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Updated!");

                    // Refresh the table view to show the updated bus data
                    availableBShowBusData();
                    // Reset the form fields to their default state
                    availableBusReset();

                    // Refresh the search to include the updated data
                    availableSearch();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources to prevent memory leaks
            closeDatabaseResources();
        }
    }

    /**
     * Deletes a bus record from the database.
     */
    public void availableBusDelete() {
        // SQL query to delete a bus record from the database
        String deleteData = "DELETE FROM bus WHERE bus_id = '" + availableB_busID.getText() + "'";

        // Establish a connection to the database
        connect = database.connectDb();

        try {
            // Check if the bus ID field is empty
            if (availableB_busID.getText().isEmpty()) {
                // Display an error alert if bus ID field is empty
                showAlert(Alert.AlertType.ERROR, "Error Message", "Please select a bus to delete.");
            } else {
                // Confirmation alert to confirm the deletion operation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete Bus ID: " + availableB_busID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                // If the user confirms, proceed with the deletion
                if (option.get().equals(ButtonType.OK)) {
                    // Execute the delete statement
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    // Display a confirmation alert on successful deletion
                    showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Deleted!");

                    // Refresh the table view to reflect the deletion
                    availableBShowBusData();
                    // Reset the form fields to their default state
                    availableBusReset();
                    // Refresh the search to include the updated data
                    availableSearch();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while deleting the bus.");
        } finally {
            // Close the database connection and other resources to prevent memory leaks
            closeDatabaseResources();
        }
    }

    /**
     * Resets the form fields to their default state.
     */
    public void availableBusReset(){
        availableB_busID.setText("");
        availableB_location_depart.setText("");
        availableB_location_arrive.setText("");
        availableB_status.getSelectionModel().clearSelection();
        availableB_price.setText("");
    }

    /**
     * Populates the status ComboBox with available status options.
     */
    // Array containing the status options for the bus
    private String [] statusList = {"Available", "Not Available"};

    public void comboBoxStatus() {
        // Create a new list to hold status options
        List<String> listS = new ArrayList<>();

        // Loop through each status in the statusList array
        for(String data: statusList) {
            // Add each status to the list
            listS.add(data);
        }

        // Convert the list to an ObservableList which is required for the ComboBox
        ObservableList listStatus = FXCollections.observableArrayList(listS);

        // Set the items of the ComboBox to the ObservableList of statuses
        availableB_status.setItems(listStatus);
    }


    /**
     * Retrieves a list of bus data from the database.
     *
     * @return An ObservableList of busData objects, each representing a row from the bus table.
     */
    public ObservableList<busData> availableBusBusData() {
        // Create an empty ObservableList to hold bus data
        ObservableList<busData> busListData = FXCollections.observableArrayList();

        // SQL query to select all records from the bus table
        String sql = "SELECT * FROM bus";

        // Establish a connection to the database
        connect = database.connectDb();
        try {
            // Prepare the SQL statement for execution
            prepare = connect.prepareStatement(sql);
            // Execute the query and store the result set
            result = prepare.executeQuery();

            // Iterate over each row in the result set
            while (result.next()) {
                // Create a new busData object from the current row's data
                busData busD = new busData(
                        result.getInt("bus_id"),
                        result.getString("depart_location"),
                        result.getString("arrive_location"),
                        result.getString("status"),
                        result.getDouble("price")
                );

                // Add the busData object to the list
                busListData.add(busD);
            }

            // Return the list of bus data
            return busListData;

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources to prevent memory leaks
            closeDatabaseResources();
        }

        // Return the list, which may be empty if an exception occurred
        return busListData;
    }

// Method to close database resources (not shown here)


    // Field to store the list of bus data
    private ObservableList<busData> availableBBusListData;

    /**
     * Populates the TableView with bus data.
     * This method fetches bus data from the database and sets it to the TableView.
     */
    public void availableBShowBusData() {
        // Retrieve bus data from the database and store it in availableBBusListData
        availableBBusListData = availableBusBusData();

        // Set up the TableView columns to display the bus data
        // The PropertyValueFactory will map the data to the respective columns based on the property names

        // Map the 'busId' property of busData objects to the 'busID' column in the TableView
        availableB_col_busID.setCellValueFactory(new PropertyValueFactory<>("busId"));
        // Map the 'depart_location' property
        availableB_col_dlocation.setCellValueFactory(new PropertyValueFactory<>("departLocation"));
        // Map the 'arrive_location' property
        availableB_col_alocation.setCellValueFactory(new PropertyValueFactory<>("arriveLocation"));
        // Map the 'status' property
        availableB_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        // Map the 'price' property
        availableB_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set the ObservableList of bus data as the items of the TableView
        // This will update the TableView to display the data
        availableB_tableView.setItems(availableBBusListData);
    }


    /**
     * Handles the selection of a bus from the TableView and updates the form fields with the selected bus's data.
     */
    public void availableBSelectBusData() {
        // Retrieve the selected busData object from the TableView
        busData busD = availableB_tableView.getSelectionModel().getSelectedItem();
        // Get the index of the selected item in the TableView
        int num = availableB_tableView.getSelectionModel().getSelectedIndex();

        // Check if the selection is valid (i.e., an item is actually selected)
        if ((num - 1) < -1) {
            // If no item is selected, exit the method
            return;
        }

        // Set the form fields to the values from the selected busData object
        // Update the bus ID field
        availableB_busID.setText(String.valueOf(busD.getBusId()));
        // Update the depart location field
        availableB_location_depart.setText(String.valueOf(busD.getDepartLocation()));
        // Update the arrive location field
        availableB_location_arrive.setText(String.valueOf(busD.getArriveLocation()));
        // Update the price field
        availableB_price.setText(String.valueOf(busD.getPrice()));
    }


    /**
     * Configures the search functionality for the TableView.
     * It filters the table data based on the user's search input.
     */
    public void availableSearch() {
        // Create a filtered list wrapper around the original list of bus data
        FilteredList<busData> filter = new FilteredList<>(availableBBusListData, e -> true);

        // Add a listener to the search text field to react to text changes
        availableB_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            System.out.println("Search query changed: " + newValue); // Debugging statement

            // Define the criteria for filtering the bus data based on the search query
            filter.setPredicate(predicateBusData -> {
                // If the search field is empty, display all data (no filter)
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert the search query to lower case for case-insensitive comparison
                String searchKey = newValue.toLowerCase();

                // Check if the bus data matches the search query in any of the columns
                if (predicateBusData.getBusId().toString().contains(searchKey)) {
                    return true; // Match found in bus ID
                } else if (predicateBusData.getDepartLocation().toLowerCase().contains(searchKey)) {
                    return true; // Match found in depart location
                } else if (predicateBusData.getArriveLocation().toLowerCase().contains(searchKey)) {
                    return true; // Match found in arrive location
                } else if (predicateBusData.getStatus().toLowerCase().contains(searchKey)) {
                    return true; // Match found in status
                } else if (predicateBusData.getPrice().toString().contains(searchKey)) {
                    return true; // Match found in price
                } else {
                    return false; // No match found
                }
            });
        });

        // Create a sorted list wrapper around the filtered list
        SortedList<busData> sortList = new SortedList<>(filter);

        // Bind the comparator of the sorted list to the comparator of the TableView
        // This ensures sorting is preserved while filtering
        sortList.comparatorProperty().bind(availableB_tableView.comparatorProperty());

        // Set the sorted and filtered list as the items of the TableView
        availableB_tableView.setItems(sortList);
    }


    /**
     * Populates the ComboBox with the list of available bus IDs.
     */
    public void busIdList() {
        // SQL query to select all available buses
        String busD = "SELECT * FROM bus WHERE status = 'Available'";

        // Establish a connection to the database
        connect = database.connectDb();

        try {
            // Prepare and execute the SQL query
            prepare = connect.prepareStatement(busD);
            result = prepare.executeQuery();

            // List to hold bus IDs
            ObservableList listB = FXCollections.observableArrayList();

            // Iterate through the result set and add each bus ID to the list
            while (result.next()) {
                listB.add(result.getString("bus_id"));
            }

            // Set the items of the ComboBox to the list of bus IDs
            bookingTicket_busID.setItems(listB);

            // Populate the ticket number list
            ticketNumList();

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources
            closeDatabaseResources();
        }
    }

    /**
     * Populates the ComboBox with the list of departure locations from available buses.
     */
    public void departLocationList() {
        // SQL query to select all available buses
        String locationL = "SELECT * FROM bus WHERE status = 'Available'";

        // Establish a connection to the database
        connect = database.connectDb();

        try {
            // Prepare and execute the SQL query
            prepare = connect.prepareStatement(locationL);
            result = prepare.executeQuery();

            // List to hold departure locations
            ObservableList listL = FXCollections.observableArrayList();

            // Iterate through the result set and add each departure location to the list
            while (result.next()) {
                listL.add(result.getString("depart_location"));
            }

            // Set the items of the ComboBox to the list of departure locations
            bookingTicket_dlocation.setItems(listL);

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources
            closeDatabaseResources();
        }
    }

    /**
     * Populates the arrival location ComboBox with available arrival locations.
     * This method fetches arrival locations from the database where the bus status is 'Available'.
     */
    public void arriveLocationList() {
        // SQL query to select all available buses
        String locationL = "SELECT * FROM bus WHERE status = 'Available'";

        // Establish a connection to the database
        connect = database.connectDb();

        try {
            // Prepare and execute the SQL query
            prepare = connect.prepareStatement(locationL);
            result = prepare.executeQuery();

            // List to hold arrival locations
            ObservableList listL = FXCollections.observableArrayList();

            // Iterate through the result set and add each arrival location to the list
            while (result.next()) {
                listL.add(result.getString("arrive_location"));
            }

            // Set the items of the arrival location ComboBox to the list of locations
            bookingTicket_alocation.setItems(listL);

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources to prevent memory leaks
            closeDatabaseResources();
        }
    }

    private String[] listT = {"First Class", "Economy Class"};

    /**
     * Populates the ticket type ComboBox with ticket types.
     * This method uses a predefined list of ticket types.
     */
    public void typeList() {
        // List to hold ticket types
        List<String> tList = new ArrayList<>();

        // Add predefined ticket types to the list
        for (String data : listT) {
            tList.add(data);
        }

        // Convert the list to an ObservableList
        ObservableList listType = FXCollections.observableArrayList(tList);

        // Set the items of the ticket type ComboBox to the ObservableList
        bookingTicket_type.setItems(listType);
    }

    /**
     * Populates the ticket number ComboBox with available ticket numbers.
     * This method calculates available ticket numbers based on the selected bus ID.
     */
    public void ticketNumList() {
        // List to hold ticket numbers (1 to 40, representing seat capacity)
        List<String> listTicket = new ArrayList<>();
        for (int q = 1; q <= 40; q++) {
            listTicket.add(String.valueOf(q));
        }

        // SQL query to select occupied seats for the selected bus
        String removeSeat = "SELECT seatNum FROM customer WHERE bus_id='"
                + bookingTicket_busID.getSelectionModel().getSelectedItem() + "'";

        // Establish a connection to the database
        connect = database.connectDb();

        try {
            // Prepare and execute the SQL query
            prepare = connect.prepareStatement(removeSeat);
            result = prepare.executeQuery();

            // Remove occupied seats from the list of ticket numbers
            while (result.next()) {
                listTicket.remove(result.getString("seatNum"));
            }

            // Convert the list to an ObservableList
            ObservableList listTi = FXCollections.observableArrayList(listTicket);

            // Set the items of the ticket number ComboBox to the ObservableList
            bookingTicket_ticketNum.setItems(listTi);

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources to prevent memory leaks
            closeDatabaseResources();
        }
    }

//    Customer Table

    private double priceData = 0;
    private double totalP = 0;

    /**
     * Handles the selection of ticket details and calculates the total price.
     * This method validates user input, checks for location consistency with the database,
     * calculates the ticket price based on the type, and updates the UI with the selected ticket details.
     */
    public void bookingTicketSelect() {
        // Retrieve user input from the form fields
        String firstName = bookingTicket_firstName.getText();
        String lastName = bookingTicket_lastName.getText();
        String gender = (String) bookingTicket_gender.getSelectionModel().getSelectedItem();
        String phoneNumber = bookingTicket_phoneNum.getText();
        String date = String.valueOf(bookingTicket_date.getValue());
        String busId = (String) bookingTicket_busID.getSelectionModel().getSelectedItem();
        String locationDepart = (String) bookingTicket_dlocation.getSelectionModel().getSelectedItem();
        String locationArrival = (String) bookingTicket_alocation.getSelectionModel().getSelectedItem();
        String type = (String) bookingTicket_type.getSelectionModel().getSelectedItem();
        String ticketNum = (String) bookingTicket_ticketNum.getSelectionModel().getSelectedItem();

        // Validate user input
        if (firstName == null || lastName == null || gender == null || phoneNumber == null || date == null
                || busId == null || locationDepart == null || locationArrival == null
                || type == null || ticketNum == null) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all blank fields");
            return;
        }

        try {
            // Establish a database connection
            connect = database.connectDb();
            if (connect == null) {
                showAlert("Database Error", "Unable to establish database connection.");
                return;
            }

            // Query to check the departure and arrival locations in the database for the selected bus ID
            String checkLocationQuery = "SELECT depart_location, arrive_location FROM bus WHERE bus_id = ?";
            prepare = connect.prepareStatement(checkLocationQuery);
            prepare.setString(1, busId);
            ResultSet checkLocationResult = prepare.executeQuery();

            // Variables to store database locations
            String dbDepartLocation = null;
            String dbArriveLocation = null;
            if (checkLocationResult.next()) {
                dbDepartLocation = checkLocationResult.getString("depart_location");
                dbArriveLocation = checkLocationResult.getString("arrive_location");
            }

            // Compare the locations from the database with the user-selected locations
            if ((dbDepartLocation != null && !dbDepartLocation.equals(locationDepart)) ||
                    (dbArriveLocation != null && !dbArriveLocation.equals(locationArrival))) {
                showAlert("Location Mismatch", "The selected locations do not match the bus's locations in the database.");
                return;
            }

            // Query to get the price for the selected departure and arrival locations
            String totalPrice = "SELECT price FROM bus WHERE depart_location = ? AND arrive_location = ?";
            prepare = connect.prepareStatement(totalPrice);
            prepare.setString(1, locationDepart);
            prepare.setString(2, locationArrival);
            result = prepare.executeQuery();

            // Calculate the total price based on the ticket type
            if (result.next()) {
                priceData = result.getDouble("price");
                totalP = type.equals("First Class") ? (priceData + 100) : priceData;
            }

            // Update the UI with the selected ticket details
            bookingTicket_sci_total.setText(String.valueOf(totalP));
            bookingTicket_sci_firstName.setText(firstName);
            bookingTicket_sci_lastName.setText(lastName);
            bookingTicket_sci_gender.setText(gender);
            bookingTicket_sci_phoneNum.setText(phoneNumber);
            bookingTicket_sci_date.setText(date);
            bookingTicket_sci_busID.setText(busId);
            bookingTicket_sci_dlocation.setText(locationDepart);
            bookingTicket_sci_alocation.setText(locationArrival);
            bookingTicket_sci_type.setText(type);
            bookingTicket_sci_ticketNum.setText(ticketNum);

            // Show confirmation alert
            showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Selected!");

            // Reset the form fields
            bookingTicketReset();

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources to prevent memory leaks
            closeDatabaseResources();
        }
    }

    /**
     * Resets the booking ticket form fields to their default state.
     */
    public void bookingTicketReset() {
        // Clearing all the input fields and selections
        bookingTicket_firstName.setText("");
        bookingTicket_lastName.setText("");
        bookingTicket_gender.getSelectionModel().clearSelection();
        bookingTicket_phoneNum.setText("");
        bookingTicket_date.setValue(null);
        bookingTicket_busID.getSelectionModel().clearSelection();
        bookingTicket_dlocation.getSelectionModel().clearSelection();
        bookingTicket_alocation.getSelectionModel().clearSelection();
        bookingTicket_type.getSelectionModel().clearSelection();
        bookingTicket_ticketNum.getSelectionModel().clearSelection();
    }

    /**
     * Populates the gender list for the booking ticket form.
     */

    // Creating a list of gender options
    private String[] genderL = {"Male","Female","Others"};

    public void genderList(){

        List<String> listG = new ArrayList<>();

        for(String data : genderL){
            listG.add(data);
        }

        // Setting the gender options in the ComboBox
        ObservableList gList = FXCollections.observableArrayList(listG);
        bookingTicket_gender.setItems(gList);

    }

    /**
     * Handles the payment and booking process for a ticket.
     * This method inserts the customer's booking information into the database.
     */
    private int countRow;
    public void bookingTicketPay(){
        // Retrieving customer and booking details from the form
        String firstName = bookingTicket_sci_firstName.getText();
        String lastName = bookingTicket_sci_lastName.getText();
        String gender = bookingTicket_sci_gender.getText();
        String phoneNumber = bookingTicket_sci_phoneNum.getText();
        String date = bookingTicket_sci_date.getText();

        String busId = bookingTicket_sci_busID.getText();
        String departLocation = bookingTicket_sci_dlocation.getText();
        String arrivalLocation = bookingTicket_sci_alocation.getText();
        String type = bookingTicket_sci_type.getText();
        String seatNum = bookingTicket_sci_ticketNum.getText();

        // SQL query to insert customer data into the 'customer' table
        String payData = "INSERT INTO customer (customer_id,firstName,lastName,gender,phoneNumber,bus_id,location_depart,location_arrive,type,seatNum,total,date)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        // Establishing a database connection
        connect = database.connectDb();
        if (connect == null) {
            // Handle the case where connection is null
            showAlert("Database Error", "Unable to establish database connection.");
            return;
        }

        try{
            // Alert for confirmation before proceeding with payment
            Alert alert;

            // Query to get the total number of customers for generating a new customer ID
            String countNum = "SELECT COUNT(id) FROM customer";
            statement = connect.createStatement();
            result = statement.executeQuery(countNum);

            // Retrieving the total number of customers
            while(result.next()){
                countRow = result.getInt("COUNT(id)");
            }

            // Validating if all required fields are filled
            if(bookingTicket_sci_firstName.getText().isEmpty()
                    || bookingTicket_sci_lastName.getText().isEmpty()
                    || bookingTicket_sci_gender.getText().isEmpty()
                    || bookingTicket_sci_phoneNum.getText().isEmpty()
                    || bookingTicket_sci_date.getText().isEmpty()
                    || bookingTicket_sci_busID.getText().isEmpty()
                    || bookingTicket_sci_dlocation.getText().isEmpty()
                    || bookingTicket_sci_alocation.getText().isEmpty()
                    || bookingTicket_sci_type.getText().isEmpty()
                    || bookingTicket_sci_ticketNum.getText().isEmpty()
                    || totalP == 0){

                showAlert(Alert.AlertType.ERROR, "Error Message", "Please select the information first");
            }else{

                // Confirmation dialog before finalizing the payment
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                alert.showAndWait();
                // Preparing the SQL statement for inserting customer data
                prepare = connect.prepareStatement(payData);
                prepare.setString(1, String.valueOf(countRow+1));
                prepare.setString(2, firstName);
                prepare.setString(3, lastName);
                prepare.setString(4, gender);
                prepare.setString(5, phoneNumber);
                prepare.setString(6, busId);
                prepare.setString(7, departLocation);
                prepare.setString(8, arrivalLocation);
                prepare.setString(9, type);
                prepare.setString(10, seatNum);
                prepare.setString(11, String.valueOf(totalP));
                prepare.setString(12, date);

                // Executing the SQL statement
                prepare.executeUpdate();

                // Displaying success message
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successful!");

                // Clearing the form fields after successful payment
                resetBookingTicketSciFields();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
        } finally {
            // Close the database connection and other resources
            closeDatabaseResources();
        }
    }

    /**
     * Resets the fields in the booking ticket summary interface.
     */
    private void resetBookingTicketSciFields() {
        bookingTicket_sci_firstName.setText("");
        bookingTicket_sci_lastName.setText("");
        bookingTicket_sci_gender.setText("");
        bookingTicket_sci_phoneNum.setText("");
        bookingTicket_sci_date.setText("");
        bookingTicket_sci_busID.setText("");
        bookingTicket_sci_dlocation.setText("");
        bookingTicket_sci_alocation.setText("");
        bookingTicket_sci_type.setText("");
        bookingTicket_sci_ticketNum.setText("");
        bookingTicket_sci_total.setText("0.0");
    }

    /**
     * Retrieves a list of all customer data from the database.
     *
     * @return An ObservableList containing customer data.
     */
    public ObservableList<customerData> customersDataList() {
        // Creating an ObservableList to hold customer data
        ObservableList<customerData> customerList = FXCollections.observableArrayList();

        // SQL query to select all records from the 'customer' table
        String sql = "SELECT * FROM customer";

        // Establishing a database connection
        connect = database.connectDb();

        try {
            // Preparing and executing the SQL query
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            // Iterating through the result set and adding each customer to the list
            while (result.next()) {
                customerData custD = new customerData(
                        result.getInt("customer_id"), result.getString("firstName"),
                        result.getString("lastName"), result.getString("gender"),
                        result.getString("phoneNumber"), result.getInt("bus_id"),
                        result.getString("location_depart"), result.getString("location_arrive"),
                        result.getString("type"), result.getInt("seatNum"),
                        result.getDouble("total"), result.getDate("date")
                );
                customerList.add(custD);
            }

            return customerList;

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
        } finally {
            // Close the database connection and other resources
            closeDatabaseResources();
        }

        return customerList; // Return the list of customers
    }

    /**
     * Populates the TableView with customer data.
     * This method fetches customer data from the database and sets it to the TableView.
     */
    private ObservableList<customerData> customersDataL;
    public void customersShowDataList() {
        // Fetching customer data and storing it in an ObservableList
        customersDataL = customersDataList();

        // Setting up the TableView columns to display the customer data
        // The PropertyValueFactory will map the data to the respective columns based on the property names
        customers_customerNum.setCellValueFactory(new PropertyValueFactory<>("customerNum"));
        customers_ticketNum.setCellValueFactory(new PropertyValueFactory<>("seatNum"));
        customers_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customers_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customers_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        customers_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        customers_busID.setCellValueFactory(new PropertyValueFactory<>("busId"));
        customers_dlocation.setCellValueFactory(new PropertyValueFactory<>("departLocation"));
        customers_alocation.setCellValueFactory(new PropertyValueFactory<>("arriveLocation"));
        customers_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        customers_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Setting the ObservableList of customer data as the items of the TableView
        customer_tableView.setItems(customersDataL);
    }

    /**
     * Configures the search functionality for the customer TableView.
     * It filters the table data based on the user's search input.
     */
    public void customersSearch() {
        // Creating a filtered list wrapper around the original list of customer data
        FilteredList<customerData> filter = new FilteredList<>(customersDataL, e -> true);

        // Adding a listener to the search text field to react to text changes
        customers_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            // Define the criteria for filtering the customer data based on the search query
            filter.setPredicate(predicateCustomerData -> {
                // If the search field is empty, display all data (no filter)
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert the search query to lower case for case-insensitive comparison
                String searchKey = newValue.toLowerCase();

                if(predicateCustomerData.getCustomerNum().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getSeatNum().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getFirstName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getLastName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getGender().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getPhoneNum().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getBusId().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getDepartLocation().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getArriveLocation().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getTotal().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getType().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getDate().toString().contains(searchKey)){
                    return true;
                }else return false;

            });
        });

        // Create a sorted list wrapper around the filtered list
        SortedList<customerData> sortList = new SortedList<>(filter);

        // Bind the comparator of the sorted list to the comparator of the TableView
        sortList.comparatorProperty().bind(customer_tableView.comparatorProperty());

        // Set the sorted and filtered list as the items of the TableView
        customer_tableView.setItems(sortList);
    }

    /**
     * Handles the selection of a customer from the TableView and updates the form fields with the selected customer's data.
     */
    public void customerSelectData() {
        // Retrieve the selected customerData object from the TableView
        customerData cusD = customer_tableView.getSelectionModel().getSelectedItem();
        // Get the index of the selected item in the TableView
        int num = customer_tableView.getSelectionModel().getSelectedIndex();

        // Check if the selection is valid (i.e., an item is actually selected)
        if (cusD == null || num == -1) {
            // No item is selected
            showAlert(Alert.AlertType.WARNING, "Selection Error", "No customer selected.");
            return;
        }

        // Set the form fields to the values from the selected customerData object
        customer_ID.setText(String.valueOf(cusD.getCustomerNum()));
    }

    /**
     * Deletes the selected customer from the database.
     */
    public void customerDelete() {
        // Check if customer_ID is empty
        if (customer_ID.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Deletion Error", "Customer ID is empty. Please select a customer to delete.");
            return;
        }

        // SQL query to delete a customer record from the database
        String deleteData = "DELETE FROM customer WHERE customer_id = ?";

        // Establish a connection to the database
        connect = database.connectDb();

        try {
            // Confirmation alert to confirm the deletion operation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete Customer ID: " + customer_ID.getText() + "?");

            Optional<ButtonType> option = alert.showAndWait();

            // If the user confirms, proceed with the deletion
            if (option.get().equals(ButtonType.OK)) {
                // Prepare and execute the delete statement using PreparedStatement
                PreparedStatement prepare = connect.prepareStatement(deleteData);
                prepare.setString(1, customer_ID.getText());
                int rowsAffected = prepare.executeUpdate();

                // Check if the deletion was successful
                if (rowsAffected > 0) {
                    // Display a confirmation alert on successful deletion
                    showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Deleted!");
                    customer_ID.setText("");
                    // Refresh the table view to reflect the deletion
                    customersShowDataList();
                    customersSearch();
                } else {
                    // Display an error alert if no rows were affected (ID does not exist)
                    showAlert(Alert.AlertType.ERROR, "Deletion Error", "Failed to delete. Customer ID does not exist.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            // Show an alert for database-related errors
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error occurred while deleting the customer.");
        } finally {
            // Close the database connection and other resources to prevent memory leaks
            closeDatabaseResources();
        }
    }
    private double x = 0;
    private double y = 0;
    public void logout(){
        try{

            Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){

                logout.getScene().getWindow().hide();;
//                login form
                FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("login.fxml")); // Replace "Dashboard.fxml" with your actual FXML file
                Parent root = dashboardLoader.load();

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event)->{
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) ->{
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }else return;

        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources
            closeDatabaseResources();
        }
    }

    private int countAB = 0;
    public void dashboardDisplayAB(){

        String sql = "SELECT COUNT(id) FROM bus WHERE status = 'Available'";

        connect = database.connectDb();

        try{

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countAB = result.getInt("COUNT(id)");
            }

            dashboard_availableB.setText(String.valueOf(countAB));

        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources
            closeDatabaseResources();
        }

    }

    private double incomeToday = 0;
    public void dashboardDisplayIT(){

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM customer WHERE date ='"+sqlDate+"'";

        connect = database.connectDb();

        try{

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                incomeToday = result.getDouble("SUM(total)");
            }

            dashboard_incomeToday.setText("$"+String.valueOf(incomeToday));

        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources
            closeDatabaseResources();
        }

    }

    private double totalIncome;
    public void dashboardDisplayTI(){

        String sql = "SELECT SUM(total) FROM customer";

        connect = database.connectDb();

        try{

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                totalIncome = result.getDouble("SUM(total)");
            }

            dashboard_totalIncome.setText("$"+String.valueOf(totalIncome));

        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources
            closeDatabaseResources();
        }

    }

    public void dashboardChart(){

        dashboard_chart.getData().clear();

        String sql = "SELECT date,SUM(total) FROM customer WHERE date != '' GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";

        connect = database.connectDb();

        XYChart.Series chart = new XYChart.Series();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_chart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, show an alert or log this error
        } finally {
            // Close the database connection and other resources
            closeDatabaseResources();
        }

    }

    public void displayUsername(){
        username.setText(getData.username);
    }

    public void defaultBtn(){
        dashboard_Btn.setStyle("-fx-background-color: #6094d1");
        availableB_Btn.setStyle("-fx-background-color: transparent");
        bookingTicket_Btn.setStyle("-fx-background-color: transparent");
        customers_Btn.setStyle("-fx-background-color: transparent");
    }

    public void switchForm(ActionEvent event){
        if (event.getSource() == dashboard_Btn) {
            dashboard_form.setVisible(true);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(false);
            customer_form.setVisible(false);

            dashboard_Btn.setStyle("-fx-background-color: #6094d1");
            availableB_Btn.setStyle("-fx-background-color: transparent");
            bookingTicket_Btn.setStyle("-fx-background-color: transparent");
            customers_Btn.setStyle("-fx-background-color: transparent");

            dashboardDisplayAB();
            dashboardDisplayIT();
            dashboardDisplayTI();
            dashboardChart();

        }else if (event.getSource() == availableB_Btn){
            dashboard_form.setVisible(false);
            availableB_form.setVisible(true);
            bookingTicket_form.setVisible(false);
            customer_form.setVisible(false);

            availableB_Btn.setStyle("-fx-background-color: #6094d1");
            dashboard_Btn.setStyle("-fx-background-color: transparent");
            bookingTicket_Btn.setStyle("-fx-background-color: transparent");
            customers_Btn.setStyle("-fx-background-color: transparent");

            availableB_search.setText("");
            availableBShowBusData();
            availableSearch();
            typeList();

        }else if (event.getSource() == bookingTicket_Btn){
            dashboard_form.setVisible(false);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(true);
            customer_form.setVisible(false);

            bookingTicket_Btn.setStyle("-fx-background-color: #6094d1");
            dashboard_Btn.setStyle("-fx-background-color: transparent");
            availableB_Btn.setStyle("-fx-background-color: transparent");
            customers_Btn.setStyle("-fx-background-color: transparent");

            busIdList();
//            bookingTicket_busID.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//                if (newSelection != null) {
//                    locationList(newSelection.toString());
//                }
//            });
            departLocationList();
            arriveLocationList();
            typeList();
            ticketNumList();
            genderList();

        }else if (event.getSource() == customers_Btn){
            dashboard_form.setVisible(false);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(false);
            customer_form.setVisible(true);

            customers_Btn.setStyle("-fx-background-color: #6094d1");
            dashboard_Btn.setStyle("-fx-background-color: transparent");
            availableB_Btn.setStyle("-fx-background-color: transparent");
            bookingTicket_Btn.setStyle("-fx-background-color: transparent");

            customers_search.setText("");
            customersShowDataList();
            customersSearch();

        }
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void closeDatabaseResources() {
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
                // Log or handle the exception as needed
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // Log or handle the exception as needed
                e.printStackTrace();
            }
        }

        if (connect != null) {
            try {
                connect.close();
            } catch (SQLException e) {
                // Log or handle the exception as needed
                e.printStackTrace();
            }
        }
    }

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        dashboard_form.setVisible(true);
        availableB_form.setVisible(false);
        bookingTicket_form.setVisible(false);
        customer_form.setVisible(false);

        defaultBtn();
        displayUsername();

        dashboardDisplayAB();
        dashboardDisplayIT();
        dashboardDisplayTI();
        dashboardChart();


        comboBoxStatus();
        availableBShowBusData();

        busIdList();
//        bookingTicket_busID.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                locationList(newSelection.toString());
//            }
//        });
        departLocationList();
        arriveLocationList();
        typeList();
        ticketNumList();
        genderList();

        customersShowDataList();
    }
}


