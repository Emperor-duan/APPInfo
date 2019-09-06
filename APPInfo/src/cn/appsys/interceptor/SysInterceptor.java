package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;

public class SysInterceptor extends HandlerInterceptorAdapter {
	
	private Logger logger = Logger.getLogger(SysInterceptor.class);
	
	public boolean preHandler(HttpServletRequest request,HttpServletResponse response,Object object)throws Exception{
		HttpSession session = request.getSession();
		DevUser devUser = (DevUser)session.getAttribute("devUserSession");
		BackendUser backendUser = (BackendUser)session.getAttribute("UserSession");
		if (devUser == null || backendUser == null) {
			if (devUser != null) {
				return true;
			}
			if (backendUser != null){
				return true;
			}
			response.sendRedirect(request.getContextPath()+"/403.jsp");
			return false;
		}
		return true;
	}
}
