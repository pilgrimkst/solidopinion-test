import spock.lang.Specification
import spock.lang.Unroll

class CampaignFinderImplTest extends Specification {
    @Unroll
    def "find: #campaign => #userSegments"() {
        given:
//        campaign_a 3 4 10 2
//        campaign_b 9 14 15 21 3
//        campaign_c 12 1024 200 3 9 4

        def campaigns = ["campaign_a", "campaign_b", "campaign_c"]
        def segments = [3   : [0, 1, 2],
                        2   : [0],
                        4   : [0, 3],
                        10  : [0],
                        9   : [1, 2],
                        12  : [1, 2],
                        14  : [1],
                        15  : [1],
                        21  : [1],
                        1024: [2],
                        200 : [2]]

        def finder = new CampaignFinderImpl(segments, campaigns)
        expect:
        campaign == finder.find(userSegments)

        where:
        campaign                  | userSegments
        Optional.of("campaign_a") | [3, 4, 5, 10, 2, 200]
        Optional.of("campaign_a") | [3]
        Optional.of("campaign_c") | [3]
        Optional.of("campaign_a") | [4, 10, 15]
        Optional.of("campaign_b") | [1024, 15, 200, 21, 9, 14, 15]
        Optional.empty()          | [9000, 29833, 65000]

    }
}
