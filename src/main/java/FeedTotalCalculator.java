import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.map.IMap;
import data.Animal;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.Callable;

public class FeedTotalCalculator implements Callable<Double>, Serializable, HazelcastInstanceAware {
    private final static long serialVersionUID = 1L;
    private transient HazelcastInstance instance;

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.instance = hazelcastInstance;
    }

    @Override
    public Double call() throws Exception {
        IMap<Long, Animal> animals = instance.getMap("animals");
        Set<Long> keys = animals.localKeySet();
        System.out.println("Instancja "+instance);
        double total = 0;
        for (Long k : keys) {
            System.out.println("Klucz "+k+" => "+animals.get(k));
            total += animals.get(k).getFeedPerDay();
        }
        System.out.println("Razem "+total+"kg karmy wszystkich rodzajów.");
        return total;
    }
}
