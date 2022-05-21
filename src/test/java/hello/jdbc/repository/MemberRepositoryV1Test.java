package hello.jdbc.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import com.zaxxer.hikari.HikariDataSource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// import org.springframework.jdbc.datasource.DriverManagerDataSource;

import hello.jdbc.domain.Member;
import static hello.jdbc.connection.ConnectionConst.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        // 기본 DriverManager - 항상 새로운 커넥션을 획득, conn5까지
        // DriverManagerDataSource dataSource = new DriverManagerDataSource(URL,
        // USERNAME, PASSWORD);

        // 커넥션 풀링: conn0 재사용하며 사용하게 됨
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException, InterruptedException {
        // save
        Member member = new Member("memberV3", 10000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        assertThat(findMember).isEqualTo(member);

        // update
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        // delete
        repository.delete(member.getMemberId());
        Assertions.assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

        Thread.sleep(10000);
    }
}
