import static javax.swing.JOptionPane.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BgeUtility {
    public BgeUtility() {}

    public void startAvProgrammet() {

        List<Person> medlemar = läsInKundlistaFrånFil();
        visaLista(medlemar);

        while (true) {
            String input = showInputDialog(null, "Söker kundmedlem\n(Förnamn Efternamn eller personNr ååmmddnnnn)", "Best Gym Ever"
                    , PLAIN_MESSAGE);
            if (input == null) {
                showMessageDialog(null, "Du har avslutat!", "Best Gym Ever"
                        , INFORMATION_MESSAGE);
                System.exit(0);
            }
            String fixadInput = cleanUpLista(input);
            Person p = retunerarKunderFrånLista(medlemar, fixadInput);
            visaUppKundPane(p);
            inCheckning(p);
        }
    }

    public List<Person> läsInKundlistaFrånFil() {

        List<Person> kundLista = new ArrayList<>();
        try (Scanner sc = new Scanner(new File("src//customers.txt"))) {
            while (sc.hasNext()) {
                String läserInKundlistaFrånFil = sc.nextLine().trim();
                String personNr = läserInKundlistaFrånFil.substring(0, läserInKundlistaFrånFil.lastIndexOf(','));
                String namn = (läserInKundlistaFrånFil.substring(läserInKundlistaFrånFil.indexOf(',') + 2));
                String datum = sc.nextLine().trim();

                kundLista.add(new Person(personNr, namn, datum, datum));
            }

        } catch (FileNotFoundException e) {
            showMessageDialog(null, "Filen hittades inte", null, WARNING_MESSAGE);
            System.exit(0);
        }
        return kundLista;
    }

    public void visaUppKundPane(Person person) {
        if (person != null)
            showMessageDialog(null, person, "Best Gym Ever", PLAIN_MESSAGE);

        if (person == null)
            showMessageDialog(null, " finns inte...", "Best Gym Ever", PLAIN_MESSAGE);

        if (person != null) {
            if (person.kollarAktivtmedlemskap(person.getInköpsdatum().toString()))
                showMessageDialog(null, "Du är instämplad", "Best Gym Ever", PLAIN_MESSAGE);

            else if (!person.kollarAktivtmedlemskap(person.getInköpsdatum().toString()))
                showMessageDialog(null, "Ditt medlemskap har gått ut", "Best Gym Ever"
                        , WARNING_MESSAGE);

        }
    }

    public String cleanUpLista(String input) {
        try {
            input = input.replaceAll("\\s+", " ").trim();
            return input;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Person retunerarKunderFrånLista(List<Person> kundLista, String input) {
        for (int i = 0; i < kundLista.size(); i++) {
            if (input.equalsIgnoreCase(kundLista.get(i).getKundNamn()) || input.equalsIgnoreCase(kundLista.get(i).getPersonNummer())) {
                return kundLista.get(i);
            }
        }
        return null;
    }

    public Person inCheckning(Person person) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src//Incheckning.txt", true))) {
            if (person != null)
                if (person.kollarAktivtmedlemskap(person.getInköpsdatum().toString())) {
                    writer.write("Namne: " + person.getKundNamn() + "\n" + "PersonNr: " + person.getPersonNummer() + "\n"
                            + "Datum: " + LocalDate.now() + "\n"
                            + "klockslag:" + LocalTime.now().withNano(0) + "\n\n");
                } else
                    return förnyaMedlemskap(person);

        } catch (IOException | NullPointerException e) {
            showMessageDialog(null, "Lyckades inte skriva till fil src//Incheckning.txt", null, WARNING_MESSAGE);
            System.exit(0);
        }
        return null;
    }

    public Person förnyaMedlemskap(Person person) {
        int i;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src//Förnyat_medlemskap.txt", true))) {
            i = showConfirmDialog(null, "Vill du förnya ditt medlemskap?", "Best Gym Ever"
                    , YES_NO_OPTION, PLAIN_MESSAGE);
            if (i == 0) {
                writer.write("Namne: " + person.getKundNamn() + "\n" + "PersonNr: " + person.getPersonNummer() + "\n"
                        + "Start datum: " + LocalDate.now() + "\n"
                        + "Giltigt till: " + LocalDate.now().plusYears(1) + "\n\n");

                person.setInköpsdatum(LocalDate.now().toString());
                person.setÄrMedelmskapAktiv(LocalDate.now().toString());
                showMessageDialog(null, "Förnyat medlemskap!\nGilltigt till: "
                        + LocalDate.now().plusYears(1), "Best Gym Ever", PLAIN_MESSAGE);
            } else
                showMessageDialog(null, "Okej :´(", "Best Gym Ever", PLAIN_MESSAGE);

        } catch (IOException | NullPointerException ex) {
            showMessageDialog(null, "Lyckades inte skriva till fil src//Förnyat_medlemskap.txt"
                    , null, WARNING_MESSAGE);
            System.exit(0);
        }
        return null;
    }
    public void visaLista(List<Person> kundLista) {
        for (Person p : kundLista) {
            System.out.println(p + "\n");
        }
    }
}




