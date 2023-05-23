package springframework.springschool.services.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = UpdateStudentRequestValidator.class)
public @interface ValidUpdateStudentRequest {

    String message() default "Invalid update student request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String firstNameMessage() default "First name can only contain letters";
    String lastNameMessage() default "Last name can only contain letters";
    String ageMessage() default "Age must be between 14 and 21";
    String academicYearMessage() default "Academic year must be between 1 and 4";

}
