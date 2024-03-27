package in.proj.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.proj.dao.UserAttendance;
import in.proj.dao.UserRepo;
import in.proj.model.Attendance;
import in.proj.model.User;

@Service
public class IUserServiceImpl implements IUserService {

	@Autowired
	private UserRepo repo;
	
	@Autowired
	private UserAttendance attenRepo;
	
	
	@Override
	public boolean createUser(User user) {
		boolean flag = false;
		
		User userDetails = repo.save(user);
		if (userDetails!=null) {
			flag = true;
		}
		return flag;
	}

	@Override
	public User loginUser(String username, String password) {
		User user = repo.findByEmailAndPassword(username, password);
		if (user!=null) {
			return user;			
		}
		else {
			return null;
		}
	}

	@Override
	public User getuserById(Integer uid) {
		// TODO Auto-generated method stub
		User user = repo.findById(uid).get();
		return user;
	}

	@Override
	public boolean inTime(User user) {
		Attendance attendance = attenRepo.findByDateAndUser(LocalDate.now(), user);
		if (attendance==null) {
			Attendance attendanceNew = new Attendance();
			attendanceNew.setDate(LocalDate.now());
			attendanceNew.setInTime(LocalTime.now()
					  .truncatedTo(ChronoUnit.SECONDS)
					  .format(DateTimeFormatter.ISO_LOCAL_TIME));
			attendanceNew.setSignedIn(true);
			attendanceNew.setUser(user);
			Attendance save = attenRepo.save(attendanceNew);
			if (save!=null) {
				return true;
			}else {
				return false;
			}
		}else {			
			return false;
		}
		
	}

	@Override
	public boolean outTime(Integer uid) {
		boolean flag = false;
		User user = repo.findById(uid).get();
		Attendance attendance = attenRepo.findByDateAndUser(LocalDate.now(), user);
		System.out.println(attendance);
		if(attendance.getOutTime() == null){
		attendance.setOutTime(LocalTime.now()
				  .truncatedTo(ChronoUnit.SECONDS)
				  .format(DateTimeFormatter.ISO_LOCAL_TIME));
		Attendance save = attenRepo.save(attendance);
		flag = true;
		}
		else {
			flag = false;
		}	
		
		return flag;
	}

	@Override
	public List<Attendance> getReport(Integer uid) {
		User user = repo.findById(uid).get();
		List<Attendance> attendanceList = attenRepo.findByUser(user);
		return attendanceList;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = repo.findAll();
		return users;
	}

	@Override
	public List<Attendance> getAllUserDetails(Integer uid) {
		User user = repo.findById(uid).get();
		List<Attendance> attendanceList = attenRepo.findByUser(user);
		return attendanceList;
	}

}
