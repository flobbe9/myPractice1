package com.example.myPractice1;

import java.util.Collection;

import org.springframework.stereotype.Service;


@Service
public class ValidationService {
    /**
     * Checks if the object is null or, if it's a string, has white spaces.
     * 
     * <p>Throws exception if one of the above is true.
     * 
     * @param obj object to check.
     * @return true if no object is null or has white spaces.
     */
    public static boolean objectNotNull(Object obj) {
        // TODO: implement

        return true;
    }


    /**
     * Iterates collection and checks if any object is null or, if it's a string, has white spaces.
     * 
     * <p>Throws exception if one of the above is true.
     * 
     * @param col collection to check.
     * @return true if no object is null or has white spaces.
     */
    public boolean collectionNotNullNotEmpty(Collection<?> col) {
        // TODO: implement

        return true;
    }
}
