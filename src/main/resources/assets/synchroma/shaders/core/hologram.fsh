#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 normal;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    float grayScale = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    vec3 targetColor = vec3(0.027, 0.745, 0.871);
    color = vec4(vec3(grayScale*targetColor), 0.6);
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}