package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths {
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale, float rotation) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(-rotation), new Vector3f(0, 0, 1), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale, float rotation, float depth) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(translation.x, translation.y, depth), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(-rotation), new Vector3f(0, 0, 1), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrix(int x, int y) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector2f(x, y), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getRotation()), new Vector3f(0, 0, 1), matrix, matrix);
		Vector2f negPos = new Vector2f(-camera.getPosition().x, -camera.getPosition().y);
		Matrix4f.translate(negPos, matrix, matrix);
		return matrix;
	}
	
	public static float lerp(float a, float b, float d) {
		return (1-d)*a + d*b;
	}
	
	public static int fastFloor(float x) {
		int xi = (int) x;
		return x<xi ? xi-1 : xi;
	}
	
}
