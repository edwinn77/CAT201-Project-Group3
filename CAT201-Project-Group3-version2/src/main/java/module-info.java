module com.example.busticketbookingmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.busticketbookingmanagementsystem to javafx.fxml;
    exports com.example.busticketbookingmanagementsystem;
}