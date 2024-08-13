package com.elisiocabral.stream_link.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Meeting {
    @Id
    private Long meetingId;
    private Long hostId;

}
