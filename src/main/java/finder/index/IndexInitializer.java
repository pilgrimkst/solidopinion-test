package finder.index;

import java.util.Collection;

public interface IndexInitializer {
    void addToIndex(String name, Collection<Integer> segments);
}
