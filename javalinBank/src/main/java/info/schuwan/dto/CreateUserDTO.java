package info.schuwan.dto;

import info.schuwan.models.User;

public class CreateUserDTO {

    private User user;

    public CreateUserDTO() {      //constructor
    }

    public CreateUserDTO(User user) {      //constructor
        this.user = user;
    }

    public User getUser() {      //getter
        return user;
    }

    public void setUser(User user) {      //setter
        this.user = user;
    }
}
