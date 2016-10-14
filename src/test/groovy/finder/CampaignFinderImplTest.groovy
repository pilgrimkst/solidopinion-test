package finder

import finder.index.CampaignIndex
import spock.lang.Specification

class CampaignFinderImplTest extends Specification {
    def index = Mock(CampaignIndex)
    def finder = new CampaignFinderImpl(index, new CampaignImpressionsCounter())

    def "find: returns most weighted campaign by number of matching segments"() {
        when:
        def result = finder.find([3, 4, 5])

        then:
        1 * index.campaignsForSegment(3) >> Optional.of(["A"])
        1 * index.campaignsForSegment(4) >> Optional.of(["A"])
        1 * index.campaignsForSegment(5) >> Optional.of(["B"])

        0 * _

        then:
        result == Optional.of("A")
    }

    def "find: favors cases, when there are no campaign for some segment"() {
        when:
        def result = finder.find([0, 1])

        then:
        1 * index.campaignsForSegment(0) >> Optional.empty()
        1 * index.campaignsForSegment(1) >> Optional.of(["A"])

        0 * _

        then:
        result == Optional.of("A")
    }

    def "find: favors cases, when there are empty compaigns for some segment"() {
        when:
        def result = finder.find([0, 1])

        then:
        1 * index.campaignsForSegment(0) >> Optional.of([])
        1 * index.campaignsForSegment(1) >> Optional.of([])

        0 * _

        then:
        result == Optional.empty()
    }

    def "find: favors cases, when there are no matching campaigns at all"() {
        when:
        def result = finder.find([0, 1])

        then:
        1 * index.campaignsForSegment(0) >> Optional.empty()
        1 * index.campaignsForSegment(1) >> Optional.empty()

        then:
        0 * _

        then:
        result == Optional.empty()

    }

    def "find should return campaign with least impressions, if weights are same"() {
        when:
        def result = [finder.find([3, 4]), finder.find([3, 4])]

        then:
        1 * index.campaignsForSegment(3) >> Optional.of(["a", "b"])
        1 * index.campaignsForSegment(4) >> Optional.of(["a", "b"])

        then:
        1 * index.campaignsForSegment(3) >> Optional.of(["a", "b"])
        1 * index.campaignsForSegment(4) >> Optional.of(["a", "b"])

        0 * _

        then:
        result == [Optional.of("a"), Optional.of("b")]

    }
}
