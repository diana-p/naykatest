package sample;

import java.util.ArrayList;
import java.util.Date;

public class Employee {
    public int id;
    public String name;
    public String position;
    public ArrayList<Date> data;
    public ArrayList<String> mark;
    public String department;


    public Employee(int id, String name, String position, ArrayList<Date> data, ArrayList<String> mark) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.data = data;
        this.mark = mark;
    }

    public Employee(int id, String name, String position, String department) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ArrayList<Date> getData() {
        return data;
    }

    public void setData(ArrayList<Date> data) {
        this.data = data;
    }

    public ArrayList<String> getMark() {
        return mark;
    }

    public String getMark(int i) {
        return mark.get(i);
    }

    public void setMark(ArrayList<String> mark) {
        this.mark = mark;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
