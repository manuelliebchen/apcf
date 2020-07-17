package de.acagamics.framework.geometry;

import de.acagamics.framework.ui.interfaces.GameObject;

import java.util.Comparator;

/**
 * A class for defining a 2-dimensional vector. With different helpful 
 * methods to work with vectors.
 * This Vector is immutable.
 */

public final class Vec2f extends Geometry2f {
	
	
	public static final Vec2f zero() {
		return new Vec2f(0F, 0F);
	}
	public static final Vec2f one() {
		return new Vec2f(1F, 1F);
	}
	public static final Vec2f unitX() {
		return new Vec2f(1F, 0F);
	}
	public static final Vec2f unitY() {
		return new Vec2f(0F, 1F);
	}
	
	private final float x;
	private final float y;
	
	/**
	 * Creates zero vector.
	 */
	public Vec2f() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Coping a vector
	 * @param vector from witch to take the values
	 */
	public Vec2f(Vec2f vector) {
		this.x = vector.x;
		this.y = vector.y;
	}
	
	/**
	 * Creates Vector with x,y values
	 * @param x value
	 * @param y value
	 */
	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the x component from the vector.
	 * @return x component of the vector.
	 */
	public float getX(){
		return this.x;
	}
	/**
	 * Get the y component from the vector.
	 * @return y component of the vector.
	 */	
	public float getY(){
		return this.y;
	}
	
	/**
	 * Adds vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to add
	 * @return new resulting vector
	 */
	public Vec2f add(Vec2f vec) {
		return new Vec2f(this.x + vec.x, this.y + vec.y);
	}
	
	/**
	 * Adds vector component-wise to a copy of this instance. (Won't change this instance)
	 * @param x incoming x-Value to add
	 * @param y incoming y-Value to add
	 * @return new resulting vector
	 */
	public Vec2f add(float x, float y) {
		return new Vec2f(this.x + x, this.y + y);
	}
	
	/**
	 * Subtracts vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to subtract
	 * @return new resulting vector
	 */
	public Vec2f sub(Vec2f vec) {
		return new Vec2f(this.x - vec.x, this.y - vec.y);
	}
	
	/**
	 * Subtracts vector component-wise from a copy of this instance. (Won't change this instance)
	 * @param x incoming x-Value to subtract
	 * @param y incoming y-Value to subtract
	 * @return new resulting vector
	 */
	public Vec2f sub(float x, float y) {
		return new Vec2f(this.x - x, this.y - y);
	}
	
	/**
	 * Multiplies both components of a copy of this instance with factor. (Won't change this instance)
	 * @param a incoming factor to multiply
	 * @return new resulting vector
	 */
	public Vec2f mult(float a){
		return new Vec2f(this.x * a, this.y * a);
	}
	
	/**
	 * Multiplies vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param x incoming x-Value to multiply
	 * @param y incoming y-Value to multiply
	 * @return new resulting vector
	 */
	public Vec2f mult(float x, float y) {
		return new Vec2f(this.x * x, this.y * y);
	}
	
	/**
	 * Multiplies vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to multiply
	 * @return new resulting vector
	 */
	public Vec2f mult(Vec2f vec) {
		return new Vec2f(this.x * vec.x, this.y * vec.y);
	}
		
	/**
	 * Divides both components of a copy of this instance by dividend. (Won't change this instance)
	 * @param a incoming dividend for division
	 * @return new resulting vector
	 */
	public Vec2f div(float a){
		if(a == 0){
			throw new ArithmeticException("Do not divide by zero!");
		}
		return new Vec2f(this.x / a, this.y / a);
	}
	
	/**
	 * Divides vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param x incoming x-Value to divide
	 * @param y incoming y-Value to divide
	 * @return new resulting vector
	 */
	public Vec2f div(float x, float y) {
		if(x == 0 || y == 0){
			throw new ArithmeticException("Do not divide by zero!");
		}
		return new Vec2f(this.x / x, this.y / y);
	}
	
	/**
	 * Divides vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to divide
	 * @return new resulting vector
	 */
	public Vec2f div(Vec2f vec) {
		if(vec.x == 0 || vec.y == 0){
			throw new ArithmeticException("Do not divide by zero!");
		}
		return new Vec2f(this.x / vec.x, this.y / vec.y);
	}
	
	/**
	 * Returns dot product (scalar product) of vectors.
	 * @param vec incoming vector
	 * @return dot product (scalar product)
	 */
	public float dot(Vec2f vec)
	{
		return this.x * vec.x + this.y * vec.y;
	}
	
	/**
	 * Returns the length of this vector.
	 * @return length
	 */
	public float length()
	{
		return (float)Math.sqrt(x*x + y*y);
	}

	/**
	 * Returns the squared length of this vector.
	 * @return squared length
	 */
	public float lengthSqr()
	{
		return x*x + y*y;
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
	 * Performs linear interpolation between two vectors.
	 * @param vec second vector
	 * @param t interpolation value [0,1]
	 * @return interpolated vector rounded integers
	 */
	public Vec2f lerp(Vec2f vec, float t) {
		return this.mult(1-t).add(vec.mult(t));
	}

	/**
	 * Computes the Euclidean distance between two vectors.
	 * @param p second vector
	 * @return the Euclidean distance between this and the second vector
	 */
	public float distance(Vec2f p) {
		return this.sub(p).length();
	}
	
	/**
	 * Computes the squared Euclidean distance between two vectors.
	 * @param p second vector
	 * @return squared Euclidean distance between this and the second vector
	 */
	public float distanceSqr(Vec2f p) {
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
		Vec2f rhs = (Vec2f) other;
		return this.distance(rhs) < Float.MIN_VALUE * 100;
	}
	
	/**
	 * Get the normalized vector. (Won't change this instance)
	 * @return This vector scaled to the length of 1. If this is a Zero-Vector, components will be set to NaN.
	 */
	public Vec2f getNormalized() {
		float length = this.length();
		if(length == 0){
			throw new ArithmeticException("Do not normalize zero vector.");
		}
		return this.div(length);
	}
	
	/**
	 * @return new Vector with same Values.
	 */
	public Vec2f copy() {
		return new Vec2f(x,y);
	}

	/**
	 * Get a vector rotated anticlockwise.
	 * @param angle in radians.
	 * @return rotated vector by angle.
	 */
	public Vec2f rotate(double angle) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		double rotX = this.x * cos - this.y * sin;
		double rotY = this.x * sin + this.y * cos;
		return new Vec2f((float) rotX, (float) rotY);
	}
	
	/**
	 * 
	 * @return angle to (1, 0)^t in radians.
	 */
	public float getAngle() {
		float angle = (float) Math.acos(this.getNormalized().dot(new Vec2f(1,0)));
		if(y > 0) {
			return (float) (2 * Math.PI - angle);
		}
		return angle;
	}

	public Vec2f clipLenght(float length) {
		if(length() > length){
			return this.getNormalized().mult(length);
		}
		return this;
	}

	public Comparator<GameObject> sortDistanceTo(){
		return (GameObject e1, GameObject e2) -> (e1.getPosition().distance(this)
				- e2.getPosition().distance(this)) > 0 ? 1 : -1;
	}

	public Vec2i getVec2i() {
		return new Vec2i((int) this.x, (int) this.y);
	}

}
