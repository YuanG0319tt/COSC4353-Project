package com.example.demo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    private UserInfo makeFilled() {
        UserInfo u = new UserInfo();
        u.setUserId(1L);
        u.setName("Alice");
        u.setEmail("alice@example.com");
        u.setPhoneNumber("555-1234");
        u.setAddress1("123 Main St");
        u.setAddress2("Apt 4");
        u.setCity("Metropolis");
        u.setState("NY");
        u.setZipCode("10101");
        u.setSkills("Cooking,Driving");
        u.setPreferences("Outdoor,Remote");
        u.setAvailability("2025-04-22");
        return u;
    }

    @Test
    void testAllGettersAndSetters() {
        UserInfo u = new UserInfo();

        u.setUserId(42L);
        assertEquals(42L, u.getUserId());

        u.setName("Bob");
        assertEquals("Bob", u.getName());

        u.setEmail("bob@example.com");
        assertEquals("bob@example.com", u.getEmail());

        u.setPhoneNumber("555-9999");
        assertEquals("555-9999", u.getPhoneNumber());

        u.setAddress1("456 Elm St");
        assertEquals("456 Elm St", u.getAddress1());

        u.setAddress2("Suite 5");
        assertEquals("Suite 5", u.getAddress2());

        u.setCity("Gotham");
        assertEquals("Gotham", u.getCity());

        u.setState("CA");
        assertEquals("CA", u.getState());

        u.setZipCode("90210");
        assertEquals("90210", u.getZipCode());

        u.setSkills("Java,Spring");
        assertEquals("Java,Spring", u.getSkills());

        u.setPreferences("Backend,API");
        assertEquals("Backend,API", u.getPreferences());

        u.setAvailability("2025-05-01");
        assertEquals("2025-05-01", u.getAvailability());
    }

    @Test
    void testEqualsReflexiveAndNullAndDifferent() {
        UserInfo a = makeFilled();
        UserInfo b = makeFilled();

        // reflexive
        assertTrue(a.equals(a));

        // null
        assertFalse(a.equals(null));

        // different type
        assertFalse(a.equals("someString"));

        // two distinct instances are not equal (default Object.equals)
        assertFalse(a.equals(b));
    }

    @Test
    void testHashCodeConsistent() {
        UserInfo u = makeFilled();
        int h1 = u.hashCode();
        int h2 = u.hashCode();
        assertEquals(h1, h2, "hashCode should be consistent on same instance");
    }

    @Test
    void testToStringIsDefaultObject() {
        UserInfo u = makeFilled();
        String s = u.toString();

        // should start with full class name and '@'
        String prefix = UserInfo.class.getName() + "@";
        assertTrue(s.startsWith(prefix), () ->
                "toString() should start with \"" + prefix + "\" but was: " + s
        );
    }
}
