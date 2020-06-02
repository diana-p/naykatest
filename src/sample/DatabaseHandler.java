package sample;

import java.sql.*;

public class DatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName + "?serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public ResultSet selectMark(int id, int month){
        ResultSet resSet = null;
        String select = "SELECT data, mark FROM report " +
                "WHERE id_empl = " + id + " AND MONTH(data) = " + month;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet selectMark(int id, int month, Date data){
        ResultSet resSet = null;
        String select = "SELECT mark FROM report " +
                "WHERE id_empl = " + id + " AND MONTH(data) = " + month
                + " AND data = \"" + data + "\"";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet selectName (int month, int depart) {
        ResultSet resSet = null;
        String select = "SELECT data, id_empl, last_name, employees.name, second_name, position, departments.name " +
                "FROM report INNER JOIN employees ON report.id_empl = employees.tab_number " + "INNER JOIN departments ON " +
                "employees.id_depart = departments.iddepart " +
                "WHERE MONTH(data) = " + month + " AND id_depart = " + depart +
                " GROUP BY id_empl";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet selectName () {
        ResultSet resSet = null;
        String select = "SELECT tab_number, last_name, employees.name, second_name, position, departments.name " +
                "FROM employees INNER JOIN departments ON " +
                "employees.id_depart = departments.iddepart ";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet selectDepartment() {
        ResultSet resSet = null;
        String select = "SELECT name FROM departments";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    public ResultSet selectDepartment(String name) {
        ResultSet resSet = null;
        String select = "SELECT iddepart FROM departments " +
                "WHERE name = \"" + name + "\"";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    public void updateDepartment(String newName, String oldName){
        String update = "UPDATE departments SET name = \"" + newName +
                "\" WHERE name = \"" + oldName + "\"";

        try {
            PreparedStatement prS = getDbConnection().prepareStatement(update);
            prS.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updatePosition(int id, String position){
        String update = "UPDATE employees SET position = \"" + position +
                "\" WHERE tab_number = " + id;
        try {
            PreparedStatement prS = getDbConnection().prepareStatement(update);
            prS.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateDepartment(int id, int idD){
        String update = "UPDATE employees SET id_depart = \"" + idD +
                "\" WHERE tab_number = " + id;
        try {
            PreparedStatement prS = getDbConnection().prepareStatement(update);
            prS.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertEmployee(int tabN, String lastN, String n, String secN, String pos, int id_dep){
        String insert = "INSERT INTO employees (tab_number, last_name, name, second_name, position, id_depart)" +
                " VALUES (" + tabN + ", \"" + lastN + "\", \"" + n + "\", \"" + secN + "\", \"" + pos +
                "\", " + id_dep + ")" ;
        try {
            PreparedStatement prS = getDbConnection().prepareStatement(insert);
            prS.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addDepartment(String name){
        String insert = "INSERT INTO departments (name) VALUES (\"" + name + "\")";

        try {
            PreparedStatement prS = getDbConnection().prepareStatement(insert);
            prS.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ResultSet selectFromCalendar(int month){
        ResultSet resSet = null;
        String select = "SELECT data, type_day " +
                "FROM calendar " +
                "WHERE MONTH(data) = " + month +
                " ORDER BY data";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    public ResultSet getUser(User user){
        ResultSet resSet = null;
        String select = "SELECT * FROM users " +
                "WHERE login = \"" + user.getLogin() +
                "\" AND password = " + user.getPassword();
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    public void setMark(String mark, Employee employee, String day, int month){
        String insert = "INSERT INTO report (data, id_empl, mark) VALUES (\"2020-" + month + "-" + day + "\", " +
                employee.getId() + ", \"" + mark + "\")";
        try {
            PreparedStatement prS = getDbConnection().prepareStatement(insert);
            prS.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
