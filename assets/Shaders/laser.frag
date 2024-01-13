
#import "Common/ShaderLib/GLSLCompat.glsllib"
#import "ShaderBoost/glsl/textures.glsllib"

uniform vec4 m_Color;
uniform float m_Length;
uniform float m_FlickerThreshold;
uniform float m_FlickerAdv;

varying vec2 texCoord;

void main() {
    
    vec2 uv = texCoord.xy;
    uv.x = fract(m_FlickerAdv*0.07);
    uv.y *= m_Length;
    
    if (noiseTexture(uv, 100.0, 1340.34) > 0.3) {
        gl_FragColor = m_Color;
    } else {
        discard;
    }
    
}
