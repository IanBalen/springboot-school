package springframework.springschool.services.validation;

import springframework.springschool.services.request.CreateStudentRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateStudentRequestValidator implements ConstraintValidator<ValidCreateStudentRequest, CreateStudentRequest> {

    String academicYearMessage;
    String ageMessage;
    String firstNameMessage;
    String lastNameMessage;
    String message;


    @Override
    public void initialize(ValidCreateStudentRequest constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.academicYearMessage = constraintAnnotation.academicYearMessage();
        this.ageMessage = constraintAnnotation.ageMessage();
        this.firstNameMessage = constraintAnnotation.firstNameMessage();
        this.lastNameMessage = constraintAnnotation.lastNameMessage();
    }

    @Override
    public boolean isValid(CreateStudentRequest request, ConstraintValidatorContext context) {

        boolean valid = true;

        if(request.getFirstName() == null || request.getLastName() == null || request.getAge() == 0 || request.getAcademicYear() == 0){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("firstName")
                    .addConstraintViolation();
            return false;
        }


        if(request.getAcademicYear() > 4 || request.getAcademicYear() < 1){
            context.buildConstraintViolationWithTemplate(academicYearMessage)
                    .addPropertyNode("academicYear")
                    .addConstraintViolation();
            valid = false;
        }

        if(request.getAge() > 21 || request.getAge() < 14){
            context.buildConstraintViolationWithTemplate(ageMessage)
                    .addPropertyNode("age")
                    .addConstraintViolation();
            valid = false;
        }

        if(!request.getFirstName().matches("^[a-zA-Z]*$")){
            context.buildConstraintViolationWithTemplate(firstNameMessage)
                    .addPropertyNode("firstName")
                    .addConstraintViolation();
            valid = false;
        }

        if(!request.getLastName().matches("^[a-zA-Z]*$")){
            context.buildConstraintViolationWithTemplate(lastNameMessage)
                    .addPropertyNode("lastName")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
