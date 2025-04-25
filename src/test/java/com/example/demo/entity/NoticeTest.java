package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

public class NoticeTest {

    private Notice createBaseNotice() {
        Notice notice = new Notice();
        notice.setId(1);
        notice.setTitle("Test Title");
        notice.setMessage("Test Message");
        notice.setType("announcement");
        notice.setCreateTime(Timestamp.valueOf("2025-04-15 10:00:00"));
        return notice;
    }

    @Test
    public void testNoArgsConstructor() {
        Notice notice = new Notice();
        assertNull(notice.getId());
        assertNull(notice.getTitle());
        assertNull(notice.getMessage());
        assertNull(notice.getType());
        assertNull(notice.getCreateTime());
    }

    @Test
    public void testSettersAndGetters() {
        Notice notice = new Notice();
        
        // Test with null values
        notice.setId(null);
        assertNull(notice.getId());

        notice.setTitle(null);
        assertNull(notice.getTitle());

        notice.setMessage(null);
        assertNull(notice.getMessage());

        notice.setType(null);
        assertNull(notice.getType());

        notice.setCreateTime(null);
        assertNull(notice.getCreateTime());

        // Test with actual values
        notice.setId(1);
        assertEquals(1, notice.getId());

        notice.setTitle("Test Title");
        assertEquals("Test Title", notice.getTitle());

        notice.setMessage("Test Message");
        assertEquals("Test Message", notice.getMessage());

        notice.setType("announcement");
        assertEquals("announcement", notice.getType());

        Timestamp timestamp = Timestamp.valueOf("2025-04-15 10:00:00");
        notice.setCreateTime(timestamp);
        assertEquals(timestamp, notice.getCreateTime());
    }

    @Test
    public void testEqualsAndHashCodeWithNullFields() {
        Notice notice1 = new Notice();
        Notice notice2 = new Notice();
        
        // Test equality with all null fields
        assertEquals(notice1, notice2);
        assertEquals(notice1.hashCode(), notice2.hashCode());
        
        // Test with one field set to null and other to value
        notice1.setId(1);
        notice2.setId(null);
        assertNotEquals(notice1, notice2);
        
        notice1 = new Notice();
        notice2 = new Notice();
        notice1.setTitle("Test Title");
        notice2.setTitle(null);
        assertNotEquals(notice1, notice2);
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTypes() {
        Notice notice = createBaseNotice();
        assertNotEquals(notice, new Object());
        assertNotEquals(notice, null);
        assertNotEquals(notice, "not a Notice");
    }

    @Test
    public void testEqualsAndHashCodeWithSameObject() {
        Notice notice = createBaseNotice();
        assertEquals(notice, notice);
        assertEquals(notice.hashCode(), notice.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentValues() {
        Notice notice1 = createBaseNotice();
        Notice notice2 = createBaseNotice();

        // Test each field individually
        notice2.setId(2);
        assertNotEquals(notice1, notice2);
        assertNotEquals(notice1.hashCode(), notice2.hashCode());

        notice2 = createBaseNotice();
        notice2.setTitle("Different Title");
        assertNotEquals(notice1, notice2);

        notice2 = createBaseNotice();
        notice2.setMessage("Different Message");
        assertNotEquals(notice1, notice2);

        notice2 = createBaseNotice();
        notice2.setType("info");
        assertNotEquals(notice1, notice2);

        notice2 = createBaseNotice();
        notice2.setCreateTime(Timestamp.valueOf("2025-04-16 10:00:00"));
        assertNotEquals(notice1, notice2);
    }

    @Test
    public void testToStringWithNullValues() {
        Notice notice = new Notice();
        String noticeString = notice.toString();
        assertNotNull(noticeString);
        assertTrue(noticeString.contains("id=null"));
        assertTrue(noticeString.contains("title=null"));
        assertTrue(noticeString.contains("message=null"));
        assertTrue(noticeString.contains("type=null"));
        assertTrue(noticeString.contains("createTime=null"));
    }

    @Test
    public void testToStringWithAllValues() {
        Notice notice = createBaseNotice();
        String noticeString = notice.toString();
        assertNotNull(noticeString);
        assertTrue(noticeString.contains("id=1"));
        assertTrue(noticeString.contains("title=Test Title"));
        assertTrue(noticeString.contains("message=Test Message"));
        assertTrue(noticeString.contains("type=announcement"));
        assertTrue(noticeString.contains("createTime=2025-04-15 10:00:00"));
    }

    @Test
    public void testCanEqual() {
        Notice notice1 = createBaseNotice();
        Notice notice2 = new Notice();
        
        // Test canEqual with same type
        assertTrue(notice1.canEqual(notice2));
        
        // Test canEqual with different type
        assertFalse(notice1.canEqual(new Object()));
        assertFalse(notice1.canEqual(null));
    }

    @Test
    public void testEdgeCases() {
        Notice notice = new Notice();
        
        // Test with empty strings
        notice.setTitle("");
        assertEquals("", notice.getTitle());
        
        notice.setMessage("");
        assertEquals("", notice.getMessage());
        
        notice.setType("");
        assertEquals("", notice.getType());
        
        // Test with special characters
        notice.setTitle("Notice's Title");
        assertEquals("Notice's Title", notice.getTitle());
        
        notice.setMessage("Message with \"quotes\" and 'apostrophes'");
        assertEquals("Message with \"quotes\" and 'apostrophes'", notice.getMessage());
        
        notice.setType("Type with spaces-and-hyphens");
        assertEquals("Type with spaces-and-hyphens", notice.getType());
    }

    @Test
    public void testDateEdgeCases() {
        Notice notice = new Notice();
        
        // Test with different date formats
        Timestamp date1 = Timestamp.valueOf("2025-04-15 00:00:00");
        notice.setCreateTime(date1);
        assertEquals(date1, notice.getCreateTime());
        
        Timestamp date2 = Timestamp.valueOf("2025-04-15 23:59:59");
        notice.setCreateTime(date2);
        assertEquals(date2, notice.getCreateTime());
        
        Timestamp date3 = Timestamp.valueOf("2025-04-15 12:00:00");
        notice.setCreateTime(date3);
        assertEquals(date3, notice.getCreateTime());
    }
}
