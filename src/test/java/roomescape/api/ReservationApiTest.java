package roomescape.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import roomescape.payment.PaymentManager;
import roomescape.payment.dto.PaymentError;
import roomescape.exception.custom.reason.payment.PaymentConfirmException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationApiTest {

    private static final Map<String, String> RESERVATION_BODY = new HashMap<>();
    private static final Map<String, String> TIME_BODY = new HashMap<>();
    private static final Map<String, String> THEME_BODY = new HashMap<>();
    private static final Map<String, String> MEMBER_BODY = new HashMap<>();
    private static final Map<String, Object> AUTH_BODY = new HashMap<>();

    private final JdbcTemplate jdbcTemplate;
    private final int port;

    @MockitoBean
    private final PaymentManager paymentManager;

    public ReservationApiTest(
            @LocalServerPort final int port,
            @Autowired final JdbcTemplate jdbcTemplate,
            @Autowired final PaymentManager paymentManager
    ) {
        this.port = port;
        this.jdbcTemplate = jdbcTemplate;
        this.paymentManager = paymentManager;
    }

    @BeforeAll
    static void initParams() {
        RESERVATION_BODY.put("date", "2026-08-05");
        RESERVATION_BODY.put("timeId", "1");
        RESERVATION_BODY.put("themeId", "1");
        RESERVATION_BODY.put("memberId", "1");
        RESERVATION_BODY.put("orderId", "BOOSTA-WTESTMC41NZA2MjQyMjI2MDEO");
        RESERVATION_BODY.put("paymentKey", "tgen_202505271618159Y599");
        RESERVATION_BODY.put("amount", "10000");
        RESERVATION_BODY.put("paymentType", "NORMAL");

        TIME_BODY.put("startAt", "10:00");

        THEME_BODY.put("name", "theme");
        THEME_BODY.put("description", "dest");
        THEME_BODY.put("thumbnail", "thumbnail");

        MEMBER_BODY.put("name", "브라운");
        MEMBER_BODY.put("email", "asd@email.com");
        MEMBER_BODY.put("password", "pass");

        AUTH_BODY.put("email", "asd@email.com");
        AUTH_BODY.put("password", "pass");
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM reservation_time");
        jdbcTemplate.update("DELETE FROM theme");
        jdbcTemplate.update("DELETE FROM member");
        jdbcTemplate.update("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE theme ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
    }

    @Nested
    @DisplayName("예약 생성")
    class Post {

        @DisplayName("reservation POST 요청 테스트")
        @ParameterizedTest
        @MethodSource
        void post(final Map<String, Object> body, final HttpStatus expectedStatusCode) {
            // given
            givenCreateMember();
            final Cookie cookie = givenAuthCookie();
            givenCreateReservationTime();
            givenCreateTheme();

            // when & then
            RestAssured.given().port(port)
                    .contentType(ContentType.JSON)
                    .cookie(cookie)
                    .body(body)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(expectedStatusCode.value());
        }

        static Stream<Arguments> post(){
            return Stream.of(
                Arguments.of(Map.of(
                        "date", "2026-12-01",
                        "timeId", 1L,
                        "themeId", 1L,
                        "orderId", "BOOSTA-WTESTMC41NZA2MjQyMjI2MDEO",
                        "paymentKey", "tgen_202505271618159Y599",
                        "amount", 10000,
                        "paymentType", "NORMAL"
                ), HttpStatus.CREATED),

                Arguments.of(Map.of(
                        "date", "2026-12-01",
                        "timeId", 1L,
                        "orderId", "BOOSTA-WTESTMC41NZA2MjQyMjI2MDEO",
                        "paymentKey", "tgen_202505271618159Y599",
                        "amount", 10000,
                        "paymentType", "NORMAL"
                ), HttpStatus.BAD_REQUEST),
                Arguments.of(Map.of(
                        "timeId", 1L,
                        "themeId", 1L,
                        "orderId", "BOOSTA-WTESTMC41NZA2MjQyMjI2MDEO",
                        "paymentKey", "tgen_202505271618159Y599",
                        "amount", 10000,
                        "paymentType", "NORMAL"
                ), HttpStatus.BAD_REQUEST),
                Arguments.of(Map.of(
                        "date", "2026-12-01",
                        "themeId", 1L,
                        "orderId", "BOOSTA-WTESTMC41NZA2MjQyMjI2MDEO",
                        "paymentKey", "tgen_202505271618159Y599",
                        "amount", 10000,
                        "paymentType", "NORMAL"
                ), HttpStatus.BAD_REQUEST),
                Arguments.of(Map.of(
                        "date", "2026-12-01",
                        "timeId", 1L,
                        "orderId", "BOOSTA-WTESTMC41NZA2MjQyMjI2MDEO",
                        "paymentKey", "tgen_202505271618159Y599",
                        "amount", 10000,
                        "paymentType", "NORMAL"
                ), HttpStatus.BAD_REQUEST),

                Arguments.of(Map.of(
                        "date", "",
                        "timeId", 1L,
                        "themeId", 1L,
                        "orderId", "BOOSTA-WTESTMC41NZA2MjQyMjI2MDEO",
                        "paymentKey", "tgen_202505271618159Y599",
                        "amount", 10000,
                        "paymentType", "NORMAL"
                ), HttpStatus.BAD_REQUEST),

                Arguments.of(Map.of(
                        "orderId", "BOOSTA-WTESTMC41NZA2MjQyMjI2MDEO",
                        "paymentKey", "tgen_202505271618159Y599",
                        "amount", 10000,
                        "paymentType", "NORMAL"
                ), HttpStatus.BAD_REQUEST)
            );
        }

        @DisplayName("존재하지 않는 시간을 선택하면, 400을 응답한다.")
        @Test
        void post2() {
            // given
            givenCreateMember();
            final Cookie cookie = givenAuthCookie();
            givenCreateTheme();

            // when & then
            RestAssured.given().port(port).log().all()
                    .contentType(ContentType.JSON)
                    .cookie(cookie)
                    .body(RESERVATION_BODY)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400);
        }

        @DisplayName("존재하지 않는 테마를 선택하면, 400을 응답한다.")
        @Test
        void post3() {
            // given
            givenCreateMember();
            final Cookie cookie = givenAuthCookie();
            givenCreateReservationTime();

            // when & then
            RestAssured.given().port(port).log().all()
                    .contentType(ContentType.JSON)
                    .cookie(cookie)
                    .body(RESERVATION_BODY)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400);
        }

        @DisplayName("예약을 생성할 때, PaymentManager#confirmPayment 예외가 발생하면 400을 응답한다.")
        @Test
        void post4() {
            // given
            givenCreateMember();
            final Cookie cookie = givenAuthCookie();
            givenCreateReservationTime();
            givenCreateTheme();
            doThrow(new PaymentConfirmException(new PaymentError("code", "message")))
                    .when(paymentManager).confirmPayment(any());

            // when & then
            RestAssured.given().port(port).log().all()
                    .contentType(ContentType.JSON)
                    .cookie(cookie)
                    .body(RESERVATION_BODY)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400);
        }

    }

    @Nested
    @DisplayName("예약 조회")
    class Get {

        @DisplayName("존재하는 모든 예약과 200 OK를 응답")
        @Test
        void get1() {
            // given
            givenCreateMember();
            final Cookie cookie = givenAuthCookie();
            givenCreateReservationTime();
            givenCreateTheme();
            givenCreateReservation(cookie);

            // when & then
            RestAssured.given().port(port).log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(1));
        }

        @DisplayName("예약이 존재하지 않는다면 200 OK와 빈 컬렉션 응답")
        @Test
        void get2() {
            // given & when & then
            RestAssured.given().port(port).log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(0));
        }

    }

    @Nested
    @DisplayName("예약 삭제")
    class Delete {

        @DisplayName("주어진 아이디에 해당하는 예약이 있다면 200 OK 응답")
        @Test
        void remove1() {
            // given
            givenCreateMember();
            final Cookie cookie = givenAuthCookie();
            givenCreateReservationTime();
            givenCreateTheme();
            givenCreateReservation(cookie);

            // when & then
            RestAssured.given().port(port).log().all()
                    .when().delete("/reservations/1")
                    .then().log().all()
                    .statusCode(204);
        }

        @DisplayName("주어진 아이디에 해당하는 예약이 없다면 404로 응답한다.")
        @Test
        void remove2() {
            // given & when & then
            RestAssured.given().port(port).log().all()
                    .when().delete("/reservations/1000")
                    .then().log().all()
                    .statusCode(404);
        }
    }

    @DisplayName("member가 가진 예약이 없다면 빈 리스트를 반환한다.")
    @Test
    void readAllMemberReservations() {
        // given
        givenCreateMember();
        final Cookie cookie = givenAuthCookie();
        givenCreateReservationTime();
        givenCreateTheme();

        // when
        RestAssured.given().port(port).log().all()
                .cookie(cookie)
                .when().get("/reservations/mine")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("member가 가진 예약이 있다면 예약 리스트를 반환한다.")
    @Test
    void readAllMemberReservations1() {
        // given
        givenCreateMember();
        final Cookie cookie = givenAuthCookie();
        givenCreateReservationTime();
        givenCreateTheme();
        givenCreateReservation(cookie);

        // when
        RestAssured.given().port(port).log().all()
                .cookie(cookie)
                .when().get("/reservations/mine")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약이 존재할 때 reservation을 waiting을 생성한다.")
    @Test
    void createWaiting(){
        // given
        givenCreateMember();
        final Cookie cookie = givenAuthCookie();

        AUTH_BODY.put("email", "asd123@email.com");
        MEMBER_BODY.put("email", "asd123@email.com");
        givenCreateMember();
        final Cookie anotherCookie = givenAuthCookie();
        givenCreateReservationTime();
        givenCreateTheme();
        givenCreateReservation(cookie);

        RESERVATION_BODY.put("memberId", "2");

        // when & then
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .cookie(anotherCookie)
                .body(RESERVATION_BODY)
                .when().post("/reservations/waiting")
                .then().log().all()
                .statusCode(201);
    }

    @DisplayName("예약이 존재하지 않는다면 400을 응답한다.")
    @Test
    void createWaiting1(){
        // given
        givenCreateMember();
        final Cookie cookie = givenAuthCookie();
        givenCreateReservationTime();
        givenCreateTheme();

        // when & then
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .cookie(cookie)
                .body(RESERVATION_BODY)
                .when().post("/reservations/waiting")
                .then().log().all()
                .statusCode(400);
    }

    @DisplayName("모든 대기 상태의 reservation을 조회한다.")
    @Test
    void readAllWaiting() {
        // given
        givenCreateMember();
        final Cookie cookie = givenAuthCookie();
        givenCreateReservationTime();
        givenCreateTheme();
        givenCreateReservation(cookie);

        MEMBER_BODY.put("email", "asd11@nav.com");
        AUTH_BODY.put("email", "asd11@nav.com");
        RESERVATION_BODY.put("memberId", "2");
        givenCreateMember();
        final Cookie cookie1 = givenAuthCookie();
        givenCreateWaitingReservation(cookie1);

        // when
        RestAssured.given().port(port).log().all()
                .cookie(cookie)
                .when().get("/reservations/waiting")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    private void givenCreateWaitingReservation(final Cookie cookie) {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .cookie(cookie)
                .body(RESERVATION_BODY)
                .when().post("/reservations/waiting")
                .then().log().all()
                .statusCode(201);
    }

    private void givenCreateReservationTime() {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(TIME_BODY)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);
    }

    private void givenCreateReservation(final Cookie cookie) {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .cookie(cookie)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }

    private void givenCreateTheme() {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(THEME_BODY)
                .when().post("/themes")
                .then().log().all()
                .statusCode(201);
    }

    private void givenCreateMember() {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(MEMBER_BODY)
                .when().post("/members")
                .then().log().all()
                .statusCode(201);
    }

    private Cookie givenAuthCookie() {
        return RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(AUTH_BODY)
                .when().post("/login")
                .then()
                .extract().detailedCookie("token");
    }
}
