
#import "Common/ShaderLib/GLSLCompat.glsllib"
#import "ShaderBoost/glsl/PBR.glsllib"

uniform sampler2D m_DiffuseMap;
uniform vec4 m_MainColor;
uniform vec4 m_SecondaryColor;
uniform vec4 m_MainPlaceholder;
uniform vec4 m_SecondaryPlaceholder;
uniform float m_Similarity;
uniform float m_TreadOffset1;
uniform float m_TreadOffset2;
uniform float m_TreadCoord1;
uniform float m_TreadCoord2;

#ifdef BURN
    uniform sampler2D m_BurnMap;
    uniform vec2 m_BurnOffset;
#endif

varying vec3 wPosition;
varying vec3 wNormal;
varying vec2 texCoord;

void main() {
    
    vec2 uv = texCoord.xy;
    
    // tred animation
    if (uv.x < m_TreadCoord1) {
        uv.y += m_TreadOffset1;
    }
    else if (uv.x < m_TreadCoord2) {
        uv.y += m_TreadOffset2;
    }
    uv.y = fract(uv.y);
    
    #ifdef BURN
        vec4 burnColor = texture2D(m_BurnMap, uv + m_BurnOffset);
    #else
        vec4 burnColor = vec4(0.0);
    #endif
    
    // diffuse
    vec4 color = mix(texture2D(m_DiffuseMap, uv), burnColor, burnColor.a);
    vec4 offsetMain = m_MainPlaceholder - color;
    vec4 offsetSec = m_SecondaryPlaceholder - color;
    if (length(offsetMain) < m_Similarity) {
        color = m_MainColor + offsetMain;
    }
    else if (length(offsetSec) < m_Similarity) {
        color = m_SecondaryColor + offsetSec;
    }
    
    // specular
    vec4 spec = vec4(0.5);
    spec.a = 1.0;
    
    // pbr
    vec4 pbr = physicallyBasedRender(wPosition, color, 1.0, spec, 0.0, wNormal);
    
    gl_FragColor = pbr;
    
}
