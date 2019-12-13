package project;


import static org.lwjgl.opengl.GL20.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;


public class Shader {
	private int program;
	private int vs;
	private int fs;
	
	public Shader(String filename) {
		
		program = glCreateProgram();//creates program
		  
		vs = glCreateShader(GL_VERTEX_SHADER);//creates shader of vertex type so opengl knows what to do with it
		glShaderSource(vs,readFile(filename+".vs"));//reads vertex shader
		glCompileShader(vs);
		if(glGetShaderi(vs, GL_COMPILE_STATUS) !=1) {//desplays error message better
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(1);
		}
		
		
		
		fs = glCreateShader(GL_FRAGMENT_SHADER);//creates shader of fragment type so opengl knows what to do with it
		glShaderSource(fs,readFile(filename+".fs"));//reads fragment shader
		glCompileShader(fs);
		if(glGetShaderi(fs, GL_COMPILE_STATUS) !=1) {//desplays error message better
			System.err.println(glGetShaderInfoLog(fs));
			System.exit(1);
		}
		glAttachShader(program, vs);
		glAttachShader(program, fs);
		
		glBindAttribLocation(program, 0, "vertices");//bind vertices to 0, send to attribute
		glBindAttribLocation(program, 1, "textures");
		
		glLinkProgram(program);//link shader
		if(glGetProgrami(program, GL_LINK_STATUS) !=1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
			
		}//gets error
		
		glValidateProgram(program);//validate shader

		if(glGetProgrami(program, GL_VALIDATE_STATUS) !=1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
			
		}//gets error

	}
	protected void finalize() throws Throwable{
		glDetachShader(program, vs);
		glDetachShader(program, fs);
		glDeleteShader(vs);
		glDeleteShader(fs);
		glDeleteProgram(program);
		super.finalize();
	}
	
	
	public void setUniform(String name, int value) {//uniform variable stored in location on graphics card where opengl returns to us where we use the location to pass through our data 
		int location = glGetUniformLocation(program, name);
		if(location != -1)
			glUniform1i(location, value);
	}
	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(program, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);//hold all information of scale, rotation and projection
		value.get(buffer);
		if(location != -1)
			glUniformMatrix4fv(location, false, buffer);
	}
	
	public void bind() {
		glUseProgram(program);//binds to program 
	}
	
	private String readFile(String filename) {
		StringBuilder string = new StringBuilder();// where all contents in file goes
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File("./shaders/" + filename)));//takes in reader
			String line;
			while((line = br.readLine()) != null){
				string.append(line);
				string.append("\n");
				
				
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return string.toString();
	}

}
