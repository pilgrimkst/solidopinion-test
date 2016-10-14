package finder;

import java.util.List;
import java.util.Optional;

public interface CampaignFinder {
    Optional<String> find(List<Integer> userSegments);
}
