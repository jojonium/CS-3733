package edu.wpi.cs.algol.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDatabase {

	java.sql.Connection conn;
	@Test
	public void testConnect() {
		try {
			 conn = DatabaseUtil.connect(); 
			
		} catch (Exception e) {
			conn = null;
			fail("Test connection failed");
		}
	}

}
