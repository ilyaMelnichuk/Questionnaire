package com.example.questionnaire.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RequiredConstraintValidator.class)

public @interface Required {
    String message() default "please  required";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}