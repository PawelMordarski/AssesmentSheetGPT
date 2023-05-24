package pl.coderslab.assessmentsheetgpt.team;

import com.fasterxml.jackson.datatype.jsr310.deser.key.ZonedDateTimeKeyDeserializer;
import lombok.*;
import pl.coderslab.assessmentsheetgpt.monitor.Monitor;

import javax.persistence.*;

@Entity
@Table(name = "teams")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

    @ManyToOne
    @ToString.Exclude
    private Monitor monitor;
}
