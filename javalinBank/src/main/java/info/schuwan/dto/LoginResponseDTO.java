package info.schuwan.dto;

public class LoginResponseDTO {

    private String username;
    private String authToken;

    public LoginResponseDTO() {     //constructor
    }

    public LoginResponseDTO(String username, String authToken) {    //constructor
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {       //getter
        return username;
    }

    public void setUsername(String username) {      //setter
        this.username = username;
    }

    public String getAuthToken() {          //getter
        return authToken;
    }

    public void setAuthToken(String authToken) {        //setter
        this.authToken = authToken;
    }
}
