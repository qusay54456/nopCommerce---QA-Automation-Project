package com.nopcommerce.utilities;

/**
 * TestDataGenerator - Provides dynamic test data so tests do not collide with
 * the nopCommerce demo store's hourly data reset or with previously created
 * accounts. Each generated email is unique per run.
 */
public class TestDataGenerator {

    /** Returns a unique email address, e.g. testuser_1717490000000@test.com */
    public static String uniqueEmail() {
        return "testuser_" + System.currentTimeMillis() + "@test.com";
    }

    /** Returns a unique email with a custom prefix. */
    public static String uniqueEmail(String prefix) {
        return prefix + "_" + System.currentTimeMillis() + "@test.com";
    }

    public static final String DEFAULT_PASSWORD   = "Test@123";
    public static final String DEFAULT_FIRST_NAME = "John";
    public static final String DEFAULT_LAST_NAME  = "Doe";
    public static final String VALID_PRODUCT      = "Apple MacBook";
    public static final String PARTIAL_PRODUCT    = "MacBook";
    public static final String INVALID_PRODUCT    = "xyzproductnotexist123";
}
