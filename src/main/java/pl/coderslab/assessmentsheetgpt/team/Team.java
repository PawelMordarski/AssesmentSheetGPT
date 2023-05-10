package pl.coderslab.assessmentsheetgpt.team;

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
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String department;

    @ManyToOne(optional = false)
    private Monitor monitor;





}
