
#import "Common/ShaderLib/GLSLCompat.glsllib"

#ifdef GLOW
    uniform vec4 m_Color;
#endif

void main() {
    
    #ifdef GLOW
        gl_FragColor = m_Color;
    #else
        gl_FragColor = vec4(0.0);
    #endif
    
}