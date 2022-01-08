package com.bedir.mywords.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bedir.mywords.web.rest.TestUtil;

public class KartTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kart.class);
        Kart kart1 = new Kart();
        kart1.setId(1L);
        Kart kart2 = new Kart();
        kart2.setId(kart1.getId());
        assertThat(kart1).isEqualTo(kart2);
        kart2.setId(2L);
        assertThat(kart1).isNotEqualTo(kart2);
        kart1.setId(null);
        assertThat(kart1).isNotEqualTo(kart2);
    }
}
