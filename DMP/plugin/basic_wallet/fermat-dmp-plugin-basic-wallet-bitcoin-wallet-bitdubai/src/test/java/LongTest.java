import org.junit.Test;

import static org.fest.assertions.api.Assertions.*;

/**
 * Created by jorgegonzalez on 2015.07.09..
 */
public class LongTest {

    @Test
    public void simpleNegativeLongTest(){
        long number = 1L;
        assertThat(algebraicSum(-number)).isEqualTo(3L);
    }

    private long algebraicSum(final long number){
        return 4L + number;
    }
}
