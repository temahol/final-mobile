package helpers;

import config.DriverConfig;
import org.aeonbits.owner.ConfigFactory;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class BrowserStack {

    static DriverConfig config = ConfigFactory.create(DriverConfig.class, System.getProperties());

    public static String getVideoUrl(String sessionId) {
        String uri = format("https://api.browserstack.com/app-automate/sessions/%s.json", sessionId);
        return given()
                .log().all()
                .filter(withCustomTemplates())
                .auth().basic(config.getUser(), config.getKey())
                .when()
                .get(uri)
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("automation_session.video_url");
    }
}
