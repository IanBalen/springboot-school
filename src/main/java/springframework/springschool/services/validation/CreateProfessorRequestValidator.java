package springframework.springschool.services.validation;

import springframework.springschool.services.request.CreateProfessorRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateProfessorRequestValidator implements ConstraintValidator<ValidCreateProfessorRequest, CreateProfessorRequest> {

    String firstNameMessage;
    String lastNameMessage;
    String ageMessage;


    @Override
    public void initialize(ValidCreateProfessorRequest constraintAnnotation) {
        firstNameMessage = constraintAnnotation.firstNameMessage();
        lastNameMessage = constraintAnnotation.lastNameMessage();
        ageMessage = constraintAnnotation.ageMessage();
    }

    @Override
    public boolean isValid(CreateProfessorRequest request, ConstraintValidatorContext context) {

        boolean valid = true;

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

        if(request.getAge() < 25 || request.getAge() > 65){
            context.buildConstraintViolationWithTemplate(ageMessage)
                    .addPropertyNode("age")
                    .addConstraintViolation();
            valid = false;
        }


        return valid;
    }
}
