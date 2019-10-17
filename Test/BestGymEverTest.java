import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BestGymEverTest {
    BgeUtility bg = new BgeUtility();
    Person p = new Person();
    Person p1 = new Person();
    @Test
    public void retunerarEnPersonFrånListaTest() {
        List<Person> lista = new ArrayList<>();
        p.setPersonNummer("8104021234");
        p.setKundNamn("Bear Belle");
        p1.setPersonNummer("123456789");
        p1.setKundNamn("jocke");
        String name = "Joakim";
        lista.add(p);
        lista.add(p1);

        assertTrue(bg.retunerarKunderFrånLista(lista, lista.get(0).getKundNamn()) instanceof Person);
        assertTrue(bg.retunerarKunderFrånLista(lista, lista.get(1).getKundNamn()).getKundNamn().equalsIgnoreCase("jocke"));
        assertTrue(bg.retunerarKunderFrånLista(lista, lista.get(1).getPersonNummer()).getPersonNummer().equals("123456789"));
        assertFalse(bg.retunerarKunderFrånLista(lista, name) instanceof Person);
    }
    @Test
    public void TestFörAttInitsieraFörnyaMedlemskap() {
        LocalDate testdate = LocalDate.now().minusYears(1);
        Person p4 = new Person();
        Person p5 = new Person();
        p4.setInköpsdatum("2016-10-16"); // datumet har gått ut
        p5.setInköpsdatum(LocalDate.now().toString()); //datum är giltigt

        assertTrue(p4.getInköpsdatum().isBefore(testdate)); // "giltig" att kastas in i förnyamedlemskaps metod...
        assertFalse(p5.getInköpsdatum().isBefore(testdate)); //ej giltig för att kastas in i förnyamedlemskaps metod...
    }
}

