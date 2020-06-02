package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.DatabaseHandler;
import sample.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeController {
    ObservableList<Employee> employeesData = FXCollections.observableArrayList();

    @FXML
    private Button emplButton;

    @FXML
    private TextField lnameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField snameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField departField;

    @FXML
    private TableView<Employee> table;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> positionColumn;

    @FXML
    private TableColumn<Employee, Integer> idColumn;

    @FXML
    private TableColumn<Employee, String> departColumn;

    @FXML
    void initialize() throws SQLException {
        initData();

        //добавление нового сотрудника
        emplButton.setOnAction(actionEvent -> {
            DatabaseHandler dbHandler = new DatabaseHandler();
            dbHandler.insertEmployee(Integer.parseInt(idField.getText()), lnameField.getText(), nameField.getText(), snameField.getText(),
                    positionField.getText(), Integer.parseInt(departField.getText()));
            try {
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void initData() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        employeesData.clear();

        ResultSet set = dbHandler.selectName();
        while (set.next()){
            String name = set.getString("last_name")
                    + " " + set.getString("name") + " " + set.getString("second_name");
            employeesData.add(new Employee(set.getInt("tab_number"), name, set.getString("position"),
                    set.getString("departments.name")));

        }

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        departColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        table.setItems(employeesData);

        //изменение должности
        positionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        positionColumn.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            TablePosition<Employee, String> pos = event.getTablePosition();
            String position = event.getNewValue();
            int row = pos.getRow();
            Employee employee = event.getTableView().getItems().get(row);
            employee.setPosition(position);
            int id = employee.getId();
            dbHandler.updatePosition(id, position);
        });

        //изменение департамента
        departColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        departColumn.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            TablePosition<Employee, String> pos = event.getTablePosition();
            String department = event.getNewValue();
            int row = pos.getRow();
            Employee employee = event.getTableView().getItems().get(row);
            employee.setDepartment(department);
            int id = employee.getId();
            ResultSet resultSet = dbHandler.selectDepartment(department);
            int idDep = 0;
            try {
                resultSet.last();
                idDep = resultSet.getInt("iddepart");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dbHandler.updateDepartment(id, idDep);
        });

    }
}
