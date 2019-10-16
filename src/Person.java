import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Person {
    private String personNummer;
    private String kundNamn;
    private LocalDate inköpsdatum;
    private boolean ärMedelmskapAktiv;

    public Person(String personNummer, String kundNamn, String inköpsdatum, String ärMedelmskapAktiv) {
        this.personNummer = personNummer;
        this.kundNamn = kundNamn;
        setInköpsdatum(inköpsdatum);
        setÄrMedelmskapAktiv(ärMedelmskapAktiv);
    }

    public Person() {
    }

    public String getPersonNummer() {
        return personNummer;
    }

    public void setPersonNummer(String personNummer) {
        this.personNummer = personNummer;
    }

    public String getKundNamn() {
        return kundNamn;
    }

    public void setKundNamn(String namn) {
        this.kundNamn = namn;
    }

    public LocalDate getInköpsdatum() {
        return inköpsdatum;
    }


    public void setInköpsdatum(String inköpsdatum) {
        this.inköpsdatum = LocalDate.parse(inköpsdatum, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setÄrMedelmskapAktiv(String temp) {
        this.ärMedelmskapAktiv = kollarAktivtmedlemskap(temp);
    }

    public boolean kollarAktivtmedlemskap(String temps) {

        boolean ärAktiv = false;
        LocalDate dagensDatum = LocalDate.now();
        LocalDate kundInköp = LocalDate.parse(temps).plusYears(1);
        if (dagensDatum.isBefore(kundInköp))
            ärAktiv = true;

        return ärAktiv;
    }

    @Override
    public String toString() {
        String check;
        if (ärMedelmskapAktiv)
            check = "Aktivt";
        else
            check = "Utgått";

        return "Namn: " + this.getKundNamn() + "\nPersonnummer: " + this.getPersonNummer() + "\nInköpsdatum: " + this.getInköpsdatum()
                + "\n" + "Medlemskap: " + check;
    }
}
