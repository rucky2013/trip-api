package com.ulplanet.trip.util;



import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by makun on 2016/1/14.
 */
public class ValidatorUtils {
    private static Validator validator;


    public static <T> String validate(T t) {
        if(validator == null){
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
        StringBuilder validateError = new StringBuilder();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                validateError.append(constraintViolation.getMessage() + ";");
            }
        }
        return validateError.toString();
    }
}
