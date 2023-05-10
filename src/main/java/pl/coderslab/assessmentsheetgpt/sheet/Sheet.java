package pl.coderslab.assessmentsheetgpt.sheet;

import lombok.*;
import pl.coderslab.assessmentsheetgpt.monitor.Monitor;
import pl.coderslab.assessmentsheetgpt.note.Note;
import pl.coderslab.assessmentsheetgpt.team.Team;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assessment_sheets")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private String proces;

    private String body;

    @Column(name = "added")
    private LocalDateTime addedOn;

    private boolean noted;

    private Integer rate;

    @OneToMany(mappedBy = "assessment_sheet")
    @ToString.Exclude
    private List<Note> noteList = new ArrayList<>();

    @ManyToOne(optional = false)
    private Team team;

    @ManyToOne(optional = false)
    private Monitor monitor;

    @PrePersist
    public void prePersist() {this.addedOn = LocalDateTime.now(); }
}
