package com.github.tel05.bitesizeapp;

public class ZipCodeValidator {
    public static boolean isValidZipCode(String zipcode) {
        // Basic regex for US ZIP codes (5 digits or 5+4 format)
        return zipcode != null && zipcode.matches("^\\d{5}(-\\d{4})?$");
    }

}
