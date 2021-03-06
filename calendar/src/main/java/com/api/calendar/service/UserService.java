package com.api.calendar.service;

import com.api.calendar.data.dto.CalendarTimeslotDTO;
import com.api.calendar.data.dto.UserDTO;
import com.api.calendar.data.entity.CalendarTimeslotDb;
import com.api.calendar.data.entity.UserDb;
import com.api.calendar.data.mappers.CalendarMapper;
import com.api.calendar.data.mappers.UserMapper;
import com.api.calendar.repository.CalendarRepository;
import com.api.calendar.repository.UserRepository;
import com.api.calendar.service.interfaces.InterviewCalendarInterface;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements InterviewCalendarInterface {

    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;
    private final UserMapper userMapper;
    private final CalendarMapper calendarMapper;

    public void clearUsers() {
        userRepository.deleteAll();
        calendarRepository.deleteAll();
    }

    public UserDTO createUser(UserDTO userDTO) throws NotFoundException {
        UserDb userDb = userMapper.mapUserDTOToUserDB(userDTO);
        UserDb userSaved = userRepository.save(userDb);

        return userMapper.mapUserDBToUserDTO(userSaved);
    }

    @Override
    public UserDTO getUserById(Integer userId) throws NotFoundException {
        Optional<UserDb> userDb = userRepository.findById(userId);

        UserDb userDb1 = userDb.orElseThrow(NotFoundException::new);

        return userMapper.mapUserDBToUserDTO(userDb1);
    }

    @Override
    public List<CalendarTimeslotDTO> getUserCalendar(Integer userId) {

        List<CalendarTimeslotDb> interviewCalendarById = calendarRepository
            .getInterviewCalendarById(userId);

        return calendarMapper.mapListCalendarDBToListCalendarDTO(interviewCalendarById);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        UserDb userDb = userMapper.mapUserDTOToUserDB(userDTO);

        userRepository.save(userDb);
    }

    @Override
    public UserDTO bookUserCalendarSlots(Integer userId, List<CalendarTimeslotDTO> calendarDTOList)
        throws NotFoundException {
        Optional<UserDb> userDb = userRepository.findById(userId);

        UserDb userDb1 = userDb.orElseThrow(NotFoundException::new);

        List<CalendarTimeslotDb> calendarTimeslotDbs = calendarMapper
            .mapListCalendarDTOToListCalendarDB(calendarDTOList);

        calendarTimeslotDbs.forEach(calendarDb -> {
            calendarDb.setUser(userDb1);
            userDb1.getInterviewCalendar().add(calendarDb);
        });

        UserDTO userSavedDto = userMapper.mapUserDBToUserDTO(userRepository.save(userDb1));

        return userSavedDto;
    }

    @Override
    public List<CalendarTimeslotDTO> getUsersCrossedCalendar(Integer interviewerId1, Integer interviewerId2) {
        List<CalendarTimeslotDTO> interviewer1 = getUserCalendar(interviewerId1);
        List<CalendarTimeslotDTO> interviewer2 = getUserCalendar(interviewerId2);

        List<CalendarTimeslotDTO> interviewersMatchingTimeslots = getInterviewersMatchingTimeslots(
            interviewer1, interviewer2);

        return interviewersMatchingTimeslots;
    }

    private List<CalendarTimeslotDTO> getInterviewersMatchingTimeslots(List<CalendarTimeslotDTO> interviewer1, List<CalendarTimeslotDTO> interviewer2) {

        List<CalendarTimeslotDTO> collect = interviewer1.stream().filter(int2 -> interviewer2.stream()
            .anyMatch(int1 -> int1.getDateTime().equals(int2.getDateTime())))
            .collect(Collectors.toList());

        return collect;
    }
}
