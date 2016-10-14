import finder.CampaignFinderImpl
import finder.CampaignImpressionsCounter
import finder.index.CampaignIndex
import spock.lang.Specification

class CampaignFinderImplTest extends Specification {
    def index = Mock(CampaignIndex)
    def finder = new CampaignFinderImpl(index, new CampaignImpressionsCounter())

    def "find: returns most weighted campaign by number of matching segments"() {
        when:
        def result = finder.find([3, 4, 5])

        then:
        1 * index.campaignIdsForSegment(3) >> Optional.of([0])
        1 * index.campaignIdsForSegment(4) >> Optional.of([0])
        1 * index.campaignIdsForSegment(5) >> Optional.of([1])

        then:
        1 * index.campaignNameForId(0) >> Optional.of("campaign_a")

        0 * _

        then:
        result == Optional.of("campaign_a")
    }

    def "find: favors cases, when there are no campaign for some segment"() {
        when:
        def result = finder.find([0, 1])

        then:
        1 * index.campaignIdsForSegment(0) >> Optional.empty()
        1 * index.campaignIdsForSegment(1) >> Optional.of([0])

        then:
        1 * index.campaignNameForId(0) >> Optional.of("campaign for existing segment")

        0 * _

        then:
        result == Optional.of("campaign for existing segment")
    }

    def "find: favors cases, when there are empty compaigns for some segment"() {
        when:
        def result = finder.find([0, 1])

        then:
        1 * index.campaignIdsForSegment(0) >> Optional.of([])
        1 * index.campaignIdsForSegment(1) >> Optional.of([])

        0 * _

        then:
        result == Optional.empty()
    }

    def "find: favors cases, when there are no matching campaigns at all"() {
        when:
        def result = finder.find([0, 1])

        then:
        1 * index.campaignIdsForSegment(0) >> Optional.empty()
        1 * index.campaignIdsForSegment(1) >> Optional.empty()

        then:
        0 * _

        then:
        result == Optional.empty()

    }

    def "find should return campaign with least impressions, if weights are same"() {
        when:
        def result = [finder.find([3, 4]), finder.find([3, 4])]

        then:
        1 * index.campaignIdsForSegment(3) >> Optional.of([0, 1])
        1 * index.campaignIdsForSegment(4) >> Optional.of([0, 1])

        1 * index.campaignNameForId(0) >> Optional.of("a")

        then:
        1 * index.campaignIdsForSegment(3) >> Optional.of([0, 1])
        1 * index.campaignIdsForSegment(4) >> Optional.of([0, 1])

        1 * index.campaignNameForId(1) >> Optional.of("b")

        0 * _

        then:
        result == [Optional.of("a"), Optional.of("b")]

    }
}
