package in.proj.service;

import java.util.List;

import in.proj.model.Attendance;
import in.proj.model.User;

public interface IUserService {

	public boolean createUser(User user);
	public User loginUser(String username, String password);
	public User getuserById(Integer uid);
	public boolean inTime(User user);
	public boolean outTime(Integer uid);
	public List<Attendance> getReport(Integer uid);
	public List<User> getAllUsers();
	public List<Attendance> getAllUserDetails(Integer uid);
}
