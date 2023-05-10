package pl.coderslab.assessmentsheetgpt.note;

import lombok.*;
import pl.coderslab.assessmentsheetgpt.sheet.Sheet;
import pl.coderslab.assessmentsheetgpt.team.Team;

import javax.persistence.*;

@Entity
@Table(name = "notes")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne
    private Sheet sheet;

    @ManyToOne
    private Team team;



}
