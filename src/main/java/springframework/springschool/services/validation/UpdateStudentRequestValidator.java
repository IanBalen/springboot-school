package springframework.springschool.services.validation;

import springframework.springschool.services.request.UpdateStudentRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateStudentRequestValidator implements ConstraintValidator<ValidUpdateStudentRequest, UpdateStudentRequest> {

    private String firstNameMessage;
    private String lastNameMessage;
    private String ageMessage;
    private String academicYearMessage;


    @Override
    public void initialize(ValidUpdateStudentRequest constraintAnnotation) {
        firstNameMessage = constraintAnnotation.firstNameMessage();
        lastNameMessage = constraintAnnotation.lastNameMessage();
        ageMessage = constraintAnnotation.ageMessage();
        academicYearMessage = constraintAnnotation.academicYearMessage();
    }

    @Override
    public boolean isValid(UpdateStudentRequest request, ConstraintValidatorContext context) {

        boolean valid = true;

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
