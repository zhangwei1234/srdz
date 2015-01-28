package com.zw.srdz.base;

import org.apache.velocity.app.event.implement.EscapeReference;

/**
 * velocity 拦截器,主要用于拦截JS,CSS,HTML 等注入信息
 * @author zhangwei
 *
 */
public class VelocityReference extends EscapeReference{

	@Override
	protected String escape(Object text) {
		if (!(text instanceof String)) {
            return text.toString();
        }
        StringBuffer str = new StringBuffer();
        char[] cs = text.toString().toCharArray();
        for (char c : cs) {
            if (c == '>') {
                str.append("&gt;");
            } else if (c == '<') {
                str.append("&lt;");
            } else {
                str.append(c);
            }
        }
        return str.toString();
	}
	
	@Override
	protected String getMatchAttribute() {
		return "eventhandler.escape.html.match";
	}
}
