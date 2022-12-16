package springframework.springschool.exceptionhandler.exceptions;

public class StudentDeleteException extends StudentException{

    public StudentDeleteException() {
        super("Student cannot be deleted because he does not exist");
    }
}
