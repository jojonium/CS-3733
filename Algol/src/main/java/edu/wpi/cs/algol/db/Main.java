package edu.wpi.cs.algol.db;

public class Main {
	public static void main(String args[]) {
		try {
			DatabaseUtil.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
