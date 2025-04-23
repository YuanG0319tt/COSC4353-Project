package com.example.demo.entity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        List<String> skills = Arrays.asList("s1", "s2");
        List<String> prefs  = Arrays.asList("p1", "p2");
        Volunteer v = new Volunteer("LocX", "NameY", "2025-04-22", skills, prefs);

        assertEquals("LocX", v.getLocation());
        assertEquals("NameY", v.getName());
        assertEquals("2025-04-22", v.getAvailability());
        assertSame(skills, v.getSkills(), "should return the same list instance");
        assertSame(prefs,  v.getPreferences(), "should return the same list instance");
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        Volunteer v = new Volunteer();
        // before setting, everything is null
        assertNull(v.getLocation());
        assertNull(v.getName());
        assertNull(v.getAvailability());
        assertNull(v.getSkills());
        assertNull(v.getPreferences());

        // now set and get
        v.setLocation("L");
        v.setName("N");
        v.setAvailability("2025-01-01");
        v.setSkills(Arrays.asList("A"));
        v.setPreferences(Arrays.asList("B"));

        assertEquals("L", v.getLocation());
        assertEquals("N", v.getName());
        assertEquals("2025-01-01", v.getAvailability());
        assertEquals(Arrays.asList("A"), v.getSkills());
        assertEquals(Arrays.asList("B"), v.getPreferences());
    }

    @Test
    void testEqualsAndHashCodeDefaultBehavior() {
        Volunteer a = new Volunteer("L","N","2025-01-01",
                Arrays.asList("A"), Arrays.asList("B"));

        // reflexive
        assertTrue(a.equals(a));

        // null and different type
        assertFalse(a.equals(null));
        assertFalse(a.equals("someString"));

        // two distinct instances—even with same field values—use Object.equals ⇒ false
        Volunteer b = new Volunteer("L","N","2025-01-01",
                Arrays.asList("A"), Arrays.asList("B"));
        assertFalse(a.equals(b));

        // hashCode is consistent on the same instance
        assertEquals(a.hashCode(), a.hashCode());
    }

    @Test
    void testToStringDefault() {
        Volunteer v = new Volunteer();
        String s = v.toString();
        // default toString() is ClassName@hex
        String prefix = Volunteer.class.getName() + "@";
        assertTrue(s.startsWith(prefix), () ->
                "toString() should start with \"" + prefix + "\", but was: " + s
        );
    }
}
