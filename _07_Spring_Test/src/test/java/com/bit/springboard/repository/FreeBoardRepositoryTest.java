package com.bit.springboard.repository;

import com.bit.springboard.config.QueryDslConfiguration;
import com.bit.springboard.entity.FreeBoard;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;

@DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//      : @DataJpaTest 어노테이션은 임베디드 데이터베이스(H2)를 사용하도록 설정되는데
//        실제 어플리케이션 사용하는 DataSource를 이용하셔 테스트를 진행하도록 설정
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfiguration.class)
public class FreeBoardRepositoryTest {
    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Test
    void findByIdTest() throws Exception {
        // 기대값을 가지고 있는 FreeBoard 엔티티
        FreeBoard freeBoard = new FreeBoard().builder()
                .id(3L)
                .title("제목3")
                .content("내용")
                .cnt(1)
                .regdate(LocalDateTime.parse("2024-08-23T16:57:25.596453"))
                .moddate(LocalDateTime.parse("2024-08-23T16:57:25.596453"))
                .boardFileList(new ArrayList<>())
                .build();

        // 실제 DB에 저장되어 있는 값을 가지고 있는 FreeBoard 엔티티
        FreeBoard findByIdFreeBoard = freeBoardRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("freeboard not exist"));

        // Assertions 객체: JUnit5에서 제공하는 클래스로 테스트 코드에 대한 다양한 검증(Assertion)
        //                 기능을 제공한다. Assertions 클래스는 테스트의 예상결과와 실제결과를 비교하여,
        //                 테스트가 성공했는지 실패했는지 판단한다.
        // assertEquals(기대값, 실제값): 기대값과 실제값이 동일한지 비교해주는 메소드.
        //                             기대값과 실제값이 동일해야 테스트를 통과시킨다.
        // assertNotEquals(기대값, 실제값): assertEquals(기대밗, 실제값)의 반대
        // assertNotNull(Object, Null일 때 발생시킬 예외메세지):
        //                                      주어진 객체가 Null인지 아닌지 판단해주는 메소드
        Assertions.assertNotNull(findByIdFreeBoard);
        Assertions.assertNotEquals(freeBoard.getTitle(), findByIdFreeBoard.getTitle());
        Assertions.assertEquals(freeBoard.getContent(), findByIdFreeBoard.getContent());
    }
}
