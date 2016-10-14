package finder.index;

import java.util.Collection;
import java.util.Optional;

public interface CampaignIndex {
    Optional<Collection<Integer>> campaignIdsForSegment(Integer segment);

    Optional<String> campaignNameForId(Integer id);
}
