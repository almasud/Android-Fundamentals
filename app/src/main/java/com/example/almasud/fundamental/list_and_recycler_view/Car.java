package com.example.almasud.fundamental.list_and_recycler_view;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class Car {
    private int carImage;
    private String carName;
    private String carCompany;

    public Car(int carImage, String carName, String carCompany) {
        this.carImage = carImage;
        this.carName = carName;
        this.carCompany = carCompany;
    }

    public int getCarImage() {
        return carImage;
    }

    public void setCarImage(int carImage) {
        this.carImage = carImage;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarCompany() {
        return carCompany;
    }

    public void setCarCompany(String carCompany) {
        this.carCompany = carCompany;
    }

    public static ArrayList<Car> getAllCars() {
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(new Car(R.drawable.car, "Toyota Corolla", "Toyota"));
        cars.add(new Car(R.drawable.car, "BMW", "BMW"));
        cars.add(new Car(R.drawable.car, "Mercedes", "Mercedes Benz"));

        return cars;
    }
}
