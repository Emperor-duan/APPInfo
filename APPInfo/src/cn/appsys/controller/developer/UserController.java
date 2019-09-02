package cn.appsys.controller.developer;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.devuser.DevUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private DevUserService devUserService;
	
	@RequestMapping("/devlogin")
	public String login(){
		return "devlogin";
	}
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	@RequestMapping(value = "/dologin",method=RequestMethod.POST)
	public String addlist(@RequestParam(value="devCode")String name,@RequestParam(value="devPassword")String pwd,HttpSession session){
		System.err.println(name+","+pwd);
		DevUser devUser = devUserService.getDevUser(name, pwd);
		if (devUser!=null) {
			session.setAttribute("devUserSession", devUser);
			return "developer/main";
		}
		return "devlogin";
	}
}
