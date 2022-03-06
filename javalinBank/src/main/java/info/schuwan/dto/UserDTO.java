package info.schuwan.dto;

import info.schuwan.models.User;

public class UserDTO {


    private User user;


    public UserDTO() {                                         //constructor
    }

    public UserDTO(User user) {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");//constructor
        this.user = user;
    }


    public User getUser() {                         //getter
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        return user;
    }

    public void setUser(User user) {                 //setter
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ "+ new Exception().getStackTrace()[0].getMethodName()+ " ]");
        this.user = user;
    }
}
