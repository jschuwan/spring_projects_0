package info.schuwan.services;

import info.schuwan.models.User;

public class AuthService {

    private UserService userService;
    private JWTService tokenService;

    public AuthService(UserService userService, JWTService tokenService) {      //constructor
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public String authenticate(String username, String password) {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ " + new Exception().getStackTrace()[0].getMethodName() + " ]");
        User user = userService.getByUsername(username);
        if (user == null || !(user.getPassword().equals(password))) {
            return null;
        }
        String token = tokenService.generate(username);
        return token;
    }

    public boolean authorize(User user, String requiredRoles) {
        System.out.println("[Info] ~~~~~~~\tClass :\t\t[ " + String.valueOf(getClass().getSimpleName()) + " ]" + "\t\tMethod :\t[ " + new Exception().getStackTrace()[0].getMethodName() + " ]");

        System.out.println("requiredRolds : " + requiredRoles + "\t\t user.getRoles() : " + user.getRoles());
        if (requiredRoles.equals("[" + user.getRoles() + "]")) {
            return true;
        }

        return false;
    }
}

