import groovyx.net.http.RESTClient
import spock.lang.Specification
import spock.lang.Unroll

class RestSpecification extends Specification {

    RESTClient restClient = new RESTClient("http://api.openweathermap.org")

    def 'Check if the weather in Eindhoven can be found'() {
        given:
        String city = "Eindhoven,nl"

        when:
        def response = restClient.get( path: '/data/2.5/weather',
                query: ['q' : city])

        then:
        response.status == 200

        and:
        response.responseData.name == "Eindhoven"
    }

    @Unroll("Check #city matches #expectedResult")
    def 'Check if we can find multiple cities'() {
        when:
        def response = restClient.get( path: '/data/2.5/weather', query: ['q' : city])

        then:
        assert response.status == 200

        and:
        response.responseData.name == expectedResult

        where:
        city            | expectedResult
        "Eindhoven,nl"  | "Eindhoven"
        "Amsterdam,nl"  | "Amsterdam"
        "Brussels,be"   | "Brussels"
    }
}