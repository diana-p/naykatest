package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import sample.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartController {
    @FXML
    private Button departButton;

    @FXML
    private TextField departField;

    @FXML
    private ListView<String> departList;

    @FXML
    void initialize() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        initData();

        //изменение департмента
        departList.setEditable(true);
        departList.setCellFactory(TextFieldListCell.forListView());
        departList.setOnEditCommit(stringEditEvent -> {
            String old = departList.getSelectionModel().getSelectedItem();
            departList.getItems().set(stringEditEvent.getIndex(), stringEditEvent.getNewValue());
            String s = stringEditEvent.getNewValue();
            dbHandler.updateDepartment(s, old);
        });

        //добавление департамента
        departButton.setOnAction(actionEvent -> {
            String dep = departField.getText().trim();
            dbHandler.addDepartment(dep);
            try {
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void initData() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<String> departments = FXCollections.observableArrayList();
        departments.removeAll();
        departList.getItems().clear();

        ResultSet set = dbHandler.selectDepartment();
        while(set.next()) {
            departments.add(set.getString("name"));
        }

        departList.getItems().addAll(departments);

    }
}
