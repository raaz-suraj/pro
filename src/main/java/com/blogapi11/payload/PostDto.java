package com.blogapi11.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;

    @NotEmpty(message = "title should not be empty")
    @Size(min=3,max =45,message = "title should be in between 3 to 45")
    private String title;
    @NotEmpty(message = "description should not be empty")
    @Size(min=3,max =45,message = "description should be in between 3 to 45")
    private String description;
    @NotEmpty(message = "content should not be empty")
    @Size(min=3,max =45,message = "content should be in between 3 to 45")
    private String content;
}
