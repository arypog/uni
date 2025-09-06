package com.winxclub.model;

public class Fairy {
	private int id;
	private String name;
	private String power;
	private int age;

	public Fairy() {
	}

	public Fairy(String name, String power, int age) {
		this.name = name;
		this.power = power;
		this.age = age;
	}

	public Fairy(int id, String name, String power, int age) {
		this.id = id;
		this.name = name;
		this.power = power;
		this.age = age;
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

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}