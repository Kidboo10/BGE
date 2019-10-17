import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class PersonTest {
    Person p = new Person();
    LocalDate ld = LocalDate.now();
    LocalDate ld1 = LocalDate.of(2018, 10, 02);
    @Test
    public void kollarAktivtmedlemskapTest() {
        assertTrue(p.kollarAktivtmedlemskap(ld.toString()));
        assertFalse(p.kollarAktivtmedlemskap(ld1.toString()));
    }
    @Test
    public void kollaPersonsNamn() {
        Person p = new Person();
        p.setKundNamn("jocke");

        assertSame("jocke", p.getKundNamn());
        assertNotSame("atef", p.getKundNamn());
    }
}
