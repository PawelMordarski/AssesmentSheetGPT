package pl.coderslab.assessmentsheetgpt.monitor;

import lombok.*;
import pl.coderslab.assessmentsheetgpt.team.Team;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monitors")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String personalNumber;

    private String name;

    @OneToMany(mappedBy = "monitor")
    @ToString.Exclude
    private List<Team> teams = new ArrayList<>();

}
