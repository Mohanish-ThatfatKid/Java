package in.proj.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.proj.model.Attendance;
import in.proj.model.User;
import in.proj.service.IUserService;
import in.proj.util.Message;

@Controller
@RequestMapping("/app/*")
public class MainController {
	
	@Autowired
	private IUserService service;

	@GetMapping()
	public String showHome(Map<String, Object> map) {
		map.put("title", "Home Page");
		return "index";
	}
	
	@GetMapping("/login")
	public String showLogin(Map<String, Object> map) {
		map.put("title", "Login Page");
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegister(Map<String, Object> map, @ModelAttribute("user") User user) {
		map.put("title", "Register Page");
		map.put("user", user);
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user,Map<String, Object> map) {
		boolean flag = service.createUser(user);
		if (flag) {
			map.put("Message", new Message("success","Registration Succesful"));
			return "login";
		}else {
			map.put("Message", new Message("fail","Registration Failed"));
			return "registration";
		}
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestParam String username, @RequestParam String password, Map<String, Object> map) {
		if (username.equals("admin") && password.equals("admin")) {
			List<User> users = service.getAllUsers();
			map.put("users", users);
			return "adminMain";
		}else {
			User userDetails = service.loginUser(username, password);
			if (userDetails!=null) {
				boolean flag = service.inTime(userDetails);
				map.put("user", userDetails);				
				return "main";			
			}else {
				map.put("message", new Message("failed", "User Name or Password is wrong!"));
				return "login";
			}
		}
	}
	
	@GetMapping("/signout/{uid}")
	public String setoutTime(@PathVariable("uid")Integer uid) {
		boolean outTime = service.outTime(uid);
		if (outTime) {
			return "redirect:/app/";
		}else {			
			return "redirect:/app/";
		}
	}
	
	@GetMapping("/report/{uid}")
	public String attendancereport(@PathVariable("uid")Integer uid,Map<String, Object> map) {
		List<Attendance> report = service.getReport(uid);
		User user = service.getuserById(uid);
		System.out.println(report);
		map.put("user", user);
		map.put("attendances", report);
		return "main";
	}
	@GetMapping("details/{uid}")
	public String userDetails(@PathVariable("uid")Integer uid, Map<String, Object> map) {
		List<Attendance> attendance = service.getAllUserDetails(uid);
		map.put("attendances", attendance);
		return "detailsPage";
	}
	
}
