package finder.index;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CampaignIndexImpl implements CampaignIndex, IndexInitializer {
    private final AtomicInteger campaignIdGenerator = new AtomicInteger(0);
    private final Map<Integer, Set<Integer>> segmentsToCampaigns = new HashMap<>();
    private final Map<Integer, String> campaignNames = new HashMap<>();
    private final Map<String, Integer> campaignIds = new HashMap<>();

    @Override
    public Optional<Collection<Integer>> campaignIdsForSegment(Integer segment) {
        return Optional.ofNullable(segmentsToCampaigns.get(segment));
    }

    @Override
    public Optional<String> campaignNameForId(Integer id) {
        return Optional.ofNullable(campaignNames.get(id));
    }

    @Override
    public void addCampaign(String name, Collection<Integer> segments) {
//        if()
//        int id = campaignIdGenerator.getAndIncrement();
//        campaignNames.put(id, name);
//        segments.forEach(s -> {
//            segmentsToCampaigns.getOrDefault(id, )
//        });
    }
}
