package springframework.springschool.services.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = CreateProfessorRequestValidator.class)
public @interface ValidCreateProfessorRequest {

    String message() default "Invalid Create professor request.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String firstNameMessage() default "First name can only contain letters.";
    String lastNameMessage() default "Last name can only contain letters.";
    String ageMessage() default "Age must be between 25 and 65.";

}
