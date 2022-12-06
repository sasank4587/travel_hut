package com.example.travelWebsite.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddFeedbackRequest {
    private String userId;
    private String option;
    private String comments;
}
