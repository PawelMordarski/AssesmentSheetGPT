package pl.coderslab.assessmentsheetgpt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import pl.coderslab.assessmentsheetgpt.note.Note;
import pl.coderslab.assessmentsheetgpt.note.NoteRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class NoteRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    NoteRepository noteRepository;

    @Test
    public void findByIdThenReturnNote(){
        Note note = new Note();
        note.setComment("odrzucono");
        testEntityManager.persist(note);

        Note result = noteRepository.findByComment("odrzucono");

        assertEquals(note.getComment(), result.getComment());

    }


}
