package org.marvel.models;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Superhero {
    private int id;
    private String fullName;
    private String birthDate;
    private String city;
    private String mainSkill;
    private String gender;
    private String phone;

//    public Superhero() {
//    }

    public Superhero(String fullName, String birthDate, String city, String mainSkill, String gender, String phone) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.city = city;
        this.mainSkill = mainSkill;
        this.gender = gender;
        this.phone = phone;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(String birthDate) {
//        this.birthDate = birthDate;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getMainSkill() {
//        return mainSkill;
//    }
//
//    public void setMainSkill(String mainSkill) {
//        this.mainSkill = mainSkill;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

//    @Override
//    public String toString() {
//        return "Superhero{" +
//                "id=" + id +
//                ", fullName='" + fullName + '\'' +
//                ", birthDate='" + birthDate + '\'' +
//                ", city='" + city + '\'' +
//                ", mainSkill='" + mainSkill + '\'' +
//                ", gender='" + gender + '\'' +
//                ", phone='" + phone + '\'' +
//                '}';
//    }
}
