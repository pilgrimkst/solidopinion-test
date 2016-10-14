import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class CampaignFinderImpl implements CampaignFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignFinderImpl.class);
    private final Map<Integer, List<Integer>> segmentsToCampaigns;
    private final List<String> campaignNames;

    public CampaignFinderImpl(Map<Integer, List<Integer>> segmentsToCampaigns, List<String> campaignNames) {
        this.segmentsToCampaigns = segmentsToCampaigns;
        this.campaignNames = campaignNames;
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

        Optional<String> bestScoreCampaign = weighedCampaigns
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(e -> campaignNames.get(e.getKey()));
        LOGGER.debug("Campaign with best score: {}", bestScoreCampaign);

        return bestScoreCampaign;
    }
}
