
#import "Common/ShaderLib/GLSLCompat.glsllib"
#import "Common/ShaderLib/MultiSample.glsllib"

uniform COLORTEXTURE m_Texture;
uniform sampler2D m_OutlineMap;
uniform vec2 m_Resolution;

varying vec2 texCoord;

const float similar = 0.01;
const int radius = 1;
const vec4 black = vec4(0.0);

void main() {
    
    vec4 base = vec4(0.0);
    vec2 offset = vec2(1.0) / m_Resolution;    
    bool edge = false;
    if (texture2D(m_OutlineMap, texCoord).a == 0) {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if ((i == 0 && j == 0) || (i+j)%2 == 0) {
                    continue;
                }
                vec4 clr = texture2D(m_OutlineMap, texCoord + offset * vec2(j, i));
                if (distance(black, clr) > similar) {
                    base = clr;
                    edge = true;
                    break;
                }
            }
            if (edge) break;
        }
    }    
    if (edge) {
        gl_FragColor = base;
    } else {
        gl_FragColor = getColor(m_Texture, texCoord);
    }
    
}
