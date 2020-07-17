package de.acagamics.framework.geometry;

import de.acagamics.framework.ui.interfaces.GameObject;

import java.util.Comparator;

public final class Vec2i extends Geometry2f {


	public static final Vec2i zero() {
		return new Vec2i(0, 0);
	}
	public static final Vec2i one() {
		return new Vec2i(1, 1);
	}
	public static final Vec2i unitX() {
		return new Vec2i(1, 0);
	}
	public static final Vec2i unitY() {
		return new Vec2i(0, 1);
	}

	private final int x;
	private final int y;

	/**
	 * Creates zero vector.
	 */
	public Vec2i() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Coping a vector
	 * @param vector from witch to take the values
	 */
	public Vec2i(Vec2i vector) {
		this.x = vector.x;
		this.y = vector.y;
	}

	/**
	 * Creates Vector with x,y values
	 * @param x value
	 * @param y value
	 */
	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the x component from the vector.
	 * @return x component of the vector.
	 */
	public int getX(){
		return this.x;
	}
	/**
	 * Get the y component from the vector.
	 * @return y component of the vector.
	 */	
	public int getY(){
		return this.y;
	}
	
	/**
	 * Adds vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to add
	 * @return new resulting vector
	 */
	public Vec2i add(Vec2i vec) {
		return new Vec2i(this.x + vec.x, this.y + vec.y);
	}
	
	/**
	 * Adds vector component-wise to a copy of this instance. (Won't change this instance)
	 * @param x incoming x-Value to add
	 * @param y incoming y-Value to add
	 * @return new resulting vector
	 */
	public Vec2i add(int x, int y) {
		return new Vec2i(this.x + x, this.y + y);
	}
	
	/**
	 * Subtracts vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to subtract
	 * @return new resulting vector
	 */
	public Vec2i sub(Vec2i vec) {
		return new Vec2i(this.x - vec.x, this.y - vec.y);
	}
	
	/**
	 * Subtracts vector component-wise from a copy of this instance. (Won't change this instance)
	 * @param x incoming x-Value to subtract
	 * @param y incoming y-Value to subtract
	 * @return new resulting vector
	 */
	public Vec2i sub(int x, int y) {
		return new Vec2i(this.x - x, this.y - y);
	}
	
	/**
	 * Multiplies both components of a copy of this instance with factor. (Won't change this instance)
	 * @param a incoming factor to multiply
	 * @return new resulting vector
	 */
	public Vec2i mult(int a){
		return new Vec2i(this.x * a, this.y * a);
	}
	
	/**
	 * Multiplies vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param x incoming x-Value to multiply
	 * @param y incoming y-Value to multiply
	 * @return new resulting vector
	 */
	public Vec2i mult(int x, int y) {
		return new Vec2i(this.x * x, this.y * y);
	}
	
	/**
	 * Multiplies vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to multiply
	 * @return new resulting vector
	 */
	public Vec2i mult(Vec2i vec) {
		return new Vec2i(this.x * vec.x, this.y * vec.y);
	}
		
	/**
	 * Divides both components of a copy of this instance by dividend. (Won't change this instance)
	 * @param a incoming dividend for division
	 * @return new resulting vector
	 */
	public Vec2i div(int a){
		if(a == 0){
			throw new ArithmeticException("Do not divide by zero!");
		}
		return new Vec2i(this.x / a, this.y / a);
	}
	
	/**
	 * Divides vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param x incoming x-Value to divide
	 * @param y incoming y-Value to divide
	 * @return new resulting vector
	 */
	public Vec2i div(int x, int y) {
		if(x == 0 || y == 0){
			throw new ArithmeticException("Do not divide by zero!");
		}
		return new Vec2i(this.x / x, this.y / y);
	}
	
	/**
	 * Divides vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to divide
	 * @return new resulting vector
	 */
	public Vec2i div(Vec2i vec) {
		if(vec.x == 0 || vec.y == 0){
			throw new ArithmeticException("Do not divide by zero!");
		}
		return new Vec2i(this.x / vec.x, this.y / vec.y);
	}
	
	/**
	 * Returns dot product (scalar product) of vectors.
	 * @param vec incoming vector
	 * @return dot product (scalar product)
	 */
	public int dot(Vec2i vec)
	{
		return this.x * vec.x + this.y * vec.y;
	}
	
	/**
	 * Returns the length of this vector.
	 * @return length
	 */
	public int length()
	{
		return Math.abs(this.x) + Math.abs(this.y);
	}

	/**
	 * Returns the squared length of this vector.
	 * @return squared length
	 */
	public int lengthSqr()
	{
		return this.dot(this);
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	/**
	 * Computes the Euclidean distance between two vectors.
	 * @param p second vector
	 * @return the Euclidean distance between this and the second vector
	 */
	public int distance(Vec2i p) {
		return this.sub(p).length();
	}
	
	/**
	 * Computes the squared Euclidean distance between two vectors.
	 * @param p second vector
	 * @return squared Euclidean distance between this and the second vector
	 */
	public int distanceSqr(Vec2i p) {
		return this.sub(p).lengthSqr();
	}
	
	/**
	 * Checks if vectors have same values.
	 * @param other vector to be checked for equality
	 * @return if vectors have same values
	 */
	@Override
	public boolean equals(Object other) {
		if(other == null || getClass() != other.getClass()){
			return false;
		}
		if(other == this){
			return true;
		}
		Vec2i rhs = (Vec2i) other;
		return this.distance(rhs) == 0;
	}
	
	/**
	 * @return new Vector with same Values.
	 */
	public Vec2i copy() {
		return new Vec2i(x,y);
	}

	/**
	 * Get a vector rotated anticlockwise.
	 * @param angle in radians.
	 * @return rotated vector by angle.
	 */
	public Vec2i rotate(double angle) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		double rotX = this.x * cos - this.y * sin;
		double rotY = this.x * sin + this.y * cos;
		return new Vec2i((int) rotX, (int) rotY);
	}
	
	/**
	 * 
	 * @return angle to (1, 0)^t in radians.
	 */
	public float getAngle() {
		float angle = (float) Math.acos(this.getVec2f().getNormalized().dot(new Vec2f(1,0)));
		if(y > 0) {
			return (float) (2 * Math.PI - angle);
		}
		return angle;
	}

	public Vec2i clipLenght(float length) {
		if(length() > length){
			return this.mult((int) (length / length()));
		}
		return this;
	}

	public Vec2f getVec2f(){
		return new Vec2f(this.x, this.y);
	}

}
