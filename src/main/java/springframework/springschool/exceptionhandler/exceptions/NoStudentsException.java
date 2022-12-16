package springframework.springschool.exceptionhandler.exceptions;

public class NoStudentsException extends StudentException{

    public NoStudentsException() {
        super("There are no students in database");
    }

}
