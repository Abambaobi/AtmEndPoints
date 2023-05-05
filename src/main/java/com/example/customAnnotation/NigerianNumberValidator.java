package com.example.customAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NigerianNumberValidator implements ConstraintValidator<NigerianNumber,String> {

    private String condition;

    @Override
    public void initialize(NigerianNumber constraintAnnotation) {
        this.condition = constraintAnnotation.condition();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null){
            return false;
        }
        if(value.length()==15){
            Pattern compile = Pattern.compile(condition);
            return Pattern.matches(compile.pattern(),value);
        }
        return false;
    }
}