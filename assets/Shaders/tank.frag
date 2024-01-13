
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

#ifdef OVERLAY
    uniform vec4 m_Overlay;
#endif

varying vec3 wPosition;
varying vec3 wNormal;
varying vec2 texCoord;

void main() {
    
    vec2 uv = texCoord.xy;
    
    // tread animation
    if (uv.x < m_TreadCoord1) {
        uv.y += m_TreadOffset1;
    }
    else if (uv.x < m_TreadCoord2) {
        uv.y += m_TreadOffset2;
    }
    uv.y = fract(uv.y);
    
    // diffuse
    vec4 color = texture2D(m_DiffuseMap, uv);
    vec4 offsetMain = m_MainPlaceholder - color;
    vec4 offsetSec = m_SecondaryPlaceholder - color;
    if (length(offsetMain) < m_Similarity) {
        color = m_MainColor + offsetMain;
    } else if (length(offsetSec) < m_Similarity) {
        color = m_SecondaryColor + offsetSec;
    }
    
    #ifdef BURN
        vec4 burnColor = texture2D(m_BurnMap, uv + m_BurnOffset);
        color = mix(color, burnColor, color.a * burnColor.a);
    #endif
    
    vec4 spec = vec4(0.5);
    spec.a = 1.0;
    color = physicallyBasedRender(wPosition, color, 1.0, spec, 0.0, wNormal);
    
    #ifdef OVERLAY
        if (m_Overlay.a > 0.0) {
            color.rgb = mix(color.rgb, m_Overlay.rgb, m_Overlay.a);
        }
    #endif
    
    gl_FragColor = color;
    
}
