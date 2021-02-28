package com.breader.springmicroservices.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(description = "All details about the user")
public class User {
    private Integer id;

    @Size(min = 2)
    @ApiModelProperty(notes = "Name should have at least 2 characters")
    private String name;

    @Past
    @ApiModelProperty(notes = "Birth date should be in the past")
    private Date birthDate;

    private List<Post> postList;
}
