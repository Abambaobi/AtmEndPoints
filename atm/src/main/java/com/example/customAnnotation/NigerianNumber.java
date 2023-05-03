package com.example.customAnnotation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NigerianNumberValidator.class)
public @interface NigerianNumber {

    String message() default "Invalid Nigerian Number";

    String condition() default "^\\+234\\d{11}$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
