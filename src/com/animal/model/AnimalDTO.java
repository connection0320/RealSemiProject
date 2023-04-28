package com.animal.model;

public class AnimalDTO {
	int animal_num;
	String animal_name;
	int animal_age;
	String animal_race;
	String animal_gender; // 1 수컷 2 암컷
	String animal_type; // 1 강아지 2 고양이
	String animal_image;
	
	public String getAnimal_image() {
		return animal_image;
	}
	public void setAnimal_image(String animal_image) {
		this.animal_image = animal_image;
	}
	public int getAnimal_num() {
		return animal_num;
	}
	public void setAnimal_num(int animal_num) {
		this.animal_num = animal_num;
	}
	public String getAnimal_name() {
		return animal_name;
	}
	public void setAnimal_name(String animal_name) {
		this.animal_name = animal_name;
	}
	public int getAnimal_age() {
		return animal_age;
	}
	public void setAnimal_age(int animal_age) {
		this.animal_age = animal_age;
	}
	public String getAnimal_race() {
		return animal_race;
	}
	public void setAnimal_race(String animal_race) {
		this.animal_race = animal_race;
	}
	public String getAnimal_gender() {
		return animal_gender;
	}
	public void setAnimal_gender(String animal_gender) {
		this.animal_gender = animal_gender;
	}
	public String getAnimal_type() {
		return animal_type;
	}
	public void setAnimal_type(String animal_type) {
		this.animal_type = animal_type;
	}
	
}
