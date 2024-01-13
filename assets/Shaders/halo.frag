
#import "Common/ShaderLib/GLSLCompat.glsllib"

uniform vec4 m_Color;

varying vec2 texCoord;

void main() {
    
    vec2 center = vec2(0.5, 0.5);
    float f = min(distance(texCoord, center) * 2, 1);
    
    gl_FragColor = m_Color;
    gl_FragColor.a = mix(m_Color.a, 0.0, f);
    
}
