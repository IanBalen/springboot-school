package springframework.springschool.exceptionhandler.exceptions;

public class SubjectExistsException extends RuntimeException{

    public SubjectExistsException() {
        super("Subject already exsits");
    }



}
