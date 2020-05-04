import data.Animal;
import javafx.util.Pair;

import java.util.*;

/*Klasa zawiera pomocnicze metody do wczytywania danych i wyświetlania na konsoli*/
class AnimalUtils {
    private AnimalRepo repo;
    private Scanner scanner;

    AnimalUtils(AnimalRepo repo) {
        this.repo = repo;
        scanner = new Scanner(System.in);
    }

    void addAnimal() {
        System.out.println("Dodawanie zwierzęcia.");
        Animal animal = new Animal();
        System.out.println("Podaj imię: ");
        animal.setName(scanner.nextLine());
        System.out.println("Podaj gatunek: ");
        animal.setSpecies(scanner.nextLine());
        System.out.println("Wybierz rodzaj karmy:\n1.dla roślinożernych\n2.dla drapieżnikow\n3.dla gadów");
        int feedType = 0;
        while (feedType < 1 || feedType > 3) {
            try {
                feedType = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("Podaj prawidłową wartość 1-3!");
            }
            switch (feedType) {
                case 1:
                    animal.setFeedType("dla roślinożernych");
                    break;
                case 2:
                    animal.setFeedType("dla drapieżnikow");
                    break;
                case 3:
                    animal.setFeedType("dla gadów");
                    break;
                default:
                    System.out.println("Podaj prawidłową wartość 1-3!");
            }
        }
        System.out.println("Podaj dzienne zapotrzebowanie na karmę w kg:");
        double amount = 0.0;
        while (amount <= 0) {
            try {
                amount = scanner.nextDouble();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Podaj prawidłową wartość > 0kg!");
                scanner.next();
            }
            if (amount >= 0) animal.setFeedPerDay(amount);
            else System.out.println("Podaj prawidłową wartość > 0kg!");
        }
        long key = Math.abs(Utils.getR().nextInt());
        repo.addAnimal(key, animal);
        System.out.println("Dodano zwierzę z kluczem: " + key);
    }

    void removeAnimal() {
        System.out.println("Podaj klucz: ");
        long key = -1L;
        while (key < 0) {
            try {
                key = scanner.nextLong();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("Podaj prawidłową wartość > 0!");
            }
            if (key < 0 ) System.out.println("Podaj prawidłową wartość > 0!");
        }
        if (repo.removeAnimal(key)) System.out.println("Usunięto zwierzę o kluczu " + key);
        else System.out.println("Usuwanie nie powiodło się.");
    }

    void getByKey() {
        System.out.println("Pobieranie po kluczu.");
        System.out.println("Podaj klucz: ");
        long key = -1L;
        while (key < 0) {
            try {
                key = scanner.nextLong();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("Podaj prawidłową wartość > 0!");
            }
            if (key < 0 ) System.out.println("Podaj prawidłową wartość > 0!");
        }
        Optional<Animal> a = repo.getByKey(key);
        if (a.isPresent()) {
            System.out.format("|%20s|%20s|%20s|%20s|\n", "Imię", "Gatunek", "Dzienna karma [kg]", "Rodzaj karmy");
            System.out.print(String.format("|%20s|%20s|%20s|%20s|\n", "-", "-", "-", "-").replace(' ', '-'));
            System.out.format("|%20s|%20s|%12s%4f|%20s|\n", a.get().getName(), a.get().getSpecies(), " ", a.get().getFeedPerDay(), a.get().getFeedType());
        } else System.out.println("Nie znaleziono zwierzęcia o takim kluczu!");
    }

    void updateAnimal() {
        System.out.println("Aktualizacja zwierzęcia");
        System.out.println("Podaj klucz: ");
        long key = -1L;
        while (key < 0) {
            try {
                key = scanner.nextLong();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("Podaj prawidłową wartość > 0!");
            }
        }
        Optional<Animal> a = repo.getByKey(key);
        if (a.isPresent()) {
            System.out.println("Podaj nowe dane. Pozostaw puste lub wybierz odpowiednią opcję by nie zmieniać");
            Animal newAnimal = new Animal();
            System.out.println("Podaj nowe imię - obecnie: " + a.get().getName());
            newAnimal.setName(scanner.nextLine());
            if (newAnimal.getName().isEmpty()) newAnimal.setName(a.get().getName());
            System.out.println("Podaj gatunek- obecnie: " + a.get().getSpecies());
            newAnimal.setSpecies(scanner.nextLine());
            if (newAnimal.getSpecies().isEmpty()) newAnimal.setSpecies(a.get().getSpecies());
            System.out.println("Wybierz rodzaj karmy - obecnie: " + a.get().getFeedType() + "\n0.bez zmian\n1.dla roślinożernych\n2.dla drapieżnikow\n3.dla gadów");
            int feedType = -1;
            while (feedType < 0 || feedType > 3) {
                try {
                    feedType = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    scanner.next();
                    System.out.println("Podaj prawidłową wartość 0-3!");
                }
                switch (feedType) {
                    case 0:
                        newAnimal.setFeedType(a.get().getFeedType());
                        break;
                    case 1:
                        newAnimal.setFeedType("dla roślinożernych");
                        break;
                    case 2:
                        newAnimal.setFeedType("dla drapieżnikow");
                        break;
                    case 3:
                        newAnimal.setFeedType("dla gadów");
                        break;
                    default:
                        System.out.println("Podaj prawidłową wartość 0-3!");
                }
            }
            System.out.println("Podaj dzienne zapotrzebowanie na karmę w kg - obecnie: " + a.get().getFeedPerDay() + ". Podaj 0 by nie zmieniać.");
            double amount = -1;
            while (amount < 0) {
                try {
                    amount = scanner.nextDouble();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Podaj prawidłową wartość > 0kg!");
                    scanner.next();
                }
                if (amount > 0) newAnimal.setFeedPerDay(amount);
                else if (amount == 0) newAnimal.setFeedPerDay(a.get().getFeedPerDay());
                else System.out.println("Podaj prawidłową wartość > 0kg!");
            }
            repo.updateAnimal(key, newAnimal);
        } else System.out.println("Nie znaleziono zwierzęcia o takim kluczu!");
    }

    void getAndPrintAll() {
        System.out.println("Pobieranie wszystkich zwierząt");
        Set<Long> keys = repo.getAllKeys();
        if (keys.isEmpty()) {
            System.out.println("Nie ma żadnych zwierząt!");
            return;
        }
        Optional<Animal> a;
        System.out.format("|%20s|%20s|%20s|%20s|%20s|\n", "Klucz", "Imię", "Gatunek", "Dzienna karma [kg]", "Rodzaj karmy");
        System.out.print(String.format("|%20s|%20s|%20s|%20s|%20s|\n", "-", "-", "-", "-", "-").replace(' ', '-'));
        for (Long k : keys) {
            a = repo.getByKey(k);
            if (!a.isPresent()) throw new IllegalArgumentException("Pobranie zwierzecia dla nieistniejącego klucza");
            System.out.format("|%20d|%20s|%20s|%12s%4f|%20s|\n", k, a.get().getName(), a.get().getSpecies(), " ", a.get().getFeedPerDay(), a.get().getFeedType());
        }
    }

    void getByNameAndSpecies() {
        System.out.println("Pobieranie z zapytaniem");
        String name, species;
        System.out.println("Podaj imię zwierzęcia. '*' by wyszukać wszystkie.");
        name = scanner.nextLine();
        System.out.println("Podaj gatunek zwierzęcia. '*' by wyszukać wszystkie.");
        species = scanner.nextLine();
        System.out.println("Wyszukiwanie zwierząt gdzie imię='" + name + "' i gatunek='" + species + "'.");
        List<Animal> animals = repo.getByNameOrSpecies(name, species);
        if (animals.size() > 0) {
            System.out.format("|%20s|%20s|%20s|%20s|\n", "Imię", "Gatunek", "Dzienna karma [kg]", "Rodzaj karmy");
            System.out.print(String.format("|%20s|%20s|%20s|%20s|\n", "-", "-", "-", "-").replace(' ', '-'));
            for (Animal a : animals) {
                System.out.format("|%20s|%20s|%12s%4f|%20s|\n", a.getName(), a.getSpecies(), " ", a.getFeedPerDay(), a.getFeedType());
            }
        } else System.out.println("Nie ma żadnych zwierząt na liście!");
    }

    void getFeedAmounts() {
        System.out.println("Przetwarzanie danych - client");
        System.out.println("Podaj ile dni: ");
        int days = -1;
        while (days < 0) {
            try {
                days = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("Podaj prawidłową wartość > 0!");
            }
            if (days < 0) System.out.println("Podaj prawidłową wartość > 0!");
        }
        List<Pair<Animal, Double>> animals = repo.getFeedAmountForAll(days);
        if (animals.isEmpty()) {
            System.out.println("Nie ma żadnych zwierząt!");
            return;
        }
        System.out.format("|%20s|%20s|%20s|%20s|\n", "Imię", "Gatunek", "Karma na " + days +" dni [kg]", "Rodzaj karmy");
        System.out.print(String.format("|%20s|%20s|%20s|%20s|\n", "-", "-", "-", "-").replace(' ', '-'));
        for (Pair<Animal, Double> p : animals) {
            System.out.format("|%20s|%20s|%12s%4f|%20s|\n", p.getKey().getName(), p.getKey().getSpecies(), " ", p.getValue() , p.getKey().getFeedType());
        }
    }

}
