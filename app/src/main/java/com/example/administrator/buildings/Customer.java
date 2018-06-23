package com.example.administrator.buildings;

import com.example.administrator.utils.NPC;

import java.util.List;
import java.util.Random;

public class Customer implements NPC {

    private int xCoordinate;
    private int money;
    private Article want;
    private int life = 100;

    public Customer(int coordinate, int money, Article want) {
        this.xCoordinate = coordinate;
        this.money = money;
        this.want = want;
    }

    public void work(){

    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Article getWant() {
        return want;
    }

    public void setWant(Article want) {
        this.want = want;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public static void randomList(Customer[] customers,int coordinateBoundary,int moneyBoundary) {
        Article article[] = Article.values();
        Random random = new Random();
        for (int count = 0;count<customers.length;count++){
            customers[count] = new Customer(random.nextInt(coordinateBoundary),random.nextInt(moneyBoundary),article[random.nextInt(article.length)]);
        }
    }
}
