package info.schuwan.dto;

import java.util.List;

public class ListUserDTO {


    private List<UserDTO> users;

    public ListUserDTO() {
    }

    public List<UserDTO> getGreetings() {
        return users;
    }

    public ListUserDTO(List<UserDTO> users) {
        this.users = users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
