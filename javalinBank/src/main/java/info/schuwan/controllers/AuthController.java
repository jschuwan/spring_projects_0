package info.schuwan.controllers;

import info.schuwan.dto.LoginRequestDTO;
import info.schuwan.dto.LoginResponseDTO;
import info.schuwan.services.AuthService;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;

public class AuthController {
    private AuthService authService;



    public AuthController(AuthService authService) {        //constructor
        this.authService = authService;
    }

    //Checks to see if the user has a token issued and halts everything if he/she doesn't
    public Handler login = (ctx) -> {
       LoginRequestDTO loginRequest = ctx.bodyAsClass(LoginRequestDTO.class);
       String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
       if(token == null) {
           throw new UnauthorizedResponse("The user could not be authenticated");
       }
       ctx.json(new LoginResponseDTO(loginRequest.getUsername(), token));       // if the token is created, generate a loginresponse to send it back
    };
}
