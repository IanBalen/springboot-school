package springframework.springschool.exceptionhandler.exceptions;

public class NoStudentsException extends StudentException{

    public NoStudentsException() {
        super("One or more students do not exist, please check your request");
    }

}
