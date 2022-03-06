package info.schuwan.dto;

public class ErrorResponseDTO {
    private int statusCode;
    private String message;

    public ErrorResponseDTO() {        //constructor
    }

    public ErrorResponseDTO(String message, int statusCode) {      //constructor
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {    //getter
        return statusCode;
    }

    public void setStatusCode(int statusCode) {     //setter
        this.statusCode = statusCode;
    }

    public String getMessage() {        //getter
        return message;
    }

    public void setMessage(String message) {        //setter
        this.message = message;
    }
}
