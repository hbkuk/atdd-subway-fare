package nextstep.core.subway.path.application;

import nextstep.core.auth.domain.LoginUser;
import nextstep.core.auth.domain.UserDetail;
import nextstep.core.auth.domain.constants.AgeGroup;
import nextstep.core.member.application.MemberService;
import nextstep.core.subway.fare.application.FareCalculator;
import nextstep.core.subway.line.application.LineService;
import nextstep.core.subway.path.application.dto.PathResponse;
import nextstep.core.subway.path.application.dto.PathRequest;
import nextstep.core.subway.path.domain.PathFinderResult;
import nextstep.core.subway.path.domain.PathType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PathService {

    private final LineService lineService;

    private final PathFinder pathFinder;

    private final MemberService memberService;

    private final FareCalculator fareCalculator;

    public PathService(LineService lineService, PathFinder pathFinder, MemberService memberService, FareCalculator fareCalculator) {
        this.lineService = lineService;
        this.pathFinder = pathFinder;
        this.memberService = memberService;
        this.fareCalculator = fareCalculator;
    }

    public PathResponse findOptimalPath(PathRequest pathRequest, UserDetail user) {
        validatePathRequest(pathRequest);

        PathFinderResult pathFinderResult = pathFinder.findOptimalPath(
                lineService.findAllLines(),
                lineService.findStation(pathRequest.getDepartureStationId()),
                lineService.findStation(pathRequest.getArrivalStationId()),
                PathType.findType(pathRequest.getPathFinderType()));

        return new PathResponse(pathFinderResult, calculateTotalFare(pathFinderResult, user));
    }

    private int calculateTotalFare(PathFinderResult result, UserDetail user) {
        if (user.isLoggedIn()) {
            return fareCalculator.calculateTotalFare(
                    createFareCalculationContext(result, AgeGroup.findAgeGroup(memberService.findMe((LoginUser) user).getAge())));
        }
        return fareCalculator.calculateTotalFare(createFareCalculationContext(result, AgeGroup.UNKNOWN));
    }

    private FareCalculationContext createFareCalculationContext(PathFinderResult result, AgeGroup ageGroup) {
        return new FareCalculationContext(result.getDistance(), result.getAdditionalFares(), ageGroup);
    }

    public boolean isValidPath(PathRequest pathRequest) {
        validatePathRequest(pathRequest);

        return pathFinder.existPathBetweenStations(
                lineService.findAllLines(),
                lineService.findStation(pathRequest.getDepartureStationId()),
                lineService.findStation(pathRequest.getArrivalStationId()));
    }

    private void validatePathRequest(PathRequest pathRequest) {
        if (areStationsSame(pathRequest)) {
            throw new IllegalArgumentException("출발역과 도착역이 동일할 수 없습니다.");
        }
    }

    private boolean areStationsSame(PathRequest pathRequest) {
        return pathRequest.getDepartureStationId().equals(pathRequest.getArrivalStationId());
    }
}
