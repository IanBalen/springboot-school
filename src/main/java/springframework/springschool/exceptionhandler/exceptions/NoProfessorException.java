package springframework.springschool.exceptionhandler.exceptions;

public class NoProfessorException extends RuntimeException{

    public NoProfessorException() {
        super("One or more Professors do not exist, Please check your request");
    }
}
