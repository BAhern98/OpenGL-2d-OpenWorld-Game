#version 120
varying vec2 tex_coords;
attribute vec3 vertices;
attribute vec2 textures;
uniform mat4 projection;

void main(){
	tex_coords = textures;
  	gl_Position = projection * vec4(vertices, 1);
}