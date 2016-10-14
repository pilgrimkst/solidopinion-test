package finder.index;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Optional.ofNullable;

public class CampaignIndexImpl implements CampaignIndex, IndexInitializer {
    private final ConcurrentMap<Integer, ConcurrentMap<String, Boolean>> segmentsToCampaigns = new ConcurrentHashMap<>(128, 0.75f, 2);

    @Override
    public Optional<Collection<String>> campaignsForSegment(Integer segment) {
        return ofNullable(segmentsToCampaigns.get(segment)).map(Map::keySet);
    }

    @Override
    public void addToIndex(String name, Collection<Integer> segments) {
        segments.forEach(s -> {
            ConcurrentMap<String, Boolean> campaigns = segmentsToCampaigns.get(s);
            if (campaigns == null) {
                segmentsToCampaigns.putIfAbsent(s, new ConcurrentHashMap<>(16, 1.0f, 1));
            }
            segmentsToCampaigns.get(s).putIfAbsent(name, Boolean.TRUE);
        });
    }
}
