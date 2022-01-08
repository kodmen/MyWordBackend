package com.bedir.mywords.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bedir.mywords.web.rest.TestUtil;

public class DesteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deste.class);
        Deste deste1 = new Deste();
        deste1.setId(1L);
        Deste deste2 = new Deste();
        deste2.setId(deste1.getId());
        assertThat(deste1).isEqualTo(deste2);
        deste2.setId(2L);
        assertThat(deste1).isNotEqualTo(deste2);
        deste1.setId(null);
        assertThat(deste1).isNotEqualTo(deste2);
    }
}
