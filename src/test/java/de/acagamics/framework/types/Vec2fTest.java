package de.acagamics.framework.types;

import de.acagamics.framework.geometry.Vec2f;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Vec2fTest {
	@Test
	public void addTest() {
		Vec2f v1 = new Vec2f(0,0);
		Vec2f v2 = new Vec2f(1,1);
		
		assertTrue(v2.add(v1).equals(v2));
		
		v1 = new Vec2f(2,3);
		assertTrue(v2.add(v1).getX() == v2.getX() + v1.getX());
		assertTrue(v2.add(v1).getY() == v2.getY() + v1.getY());
	}
	
	@Test
	public void lenghtTest() {
		Vec2f v1 = new Vec2f(0,0);
		
		assertTrue(v1.length() == 0);
		assertTrue(v1.lengthSqr() == 0);
		
		v1 = new Vec2f(1,0);
		assertTrue(v1.length() == 1);
		assertTrue(v1.lengthSqr() == 1);
	}
}
