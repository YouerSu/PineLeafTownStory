package com.example.administrator.character;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.buildings.Building;
import com.example.administrator.item.Item;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.OwnName;
import com.example.administrator.buildings.ShowAdapter;
import com.example.administrator.utils.Sql;
import com.example.administrator.utils.Tools;

import java.util.LinkedList;
import java.util.Map;

public abstract class Character implements OwnName,ShowAdapter{
    public static LinkedList<Character> characters = new LinkedList<>();
    private String name;
    private int money;
    private int prestige;
    private int x_coordinate;
    private int salary;
    private String workSpace;

    @Override
    public Map<String, String> UIPageAdapter() {
        return GameUI.getAdapterMap(name,"薪水："+salary,"工作地点："+workSpace,"职业："+getClass().getName().substring(getClass().getName().lastIndexOf(".")+1));
    }

    abstract void initialization();


//    public void showMyOwnOnClick(GameUI UI) {
//        new Mall("Employee",0,0,0,"Employee").showMyOwnOnClick(UI);
//
//    }

    @Override
    public void onClick(GameUI gameUI) {
        gameUI.dialogueBox(name+":你好,"+Player.getPlayerName());
//        if (Character.getFirstMaster(Character.findMaster(workSpace,Building.getBuildings())).getMaster().equals(Player.getPlayerName()))
//            showMyOwnOnClick(gameUI);
//        else
//            showNotMyOwnOnClick(gameUI);
        /*招聘改为在商店放入一个招聘品XD*/
    }

//    public void showNotMyOwnOnClick(GameUI UI ) {
//        new Mall("Character",0,0,0,"Character").showMyOwnOnClick(UI);
//    }

    public static void getAllDate(){
        Building.getBuildingDate();
        getDate(Sql.getDateBase().rawQuery("select * from "+Info.CHARACTER,null));
    }

    public static void createNewCharacter(SQLiteDatabase db, String className, String name, int money, int prestige, int x_coordinate, int salary, String workSpace){
        if (!(Tools.getType(className) instanceof Character)) return;
        db.execSQL
        ("insert into "+Info.CHARACTER+" ("+Info.id+","+Info.NAME+","+Info.MONEY+","+Info.PRESTIGE+","+Info.coordinate+","+Info.salary+","+Info.MASTER+") values ('"+className+"','"+name+"',"+money+","+prestige+","+x_coordinate+","+salary+",'"+workSpace+"')");
        Character character = Tools.getType(className);
        character.setName(name);
        character.setMoney(money);
        character.setPrestige(prestige);
        character.setX_coordinate(x_coordinate);
        character.setSalary(salary);
        character.setWorkSpace(workSpace);
        getCharacters().add(character);
    }

    public static void saveBuildingDate(){
        Sql.operatingSql(new String[]{"DELETE FROM "+Info.BUILDING});
        for (Building building: Building.getBuildings())
            building.saveDate();
    }


    public static void saveCharacterDate(){
        for (Character character:characters)
            character.saveDate();
    }

    public static void saveAllDate() {
        saveBuildingDate();
        saveCharacterDate();
    }


    public void saveDate(){
        Sql.operatingSql(new String[]{
        "update "+Info.CHARACTER+" set "+Info.id+" = '"+getClass().getName()+"',"+Info.MONEY+" = "+money+","+Info.PRESTIGE+" = "+prestige+","+Info.coordinate+" = "+x_coordinate+","+Info.salary+" = "+salary+","+Info.MASTER+" = '"+ workSpace +"' where "+Info.NAME+" = '"+ name +"'"
        });
    }

    public static void getDate(Cursor iDate) {
        while (iDate.moveToNext()){
        Character character = Tools.getType(iDate.getString(iDate.getColumnIndex(Info.id)));
        character.setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        character.setMoney(iDate.getInt(iDate.getColumnIndex(Info.MONEY)));
        character.setPrestige(iDate.getInt(iDate.getColumnIndex(Info.PRESTIGE)));
        character.setX_coordinate(iDate.getInt(iDate.getColumnIndex(Info.coordinate)));
        character.setWorkSpace(iDate.getString(iDate.getColumnIndex(Info.MASTER)));
        character.setSalary(iDate.getInt(iDate.getColumnIndex(Info.salary)));
        character.initialization();
        characters.add(character);
        }
        iDate.close();
    }

    public void wages(){
        for (Character character: Tools.findMaster(Building.findWorkSpace(workSpace).getMaster(),characters))
            character.setMoney(character.getMoney()-getSalary());
        setMoney(getMoney()+getSalary());
    }

    public static void findWorker(String buildingName,Item item){
        for (Character employee: Tools.findMaster(buildingName,characters))
            if (employee instanceof Employee &&((Employee) employee).work(item))
                return;

    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }


    public int getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(int x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public void setWorkSpace(String workSpace) {
        this.workSpace = workSpace;
    }

    public static LinkedList<Character> getCharacters() {
        return characters;
    }

    public static void setCharacters(LinkedList<Character> characters) {
        Character.characters = characters;
    }


    public String getWorkSpace() {
        return workSpace;
    }
}


