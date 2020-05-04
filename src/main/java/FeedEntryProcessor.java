import com.hazelcast.map.EntryProcessor;
import data.Animal;

import java.io.Serializable;
import java.util.Map;

public class FeedEntryProcessor implements EntryProcessor<Long, Animal, Double>, Serializable {
    private final static long serialVersionUID = 5L;

    @Override
    public Double process(Map.Entry<Long, Animal> entry) {
        return entry.getValue().getFeedPerDay();
    }
}
