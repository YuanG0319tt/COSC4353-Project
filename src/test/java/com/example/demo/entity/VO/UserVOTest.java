package com.example.demo.entity.VO;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class UserVOTest {

    private UserVO make(String username, String role, String first, Integer uid) {
        UserVO u = new UserVO();
        u.setUsername(username);
        u.setRole(role);
        u.setIsFirstLogin(first);
        u.setUid(uid);
        return u;
    }

    @Test
    void equals_sameReference_isTrue() {
        UserVO u = make("alice", "admin", "yes", 1);
        assertTrue(u.equals(u), "same reference should be equal");
    }

    @Test
    void equals_nullAndDifferentType_isFalse() {
        UserVO u = make("alice", "admin", "yes", 1);
        assertFalse(u.equals(null),      "null should not be equal");
        assertFalse(u.equals("someString"), "different type should not be equal");
    }

    @Test
    void equals_differentFields_isFalse() {
        UserVO base = make("alice", "admin", "yes", 1);
        // differ username
        UserVO diff1 = make("bob", "admin", "yes", 1);
        assertFalse(base.equals(diff1));
        // differ role
        UserVO diff2 = make("alice", "user", "yes", 1);
        assertFalse(base.equals(diff2));
        // differ isFirstLogin
        UserVO diff3 = make("alice", "admin", "no", 1);
        assertFalse(base.equals(diff3));
        // differ uid
        UserVO diff4 = make("alice", "admin", "yes", 2);
        assertFalse(base.equals(diff4));
    }

    @Test
    void equals_sameValuesAnd_hashCodeMatch() {
        UserVO u1 = make("alice", "admin", "yes", 1);
        UserVO u2 = make("alice", "admin", "yes", 1);

        assertTrue(u1.equals(u2), "objects with same field values should be equal");
        assertTrue(u2.equals(u1), "symmetry");
        assertEquals(u1.hashCode(), u2.hashCode(), "equal objects must have same hashCode");
    }

    @Test
    void toString_containsAllFields() {
        UserVO u = make("alice", "admin", "yes", 1);
        String s = u.toString();
        assertTrue(s.contains("username=alice"), "toString should include username");
        assertTrue(s.contains("role=admin"),      "toString should include role");
        assertTrue(s.contains("isFirstLogin=yes"),"toString should include isFirstLogin");
        assertTrue(s.contains("uid=1"),           "toString should include uid");
    }

    @Test
    void canEqual_directInvocation() throws Exception {
        UserVO u = make("alice", "admin", "yes", 1);
        // lombok generates: protected boolean canEqual(Object other)
        Method canEq = UserVO.class.getDeclaredMethod("canEqual", Object.class);
        canEq.setAccessible(true);

        // same class -> true
        assertTrue((Boolean) canEq.invoke(u, new UserVO()));

        // different class -> false
        assertFalse((Boolean) canEq.invoke(u, "not a UserVO"));
    }

    @Test
    void testEqualsWithNullFields() {
        UserVO user1 = new UserVO();
        UserVO user2 = new UserVO();
        
        // Test equals with both objects having null fields
        assertTrue(user1.equals(user2));
        
        // Test equals with one object having null username
        user1.setUsername("alice");
        assertFalse(user1.equals(user2));
        
        // Test equals with one object having null role
        user1 = new UserVO();
        user2 = new UserVO();
        user1.setRole("admin");
        assertFalse(user1.equals(user2));
        
        // Test equals with one object having null isFirstLogin
        user1 = new UserVO();
        user2 = new UserVO();
        user1.setIsFirstLogin("yes");
        assertFalse(user1.equals(user2));
        
        // Test equals with one object having null uid
        user1 = new UserVO();
        user2 = new UserVO();
        user1.setUid(1);
        assertFalse(user1.equals(user2));
    }

    @Test
    void testHashCodeWithNullFields() {
        UserVO user1 = new UserVO();
        UserVO user2 = new UserVO();
        
        // Test hashCode with null fields
        assertEquals(user1.hashCode(), user2.hashCode());
        
        // Test hashCode with same non-null values
        user1.setUsername("alice");
        user1.setRole("admin");
        user1.setIsFirstLogin("yes");
        user1.setUid(1);
        user2.setUsername("alice");
        user2.setRole("admin");
        user2.setIsFirstLogin("yes");
        user2.setUid(1);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToStringWithNullFields() {
        UserVO user = new UserVO();
        String toStringOutput = user.toString();
        assertNotNull(toStringOutput);
        assertTrue(toStringOutput.contains("username=null"));
        assertTrue(toStringOutput.contains("role=null"));
        assertTrue(toStringOutput.contains("isFirstLogin=null"));
        assertTrue(toStringOutput.contains("uid=null"));
    }

    @Test
    void testHashCodeConsistency() {
        UserVO user = make("alice", "admin", "yes", 1);
        int hashCode1 = user.hashCode();
        int hashCode2 = user.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testAllFields() {
        UserVO user = new UserVO();
        user.setUsername("alice");
        user.setRole("admin");
        user.setIsFirstLogin("yes");
        user.setUid(1);
        
        assertEquals("alice", user.getUsername());
        assertEquals("admin", user.getRole());
        assertEquals("yes", user.getIsFirstLogin());
        assertEquals(1, user.getUid());
    }

    @Test
    void testEqualsWithPartialNullFields() {
        UserVO user1 = make("alice", null, "yes", 1);
        UserVO user2 = make("alice", "admin", "yes", 1);
        assertFalse(user1.equals(user2));
        
        user1 = make(null, "admin", "yes", 1);
        user2 = make("alice", "admin", "yes", 1);
        assertFalse(user1.equals(user2));
        
        user1 = make("alice", "admin", null, 1);
        user2 = make("alice", "admin", "yes", 1);
        assertFalse(user1.equals(user2));
        
        user1 = make("alice", "admin", "yes", null);
        user2 = make("alice", "admin", "yes", 1);
        assertFalse(user1.equals(user2));
    }

    @Test
    void testEqualsWithDifferentTypes() {
        UserVO user = make("alice", "admin", "yes", 1);
        assertFalse(user.equals("not a UserVO"));
        assertFalse(user.equals(null));
        assertFalse(user.equals(new Object()));
    }
}
