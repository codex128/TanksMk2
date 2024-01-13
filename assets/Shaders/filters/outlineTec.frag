
#ifdef OUTLINE
    uniform vec4 m_OutlineColor;
#endif

const vec4 black = vec4(0.0);

void main() {
    #ifdef OUTLINE
        gl_FragColor = m_OutlineColor;
    #else
        gl_FragColor = black;
    #endif
}
