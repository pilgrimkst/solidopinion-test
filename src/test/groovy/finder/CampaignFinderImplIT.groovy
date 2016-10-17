package finder

import finder.index.CampaignIndexer
import spock.lang.Specification

class CampaignFinderImplIT extends Specification {
    def "Find"() {
        given:
        InputStream is = getClass().getResourceAsStream("../campaign.txt")
        def index = CampaignIndexer.buildFromStream(is)
        def finder = new CampaignFinderImpl(index, new CampaignImpressionsCounter())

        expect:
        finder.find([24691, 8626, 31755]) == Optional.of("psychologist")
        finder.find([25481, 27928, 22956]) == Optional.of("commissioner")
    }

}
