package racingCar;

import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racingCar.domain.game.RacingCarGame;
import racingCar.domain.game.RacingCarGameResult;
import racingCar.exception.NotAllowedGameSettingException;
import racingCar.domain.game.move.RandomMoveAckGenerator;
import racingCar.domain.game.move.MoveAckGenerator;

public class RacingCarGameTest {

  private MoveAckGenerator randomMoveAckGenerator;

  @BeforeEach
  void setup() {
    randomMoveAckGenerator = new RandomMoveAckGenerator();
  }

  @DisplayName("[RacingCarGame.class] 레이싱카 게임은 사용자가 입력한 숫자만큼 레이싱카가 설정된다.")
  public void 사용자_입력만큼_레이싱카_세팅() {

    final List<String> 레이싱카_목록 = List.of("car1");
    // when
    RacingCarGame racingCarGame = new RacingCarGame(레이싱카_목록, randomMoveAckGenerator);

    // then
    Assertions.assertThat(racingCarGame)
        .extracting("racingCars")
        .asList()
        .isEqualTo(레이싱카_목록);
  }

  @DisplayName("[RacingCarGame.class] 레이싱카 게임은 게임 턴마다 자동차 움직임을 기록한다.")
  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4})
  public void 레이싱카_게임은_매_턴_마다_기록을_가지고_있음(int moveTryCnt) {
    // given
    final List<String> 레이싱카_목록 = List.of("car1");
    RacingCarGame racingCarGame = new RacingCarGame(레이싱카_목록, randomMoveAckGenerator);

    // when
    RacingCarGameResult gameResult = racingCarGame.play(moveTryCnt);

    // then
    Assertions.assertThat(gameResult.getSnapShots())
        .asList()
        .hasSize(moveTryCnt);
  }

  @DisplayName("[RacingCarGame.class] 레이싱카가 한대도 존재하지 않으면 게임을 시작 할 수 없다.")
  @Test
  public void 레이싱카_게임은_최소_한대의_자동차가_요구된다() {
    // given
    final List<String> 레이싱카_목록 = Collections.emptyList();

    // when && then
    Assertions.assertThatThrownBy(() -> new RacingCarGame(레이싱카_목록, randomMoveAckGenerator))
        .isInstanceOf(NotAllowedGameSettingException.class);
  }
}
