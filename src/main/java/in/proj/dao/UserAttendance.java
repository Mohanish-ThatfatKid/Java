package in.proj.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.proj.model.Attendance;
import in.proj.model.User;


public interface UserAttendance extends JpaRepository<Attendance, LocalDate> {
	
List<Attendance> findByUser(User user);
Attendance findByDateAndUser(LocalDate id, User user);
}
