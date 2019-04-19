uniform mat4 u_matViewProjection;
uniform float u_valInputTextureAlpha;
attribute vec2 inputTextureCoordinate;
attribute vec4 a_Position;
varying vec2 textureCoordinate;
varying float textureAlpha;

void main()
{
    gl_Position = u_matViewProjection * a_Position;
    textureCoordinate = inputTextureCoordinate;
    textureAlpha = u_valInputTextureAlpha;
}
