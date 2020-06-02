package sample.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.DatabaseHandler;
import sample.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewController {
    private int department = 1;
    private int month = 1;
    private ObservableList<Employee> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView<Employee> table;

    @FXML
    private Button january;

    @FXML
    private Button february;

    @FXML
    private Button march;

    @FXML
    private Button april;

    @FXML
    private Button may;

    @FXML
    private Button june;

    @FXML
    private Button july;

    @FXML
    private Button augest;

    @FXML
    private Button september;

    @FXML
    private Button october;

    @FXML
    private Button november;

    @FXML
    private Button december;
    @FXML
    private Button id1;

    @FXML
    private Button id2;

    @FXML
    private Button id3;

    @FXML
    void initialize() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();

        ResultSet resultSet = dbHandler.selectDepartment();
        resultSet.next();
        id1.setText(resultSet.getString("name"));
        resultSet.next();
        id2.setText(resultSet.getString("name"));
        resultSet.next();
        id3.setText(resultSet.getString("name"));

        id1.setOnAction(actionEvent -> {
            department = 1;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        id2.setOnAction(actionEvent -> {
            department = 2;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        id3.setOnAction(actionEvent -> {
            department = 3;
            try {
                usersData.clear();
                table.getColumns().clear();
                table.refresh();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        january.setOnAction(actionEvent -> {
            month = 1;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        february.setOnAction(actionEvent -> {
            month = 2;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        march.setOnAction(actionEvent -> {
            month = 3;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        april.setOnAction(actionEvent -> {
            month = 4;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        may.setOnAction(actionEvent -> {
            month = 5;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        june.setOnAction(actionEvent -> {
            month = 6;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        july.setOnAction(actionEvent -> {
            month = 7;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        augest.setOnAction(actionEvent -> {
            month = 8;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        september.setOnAction(actionEvent -> {
            month = 9;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        october.setOnAction(actionEvent -> {
            month = 10;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        november.setOnAction(actionEvent -> {
            month = 11;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        december.setOnAction(actionEvent -> {
            month = 12;
            try {
                usersData.clear();
                table.getColumns().clear();
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        initData();
    }


    public void initData() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        TableColumn<Employee, String> nameColumn = new TableColumn<>("ФИО");
        TableColumn<Employee, String> positionColumn = new TableColumn<>("Должность");
        TableColumn<Employee, Integer> idColumn = new TableColumn<>("Табельный №");
        int numberRows = 0;

        //заносим фамилии, должности и id в таблицу
        ResultSet resultSet = dbHandler.selectName(month, department);
        while (resultSet.next()) {
            ArrayList<Date> date = createData(dbHandler.selectMark(resultSet.getInt("id_empl"), month));
            ArrayList<String> mark = createMark(dbHandler.selectMark(resultSet.getInt("id_empl"), month));
            String name = resultSet.getString("last_name")
                    + " " + resultSet.getString("name") + " " + resultSet.getString("second_name");
            usersData.add(new Employee(resultSet.getInt("id_empl"), name, resultSet.getString("position"), date, mark));
            numberRows++;
        }


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(nameColumn);
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        table.getColumns().add(positionColumn);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        table.getColumns().add(idColumn);
        table.setItems(usersData);

        ResultSet set = dbHandler.selectFromCalendar(month);
        while (set.next()) {
            TableColumn<Employee, String> day = new TableColumn<>(new SimpleDateFormat("dd").format(set.getDate("data")));
            day.setCellFactory(userStringTableColumn -> new TextFieldTableCell<>());
            table.getColumns().add(day);
            for (int i = 0; i < numberRows; i++) {
                int id = idColumn.getCellData(i);
                ResultSet set1 = dbHandler.selectMark(id, month, set.getDate("data"));
                set1.last();
                day.setCellValueFactory(userStringCellDataFeatures -> {
                    SimpleStringProperty s = null;
                    try {
                        s = new SimpleStringProperty(set1.getString("mark"));
                    } catch (SQLException e) {
                        //e.printStackTrace();
                    }
                    return s;
                });
            }
        }
    }

    public ArrayList<Date> createData(ResultSet resSet) throws SQLException {
        ArrayList<Date> data = new ArrayList<>();
        while (resSet.next()) {
            data.add(resSet.getDate("data"));
        }
        return data;
    }

    public ArrayList<String> createMark(ResultSet resSet) throws SQLException {
        ArrayList<String> mark = new ArrayList<>();

        while (resSet.next()) {
            mark.add(resSet.getString("mark"));
        }
        return mark;
    }
}
