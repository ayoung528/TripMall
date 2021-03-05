package com.web.blog.model.collaborated;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
public class Survey {
    int contentid;
    String title;
    String img;
}
