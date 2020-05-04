import data.Animal;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AnimalRepo {

    Animal addAnimal(Long key, Animal a);
    Optional<Animal> getByKey(Long key);
    Animal updateAnimal(Long key, Animal newAnimal);
    boolean removeAnimal(Long key);
    List<Animal> getAllAnimals();
    Set<Long> getAllKeys();
    List<Animal> getByNameOrSpecies(String name, String species);
    List<Pair<Animal, Double>> getFeedAmountForAll(int days);
}
