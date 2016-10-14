package finder.index

import spock.lang.Specification

class CampaignIndexImplTest extends Specification {

    def "addToIndex should add new campaign to segment"() {
        given:
        def index = new CampaignIndexImpl()

        when:
        index.addToIndex("a", [0, 1, 2])
        index.addToIndex("b", [2, 3, 4])
        index.addToIndex("a", [0])

        then:
        index.campaignsForSegment(2).get().containsAll(["a", "b"])
        index.campaignsForSegment(0).get().size() == 1 && index.campaignsForSegment(0).get().containsAll(["a"])
    }
}
