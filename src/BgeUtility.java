import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BgeUtility {
    public BgeUtility(){}

    public void startAvProgrammet() {
        List<Person> medlemar = läsInKundlistaFrånFil();
        visaKundLista(medlemar);
        while (true) {
            String input = JOptionPane.showInputDialog("Sök kund");
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Du har avslutat!");
                System.exit(0);
            }
            String fixedInput = cleanUpList(input);
            Person p = retunerarKunderFrånLista(medlemar, fixedInput);
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
            JOptionPane.showMessageDialog(null,"Filen hittades inte");
            System.exit(0);
        }
        return kundLista;
    }

    public void visaKundLista(List<Person> kundLista) {
        for (Person p : kundLista) {
            System.out.println(p + "\n");
        }
    }

    public String cleanUpList(String input) {
        try {
            input = input.replaceAll("\\s+", " ").trim();
            return input;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Person retunerarKunderFrånLista(List<Person> hittaKund, String input) {
        for (int i = 0; i < hittaKund.size(); i++) {
            if (input.equalsIgnoreCase(hittaKund.get(i).getKundNamn()) || input.equalsIgnoreCase(hittaKund.get(i).getPersonNummer())) {
                JOptionPane.showMessageDialog(null, hittaKund.get(i));
                return hittaKund.get(i);
            }
        }
        JOptionPane.showMessageDialog(null, " finns inte...");
        return null;
    }

    public Person inCheckning(Person person) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src//Incheckning.txt", true))) {
            if (person != null)
                if (person.kollarAktivtmedlemskap(person.getInköpsdatum().toString())) {
                    JOptionPane.showMessageDialog(null, "Du är instämplad");
                    writer.write("Namne: " + person.getKundNamn() + "\n" + "PersonNr: " + person.getPersonNummer() + "\n"
                            + "Datum: " + LocalDate.now() + "\n"
                            + "klockslag:" + LocalTime.now().withNano(0) + "\n\n");
                } else if (!person.kollarAktivtmedlemskap(person.getInköpsdatum().toString())) {
                    JOptionPane.showMessageDialog(null, "Ditt medlemskap har gått ut");
                    return förnyaMedlemskap(person);
                }

        } catch (IOException | NullPointerException e) {
            JOptionPane.showMessageDialog(null,"Skriv inte till en fil");
            System.exit(0);
        }
        return null;
    }

    public Person förnyaMedlemskap(Person person) {
        int i;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src//Förnyat_medlemskap.txt", true))) {
            i = JOptionPane.showConfirmDialog(null, "Vill du förnya ditt medlemskap?"
                    ,JOptionPane.OPTIONS_PROPERTY, JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                writer.write("Namne: " + person.getKundNamn() + "\n" + "PersonNr: " + person.getPersonNummer() + "\n"
                        + "Start datum: " + LocalDate.now() + "\n"
                        + "Giltigt till: " + LocalDate.now().plusYears(1) + "\n\n");
                person.setInköpsdatum(LocalDate.now().toString());
                person.setÄrMedelmskapAktiv(LocalDate.now().toString());
                JOptionPane.showMessageDialog(null, "Förnyat Medlemskap!\nGilltigt till: "
                        + LocalDate.now().plusYears(1));
            } else
                JOptionPane.showMessageDialog(null, "Okej");

        } catch (IOException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null,"Skriv inte till en fil");
            System.exit(0);
        }
        return null;
    }
}




