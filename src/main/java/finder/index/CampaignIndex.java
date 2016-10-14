package finder.index;

import java.util.Collection;
import java.util.Optional;

public interface CampaignIndex {
    Optional<Collection<String>> campaignsForSegment(Integer segment);
}
