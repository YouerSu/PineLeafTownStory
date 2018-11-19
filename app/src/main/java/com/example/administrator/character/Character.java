package com.example.administrator.character;

import android.database.Cursor;

import com.example.administrator.listener.Click;
import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.item.Tool;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.OwnMaster;
import com.example.administrator.utils.OwnName;
import com.example.administrator.buildings.ShowAdapter;
import com.example.administrator.utils.Sql;
import com.example.administrator.utils.Tools;

import java.util.LinkedList;
import java.util.Map;

public abstract class Character implements OwnName,OwnMaster,ShowAdapter,NPC{
    public static LinkedList<Character> characters = new LinkedList<>();
    private String name;
    private int money;
    private int prestige;
    private int x_coordinate;
    private int salary;
    private String workSpace;
    public Click click;

     abstract void init();

    @Override
    public void behavior() {
        for (Tool tool: Building.getWhere(x_coordinate).services())
            if (tool.receive(this)&&tool.use(this)) break;

    }


    @Override
    public Map<String, String> UIPageAdapter() {
        return GameUI.getAdapterMap(name,"薪水："+salary,"工作地点："+workSpace,"职业："+Tools.getSuffix(getClass().getName()));
    }

    @Override
    public void onClick(GameUI gameUI) {
        click.onClick(gameUI,this);
    }

    public static void getAllDate(){
        Building.getBuildingDate();
        getDate(Sql.getDateBase().rawQuery("select * from "+ Info.INSTANCE.getCHARACTER(),null));
    }

    public static void getDate(Cursor iDate) {
        while (iDate.moveToNext()){
            Character character = Tools.getType(iDate.getString(iDate.getColumnIndex(Info.INSTANCE.getId())));
            character.setName(iDate.getString(iDate.getColumnIndex(Info.INSTANCE.getNAME())));
            character.setMoney(iDate.getInt(iDate.getColumnIndex(Info.INSTANCE.getMONEY())));
            character.setPrestige(iDate.getInt(iDate.getColumnIndex(Info.INSTANCE.getPRESTIGE())));
            character.setX_coordinate(iDate.getInt(iDate.getColumnIndex(Info.INSTANCE.getCoordinate())));
            character.setMaster(iDate.getString(iDate.getColumnIndex(Info.INSTANCE.getMASTER())));
            character.setSalary(iDate.getInt(iDate.getColumnIndex(Info.INSTANCE.getSalary())));
            character.init();
            characters.add(character);
        }
        iDate.close();
    }

    public static void saveAllDate() {
        saveBuildingDate();
        saveCharacterDate();
    }

    public static void saveBuildingDate(){
        Sql.operating(new String[]{"DELETE FROM "+ Info.INSTANCE.getBUILDING()});
        for (Building building: Building.getBuildings())
            building.saveDate();
    }


    public static void saveCharacterDate(){
        for (Character character:characters)
            character.saveDate();
    }

    public void saveDate(){
        Sql.operating(new String[]{
        "update "+ Info.INSTANCE.getCHARACTER() +" set "+ Info.INSTANCE.getId() +" = '"+getClass().getName()+"',"+ Info.INSTANCE.getMONEY() +" = "+money+","+ Info.INSTANCE.getPRESTIGE() +" = "+prestige+","+ Info.INSTANCE.getCoordinate() +" = "+x_coordinate+","+ Info.INSTANCE.getSalary() +" = "+salary+","+ Info.INSTANCE.getMASTER() +" = '"+ workSpace +"' where "+ Info.INSTANCE.getNAME() +" = '"+ name +"'"
        });
    }

    public static void createNewCharacter( String className, String name, int money, int prestige, int x_coordinate, int salary, String workSpace){
        if (!(Tools.getType(className) instanceof Character)) return;
        Sql.getDateBase().execSQL
        ("insert into "+ Info.INSTANCE.getCHARACTER() +" ("+ Info.INSTANCE.getId() +","+ Info.INSTANCE.getNAME() +","+ Info.INSTANCE.getMONEY() +","+ Info.INSTANCE.getPRESTIGE() +","+ Info.INSTANCE.getCoordinate() +","+ Info.INSTANCE.getSalary() +","+ Info.INSTANCE.getMASTER() +") values ('"+className+"','"+name+"',"+money+","+prestige+","+x_coordinate+","+salary+",'"+workSpace+"')");
        Character character = Tools.getType(className);
        character.setName(name);
        character.setMoney(money);
        character.setPrestige(prestige);
        character.setX_coordinate(x_coordinate);
        character.setSalary(salary);
        character.setMaster(workSpace);
        getCharacters().add(character);
    }

    public void wages(){
        String masterName = Tools.findMaster(workSpace,Building.getBuildings()).getMaster();
        Character master = Tools.findMaster(masterName,characters);
        master.setMoney(master.getMoney()-getSalary());
        setMoney(getMoney()+getSalary());
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

    @Override
    public void setMaster(String workSpace) {
        this.workSpace = workSpace;
    }

    public static LinkedList<Character> getCharacters() {
        return characters;
    }

    public static void setCharacters(LinkedList<Character> characters) {
        Character.characters = characters;
    }

    @Override
    public String getMaster() {
        return workSpace;
    }
}