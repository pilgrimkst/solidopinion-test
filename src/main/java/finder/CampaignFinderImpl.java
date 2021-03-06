package finder;

import finder.index.CampaignIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Long.compare;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class CampaignFinderImpl implements CampaignFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignFinderImpl.class);

    private final CampaignIndex index;
    private final CampaignImpressionsCounter impressionsCounter;

    public CampaignFinderImpl(CampaignIndex index, CampaignImpressionsCounter impressionsCounter) {
        this.index = index;
        this.impressionsCounter = impressionsCounter;
    }

    @Override
    public Optional<String> find(List<Integer> userSegments) {
        LOGGER.debug("Searching campaigns for segments: {}", userSegments);

        Map<String, Long> weighedCampaigns = userSegments
                .stream()
                .map(index::campaignsForSegment)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(Collection::stream)
                .collect(groupingBy(i -> i, counting()));

        LOGGER.debug("Campaigns results: {}", weighedCampaigns);

        Optional<String> bestScoreCampaign = weighedCampaigns
                .entrySet()
                .stream()
                .max((o1, o2) -> {
                    int compare = compare(o1.getValue(), o2.getValue());
                    if (compare == 0) {
                        compare = -1 * compare(
                                impressionsCounter.getImpressions(o1.getKey()),
                                impressionsCounter.getImpressions(o2.getKey()));
                    }
                    return compare;
                })
                .map(Map.Entry::getKey);

        bestScoreCampaign
                .ifPresent(impressionsCounter::addImpression);

        LOGGER.debug("Campaign with best score: {}", bestScoreCampaign);

        return bestScoreCampaign;
    }

}
