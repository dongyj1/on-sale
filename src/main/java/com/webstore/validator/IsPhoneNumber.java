package com.webstore.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsPhoneNumberValidator.class })
public @interface IsPhoneNumber {
    boolean required() default true;

    String message() default "Phone number formation error";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
