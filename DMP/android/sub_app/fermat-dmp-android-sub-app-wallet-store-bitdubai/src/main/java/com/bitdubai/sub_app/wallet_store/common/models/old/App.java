package com.bitdubai.sub_app.wallet_store.common.models.old;

import java.io.Serializable;

//Matias

public class App implements Serializable {



        private static final long serialVersionUID = -8730067026050196758L;

        private String title;

        private String description;

        private String picture;

        private String company;

        private String Open_hours;

        private String Address;

        private String Phone;

        private float rate;

        private int value;

        private float favorite;

        private float sale;

        private float timetoarraive;
        private boolean installed;

        //Matias
        //este ubicaciń puede tener un valor de región para realizar la busqueda por wallets cercanas
        //por ahora mientras no esté la base de datos lo trato como un numero más
        private int location;

        public App(){
        }

        public App(String title, String description, String picture, String company, String open_hours, String address, String phone, float rate, int value, float favorite, float sale, float timetoarraive, boolean installed, int location) {
                this.title = title;
                this.description = description;
                this.picture = picture;
                this.company = company;
                Open_hours = open_hours;
                Address = address;
                Phone = phone;
                this.rate = rate;
                this.value = value;
                this.favorite = favorite;
                this.sale = sale;
                this.timetoarraive = timetoarraive;
                this.installed = installed;
                this.location = location;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getPicture() {
                return picture;
        }

        public void setPicture(String picture) {
                this.picture = picture;
        }

        public String getCompany() {
                return company;
        }

        public void setCompany(String company) {
                this.company = company;
        }

        public String getOpen_hours() {
                return Open_hours;
        }

        public void setOpen_hours(String open_hours) {
                Open_hours = open_hours;
        }

        public String getAddress() {
                return Address;
        }

        public void setAddress(String address) {
                Address = address;
        }

        public String getPhone() {
                return Phone;
        }

        public void setPhone(String phone) {
                Phone = phone;
        }

        public float getRate() {
                return rate;
        }

        public void setRate(float rate) {
                this.rate = rate;
        }

        public int getValue() {
                return value;
        }

        public void setValue(int value) {
                this.value = value;
        }

        public float getFavorite() {
                return favorite;
        }

        public void setFavorite(float favorite) {
                this.favorite = favorite;
        }

        public float getSale() {
                return sale;
        }

        public void setSale(float sale) {
                this.sale = sale;
        }

        public float getTimetoarraive() {
                return timetoarraive;
        }

        public void setTimetoarraive(float timetoarraive) {
                this.timetoarraive = timetoarraive;
        }

        public boolean isInstalled() {
                return installed;
        }

        public void setInstalled(boolean installed) {
                this.installed = installed;
        }

        public int getLocation() {
                return location;
        }

        public void setLocation(int location) {
                this.location = location;
        }

        public static long getSerialVersionUID() {
                return serialVersionUID;
        }
}