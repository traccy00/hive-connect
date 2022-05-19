package fpt.edu.capstone.entity.sprint1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TechStack {
    private long id;
    private String stackName;
    private boolean status;
}
