package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalcTest {
    // 최종 목표 Calc.run("((3 + 5) * 5 + -10) * 10 / 5");

    @Test
    @DisplayName("3 + 5")
    public void t1() {
        int rs = Calc.run("3+5");
        assertThat(rs).isEqualTo(8);
    }
    @Test
    @DisplayName("3 + 5")
    public void t2() {
        int rs = Calc.run("3+5");
        assertThat(rs).isEqualTo(8);
    }

}
