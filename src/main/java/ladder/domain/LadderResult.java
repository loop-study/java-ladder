package ladder.domain;

import java.util.HashMap;
import java.util.Map;

public class LadderResult {
    private static final String KEY_ERROR = "참여자를 잘못 입력했습니다.";
    private static final int DEFAULT_INDEX = 0;
    private final Map<Person, Winning> results;

    private LadderResult(Map results) {
        this.results = results;
    }

    public static LadderResult of(Persons person, Winnings winning, Ladder ladder) {
        Map<Person, Winning> result = new HashMap<>();
        int[] lineIndex = {DEFAULT_INDEX};
        person.readOnlyPersons()
                .forEach(index -> result.put(index, ladderResult(lineIndex[DEFAULT_INDEX]++, winning, ladder)));
        
        return new LadderResult(result);
    }

    private static Winning ladderResult(int nameIndex, Winnings winning, Ladder ladder) {
        int[] lineIndex = { nameIndex };
        ladder.readOnlyLines()
                .forEach(line -> lineIndex[DEFAULT_INDEX] = line.lineMoving(lineIndex[DEFAULT_INDEX]));

        return winning.findWinning(lineIndex[DEFAULT_INDEX]);
    }

    public Winning resultOfLadder(String name) {
        Person person1 = results.keySet().stream()
                .filter(person -> person.isName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(KEY_ERROR));

        return results.get(person1);
    }
}
