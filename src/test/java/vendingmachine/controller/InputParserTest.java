package vendingmachine.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InputParserTest {

    @Test
    void 상품_정보_파싱() {
        InputParser.parseProducts("[콜라,1000,20];[사이다,2000,10]");
    }

}