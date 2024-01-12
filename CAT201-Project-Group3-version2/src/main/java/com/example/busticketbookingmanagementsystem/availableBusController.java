//package com.example.busticketbookingmanagementsystem;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.chart.AreaChart;
//import javafx.scene.chart.XYChart;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//
//import java.net.URL;
//import java.sql.*;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.ResourceBundle;
//
//public class dashboardController implements Initializable {
////    implements Initializable
//
//    @FXML
//    private AnchorPane main_form;
//
//    @FXML
//    private Button availableB_Btn;
//
//    @FXML
//    private Button availableB_addBtn;
//
//    @FXML
//    private TextField availableB_busID;
//
//    @FXML
//    private TableView<busData> availableB_tableView;
//
//    @FXML
//    private TableColumn<busData, String> availableB_col_busID;
//
//    @FXML
//    private TableColumn<busData, String> availableB_col_date;
//
//    @FXML
//    private TableColumn<busData, String> availableB_col_location;
//
//    @FXML
//    private TableColumn<busData, String> availableB_col_price;
//
//    @FXML
//    private TableColumn<busData, String> availableB_col_status;
//
//    @FXML
//    private DatePicker availableB_date;
//
//    @FXML
//    private Button availableB_deleteBtn;
//
//    @FXML
//    private AnchorPane availableB_form;
//
//    @FXML
//    private TextField availableB_location;
//
//    @FXML
//    private TextField availableB_price;
//
//
//    @FXML
//    private Button availableB_resetBtn;
//
//    @FXML
//    private TextField availableB_search;
//
//    @FXML
//    private ComboBox<?> availableB_status;
//
//    @FXML
//    private Button availableB_updateBtn;
//
//    @FXML
//    private Button bookingTicket_Btn;
//
//    @FXML
//    private ComboBox<?> bookingTicket_busID;
//
//    @FXML
//    private DatePicker bookingTicket_date;
//
//    @FXML
//    private TextField bookingTicket_firstName;
//
//    @FXML
//    private AnchorPane bookingTicket_form;
//
//    @FXML
//    private ComboBox<?> bookingTicket_gender;
//
//    @FXML
//    private TextField bookingTicket_lastName;
//
//    @FXML
//    private ComboBox<?> bookingTicket_location;
//
////    @FXML
////    private ComboBox<String> bookingTicket_location;
//
//
//    @FXML
//    private TextField bookingTicket_phoneNum;
//
//    @FXML
//    private Button bookingTicket_resetBtn;
//
//    @FXML
//    private Label bookingTicket_sci_busID;
//
//    @FXML
//    private Label bookingTicket_sci_date;
//
//    @FXML
//    private Label bookingTicket_sci_firstName;
//
//    @FXML
//    private Label bookingTicket_sci_gender;
//
//    @FXML
//    private Label bookingTicket_sci_lastName;
//
//    @FXML
//    private Label bookingTicket_sci_location;
//
//    @FXML
//    private Button bookingTicket_sci_pay;
//
//    @FXML
//    private Label bookingTicket_sci_phoneNum;
//
//    @FXML
//    private Button bookingTicket_sci_receipt;
//
//    @FXML
//    private Label bookingTicket_sci_ticketNum;
//
//    @FXML
//    private Label bookingTicket_sci_total;
//
//    @FXML
//    private Label bookingTicket_sci_type;
//
//    @FXML
//    private Button bookingTicket_selectBtn;
//
//    @FXML
//    private ComboBox<?> bookingTicket_ticketNum;
//
//    @FXML
//    private ComboBox<?> bookingTicket_type;
//
//    @FXML
//    private Button close;
//
//    @FXML
//    private AnchorPane customer_form;
//
//    @FXML
//    private TableView<customerData> customer_tableView;
//
//    @FXML
//    private Button customers_Btn;
//
//    @FXML
//    private TableColumn<?, ?> customers_busID;
//
//    @FXML
//    private TableColumn<?, ?> customers_customerNum;
//
//    @FXML
//    private TableColumn<?, ?> customers_date;
//
//    @FXML
//    private TableColumn<?, ?> customers_firstName;
//
//    @FXML
//    private TableColumn<?, ?> customers_gender;
//
//    @FXML
//    private TableColumn<?, ?> customers_lastName;
//
//    @FXML
//    private TableColumn<?, ?> customers_location;
//
//    @FXML
//    private TableColumn<?, ?> customers_phoneNum;
//
//    @FXML
//    private TextField customers_search;
//
//    @FXML
//    private TableColumn<?, ?> customers_ticketNum;
//
//    @FXML
//    private TableColumn<?, ?> customers_type;
//
//    @FXML
//    private Button dashboard_Btn;
//
//    @FXML
//    private Label dashboard_availableB;
//
//    @FXML
//    private AreaChart<?, ?> dashboard_chart;
//
//    @FXML
//    private AnchorPane dashboard_form;
//
//    @FXML
//    private Label dashboard_incomeToday;
//
//    @FXML
//    private Label dashboard_totalIncome;
//
//    @FXML
//    private Button logout;
//
//    @FXML
//    private Button minimize;
//
//    @FXML
//    private Label username;
//
//    //database tools
//    private Connection connect;
//    private PreparedStatement prepare;
//    private ResultSet result;
//    private Statement statement;
//
//    public void availableBusAdd() {
//        // SQL query to insert a new bus record into the database
//        String addData = "INSERT INTO bus (bus_id, location, status, price, date) VALUES(?,?,?,?,?)";
//
//        // Establish a connection to the database
//        connect = database.connectDb();
//
//        try {
//            // Check if any of the required fields are empty
//            if (availableB_busID.getText().isEmpty()
//                    || availableB_location.getText().isEmpty()
//                    || availableB_status.getSelectionModel().getSelectedItem() == null
//                    || availableB_price.getText().isEmpty()
//                    || availableB_date.getValue() == null) {
//
//                // Display an error alert if any field is empty
//                showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all blank fields");
//            } else {
//                // SQL query to check if the bus ID already exists in the database
//                String check = "SELECT bus_id FROM bus WHERE bus_id = '" + availableB_busID.getText() + "'";
//
//                // Execute the query to check for existing bus ID
//                statement = connect.createStatement();
//                result = statement.executeQuery(check);
//
//                // Check if the bus ID already exists
//                if (result.next()) {
//                    // Display an error alert if the bus ID already exists
//                    showAlert(Alert.AlertType.ERROR, "Error Message", "Bus ID: " + availableB_busID.getText() + " already exists!");
//                } else {
//                    // Prepare the SQL statement for inserting a new bus record
//                    prepare = connect.prepareStatement(addData);
//                    prepare.setString(1, availableB_busID.getText());
//                    prepare.setString(2, availableB_location.getText());
//                    prepare.setString(3, (String) availableB_status.getSelectionModel().getSelectedItem());
//                    prepare.setString(4, availableB_price.getText());
//                    prepare.setString(5, String.valueOf(availableB_date.getValue()));
//
//                    // Execute the insert operation
//                    prepare.executeUpdate();
//
//                    // Display a confirmation alert on successful addition
//                    showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Added!");
//
//                    // Refresh the table view to show the newly added bus data
//                    availableBShowBusData();
//                    // Reset the form fields to their default state
//                    availableBusReset();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception for debugging purposes
//            // Optionally, show an alert or log this error
//        } finally {
//            // Close the database connection and other resources to prevent memory leaks
//            closeDatabaseResources();
//        }
//    }
//
//    public void availableBusUpdate() {
//        // SQL query to update a bus record in the database
//        String updateData =
//                "UPDATE bus SET location = '" + availableB_location.getText()
//                        + "', status = '" + availableB_status.getSelectionModel().getSelectedItem()
//                        + "', price = '" + availableB_price.getText()
//                        + "', date = '" + availableB_date.getValue()
//                        + "' WHERE bus_id = '" + availableB_busID.getText() + "'";
//
//        // Establish a connection to the database
//        connect = database.connectDb();
//
//        try {
//            // Check if any of the required fields are empty
//            if (availableB_busID.getText().isEmpty()
//                    || availableB_location.getText().isEmpty()
//                    || availableB_status.getSelectionModel().getSelectedItem() == null
//                    || availableB_price.getText().isEmpty()
//                    || availableB_date.getValue() == null) {
//
//                // Display an error alert if any field is empty
//                showAlert(Alert.AlertType.ERROR, "Error Message", "Please select the item first");
//            } else {
//                // Confirmation alert to confirm the update operation
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Confirmation Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Are you sure you want to UPDATE Bus ID: " + availableB_busID.getText() + "?");
//
//                Optional<ButtonType> option = alert.showAndWait();
//
//                // If the user confirms, proceed with the update
//                if (option.get().equals(ButtonType.OK)) {
//                    // Prepare and execute the update statement
//                    prepare = connect.prepareStatement(updateData);
//                    prepare.executeUpdate();
//
//                    // Display a confirmation alert on successful update
//                    showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Updated!");
//
//                    // Refresh the table view to show the updated bus data
//                    availableBShowBusData();
//                    // Reset the form fields to their default state
//                    availableBusReset();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception for debugging purposes
//            // Optionally, show an alert or log this error
//        } finally {
//            // Close the database connection and other resources to prevent memory leaks
//            closeDatabaseResources();
//        }
//    }
//
//
//    public void availableBusDelete() {
//        // SQL query to delete a bus record from the database
//        String deleteData = "DELETE FROM bus WHERE bus_id = '" + availableB_busID.getText() + "'";
//
//        // Establish a connection to the database
//        connect = database.connectDb();
//
//        try {
//            // Check if any of the required fields are empty
//            if (availableB_busID.getText().isEmpty()
//                    || availableB_location.getText().isEmpty()
//                    || availableB_status.getSelectionModel().getSelectedItem() == null
//                    || availableB_price.getText().isEmpty()
//                    || availableB_date.getValue() == null) {
//
//                // Display an error alert if any field is empty
//                showAlert(Alert.AlertType.ERROR, "Error Message", "Please select the item first");
//            } else {
//                // Confirmation alert to confirm the deletion operation
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Confirmation Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Are you sure you want to delete Bus ID: " + availableB_busID.getText() + "?");
//
//                Optional<ButtonType> option = alert.showAndWait();
//
//                // If the user confirms, proceed with the deletion
//                if (option.get().equals(ButtonType.OK)) {
//                    // Execute the delete statement
//                    statement = connect.createStatement();
//                    statement.executeUpdate(deleteData);
//
//                    // Display a confirmation alert on successful deletion
//                    showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Deleted!");
//
//                    // Refresh the table view to reflect the deletion
//                    availableBShowBusData();
//                    // Reset the form fields to their default state
//                    availableBusReset();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception for debugging purposes
//            // Optionally, show an alert or log this error
//        } finally {
//            // Close the database connection and other resources to prevent memory leaks
//            closeDatabaseResources();
//        }
//    }
//    public void availableBusReset(){
//        availableB_busID.setText("");
//        availableB_location.setText("");
//        availableB_status.getSelectionModel().clearSelection();
//        availableB_price.setText("");
//        availableB_date.setValue(null);
//    }
//
//    // Array containing the status options for the bus
//    private String [] statusList = {"Available", "Not Available"};
//
//    public void comboBoxStatus() {
//        // Create a new list to hold status options
//        List<String> listS = new ArrayList<>();
//
//        // Loop through each status in the statusList array
//        for(String data: statusList) {
//            // Add each status to the list
//            listS.add(data);
//        }
//
//        // Convert the list to an ObservableList which is required for the ComboBox
//        ObservableList listStatus = FXCollections.observableArrayList(listS);
//
//        // Set the items of the ComboBox to the ObservableList of statuses
//        availableB_status.setItems(listStatus);
//    }
//
//
//    /**
//     * Retrieves a list of bus data from the database.
//     *
//     * @return An ObservableList of busData objects, each representing a row from the bus table.
//     */
//    public ObservableList<busData> availableBusBusData() {
//        // Create an empty ObservableList to hold bus data
//        ObservableList<busData> busListData = FXCollections.observableArrayList();
//
//        // SQL query to select all records from the bus table
//        String sql = "SELECT * FROM bus";
//
//        // Establish a connection to the database
//        connect = database.connectDb();
//        try {
//            // Prepare the SQL statement for execution
//            prepare = connect.prepareStatement(sql);
//            // Execute the query and store the result set
//            result = prepare.executeQuery();
//
//            // Iterate over each row in the result set
//            while (result.next()) {
//                // Create a new busData object from the current row's data
//                busData busD = new busData(
//                        result.getInt("bus_id"),
//                        result.getString("location"),
//                        result.getString("status"),
//                        result.getDouble("price"),
//                        result.getDate("date")
//                );
//
//                // Add the busData object to the list
//                busListData.add(busD);
//            }
//
//            // Return the list of bus data
//            return busListData;
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception for debugging purposes
//            // Optionally, show an alert or log this error
//        } finally {
//            // Close the database connection and other resources to prevent memory leaks
//            closeDatabaseResources();
//        }
//
//        // Return the list, which may be empty if an exception occurred
//        return busListData;
//    }
//
//// Method to close database resources (not shown here)
//
//
//    // Field to store the list of bus data
//    private ObservableList<busData> availableBBusListData;
//
//    /**
//     * Populates the TableView with bus data.
//     * This method fetches bus data from the database and sets it to the TableView.
//     */
//    public void availableBShowBusData() {
//        // Retrieve bus data from the database and store it in availableBBusListData
//        availableBBusListData = availableBusBusData();
//
//        // Set up the TableView columns to display the bus data
//        // The PropertyValueFactory will map the data to the respective columns based on the property names
//
//        // Map the 'busId' property of busData objects to the 'busID' column in the TableView
//        availableB_col_busID.setCellValueFactory(new PropertyValueFactory<>("busId"));
//        // Map the 'location' property
//        availableB_col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
//        // Map the 'status' property
//        availableB_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
//        // Map the 'price' property
//        availableB_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
//        // Map the 'date' property
//        availableB_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
//
//        // Set the ObservableList of bus data as the items of the TableView
//        // This will update the TableView to display the data
//        availableB_tableView.setItems(availableBBusListData);
//    }
//
//
//    /**
//     * Handles the selection of a bus from the TableView and updates the form fields with the selected bus's data.
//     */
//    public void availableBSelectBusData() {
//        // Retrieve the selected busData object from the TableView
//        busData busD = availableB_tableView.getSelectionModel().getSelectedItem();
//        // Get the index of the selected item in the TableView
//        int num = availableB_tableView.getSelectionModel().getSelectedIndex();
//
//        // Check if the selection is valid (i.e., an item is actually selected)
//        if ((num - 1) < -1) {
//            // If no item is selected, exit the method
//            return;
//        }
//
//        // Set the form fields to the values from the selected busData object
//        // Update the bus ID field
//        availableB_busID.setText(String.valueOf(busD.getBusId()));
//        // Update the location field
//        availableB_location.setText(String.valueOf(busD.getLocation()));
//        // Update the price field
//        availableB_price.setText(String.valueOf(busD.getPrice()));
//        // Update the date field, converting the Date object to a LocalDate object
//        availableB_date.setValue(LocalDate.parse(String.valueOf(busD.getDate())));
//    }
//
//
//    /**
//     * Configures the search functionality for the TableView.
//     * It filters the table data based on the user's search input.
//     */
//    public void availableSearch() {
//        // Create a filtered list wrapper around the original list of bus data
//        FilteredList<busData> filter = new FilteredList<>(availableBBusListData, e -> true);
//
//        // Add a listener to the search text field to react to text changes
//        availableB_search.textProperty().addListener((Observable, oldValue, newValue) -> {
//            System.out.println("Search query changed: " + newValue); // Debugging statement
//
//            // Define the criteria for filtering the bus data based on the search query
//            filter.setPredicate(predicateBusData -> {
//                // If the search field is empty, display all data (no filter)
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//
//                // Convert the search query to lower case for case-insensitive comparison
//                String searchKey = newValue.toLowerCase();
//
//                // Check if the bus data matches the search query in any of the columns
//                if (predicateBusData.getBusId().toString().contains(searchKey)) {
//                    return true; // Match found in bus ID
//                } else if (predicateBusData.getLocation().toLowerCase().contains(searchKey)) {
//                    return true; // Match found in location
//                } else if (predicateBusData.getStatus().toLowerCase().contains(searchKey)) {
//                    return true; // Match found in status
//                } else if (predicateBusData.getDate().toString().contains(searchKey)) {
//                    return true; // Match found in date
//                } else if (predicateBusData.getPrice().toString().contains(searchKey)) {
//                    return true; // Match found in price
//                } else {
//                    return false; // No match found
//                }
//            });
//        });
//
//        // Create a sorted list wrapper around the filtered list
//        SortedList<busData> sortList = new SortedList<>(filter);
//
//        // Bind the comparator of the sorted list to the comparator of the TableView
//        // This ensures sorting is preserved while filtering
//        sortList.comparatorProperty().bind(availableB_tableView.comparatorProperty());
//
//        // Set the sorted and filtered list as the items of the TableView
//        availableB_tableView.setItems(sortList);
//    }