#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES videoTex;
varying vec2 textureCoordinate;
varying float textureAlpha;

void main()
{
    vec4 tc = texture2D(videoTex, textureCoordinate);
    //float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;
    gl_FragColor = vec4(tc.r, tc.g, tc.b, textureAlpha);
}
