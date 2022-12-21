package springframework.springschool.exceptionhandler.exceptions;

public class NoSubjectExists extends RuntimeException{

    public NoSubjectExists() {
        super("One or more subjects do not exist, please check your request");
    }
}
