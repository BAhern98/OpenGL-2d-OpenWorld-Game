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
	private int vertexShader;//vertex shader = processes vertices the shader takes
	private int fragmentShader;//fragment shader = gives texture and effects
	
	public Shader(String filename) {
		
		program = glCreateProgram();//creates program
		  
		vertexShader = glCreateShader(GL_VERTEX_SHADER);//creates shader of vertex type so opengl knows what to do with it
		glShaderSource(vertexShader,readFile(filename+".vs"));//reads vertex shader
		glCompileShader(vertexShader);
		if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) !=1) {//desplays error message better
			System.err.println(glGetShaderInfoLog(vertexShader));
			System.exit(1);
		}
		
		
		
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);//creates shader of fragment type so opengl knows what to do with it
		glShaderSource(fragmentShader,readFile(filename+".fs"));//reads fragment shader
		glCompileShader(fragmentShader);//compiling shader
		if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) !=1) {//desplays error message better
			System.err.println(glGetShaderInfoLog(fragmentShader));
			System.exit(1);
		}
		
		
		glAttachShader(program, vertexShader);//attaches shader to program
		glAttachShader(program, fragmentShader);//attaches shader to program
		
		glBindAttribLocation(program, 0, "vertices");//bind vertices to 0, send to attribute 0
		glBindAttribLocation(program, 1, "textures");
		
		glLinkProgram(program);//link shader
		
		if(glGetProgrami(program, GL_LINK_STATUS) !=1) {//check error
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
			
		}//gets error
		
		glValidateProgram(program);//validate shader

		if(glGetProgrami(program, GL_VALIDATE_STATUS) !=1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
			
		}//gets error

	}
//	protected void finalize() throws Throwable{
//		glDetachShader(program, vs);
//		glDetachShader(program, fs);
//		glDeleteShader(vs);
//		glDeleteShader(fs);
//		glDeleteProgram(program);
//		super.finalize();
//	}
	
	
	public void setUniform(String name, int value) {//uniform variable stored in location on graphics card where opengl returns to us where we use the location to pass through our data 
		int location = glGetUniformLocation(program, name);//stores location on graphics card
		if(location != -1)//tests if location is valid
			glUniform1i(location, value);//takes in location and value
	}
	public void setUniform(String name, Matrix4f value) {//entity
		int location = glGetUniformLocation(program, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);//hold all information of scale, rotation and projection
		value.get(buffer);//adds value to buffer
		if(location != -1)
			glUniformMatrix4fv(location, false, buffer);
	}
	
	public void bind() {
		glUseProgram(program);//binds to program 
	}
	
	private String readFile(String filename) {//reads file for attaching shader to program
		StringBuilder string = new StringBuilder();// where all contents in file goes
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File("./shaders/" + filename)));//takes in reader
			String line;
			while((line = br.readLine()) != null){
				string.append(line);
				string.append("\n");//opengl wants it exactly like a file so new line is nessary
				
				
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return string.toString();
	}

}
