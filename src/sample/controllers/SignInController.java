package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DatabaseHandler;
import sample.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInController {
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private Button signInButton;

    @FXML
    void initialize() {
        signInButton.setOnAction(actionEvent -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();
            int role = 0;
            String r;

            if(!login.equals("") && !password.equals("")){
                try {
                    role = signInUser(login, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Fields is empty");
            }

            //отображение функциональности
            switch (role){
                case 1:
                    r = "/sample/views/reportView.fxml";
                    break;
                case 2:
                    r = "/sample/views/departView.fxml";
                    break;
                case 3:
                    r = "/sample/views/employeeView.fxml";
                    break;
                default:
                    r = "/sample/views/view.fxml";
            }

            signInButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(r));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    private int signInUser(String login, String password) throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        int counter = 0;
        int role = 0;
        User user = new User();

        //получение роли для отображения нужной функциональности
        user.setLogin(login);
        user.setPassword(password);
        ResultSet resultSet = dbHandler.getUser(user);

        while (resultSet.next()){
            counter++;
            role = resultSet.getInt("role");
        }

        if (counter >= 1){
            return role;
        }
        else {
            return -1;
        }
    }
}
