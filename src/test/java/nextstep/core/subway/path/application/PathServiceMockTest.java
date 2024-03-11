package nextstep.core.subway.path.application;

import nextstep.core.auth.domain.LoginUser;
import nextstep.core.auth.domain.NonLoginUser;
import nextstep.core.auth.domain.constants.AgeGroup;
import nextstep.core.member.application.MemberService;
import nextstep.core.member.application.dto.MemberResponse;
import nextstep.core.member.domain.Member;
import nextstep.core.subway.line.application.LineService;
import nextstep.core.subway.line.domain.Line;
import nextstep.core.subway.path.application.dto.PathFinderResponse;
import nextstep.core.subway.path.application.dto.PathFinderResult;
import nextstep.core.subway.path.application.dto.PathRequest;
import nextstep.core.subway.path.domain.PathType;
import nextstep.core.subway.section.domain.Section;
import nextstep.core.subway.station.domain.Station;
import nextstep.core.subway.station.fixture.StationFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static nextstep.core.member.fixture.MemberFixture.잭슨;
import static nextstep.core.subway.path.fixture.PathFixture.경로_조회_최단거리_타입;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("경로 조회 관련 Mock 테스트")
@ExtendWith(MockitoExtension.class)
public class PathServiceMockTest {

    PathService pathService;

    @Mock
    LineService lineService;

    @Mock
    PathFinder pathFinder;

    @Mock
    MemberService memberService;

    @Mock
    FareCalculator fareCalculator;

    @BeforeEach
    void 서비스_객체_생성() {
        pathService = new PathService(lineService, pathFinder, memberService, fareCalculator);
    }

    @Nested
    class 지하철_경로 {
        List<Line> 모든_노선_목록;

        Line 이호선;
        Line 신분당선;
        Line 삼호선;
        Line 사호선;

        Station 교대;
        Station 강남;
        Station 양재;
        Station 남부터미널;
        Station 정왕;
        Station 오이도;

        Long 교대역_번호;
        Long 강남역_번호;
        Long 양재역_번호;
        Long 남부터미널역_번호;
        Long 정왕역_번호;
        Long 오이도역_번호;


        @BeforeEach
        void 사전_노선_설정() {
            이호선 = new Line("이호선", "green", 0);
            신분당선 = new Line("신분당선", "red", 400);
            삼호선 = new Line("삼호선", "orange", 800);
            사호선 = new Line("사호선", "blue", 600);

            모든_노선_목록 = List.of(이호선, 신분당선, 삼호선, 사호선);

            교대역_번호 = 1L;
            강남역_번호 = 2L;
            양재역_번호 = 3L;
            남부터미널역_번호 = 4L;
            정왕역_번호 = 5L;
            오이도역_번호 = 6L;

            교대 = StationFixture.교대;
            ReflectionTestUtils.setField(교대, "id", 교대역_번호);

            강남 = StationFixture.강남;
            ReflectionTestUtils.setField(강남, "id", 강남역_번호);

            양재 = StationFixture.양재;
            ReflectionTestUtils.setField(양재, "id", 양재역_번호);

            남부터미널 = StationFixture.남부터미널;
            ReflectionTestUtils.setField(남부터미널, "id", 남부터미널역_번호);

            정왕 = StationFixture.정왕;
            ReflectionTestUtils.setField(정왕, "id", 정왕역_번호);

            오이도 = StationFixture.오이도;
            ReflectionTestUtils.setField(오이도, "id", 오이도역_번호);

            이호선.addSection(new Section(교대, 강남, 10, 10, 이호선));
            신분당선.addSection(new Section(강남, 양재, 10, 10, 신분당선));
            삼호선.addSection(new Section(교대, 남부터미널, 2, 2, 삼호선));
            삼호선.addSection(new Section(남부터미널, 양재, 3, 3, 삼호선));
            사호선.addSection(new Section(정왕, 오이도, 10, 10, 사호선));
        }

        @Nested
        class 최적_경로_찾기 {
            
            @Nested
            class 로그인한_회원_요청 {

                /**
                 * Given 지하철 노선을 생성하고, 구간을 추가한다.
                 * When  출발역과 도착역을 통해 최단거리 기준으로 로그인한 회원이 경로를 조회할 경우
                 * Then  경로 내 존재하는 역, 이동 거리, 소요 시간, 요금 정보를 확인할 수 있다.
                 */
                @Test
                void 강남역에서_남부터미널역까지_경로_조회() {
                    // given
                    LoginUser 로그인된_잭슨_정보 = new LoginUser(잭슨.get이메일());
                    PathFinderResult 경로_조회_결과 = new PathFinderResult(List.of(강남, 교대, 남부터미널), 12, 12, List.of(0, 800));

                    when(lineService.findStation(강남역_번호)).thenReturn(강남);
                    when(lineService.findStation(남부터미널역_번호)).thenReturn(남부터미널);
                    when(lineService.findAllLines()).thenReturn(모든_노선_목록);
                    when(pathFinder.findOptimalPath(모든_노선_목록, 강남, 남부터미널, PathType.valueOf(경로_조회_최단거리_타입)))
                            .thenReturn(경로_조회_결과);
                    when(memberService.findMe(로그인된_잭슨_정보)).thenReturn(MemberResponse.of(new Member(잭슨.get이메일(), 잭슨.get비밀번호(), 잭슨.get나이())));
                    when(fareCalculator.calculateTotalFare(anyInt(), anyList(), any(AgeGroup.class))).thenReturn(1250 + 100 + 800);

                    // when
                    PathFinderResponse 경로_조회_응답 = pathService.findOptimalPath(new PathRequest(강남역_번호, 남부터미널역_번호, 경로_조회_최단거리_타입), 로그인된_잭슨_정보);

                    // then
                    assertThat(경로_조회_응답).usingRecursiveComparison()
                            .isEqualTo(new PathFinderResponse(경로_조회_결과, 1250 + 100 + 800));
                }

                /**
                 * Given 지하철 노선을 생성하고, 구간을 추가한다.
                 * When  출발역과 도착역을 통해 최단거리 기준으로 로그인한 회원이 경로를 조회할 경우
                 * Then  경로 내 존재하는 역, 이동 거리, 소요 시간, 요금 정보를 확인할 수 있다.
                 */
                @Test
                void 교대역에서_양재역까지_경로_조회() {
                    // given
                    LoginUser 로그인된_잭슨_정보 = new LoginUser(잭슨.get이메일());
                    PathFinderResult 경로_조회_결과 = new PathFinderResult(List.of(교대, 남부터미널, 양재), 5, 5, List.of(800));

                    when(lineService.findStation(교대역_번호)).thenReturn(교대);
                    when(lineService.findStation(양재역_번호)).thenReturn(양재);
                    when(lineService.findAllLines()).thenReturn(모든_노선_목록);
                    when(pathFinder.findOptimalPath(모든_노선_목록, 교대, 양재, PathType.valueOf(경로_조회_최단거리_타입)))
                            .thenReturn(경로_조회_결과);
                    when(memberService.findMe(로그인된_잭슨_정보)).thenReturn(MemberResponse.of(new Member(잭슨.get이메일(), 잭슨.get비밀번호(), 잭슨.get나이())));
                    when(fareCalculator.calculateTotalFare(anyInt(), anyList(), any(AgeGroup.class))).thenReturn(1250 + 0 + 800);

                    // when
                    PathFinderResponse 경로_조회_응답 = pathService.findOptimalPath(new PathRequest(교대역_번호, 양재역_번호, 경로_조회_최단거리_타입), 로그인된_잭슨_정보);

                    // then
                    assertThat(경로_조회_응답).usingRecursiveComparison()
                            .isEqualTo(new PathFinderResponse(경로_조회_결과, 1250 + 0 + 800));
                }

            }

            @Nested
            class 로그인하지_않은_회원_요청 {

                /**
                 * Given 지하철 노선을 생성하고, 구간을 추가한다.
                 * When  출발역과 도착역을 통해 최단거리 기준으로 경로를 조회할 경우
                 * Then  경로 내 존재하는 역, 이동 거리, 소요 시간, 요금 정보를 확인할 수 있다.
                 */
                @Test
                void 강남역에서_남부터미널역까지_경로_조회() {
                    // given
                    NonLoginUser 로그인되지_않은_회원 = new NonLoginUser();
                    PathFinderResult 경로_조회_결과 = new PathFinderResult(List.of(강남, 교대, 남부터미널), 12, 12, List.of(0, 800));

                    when(lineService.findStation(강남역_번호)).thenReturn(강남);
                    when(lineService.findStation(남부터미널역_번호)).thenReturn(남부터미널);
                    when(lineService.findAllLines()).thenReturn(모든_노선_목록);
                    when(pathFinder.findOptimalPath(모든_노선_목록, 강남, 남부터미널, PathType.valueOf(경로_조회_최단거리_타입)))
                            .thenReturn(경로_조회_결과);
                    when(fareCalculator.calculateTotalFare(anyInt(), anyList(), any(AgeGroup.class))).thenReturn(1250 + 100 + 800);

                    // when
                    PathFinderResponse 경로_조회_응답 = pathService.findOptimalPath(new PathRequest(강남역_번호, 남부터미널역_번호, 경로_조회_최단거리_타입), 로그인되지_않은_회원);

                    // then
                    assertThat(경로_조회_응답).usingRecursiveComparison()
                            .isEqualTo(new PathFinderResponse(경로_조회_결과, 1250 + 100 + 800));
                }

                /**
                 * Given 지하철 노선을 생성하고, 구간을 추가한다.
                 * When  출발역과 도착역을 통해 최단거리 기준으로 경로를 조회할 경우
                 * Then  경로 내 존재하는 역, 이동 거리, 소요 시간, 요금 정보를 확인할 수 있다.
                 */
                @Test
                void 교대역에서_양재역까지_경로_조회() {
                    // given
                    NonLoginUser 로그인되지_않은_회원 = new NonLoginUser();
                    PathFinderResult 경로_조회_결과 = new PathFinderResult(List.of(교대, 남부터미널, 양재), 5, 5, List.of(800));

                    when(lineService.findStation(교대역_번호)).thenReturn(교대);
                    when(lineService.findStation(양재역_번호)).thenReturn(양재);
                    when(lineService.findAllLines()).thenReturn(모든_노선_목록);
                    when(pathFinder.findOptimalPath(모든_노선_목록, 교대, 양재, PathType.valueOf(경로_조회_최단거리_타입))).thenReturn(경로_조회_결과);
                    when(fareCalculator.calculateTotalFare(anyInt(), anyList(), any(AgeGroup.class))).thenReturn(1250 + 0 + 800);

                    // when
                    PathFinderResponse 경로_조회_응답 = pathService.findOptimalPath(new PathRequest(교대역_번호, 양재역_번호, 경로_조회_최단거리_타입), 로그인되지_않은_회원);

                    // then
                    assertThat(경로_조회_응답).usingRecursiveComparison()
                            .isEqualTo(new PathFinderResponse(경로_조회_결과, 1250 + 0 + 800));
                }

            }
        }

        @Nested
        class 유효한_경로인지_확인 {
            /**
             * Given 지하철 노선을 생성하고, 구간을 추가한다.
             * When  출발역과 도착역을 잇는 유효한 경로인지 확인할 경우
             * Then  유효한 경로로 확인할 수 있다.
             */
            @Test
            void 강남역에서_남부터미널역까지_유효한_경로_확인() {
                // given
                when(lineService.findStation(강남역_번호)).thenReturn(강남);
                when(lineService.findStation(남부터미널역_번호)).thenReturn(남부터미널);
                when(lineService.findAllLines()).thenReturn(모든_노선_목록);
                when(pathFinder.existPathBetweenStations(모든_노선_목록, 강남, 남부터미널)).thenReturn(true);

                // when
                boolean actual = pathService.isValidPath(new PathRequest(강남역_번호, 남부터미널역_번호));


                // then
                verify(pathFinder, atLeast(1)).existPathBetweenStations(모든_노선_목록, 강남, 남부터미널);
                assertThat(actual).isTrue();
            }
        }
    }
}
