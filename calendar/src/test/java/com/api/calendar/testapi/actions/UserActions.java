package com.api.calendar.testapi.actions;


import com.api.calendar.data.dto.CalendarTimeslotDTO;
import com.api.calendar.data.dto.InterviewerScheduleDto;
import com.api.calendar.data.dto.UserDTO;
import com.api.calendar.testapi.connector.GetUsersConnector;
import com.api.calendar.testapi.connector.PostPutUsersConnector;
import com.api.calendar.testapi.functionaltest.ApacheHttpConnector;
import com.api.calendar.testapi.test.TestSourceUsers;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
public class UserActions extends TestSourceUsers {

    private static final List<Integer> EXPECT_ERROR_STATUS_LIST = List.of(400, 402, 404, 422, 500);

    private static final Logger log = LoggerFactory.getLogger(UserActions.class);

    public static void clearUsers(Integer expectedStatus) throws IOException {
        HttpResponse httpResponse = PostPutUsersConnector.clearUsers();

        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);
    }

    public static UserDTO createUser(UserDTO userDTO, Integer expectedStatus) throws IOException {
        HttpResponse httpResponse = PostPutUsersConnector.createUser(userDTO);

        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);

        if (EXPECT_ERROR_STATUS_LIST.contains(expectedStatus)) return null;

        return ApacheHttpConnector.readResponse(httpResponse, UserDTO.class);
    }

    public static UserDTO getCandidate(Integer userId, Integer expectedStatus)
        throws IOException {

        HttpResponse httpResponse = GetUsersConnector.getCandidate(userId);
        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);

        if (EXPECT_ERROR_STATUS_LIST.contains(expectedStatus)) return null;

        return ApacheHttpConnector.readResponse(httpResponse, UserDTO.class);
    }

    public static CalendarTimeslotDTO getCandidateBookedTimeslot(Integer userId, Integer expectedStatus) throws IOException {
        HttpResponse httpResponse = GetUsersConnector.getCandidateBookedTimeslot(userId);
        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);

        if (EXPECT_ERROR_STATUS_LIST.contains(expectedStatus)) return null;

        return ApacheHttpConnector.readResponse(httpResponse, CalendarTimeslotDTO.class);
    }

    public static UserDTO getInterviewer(Integer userId, Integer expectedStatus) throws IOException {

        HttpResponse httpResponse = GetUsersConnector.getInterviewer(userId);
        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);

        if (EXPECT_ERROR_STATUS_LIST.contains(expectedStatus)) return null;

        return ApacheHttpConnector.readResponse(httpResponse, UserDTO.class);
    }

    public static List<CalendarTimeslotDTO> getInterviewerAvailableTimeslots(Integer userId, Integer expectedStatus)
        throws Exception {

        HttpResponse httpResponse = GetUsersConnector.getInterviewerTimeslots(userId);
        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);

        if (EXPECT_ERROR_STATUS_LIST.contains(expectedStatus)) return null;

        return ApacheHttpConnector.readResponse(httpResponse, new TypeReference<>() {});
    }

    public static List<CalendarTimeslotDTO> getUsersCrossedAvailableTimeslots( Integer userId, Integer userId2, Integer expectedStatus) throws IOException {
        HttpResponse httpResponse = GetUsersConnector.getInterviewersCrossedTimeslots(userId, userId2);
        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);

        if (EXPECT_ERROR_STATUS_LIST.contains(expectedStatus)) return null;

        return ApacheHttpConnector.readResponse(httpResponse, new TypeReference<>() {});
    }

    public static void updateInterviewerWithTimeslots(Integer userId, UserDTO userDTO, Integer expectedStatus)
        throws IOException {

        HttpResponse httpResponse = PostPutUsersConnector
            .postInterviewerWithTimeslots(userId, userDTO);
        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);

        if (EXPECT_ERROR_STATUS_LIST.contains(expectedStatus)) throw new IOException("Error status" + expectedStatus);
    }

    public static UserDTO updateInterviewerWithTimeslotsById(Integer userId, List<CalendarTimeslotDTO> calendarDto, Integer expectedStatus)
        throws IOException {

        HttpResponse httpResponse = PostPutUsersConnector
            .putInterviewerTimeslots(userId, calendarDto);
        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);

        if (EXPECT_ERROR_STATUS_LIST.contains(expectedStatus)) throw new IOException("Error status" + expectedStatus);

        return ApacheHttpConnector.readResponse(httpResponse, UserDTO.class);

    }

    public static void sendCandidateTimeslot(Integer userId, UserDTO userDTO, Integer expectedStatus)
        throws IOException {

        HttpResponse httpResponse = PostPutUsersConnector
            .postCandidateTimeslot(userId, userDTO);
        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);

        if (EXPECT_ERROR_STATUS_LIST.contains(expectedStatus)) throw new IOException("Error status" + expectedStatus);
    }

    public static void sendInterviewerSchedule(InterviewerScheduleDto interviewerScheduleDto, Integer expectedStatus) throws IOException {

        HttpResponse httpResponse = PostPutUsersConnector.postInterviewerSchedule(
            interviewerScheduleDto);
        ApacheHttpConnector.validateStatus(expectedStatus, httpResponse);


        if (EXPECT_ERROR_STATUS_LIST.contains(expectedStatus)) throw new IOException("Error status" + expectedStatus);

    }
}
