package render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.Sprite;
import shaders.Shader;
import toolbox.Maths;

public class EntityRenderer {
	
	private static final float RED = 0.529412f;
	private static final float GREEN = 0.807843f;
	private static final float BLUE = 0.921569f;
	
	private Matrix4f projectionMatrix;
	
	private Shader shader;
	
	public EntityRenderer() {
		shader = new Shader();
		createOrthoProjectionMatrix(-DisplayManager.aspectRatio, DisplayManager.aspectRatio, -1, 1, 1, -1);
		dispMatrix(projectionMatrix);
	}
	
	public static void dispMatrix(Matrix4f mat) {
		System.out.printf("(%.3f\t%.3f\t%.3f\t%.3f)\n", mat.m00, mat.m01, mat.m02, mat.m03);
		System.out.printf("(%.3f\t%.3f\t%.3f\t%.3f)\n", mat.m10, mat.m11, mat.m12, mat.m13);
		System.out.printf("(%.3f\t%.3f\t%.3f\t%.3f)\n", mat.m20, mat.m21, mat.m22, mat.m23);
		System.out.printf("(%.3f\t%.3f\t%.3f\t%.3f)\n", mat.m30, mat.m31, mat.m32, mat.m33);
	}
	
	public static void dispVector4(Vector4f vec) {
		System.out.printf("(%.3f\t%.3f\t%.3f\t%.3f)\n", vec.x, vec.y, vec.z, vec.w);
	}
	
	public void render(Entity entity, Camera camera) {
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.loadViewMatrix(camera);
		
		prepare();
		prepareSprite(entity.getSprite());
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		Matrix4f matrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getScale(), entity.getRotation());
		shader.loadTransformation(matrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, entity.getSprite().getModel().getVextexCount());
		
//		DEBUG THAT SIMULATES SHADER CALCULATIONS
//		System.out.println("\nTRANSF-MATRIX");
//		dispMatrix(matrix);
//		System.out.println("gl_Positions");
//		float sx = entity.getSprite().getSize().x;
//		float sy = entity.getSprite().getSize().y;
//		float relpos[][] = {{-sx/2, sy}, {sx/2, sy/2}, {-sx/2, -sy/2}, {sx/2, -sy/2}};
//		for (float vert[] : relpos) {
//			Vector4f vec = new Vector4f();
//			Matrix4f.transform(matrix, new Vector4f(vert[0], vert[1], 0.0f, 1.0f), vec);
//			Matrix4f.transform(Maths.createViewMatrix(camera), vec, vec);
//			Matrix4f.transform(projectionMatrix, vec, vec);
//			dispVector4(vec);
//		}
		
		unbindSprite();
		shader.stop();
	}
	
	private void prepareSprite(Sprite spr) {
		RawModel raw = spr.getModel();
		GL30.glBindVertexArray(raw.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, spr.getTexture());
	}
	
	private void unbindSprite() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void prepare() {
		GL11.glClearColor(RED, GREEN, BLUE, 0.1f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
	public void createOrthoProjectionMatrix(float left, float right, float bottom, float top, float near, float far) {
        float x_orth = 2 / (right - left);
        float y_orth = 2 / (top - bottom);
        float z_orth = -2 / (far - near);
        
        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(far + near) / (far - near);
        
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_orth;
        projectionMatrix.m10 = 0;
        projectionMatrix.m20 = 0;
        projectionMatrix.m30 = 0;
        projectionMatrix.m01 = 0;
        projectionMatrix.m11 = y_orth;
        projectionMatrix.m21 = 0;
        projectionMatrix.m31 = 0;
        projectionMatrix.m02 = 0;
        projectionMatrix.m12 = 0;
        projectionMatrix.m22 = z_orth;
        projectionMatrix.m32 = 0;
        projectionMatrix.m03 = tx;
        projectionMatrix.m13 = ty;
        projectionMatrix.m23 = tz;
        projectionMatrix.m33 = 1;
	}
	
}
