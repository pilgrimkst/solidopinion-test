import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class CampaignFinderImpl implements CampaignFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignFinderImpl.class);
    private final Map<Integer, List<Integer>> segmentsToCampaigns;
    private final List<String> campaignNames;
    private final List<AtomicLong> campaignImpressions;

    public CampaignFinderImpl(Map<Integer, List<Integer>> segmentsToCampaigns, List<String> campaignNames) {
        this.segmentsToCampaigns = segmentsToCampaigns;
        this.campaignNames = campaignNames;

        campaignImpressions = IntStream
                .range(0, campaignNames.size())
                .mapToObj(i -> new AtomicLong(0))
                .collect(toList());
    }

    @Override
    public Optional<String> find(List<Integer> userSegments) {
        LOGGER.debug("Searching campaigns for segments: {}", userSegments);

        Map<Integer, Long> weighedCampaigns = userSegments
                .stream()
                .map((key) -> Optional.ofNullable(segmentsToCampaigns.get(key)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(Collection::stream)
                .collect(groupingBy(i -> i, counting()));

        LOGGER.debug("Campaigns results: {}", weighedCampaigns);

        Optional<Integer> bestScoreCampaign = weighedCampaigns
                .entrySet()
                .stream()
                .max((o1, o2) -> {
                    int compare = Long.compare(o1.getValue(), o2.getValue());
                    if (compare == 0) {
                        compare = -1 * Long.compare(
                                campaignImpressions.get(o1.getKey()).get(),
                                campaignImpressions.get(o2.getKey()).get());
                    }
                    return compare;
                })
                .map(Map.Entry::getKey);

        bestScoreCampaign
                .ifPresent(i -> {
                    LOGGER.trace("Increasing impressions for campaign {}, impressions: {}", i, campaignImpressions);
                    campaignImpressions.get(i).incrementAndGet();
                });

        LOGGER.debug("Campaign with best score: {}", bestScoreCampaign);

        return bestScoreCampaign.map(campaignNames::get);
    }

}
