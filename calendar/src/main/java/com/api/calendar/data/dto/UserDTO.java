package com.api.calendar.data.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    private String userName;

    private List<CalendarTimeslotDTO> interviewCalendar = new ArrayList<>();

}
