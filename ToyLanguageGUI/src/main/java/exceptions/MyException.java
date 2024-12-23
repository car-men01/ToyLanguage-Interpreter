package exceptions;

public class MyException extends Exception {
    String message;

    public MyException(String message){
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return "Error " + this.message;
    }
}

