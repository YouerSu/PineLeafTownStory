package com.example.administrator.storeboss;

public class Building {
    public int loyalty;
    public String name;
    public int strongLevel;
    public int clever;
    public int salary;
    public int capacity;
    public int customer;

    public Building(int loyalty, String name, int strongLevel, int clever, int salary, int capacity, int customer) {
        this.loyalty = loyalty;
        this.name = name;
        this.strongLevel = strongLevel;
        this.clever = clever;
        this.salary = salary;
        this.capacity = capacity;
        this.customer = customer;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer += customer;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty += loyalty;
    }

    public void setName(String name) {
        this.name += name;
    }

    public void setStrongLevel(int strongLevel) {
        this.strongLevel += strongLevel;
    }

    public void setClever(int clever) {
        this.clever += clever;
    }

    public void setSalary(int salary) {
        this.salary += salary;
    }

    public void setCapacity(int capacity) {
        this.capacity += capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getStrongLevel() {

        return strongLevel;
    }

    public int getSalary() {
        return salary;
    }

    public int getClever() {
        return clever;
    }

    public int getLoyalty() {
        return loyalty;
    }

    public String getstrangeName() {
        return name;
    }


    public void setcustomer() {
        this.customer = 0;
    }
}
