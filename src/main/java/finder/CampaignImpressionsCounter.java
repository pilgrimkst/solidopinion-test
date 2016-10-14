package finder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class CampaignImpressionsCounter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignImpressionsCounter.class);
    private final Map<String, AtomicLong> campaignImpressions = new HashMap<>();

    public long getImpressions(String companyId) {
        AtomicLong c = getCounterForId(companyId);
        return c.get();
    }

    public long addImpression(String companyId) {
        AtomicLong c = getCounterForId(companyId);
        LOGGER.debug("Increasing impressions for campaign {}, impressions: {}", companyId, campaignImpressions);
        return c.getAndIncrement();
    }

    private AtomicLong getCounterForId(String companyId) {
        AtomicLong c = campaignImpressions.get(companyId);
        if (c == null) {
            c = new AtomicLong(0);
            campaignImpressions.putIfAbsent(companyId, c);
            c = campaignImpressions.get(companyId);
        }
        return c;
    }

}
