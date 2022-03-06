package info.schuwan.dto;

public class LoginRequestDTO {
    private String username;
    private String password;

    public LoginRequestDTO() {      //constructor
    }

    public String getUsername() {        //getter
        return username;
    }

    public void setUsername(String username) {      //setter
        this.username = username;
    }

    public String getPassword() {               //getter
        return password;
    }

    public void setPassword(String password) {      //setter
        this.password = password;
    }
}
