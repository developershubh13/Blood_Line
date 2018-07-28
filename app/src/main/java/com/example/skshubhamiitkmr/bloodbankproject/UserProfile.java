package com.example.skshubhamiitkmr.bloodbankproject;

/**
 * Created by skshubhamiitkmr on 15-03-2018.
 */

public class UserProfile {

    public String registerEmail,registerName,registerPhone,registerBloodGroup,registerAge,checkDonor,registerAdd1,registerGender,registerImage;
    public String registerUnits,registerBlood;
    public UserProfile() {

    }

    public UserProfile(String registerEmail, String registerName, String registerPhone, String registerBloodGroup,String registerGender,
                       String registerAge,String registerAdd1,String checkDonor,String registerImage, String registerBlood,String registerUnits) {
        this.registerEmail = registerEmail;
        this.registerName = registerName;
        this.registerPhone = registerPhone;
        this.registerBloodGroup = registerBloodGroup;
        this.registerAge = registerAge;
        this.checkDonor=checkDonor;
        this.registerAdd1=registerAdd1;
        this.registerGender=registerGender;
         this.registerImage=registerImage;
         this.registerBlood=registerBlood;
         this.registerUnits=registerUnits;
    }

    public String getRegisterEmail() {
        return registerEmail;
    }

    public String getRegisterName() {
        return registerName;
    }

    public String getRegisterPhone() {
        return registerPhone;
    }

    public String getRegisterBloodGroup() {
        return registerBloodGroup;
    }

    public String getRegisterAge() {
        return registerAge;
    }

    public String getCheckDonor(){return checkDonor;}

    public String getRegisterGender(){return registerGender;}

    public String getRegisterAdd1(){return registerAdd1;}

   public String getRegisterImage() {return registerImage;}

    public String getRegisterUnits() {
        return registerUnits;
    }

    public void setRegisterUnits(String registerUnits) {
        this.registerUnits = registerUnits;
    }

    public String getRegisterBlood() {
        return registerBlood;
    }

    public void setRegisterBlood(String registerBlood) {
        this.registerBlood = registerBlood;
    }
}
