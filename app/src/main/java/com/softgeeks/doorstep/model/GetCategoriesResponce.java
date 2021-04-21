package com.softgeeks.doorstep.model;

/**
 * Created by Ghulam Qadir on 29,June,2020
 */
public class GetCategoriesResponce {
    //This model class is used for saving categories response which is sent from backend data base accordingly
    String car_id,user_id,owner_name,mobile,plate,image,color,start_date,end_date,price,city,area,is_driver,status,created_at,updated_at;

    public GetCategoriesResponce(String car_id, String user_id, String owner_name, String mobile, String plate, String image, String color, String start_date, String end_date, String price, String city, String area, String is_driver, String status, String created_at, String updated_at) {
        this.car_id=car_id;
        this.user_id=user_id;
        this.owner_name=owner_name;
        this.mobile=mobile;
        this.plate=plate;
        this.image=image;
        this.color=color;
        this.start_date=start_date;
        this.end_date=end_date;
        this.price=price;
        this.city=city;
        this.area=area;
        this.is_driver=is_driver;
        this.status=status;
        this.created_at=created_at;
        this.updated_at=updated_at;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id=car_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id=user_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name=owner_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile=mobile;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate=plate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image=image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color=color;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date=start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date=end_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price=price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city=city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area=area;
    }

    public String getIs_driver() {
        return is_driver;
    }

    public void setIs_driver(String is_driver) {
        this.is_driver=is_driver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status=status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at=created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at=updated_at;
    }
}
