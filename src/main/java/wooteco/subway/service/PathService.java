package wooteco.subway.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.subway.dao.LineDao;
import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.Distance;
import wooteco.subway.domain.Fare;
import wooteco.subway.domain.Line;
import wooteco.subway.domain.Station;
import wooteco.subway.domain.SubwayMap;
import wooteco.subway.dto.path.PathRequest;
import wooteco.subway.dto.path.PathResponse;
import wooteco.subway.exception.station.NoSuchStationException;

@Service
@Transactional(readOnly = true)
public class PathService {

    private final LineDao lineDao;
    private final StationDao stationDao;

    public PathService(final LineDao lineDao, final StationDao stationDao) {
        this.lineDao = lineDao;
        this.stationDao = stationDao;
    }

    public PathResponse find(PathRequest pathRequest) {
        final SubwayMap subwayMap = toSubwayMap();
        final Station sourceStation = findStationById(pathRequest.getSourceStationId());
        final Station targetStation = findStationById(pathRequest.getTargetStationId());

        final List<Station> stations = subwayMap.searchPath(sourceStation, targetStation);
        final Distance distance = subwayMap.searchDistance(sourceStation, targetStation);
        final int extraFare = subwayMap.searchExtraFareOfPath(sourceStation, targetStation);
        final Fare fare = Fare.from(distance, extraFare, pathRequest.getAge());

        return PathResponse.of(stations, distance, fare);
    }

    private SubwayMap toSubwayMap() {
        final List<Line> lines = lineDao.findAll();
        return new SubwayMap(lines);
    }

    private Station findStationById(final Long stationId) {
        return stationDao.findById(stationId)
                .orElseThrow(NoSuchStationException::new);
    }
}
