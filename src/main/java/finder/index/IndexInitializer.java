package finder.index;

import java.util.Collection;

public interface IndexInitializer {
    void addCampaign(String name, Collection<Integer> segments);
}
