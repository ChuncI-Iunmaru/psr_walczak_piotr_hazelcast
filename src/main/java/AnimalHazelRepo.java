import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import data.Animal;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/*Klasa zawiera metody operujące na mapie z danej instancji/klienta Hazelcast*/
public class AnimalHazelRepo implements AnimalRepo {

    private IMap<Long, Animal> map;

    public AnimalHazelRepo(IMap<Long, Animal> map) {
        this.map = map;
    }

    @Override
    public Animal addAnimal(Long key, Animal a){
        return map.put(key, a);
    }

    @Override
    public Optional<Animal> getByKey(Long key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public Animal updateAnimal(Long key, Animal newAnimal) {
        return map.put(key, newAnimal);
    }

    @Override
    public boolean removeAnimal(Long key) {
        return map.remove(key) != null;
    }

    @Override
    public List<Animal> getAllAnimals() {
        return map.values().stream().collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Set<Long> getAllKeys() {
        return map.keySet();
    }

    @Override
    public List<Animal> getByNameOrSpecies(String name, String species) {
        Predicate<?, ?> namePredicate = Predicates.equal("name", name);
        Predicate<?, ?> speciesPredicate = Predicates.equal("species", species);
        Collection<Animal> result = Collections.emptyList();
        if (name.equals("*") && species.equals("*")) {
            return getAllAnimals();
        } else if (!name.equals("*") && species.equals("*")) {
            result = map.values(Predicates.equal("name", name));
        } else if (name.equals("*")) {
            result = map.values(Predicates.equal("species", species));
        } else result = map.values(Predicates.and(namePredicate, speciesPredicate));
        return result.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Pair<Animal, Double>> getFeedAmountForAll(int days) {
        Map<Long, Double> feedAmounts = map.executeOnEntries(new FeedEntryProcessor());
        List<Pair<Animal, Double>> result = new ArrayList<>();
        for (Long k : map.keySet()) {
            result.add(new Pair<>(map.get(k), feedAmounts.get(k)*days));
        }
        return result;
    }
}
