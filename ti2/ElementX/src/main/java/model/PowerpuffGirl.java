package model;

public class PowerpuffGirl {
    private int id;
    private String name;
    private String superpower;
    private int age;

    public PowerpuffGirl() {}

    public PowerpuffGirl(String name, String superpower, int age) {
        this.name = name;
        this.superpower = superpower;
        this.age = age;
    }

    public PowerpuffGirl(int id, String name, String superpower, int age) {
        this.id = id;
        this.name = name;
        this.superpower = superpower;
        this.age = age;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSuperpower() { return superpower; }
    public void setSuperpower(String superpower) { this.superpower = superpower; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
