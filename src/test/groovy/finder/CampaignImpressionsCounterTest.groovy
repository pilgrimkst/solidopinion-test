package finder

import spock.lang.Specification

class CampaignImpressionsCounterTest extends Specification {

    def "Should increment counter for id and return old value"() {
        given:
        def c = new CampaignImpressionsCounter()

        when:
        def result = c.addImpression(1)

        then:
        result == 0l

        when:
        result = c.addImpression(1)

        then:
        result == 1l

        when:
        result = c.getImpressions(1)

        then:
        result == 2l
    }

    def "Should get counter for id"() {
        given:
        def c = new CampaignImpressionsCounter()

        when:
        def result = c.getImpressions(1)

        then:
        result == 0l
    }

}
