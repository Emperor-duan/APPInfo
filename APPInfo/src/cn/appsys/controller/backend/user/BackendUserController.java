package cn.appsys.controller.backend.user;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.UserService;

@Controller
@RequestMapping("/user")
public class BackendUserController {
	private Logger logger = Logger.getLogger(BackendUserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/index.html")
	public String login() {
		logger.debug("退出----------------------------------------------------");
		return "backendlogin";
	}

	@RequestMapping("backendlogin.html")
	public String backendlogin() {
		logger.debug("跳转到后端登录页面》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》");
		return "backendlogin";
	}

	@RequestMapping("/dologin.html")
	public String dologin(HttpSession session, @RequestParam(value = "userCode", required = false) String userCode,
			@RequestParam(value = "userPassword", required = false) String userPassword) {
		logger.debug("进入判断登录---------------------------------------------------------");
		logger.debug(userCode + "-------------------------");
		logger.debug(userPassword + "----------------------");
		BackendUser backendUser = userService.getuser(userCode, userPassword);
		if (backendUser != null) {
			logger.debug("账号/密码--------------" + backendUser.getUserCode() + "  " + backendUser.getUserPassword());
			logger.debug("hjkashdahkfhsdfhsadh>>>>>>>>>>" + backendUser.getUserTypeName());
			session.setAttribute("userSession", backendUser);
			return "backend/main";
		} else {
			return "backendlogin";
		}
	}

}
